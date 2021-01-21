package ru.mousecray.endmagic.client.render.rune

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.EnumFacing
import net.minecraftforge.client.model.pipeline.{BlockInfoLense, IVertexConsumer, VertexLighterFlat}
import ru.mousecray.endmagic.capability.chunk.client.TopQuadData
import ru.mousecray.endmagic.client.render.rune.VolumetricBakedQuad.atlasSpriteRune
import ru.mousecray.endmagic.rune.RuneIndex
import ru.mousecray.endmagic.util.render.endothermic.format.UnpackEvaluations

class VolumetricBakedQuad2(side: EnumFacing, allEdges: Map[EnumFacing, (Option[EnumFacing], BakedQuad)]) extends BakedQuad(
  UnpackEvaluations.defaultVertexData(allEdges.values.headOption.map(_._2.getFormat).getOrElse(DefaultVertexFormats.BLOCK)),
  0,
  side,
  atlasSpriteRune,
  allEdges.values.headOption.exists(_._2.shouldApplyDiffuseLighting()),
  allEdges.values.headOption.map(_._2.getFormat).getOrElse(DefaultVertexFormats.BLOCK)
) {
  override def pipe(consumer: IVertexConsumer): Unit = {

    consumer match {
      case consumer: VertexLighterFlat =>

        val blockInfo = BlockInfoLense.get(consumer)
        val pos = blockInfo.getBlockPos
        val capability = RuneIndex.getCapability(Minecraft.getMinecraft.world, pos)

        val maybeState = capability.getRuneState(pos)
        if (maybeState.isPresent)
          maybeState.get().foreachRuneQuadsData(side, { case (data, sourceSide) =>
            if (data.isInstanceOf[TopQuadData])
              QuadDataCache.getQuadFor(data, allEdges(sourceSide)._2).pipe(consumer)
          })
        else
          allEdges.get(side).foreach(_._2.pipe(consumer))
      case _ =>
        allEdges.get(side).foreach(_._2.pipe(consumer))
    }
  }

}
