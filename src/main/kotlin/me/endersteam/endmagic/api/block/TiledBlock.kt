package me.endersteam.endmagic.api.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.world.IBlockReader

abstract class TiledBlock<T : TileEntity>(properties: Properties, private val tileType : TileEntityType<out T>? = null) : Block(properties)
{
    override fun hasTileEntity(state: BlockState): Boolean = true

    override fun createTileEntity(state: BlockState, world: IBlockReader): TileEntity? = tileType?.create()
}