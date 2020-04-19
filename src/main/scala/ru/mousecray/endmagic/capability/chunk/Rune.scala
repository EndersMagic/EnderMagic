package ru.mousecray.endmagic.capability.chunk

import ru.mousecray.endmagic.capability.chunk.RuneEffect.EmptyEffect
import ru.mousecray.endmagic.capability.chunk.client._
import ru.mousecray.endmagic.rune.RuneEffectRegistry
import ru.mousecray.endmagic.util.Vec2i

case class Rune(parts: Map[Vec2i, RunePart], runeEffect: RuneEffect = EmptyEffect, averageCreatingTime: Long = Long.MaxValue, startingTime: Long = -1,
                topQuadData: TopQuadsMatrix = TopQuadsMatrix.SingleArea,
                quadsData: Set[QuadData] = Set()
               ) {

  def incrementTopQuadsData(topQuadData: TopQuadsMatrix, vec2i: Vec2i, part: RunePart): TopQuadsMatrix =
    topQuadData.replace(vec2i, topQuadData.get(vec2i).splitBy(vec2i))

  def incrementQuadsData(quadsData: Set[QuadData], vec2i: Vec2i, part: RunePart, newParts: Map[Vec2i, RunePart]): Set[QuadData] = {
    val (x, y) = (vec2i.x, vec2i.y)
    val bottom = BottomQuadData(x, y)
    val (unnecessary, necessary) = Seq(
      (new Vec2i(x - 1, y), LeftSideQuadData(x, y), RightSideQuadData(x - 1, y)),
      (new Vec2i(x + 1, y), RightSideQuadData(x, y), LeftSideQuadData(x + 1, y)),
      (new Vec2i(x, y - 1), DownSideQuadData(x, y), UpSideQuadData(x, y - 1)),
      (new Vec2i(x, y + 1), UpSideQuadData(x, y), DownSideQuadData(x, y + 1))
    ).partition(i => newParts.contains(i._1))
    quadsData -- unnecessary.map(_._3) ++ necessary.map(_._2) + bottom
  }

  def add(coord: Vec2i, runePart: RunePart, currentTimeMillis: Long): Rune = {
    if (parts.contains(coord))
      this
    else {
      val newParts = parts + (coord -> runePart)
      val newEffect = RuneEffectRegistry.findEffect(newParts)

      if (newParts.size == 1)
        copy(newParts, newEffect, Long.MaxValue, currentTimeMillis, topQuadData = incrementTopQuadsData(topQuadData, coord, runePart), quadsData = incrementQuadsData(quadsData, coord, runePart, newParts))
      else if (runeEffect != EmptyEffect)
        copy(newParts, newEffect, (currentTimeMillis - startingTime) / parts.size, topQuadData = incrementTopQuadsData(topQuadData, coord, runePart), quadsData = incrementQuadsData(quadsData, coord, runePart, newParts))
      else
        copy(newParts, newEffect, topQuadData = incrementTopQuadsData(topQuadData, coord, runePart), quadsData = incrementQuadsData(quadsData, coord, runePart, newParts))
    }
  }

}

object Rune {
  val empty = new Rune(Map())

}
