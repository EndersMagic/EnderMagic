package me.endersteam.endmagic.init

import me.endersteam.endmagic.block.BlastFurnaceBlock
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraftforge.registries.ForgeRegistries

object EmBlocks : AutoRegisterer<Block>(ForgeRegistries.BLOCKS)
{
    val dragonCoal: Block by register("dragon_coal_block", defaultCoalBlock())
    val naturalCoal: Block by register("natural_coal_block", defaultCoalBlock())
    val phantomCoal: Block by register("phantom_coal_block", defaultCoalBlock())
    val immortalCoal: Block by register("immortal_coal_block", defaultCoalBlock())

    val dragonSteel: Block by register("dragon_steel_block", defaultSteelBlock())
    val naturalSteel: Block by register("natural_steel_block", defaultSteelBlock())
    val phantomSteel: Block by register("phantom_steel_block", defaultSteelBlock())
    val immortalSteel: Block by register("immortal_steel_block", defaultSteelBlock())

    val blastFurnace : Block by register("blast_furnace", BlastFurnaceBlock)

    fun defaultCoalBlock() = Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(6f))
    fun defaultSteelBlock() = Block(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(9f))
}