package me.endersteam.endmagic.block

import me.endersteam.endmagic.api.block.TiledBlock
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.IBlockReader

class BlastFurnaceBlock(properties: Properties) : TiledBlock(properties)
{
    override fun createTileEntity(state: BlockState, world: IBlockReader): TileEntity
    {
        TODO("Not yet implemented")
    }

    override fun getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL


}