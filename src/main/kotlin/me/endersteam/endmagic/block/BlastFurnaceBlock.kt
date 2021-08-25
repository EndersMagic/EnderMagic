package me.endersteam.endmagic.block

import me.endersteam.endmagic.api.block.TiledBlock
import me.endersteam.endmagic.init.EmBlocks
import me.endersteam.endmagic.init.EmItems
import me.endersteam.endmagic.init.EmTiles
import me.endersteam.endmagic.tile.BlastFurnaceTile
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.IBlockReader

object BlastFurnaceBlock : TiledBlock<BlastFurnaceTile>(Properties.create(Material.ROCK).hardnessAndResistance(5f), EmTiles.blast)
{
    override fun getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL

    val recipes : List<BlastFurnaceRecipe>
    val resultByInput : Map<Pair<Item, Item>, Item>
    val coal: Set<Item>
    val iron: Set<Item>
    val steel: Set<Item>

    init
    {
        val recipes = arrayListOf<BlastFurnaceRecipe>()

        recipes.add(BlastFurnaceRecipe(EmItems.dragonCoal, Items.IRON_INGOT, EmItems.dragonSteel))
        recipes.add(BlastFurnaceRecipe(EmItems.immortalCoal, Items.IRON_INGOT, EmItems.immortalSteel))
        recipes.add(BlastFurnaceRecipe(EmItems.naturalCoal, Items.IRON_INGOT, EmItems.naturalSteel))
        recipes.add(BlastFurnaceRecipe(EmItems.phantomCoal, Items.IRON_INGOT, EmItems.phantomSteel))

        recipes.add(BlastFurnaceRecipe(EmBlocks.dragonCoal, Blocks.IRON_BLOCK, EmBlocks.dragonSteel))
        recipes.add(BlastFurnaceRecipe(EmBlocks.immortalCoal, Blocks.IRON_BLOCK, EmBlocks.immortalSteel))
        recipes.add(BlastFurnaceRecipe(EmBlocks.naturalCoal, Blocks.IRON_BLOCK, EmBlocks.naturalSteel))
        recipes.add(BlastFurnaceRecipe(EmBlocks.phantomCoal, Blocks.IRON_BLOCK, EmBlocks.phantomSteel))

        this.recipes = recipes

        val resultByInput = mutableMapOf<Pair<Item, Item>, Item>()
        val coalSet = mutableSetOf<Item>()
        val ironSet = mutableSetOf<Item>()
        val steelSet = mutableSetOf<Item>()

        for ((coal, iron, steel) in recipes)
        {
            resultByInput[Pair(coal, iron)] = steel
            coalSet.add(coal)
            ironSet.add(iron)
            steelSet.add(steel)
        }

        this.resultByInput = resultByInput
        this.coal = coalSet
        this.iron = ironSet
        this.steel = steelSet
    }

    data class BlastFurnaceRecipe(val coal : Item, val iron : Item, val steel : Item)
    {
        constructor(coal : Block, iron : Block, steel : Block) : this(coal.asItem(), iron.asItem(), steel.asItem())
    }
}