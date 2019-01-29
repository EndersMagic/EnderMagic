package ru.mousecray.endmagic.runes

import net.minecraft.entity.Entity
import net.minecraftforge.registries.IForgeRegistryEntry
import ru.mousecray.endmagic.teleport.Location

abstract class RuneEffect extends IForgeRegistryEntry.Impl[RuneEffect]{
  def neighborChanged(location: Location): Unit = ()

  def onDestroyed(location: Location): Unit = ()

  def onEntityWalk(location: Location, entity: Entity): Unit = ()
}
