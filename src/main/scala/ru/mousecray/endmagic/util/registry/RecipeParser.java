package ru.mousecray.endmagic.util.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import scala.collection.immutable.StringOps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class RecipeParser {
    public static List<IRecipe> parse(String fileContent) {
        Map<String, List<String>> sections = parseSections(fileContent);
        Map<Character, ItemStack> id_map = sections.getOrDefault("id_map", ImmutableList.of()).stream()
                .map(i -> split(i, '|'))
                .filter(i -> i.length == 2)
                .peek(System.out::println)
                .collect(toMap(i -> i[0].toCharArray()[0], i -> findItem(i[1])));

        id_map.put('_', ItemStack.EMPTY);

        checkInvalidSymbols(id_map);

        sections.forEach((k, v) -> System.out.println(k + " -> " + v));

        return sections.keySet().stream().filter(i -> !i.equals("id_map")).flatMap(group ->
                sections.get(group).stream()
                        .map(i -> split(i, '|'))
                        .filter(i -> i.length == 2)
                        .map(i -> {
                            ItemStack result = findItem(i[0]);
                            int leftBracket = i[1].indexOf('(');
                            int rightBracket = i[1].indexOf(')');

                            String recipeType = i[1].substring(0, leftBracket);

                            String recipe = i[1].substring(leftBracket + 1, rightBracket);

                            NonNullList<Ingredient> ingredients = recipe.replaceAll(",", "").chars()
                                    .mapToObj(c -> id_map.getOrDefault((char) c, ItemStack.EMPTY))
                                    .map(Ingredient::fromStacks)
                                    .collect(Collectors.toCollection(NonNullList::create));

                            if (recipeType.equals("shaped")) {
                                String[] recipeLines = split(recipe, ',');
                                if (recipeLines.length == 0)
                                    throw new IllegalArgumentException("Invalid recipe " + i[0] + "| " + i[1]);

                                return new ShapedRecipes(group, recipeLines[0].length(), recipeLines.length, ingredients, result)
                                        .setRegistryName(i[0]);
                            } else if (recipeType.equals("shapeless")) {
                                return new ShapelessRecipes(group, result, ingredients)
                                        .setRegistryName(i[0]);
                            } else
                                throw new IllegalArgumentException("Unsupported recipe type " + recipeType);
                        })
        ).collect(toList());
    }

    private static Set<Character> blacklistedSymbolChars = ImmutableSet.copyOf("(){}|:,".chars().mapToObj(c -> (char) c).collect(Collectors.toSet()));

    private static void checkInvalidSymbols(Map<Character, ItemStack> id_map) {
        Set<Character> collect = id_map.keySet().stream().filter(blacklistedSymbolChars::contains).collect(Collectors.toSet());
        if (!collect.isEmpty())
            throw new IllegalArgumentException("Invalid symbols in id_map: " + collect);
    }

    private static ItemStack findItem(String id) {
        String[] domian_name_meta = split(id, ':');
        if (domian_name_meta.length < 2)
            throw new IllegalArgumentException("Invalid item id: " + id);

        String domian = domian_name_meta[0];
        String name = domian_name_meta[1];
        int meta = domian_name_meta.length == 3 ? Integer.parseInt(domian_name_meta[2]) : 0;

        return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(domian, name)), 1, meta);

    }

    private static Map<String, List<String>> parseSections(String fileContent) {
        ImmutableMap.Builder<String, List<String>> r = ImmutableMap.<String, List<String>>builder();

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

                ImmutableList.Builder<String> list = ImmutableList.builder();

                for (int j = i + 1; j < secondBracketLine; j++)
                    list.add(split(removeSpaces(lines[j]), ';'));

                r.put(name, list.build());
                i = secondBracketLine + 1;
            }
        }

        return r.build();
    }

    private static String removeSpaces(String some) {
        return some.replaceAll("\\s+", "");
    }

    public static String[] split(String some, char separator) {
        return new StringOps(some).split(separator);
    }

    public static void main(String[] args) {
        System.out.println("test");

        List<IRecipe> sections = parse("id_map {\n" +
                "  #| minecraft:apple\n" +
                "  B| endmagic:blue_pearl;  G| custommod:customitem\n" +
                "}\n" +
                "recipes {\n" +
                "  endmagic:tallgrass| shaped (###, B#G, ###)\n" +
                "  custommod:customitem| shapeless (##G##G##,#)\n" +
                "}");

        //sections.forEach((k, v) -> System.out.println(k + " -> " + v));
    }

    private static IllegalArgumentException incorrectLine(String fileContent, int i) {
        IllegalArgumentException exception = new IllegalArgumentException("fileContent hash incorrect section at line " + i + "\n" + fileContent);
        exception.setStackTrace(Arrays.copyOfRange(exception.getStackTrace(), 1, exception.getStackTrace().length));
        return exception;
    }
}
