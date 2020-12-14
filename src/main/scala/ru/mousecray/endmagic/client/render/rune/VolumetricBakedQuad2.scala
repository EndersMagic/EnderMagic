package ru.mousecray.endmagic.client.render.rune

import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.EnumFacing
import net.minecraftforge.client.model.pipeline.IVertexConsumer
import ru.mousecray.endmagic.client.render.rune.VolumetricBakedQuad.atlasSpriteRune
import ru.mousecray.endmagic.util.render.endothermic.format.UnpackEvaluations
import ru.mousecray.endmagic.util.render.endothermic.quad.immutable.LazyUnpackedQuad

class VolumetricBakedQuad2(side: EnumFacing, allEdges: Map[EnumFacing, (Option[EnumFacing], BakedQuad)]) extends BakedQuad(
  UnpackEvaluations.defaultVertexData(allEdges.values.headOption.map(_._2.getFormat).getOrElse(DefaultVertexFormats.BLOCK)),
  0,
  null,
  atlasSpriteRune,
  allEdges.values.headOption.exists(_._2.shouldApplyDiffuseLighting()),
  allEdges.values.headOption.map(_._2.getFormat).getOrElse(DefaultVertexFormats.BLOCK)
) {
  override def pipe(consumer: IVertexConsumer): Unit = {
    val quad = LazyUnpackedQuad(allEdges(side)._2)
    println(quad.v1_r)
    val q2 = quad.reconstruct(
      v1_r = 0,
      v1_g = 0,
      v1_b = 0,
      v1_a = 0
    )
    println(q2.v1_r)
    q2.toBakedQuad.pipe(consumer)
  }

}
