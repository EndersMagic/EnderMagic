package me.endersteam.endmagic.init

import me.endersteam.endmagic.block.BlastFurnaceBlock
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraftforge.registries.ForgeRegistries

object EmBlocks : AutoRegisterer<Block>(ForgeRegistries.BLOCKS)
{
    val dragonCoal: Block by register("dragon_coal_block", defaultBlock())
    val naturalCoal: Block by register("natural_coal_block", defaultBlock())
    val phantomCoal: Block by register("phantom_coal_block", defaultBlock())
    val immortalCoal: Block by register("immortal_coal_block", defaultBlock())

    val dragonSteel: Block by register("dragon_steel_block", defaultBlock())
    val naturalSteel: Block by register("natural_steel_block", defaultBlock())
    val phantomSteel: Block by register("phantom_steel_block", defaultBlock())
    val immortalSteel: Block by register("immortal_steel_block", defaultBlock())

    val blastFurnace : Block by register("blast_furnace", BlastFurnaceBlock)

    fun defaultBlock() = Block(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(9f))
}