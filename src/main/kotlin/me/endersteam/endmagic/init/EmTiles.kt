package me.endersteam.endmagic.init

import me.endersteam.endmagic.tile.BlastFurnaceTile
import net.minecraft.block.Block
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.registries.ForgeRegistries
import kotlin.properties.ReadOnlyProperty

object EmTiles : AutoRegisterer<TileEntityType<*>>(ForgeRegistries.TILE_ENTITIES)
{
    val blast : TileEntityType<BlastFurnaceTile> by register("blast", ::BlastFurnaceTile, EmBlocks.blastFurnace)

    fun<T : TileEntity> register(name : String, value : () -> T, vararg blocks : Block) : ReadOnlyProperty<Any?, TileEntityType<T>> = registerer.register(name) { TileEntityType<T>(value, blocks.toSet(), null) }
}