package ru.mousecray.endmagic.client.render.rune

import java.util

import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.{BakedQuad, IBakedModel}
import net.minecraft.util.EnumFacing
import ru.mousecray.endmagic.client.render.model.baked.BakedModelDelegate
import ru.mousecray.endmagic.client.render.rune.DebugModelWrapper._

import scala.collection.JavaConverters._

class DebugModelWrapper(baseModel: IBakedModel) extends BakedModelDelegate(baseModel) {

  override def getQuads(state: IBlockState, side: EnumFacing, rand: Long): util.List[BakedQuad] = {
    randMin = randMin.filter(_ <= rand).orElse(Some(rand))
    randMax = randMax.filter(_ >= rand).orElse(Some(rand))
    //if (state != null)
    //  println("getQuads", side)
    //if (DebugModelWrapper.activeSides(Option(side)))
    super.getQuads(state, side, if (activeSides(None)) rand else 0).asScala.filter(q => activeSides.apply(Option(q.getFace))).asJava
    //else
    //ImmutableList.of()
  }

}

object DebugModelWrapper {
  var activeSides: Map[Option[EnumFacing], Boolean] = (EnumFacing.values().toSet.map(Option[EnumFacing]) + None).map(_ -> true).toMap

  var randMin: Option[Long] = None
  var randMax: Option[Long] = None
}
