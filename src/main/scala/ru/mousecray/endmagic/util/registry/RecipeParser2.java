package ru.mousecray.endmagic.util.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import scala.collection.immutable.StringOps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static net.minecraft.item.ItemStack.EMPTY;

public class RecipeParser2 {
    private static ItemStack apple = new ItemStack(Items.APPLE);
    private static ItemStack stick = new ItemStack(Items.STICK);
    public static Map<String, List<List<ItemStack>>> fill_shapes = ImmutableMap.<String, List<List<ItemStack>>>builder()
            .put("all", makeFillPattern(list("aaa", "aaa", "aaa")))
            .put("helmet", makeFillPattern(list(
                    "aaa",
                    "a_a")))
            .put("chestplate", makeFillPattern(list(
                    "a_a",
                    "aaa",
                    "aaa")))
            .put("leggings", makeFillPattern(list(
                    "aaa",
                    "a_a",
                    "a_a")))
            .put("boots", makeFillPattern(list(
                    "a_a",
                    "a_a")))
            .put("axe", list(list(apple, apple), list(apple, stick), list(EMPTY, stick)))
            .put("hoe", list(list(apple, apple), list(EMPTY, stick), list(EMPTY, stick)))
            .put("pickaxe", list(list(apple, apple, apple), list(EMPTY, stick, EMPTY), list(EMPTY, stick, EMPTY)))
            .put("shovel", list(list(apple), list(stick), list(stick)))
            .put("sword", list(list(apple), list(apple), list(stick)))
            .build();

    private static <A> ImmutableList<A> list(A... values) {
        return ImmutableList.copyOf(values);
    }

    private static List<List<ItemStack>> makeFillPattern(List<String> textPattern) {
        return textPattern.stream().map(line -> line.chars().mapToObj(c -> (char) c).map(c -> c == 'a' ? apple : EMPTY).collect(toList())).collect(toList());
    }

