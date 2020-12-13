package ru.mousecray.endmagic.client.render.rune

import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.EnumFacing
import ru.mousecray.endmagic.client.render.rune.VolumetricBakedQuad.atlasSpriteRune
import ru.mousecray.endmagic.util.render.endothermic.format.UnpackEvaluations

class VolumetricBakedQuad2(allEdges: Map[EnumFacing, BakedQuad]) extends BakedQuad(
  UnpackEvaluations, 0, face, atlasSpriteRune, sides.headOption.exists(_._2.shouldApplyDiffuseLighting()), sides.headOption.map(_._2.getFormat).getOrElse(DefaultVertexFormats.BLOCK)
) {

}
