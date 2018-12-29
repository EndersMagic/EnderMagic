package ru.mousecray.endmagic.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.mousecray.endmagic.EndMagicData;
import ru.mousecray.endmagic.blocks.ListBlock;

public class ListItem {
	
	public static final Item.ToolMaterial 
	DRAGON_TOOL_M = EnumHelper.addToolMaterial(EndMagicData.ID + ":dragon_tool_m", 2, 250, 4F, 0F, 15),
	NATURAL_TOOL_M = EnumHelper.addToolMaterial(EndMagicData.ID + ":natural_tool_m", 1, 59, 2F, 0F, 15),
	IMMORTAL_TOOL_M = EnumHelper.addToolMaterial(EndMagicData.ID + ":immortal_tool_m", 3, 1561, 8F, 3F, 15),
	VANISHING_TOOL_M = EnumHelper.addToolMaterial(EndMagicData.ID + ":vanishing_tool_m", 0, 32, 0F, 0F, 15);
	
	public static ItemArmor.ArmorMaterial
	DRAGON_ARMOR_M = EnumHelper.addArmorMaterial(EndMagicData.ID + ":dragon", EndMagicData.ID + ":dragon", 15, new int[]{2, 5, 6, 2}, 15, 
	SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F).setRepairItem(new ItemStack(Item.getItemFromBlock
	(ListBlock.ENDER_LOGS))),
	NATURAL_ARMOR_M = EnumHelper.addArmorMaterial(EndMagicData.ID + ":natural", EndMagicData.ID + ":natural", 9, new int[]{1, 4, 5, 2}, 15, 
	SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F).setRepairItem(new ItemStack(Item.getItemFromBlock
	(ListBlock.ENDER_LOGS), 1, 1)),
	IMMORTAL_ARMOR_M = EnumHelper.addArmorMaterial(EndMagicData.ID + ":immortal", EndMagicData.ID + ":immortal", 33, new int[]{3, 6, 8, 3}, 15, 
	SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F).setRepairItem(new ItemStack(Item.getItemFromBlock
	(ListBlock.ENDER_LOGS), 1, 2)),
	VANISHING_ARMOR_M = EnumHelper.addArmorMaterial(EndMagicData.ID + ":vanishing", EndMagicData.ID + ":vanishing", 5, new int[]{1, 2, 3, 1}, 15, 
	SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F).setRepairItem(new ItemStack(Item.getItemFromBlock
	(ListBlock.ENDER_LOGS), 1, 3));
	
	public static Item 
	PORTAL_ACTIVATOR = new PortalActivator(), 
	PORTAL_ACTIVATORH = new PortalActivatorH(),
	PORTAL_DEACTIVATOR = new PortalDeactivator(), 
	ENDERITE_BRICK = new Default("enderite_brick"),
	ENDERITE_DOOR_ITEM = new EnderiteDoorItem(ListBlock.ENDERITE_DOOR), 
	ENDER_SEEDS = new TPSeeds(ListBlock.ENDER_CROPS, Blocks.END_STONE, "ender_seeds", "tooltip.ender_seeds"),
	ENDER_APPLE = new ItemFood(4, 0.3F, false).setUnlocalizedName("ender_apple").setRegistryName("ender_apple").setCreativeTab(EndMagicData.EM_CREATIVE),
	PURPLE_PEARL = new PurpleEnderPearl(), 
	DRAGON_SWORD = new DragonSword(DRAGON_TOOL_M), 
	NATURAL_SWORD = new NaturalSword(NATURAL_TOOL_M), 
	IMMORTAL_SWORD = new ImmortalSword(IMMORTAL_TOOL_M), 
	VANISHING_SWORD = new VanishingSword(VANISHING_TOOL_M), 
	DRAGON_PICKAXE = new DragonPickaxe(DRAGON_TOOL_M),
	NATURAL_PICKAXE = new NaturalPickaxe(NATURAL_TOOL_M), 
	IMMORTAL_PICKAXE = new ImmortalPickaxe(IMMORTAL_TOOL_M),
	VANISHING_PICKAXE= new VanishingPickaxe(VANISHING_TOOL_M),
    DRAGON_HELMET = new EnderArmor(DRAGON_ARMOR_M, 1, EntityEquipmentSlot.HEAD, "dragon_helmet"),
    NATURAL_HELMET = new EnderArmor(NATURAL_ARMOR_M, 1, EntityEquipmentSlot.HEAD, "natural_helmet"),
    IMMORTAL_HELMET = new EnderArmor(IMMORTAL_ARMOR_M, 1, EntityEquipmentSlot.HEAD, "immortal_helmet"),
    VANISHING_HELMET = new EnderArmor(VANISHING_ARMOR_M, 1, EntityEquipmentSlot.HEAD, "vanishing_helmet"),
	ENDER_ARROW = new EnderArrow(),
	ENDER_COALS = new EnderCoals(),
	EMBOOK = new EMBook();
	
	public static void onRegister() {
		register(PORTAL_ACTIVATOR, PORTAL_ACTIVATORH, PORTAL_DEACTIVATOR, ENDERITE_BRICK, ENDERITE_DOOR_ITEM, 
		ENDER_SEEDS, ENDER_APPLE, PURPLE_PEARL, DRAGON_SWORD, NATURAL_SWORD, IMMORTAL_SWORD, VANISHING_SWORD, 
		DRAGON_PICKAXE, NATURAL_PICKAXE, IMMORTAL_PICKAXE, VANISHING_PICKAXE, DRAGON_HELMET, NATURAL_HELMET, IMMORTAL_HELMET, 
		VANISHING_HELMET, ENDER_ARROW, ENDER_COALS, EMBOOK);
	}
	
	public static void onRender() {
		registerRender(PORTAL_ACTIVATOR, PORTAL_ACTIVATORH, PORTAL_DEACTIVATOR, ENDERITE_BRICK, ENDERITE_DOOR_ITEM, 
		ENDER_SEEDS, ENDER_APPLE, PURPLE_PEARL, DRAGON_SWORD, NATURAL_SWORD, IMMORTAL_SWORD, VANISHING_SWORD, 
		DRAGON_PICKAXE, NATURAL_PICKAXE, IMMORTAL_PICKAXE, VANISHING_PICKAXE, DRAGON_HELMET, NATURAL_HELMET, IMMORTAL_HELMET, 
		VANISHING_HELMET, ENDER_ARROW, EMBOOK);
		registerRenderTP(ENDER_COALS);
	}
	
	private static void register(Item... items) {
		ForgeRegistries.ITEMS.registerAll(items);
	}
	
	private static void registerRender(Item... items) {
		for(Item item : items) {
			ModelLoader.setCustomModelResourceLocation(item, 0, 
			new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
	
	private static void registerRenderTP(Item... items) {
		for(Item item : items) {
			int metaCount = ((DefaultMeta)item).getMetaCount();
			for(int meta = 0; meta < metaCount; meta++) {
				ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(((DefaultMeta)item).getRegistryName(meta), "inventory"));
			}	
		}
	}
}