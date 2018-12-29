package ru.mousecray.endmagic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.mousecray.endmagic.blocks.item.IMetaBlockName;
import ru.mousecray.endmagic.blocks.item.TPItemBlock;

public class ListBlock {
	
	public static Block 
	TP = new TeleportBlock(),
	TPH = new TeleportBlockHeight(),
	GEN = new Generator(),
	GEN_N = new GeneratorNewH(),
	ROUGH_ENDERITE = new Default(Material.IRON, "rough_enderite", "pickaxe", 1),
	TECH_ENDERITE = new TechnicalEnderite(),
	ENDERITE_DOOR = new EnderiteDoor(),
	ENDER_CROPS = new EnderCrops(),
	ENDER_PLANKS = new  EnderPlanks(),
	ENDER_LOGS = new EnderLogs(),
	ENDER_LEAVES = new EnderLeaves(),
	ENDER_SAPLING = new EnderSapling(),
	TRAVERTINE = new Travertine(),
	ETERNAL_STONE = new EternalStone(),
	ALTAR = new Altar(),
	ARTIFACTOR = new Artifactor();
	
	public static EnderGrass ENDER_GRASS = new EnderGrass();
		
	public static void onRegister() {
		register(TP, TPH, GEN, GEN_N, ROUGH_ENDERITE, ENDERITE_DOOR, ENDER_CROPS, ENDER_GRASS, ALTAR, ARTIFACTOR);	
		registerTP(TECH_ENDERITE, ENDER_PLANKS, ENDER_LOGS, ENDER_LEAVES, ENDER_SAPLING, TRAVERTINE, ETERNAL_STONE);
	}
	
	public static void onRender() {
		registerRender(TP, TPH, GEN, GEN_N, ROUGH_ENDERITE, /*ENDER_GRASS,*/ ALTAR, ARTIFACTOR);
		registerRenderTP(TECH_ENDERITE, ENDER_PLANKS, ENDER_LOGS, ENDER_LEAVES, ENDER_SAPLING, TRAVERTINE, 
						ETERNAL_STONE);
		
	}
	
	private static void register(Block... blocks) {
        ForgeRegistries.BLOCKS.registerAll(blocks);
        for(Block block : blocks) {
        	ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
	}
	private static void registerTP(Block... blocks) {
        ForgeRegistries.BLOCKS.registerAll(blocks);
        for(Block block : blocks) {
        	ForgeRegistries.ITEMS.register(new TPItemBlock(block).setRegistryName(block.getRegistryName()));        	
        }
	}
		
	private static void registerRender(Block... blocks) {
		for(Block block : blocks) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));			
		}
	}
	
	private static void registerRenderTP(Block... blocks) {
		for(Block block : blocks) {
			Item item = Item.getItemFromBlock(block);
			TPItemBlock itemBlock = (TPItemBlock)item;
			int metaCount = ((IMetaBlockName)block).getMetaCount();
			for(int meta = 0; meta < metaCount; meta++) {
				ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(itemBlock.getRegistryName(meta), "inventory"));
			}	
		}
	}
}