    public static List<IRecipe> parse(String fileContent) {
        ImmutableMap<String, List<Token>> sections = parseSections(fileContent);

        List<Token> id_map1 = sections.getOrDefault("id_map", ImmutableList.of());
        ImmutableMap.Builder<String, ItemStack> id_map_builder = ImmutableMap.builder();
        for (int i = 0; i < id_map1.size(); i++) {
            Token token = id_map1.get(i);
            if (token.textFragment.equals(";")) {
                i++;
                token = id_map1.get(i);
            }

            if (token.type != CharType.letter)
                throw new IllegalArgumentException("Symbol declaration must start with letter\n" + aroundContext(id_map1, i));
            String id = token.textFragment;

            i++;
            token = id_map1.get(i);
            if (!token.textFragment.equals("|"))
                throw new IllegalArgumentException("Symbol declaration separator must be |\n" + aroundContext(id_map1, i));

            i++;
            token = id_map1.get(i);
            if (token.type != CharType.letter)
                throw new IllegalArgumentException("Domain must consist letter\n" + aroundContext(id_map1, i));
            String domain = token.textFragment;

            i++;
            token = id_map1.get(i);
            if (!token.textFragment.equals(":"))
                throw new IllegalArgumentException("ResourceLocation separator must be :\n" + aroundContext(id_map1, i));

            i++;
            token = id_map1.get(i);
            if (token.type != CharType.letter)
                throw new IllegalArgumentException("Path must consist letter\n" + aroundContext(id_map1, i));
            String path = token.textFragment;

            int meta = 0;
            if (i + 1 < id_map1.size()) {
                token = id_map1.get(i + 1);
                if (token.textFragment.equals(":")) {
                    i += 2;
                    token = id_map1.get(i);
                    meta = Integer.parseInt(token.textFragment);
                }
            }
            id_map_builder.put(id, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(domain, path)), 1, meta));
        }
        id_map_builder.put("_", EMPTY);
        ImmutableMap<String, ItemStack> id_map = id_map_builder.build();
        return sections.keySet().stream().filter(i -> !i.equals("id_map")).flatMap(group ->
                {
                    List<IRecipe> r = new ArrayList<>();
                    List<Token> tokens = sections.get(group);
                    for (int i = 0; i < tokens.size(); i++) {
                        Token token = tokens.get(i);
                        int count = 1;
                        if (token.type == CharType.number && tokens.get(i + 1).textFragment.equals("x")) {
                            count = Integer.parseInt(token.textFragment);
                            i += 2;
                            token = tokens.get(i);
                        }

                        if (token.type != CharType.letter)
                            throw new IllegalArgumentException("Domain must consist letter\n" + aroundContext(tokens, i));
                        String domain = token.textFragment;

                        i++;
                        token = tokens.get(i);
                        if (!token.textFragment.equals(":"))
                            throw new IllegalArgumentException("ResourceLocation separator must be :\n" + aroundContext(tokens, i));

                        i++;
                        token = tokens.get(i);
                        if (token.type != CharType.letter)
                            throw new IllegalArgumentException("Path must consist letter\n" + aroundContext(tokens, i));
                        String path = token.textFragment;

                        int meta = 0;
                        if (i + 1 < tokens.size()) {
                            token = tokens.get(i + 1);
                            if (token.textFragment.equals(":")) {
                                i += 2;
                                token = tokens.get(i);
                                meta = Integer.parseInt(token.textFragment);
                            }
                        }

                        ItemStack result = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(domain, path)), count, meta);
                        String recipeRegistryName = result.getItem().getRegistryName().toString() + "@" + result.getItemDamage();

                        i++;
                        token = tokens.get(i);
                        if (!token.textFragment.equals("|"))
                            throw new IllegalArgumentException("Symbol declaration separator must be |\n" + aroundContext(tokens, i));

                        i++;
                        token = tokens.get(i);
                        if (token.type != CharType.letter)
                            throw new IllegalArgumentException("Recipe type must consist letter\n" + aroundContext(tokens, i));
                        String recipeType = token.textFragment;

                        i++;
                        token = tokens.get(i);
                        if (!token.textFragment.equals("("))
                            throw new IllegalArgumentException("Grid bracket must be (\n" + aroundContext(tokens, i));

                        ImmutableList.Builder<Token> recipeBuilder = ImmutableList.builder();
                        for (i++; i < tokens.size() && !tokens.get(i).textFragment.equals(")"); i++)
                            recipeBuilder.add(tokens.get(i));
                        ImmutableList<Token> recipe = recipeBuilder.build();

                        if ("(){}|:".chars().anyMatch(c -> recipe.stream().anyMatch(j -> j.textFragment.contains("" + c))))
                            throw new IllegalArgumentException("Invalid symbols in recipe: " + recipe);

                        NonNullList<Ingredient> ingredients = parseGrid(recipe, recipeType, id_map);

                        if (ingredients.stream().allMatch(j -> j.apply(EMPTY)))
                            throw new IllegalArgumentException("Invalid recipe, all ingredients are empty: " + recipe.stream().map(j -> j.textFragment).collect(Collectors.joining()));

                        switch (recipeType) {
                            case "shaped":
                                ImmutableList.Builder<List<Token>> recipeLinesBuilder = ImmutableList.builder();
                                ImmutableList.Builder<Token> lineBuilder = ImmutableList.builder();
                                for (int j = 0; j < recipe.size(); j++) {
                                    Token t = recipe.get(j);
                                    if (t.textFragment.equals(";")) {
                                        recipeLinesBuilder.add(lineBuilder.build());
                                        lineBuilder = ImmutableList.builder();
                                    } else if (!t.textFragment.equals(","))
                                        lineBuilder.add(t);
                                }
                                recipeLinesBuilder.add(lineBuilder.build());

                                ImmutableList<List<Token>> recipeLines = recipeLinesBuilder.build();
                                if (recipeLines.size() == 0 || recipeLines.stream().map(List::size).anyMatch(j -> j != recipeLines.get(0).size()))
                                    throw new IllegalArgumentException("Invalid recipe " + recipe);

                                r.add(new ShapedRecipes(group, recipeLines.get(0).size(), recipeLines.size(), ingredients, result)
                                        .setRegistryName(recipeRegistryName));
                                break;
                            case "shapeless":
                                r.add(new ShapelessRecipes(group, result, ingredients)
                                        .setRegistryName(recipeRegistryName));
                                break;
                            default:
                                if (fill_shapes.containsKey(recipeType)) {
                                    List<List<ItemStack>> pattern = fill_shapes.get(recipeType);
                                    NonNullList<Ingredient> filled = pattern.stream().flatMap(line -> line.stream().map(is -> {
                                        if (is == apple)
                                            return Ingredient.fromStacks(ingredients.get(0).getMatchingStacks());
                                        else if (is.isEmpty())
                                            return Ingredient.EMPTY;
                                        else
                                            return Ingredient.fromStacks(is);
                                    })).collect(Collectors.toCollection(NonNullList::create));
                                    int w = pattern.get(0).size();
                                    int h = pattern.size();
                                    r.add(new ShapedRecipes(group, w, h, filled, result)
                                            .setRegistryName(recipeRegistryName));
                                } else
                                    throw new IllegalArgumentException("Unsupported recipe type " + recipeType);
                        }
                    }
                    return r.stream();
                }
        ).collect(toList());
    }

    private static String aroundContext(List<Token> tokens, int i) {
        String r = "";
        for (int j = Math.max(0, i - 4); j < Math.min(tokens.size(), i + 4); j++)
            r += tokens.get(j).textFragment + " ";
        return r;
    }

    public static String[] split(String some, char separator) {
        return new StringOps(some).split(separator);
    }

    private static NonNullList<Ingredient> parseGrid(ImmutableList<Token> recipe, String recipeType, Map<String, ItemStack> id_map) {
        return (
                recipeType.equals("all") ?
                        Stream.generate(() -> Ingredient.fromStacks(id_map.getOrDefault(recipe.get(0).textFragment, EMPTY))).limit(9)
                        :
                        recipe.stream().filter(t -> !t.textFragment.equals(",") && !t.textFragment.equals(";")).map(c -> id_map.getOrDefault(c.textFragment, EMPTY))
                                .map(Ingredient::fromStacks)
        )
                .collect(Collectors.toCollection(NonNullList::create));
    }

    private static ImmutableMap<String, List<Token>> parseSections(String fileContent) {
        ImmutableMap.Builder<String, List<Token>> r = ImmutableMap.builder();

        String[] lines = fileContent.split("\\r?\\n");

        int i = 0;
        while (i < lines.length) {
            String line = lines[i];
            if (line.isEmpty())
                i++;
            else {
                int firstBracket = line.indexOf("{");

                if (firstBracket == -1)
                    throw incorrectLine(fileContent, i);

                String name = removeSpaces(line.substring(0, firstBracket));

                int secondBracketLine = i + 1;
                while (secondBracketLine < lines.length && !lines[secondBracketLine].contains("}"))
                    secondBracketLine++;

                if (!lines[secondBracketLine].contains("}"))
                    throw incorrectLine(fileContent, i);

                StringBuilder list = new StringBuilder();

                for (int j = i + 1; j < secondBracketLine; j++)
                    list.append(lines[j] + "\n");

                r.put(name, parseTokens(list.toString()));
                i = secondBracketLine + 1;
            }
        }

        return r.build();
    }

    private static String removeSpaces(String some) {
        return some.replaceAll("\\s+", "");
    }

    private static IllegalArgumentException incorrectLine(String fileContent, int i) {
        IllegalArgumentException exception = new IllegalArgumentException("fileContent hash incorrect section at line " + i + "\n" + fileContent);
        exception.setStackTrace(Arrays.copyOfRange(exception.getStackTrace(), 1, exception.getStackTrace().length));
        return exception;
    }

    private static List<Token> parseTokens(String fileContent) {
        ImmutableList.Builder<Token> r = ImmutableList.builder();
        String acc = "";
        CharType prevType = CharType.white;
        for (int i = 0; i < fileContent.length(); i++) {
            char c = fileContent.charAt(i);
            CharType currentType = typeOFChar(c);
            if (prevType != currentType) {
                if (prevType != CharType.white)
                    r.add(new Token(acc, prevType));
                acc = "";
            }
            prevType = currentType;
            acc += c;
        }
        return r.build();
    }

    private static CharType typeOFChar(char c) {
        if (c >= 'A' && c <= 'z' || c >= 'А' && c <= 'я')
            return CharType.letter;
        else if (c >= '0' && c <= '9')
            return CharType.number;
        else if (c == ' ' || c == '\t' || c == '\n')
            return CharType.white;
        else
            return CharType.other;
    }

    private enum CharType {
        letter, number, white, other
    }


    private static class Token {
        public final String textFragment;
        public final CharType type;

        private Token(String textFragment, CharType type) {
            this.textFragment = textFragment;
            this.type = type;
        }

        @Override
        public String toString() {
            return "Token(" + textFragment + ")";
        }
    }
}
