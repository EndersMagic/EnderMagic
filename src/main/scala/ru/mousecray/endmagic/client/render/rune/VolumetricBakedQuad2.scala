package ru.mousecray.endmagic.client.render.rune

import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.EnumFacing
import net.minecraftforge.client.model.pipeline.IVertexConsumer
import ru.mousecray.endmagic.client.render.rune.VolumetricBakedQuad.atlasSpriteRune
import ru.mousecray.endmagic.util.render.endothermic.format.UnpackEvaluations

class VolumetricBakedQuad2(allEdges: Map[EnumFacing, BakedQuad]) extends BakedQuad(
  UnpackEvaluations.defaultVertexData(allEdges.values.headOption.map(_.getFormat).getOrElse(DefaultVertexFormats.BLOCK)),
  0,
  null,
  atlasSpriteRune,
  allEdges.values.headOption.exists(_.shouldApplyDiffuseLighting()),
  allEdges.values.headOption.map(_.getFormat).getOrElse(DefaultVertexFormats.BLOCK)
) {
  override def pipe(consumer: IVertexConsumer): Unit = {
    allEdges.foreach { case (_, q) => q.pipe(consumer) }
  }

}
