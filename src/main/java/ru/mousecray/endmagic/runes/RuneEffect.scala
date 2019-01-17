package ru.mousecray.endmagic.runes

import net.minecraft.entity.Entity
import ru.mousecray.endmagic.teleport.Location

abstract class RuneEffect {
  def neighborChanged(location: Location): Unit = ()

  def onDestroyed(location: Location): Unit = ()

  def onEntityWalk(location: Location, entity: Entity): Unit = ()
}
