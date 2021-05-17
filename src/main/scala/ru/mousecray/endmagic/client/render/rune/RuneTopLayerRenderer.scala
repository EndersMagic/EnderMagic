package ru.mousecray.endmagic.client.render.rune

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.{GlStateManager, Tessellator}
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.chunk.Chunk
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import org.lwjgl.opengl.GL11
import ru.mousecray.endmagic.capability.chunk.{Rune, RunePart, RuneState}
import ru.mousecray.endmagic.rune.{RuneColor, RuneIndex}
import ru.mousecray.endmagic.util.Java2Scala._
import ru.mousecray.endmagic.util.Vec2i
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA
import ru.mousecray.endmagic.util.render.endothermic.quad.immutable.LazyUnpackedQuad

import scala.collection.JavaConverters._
import scala.language.implicitConversions

class RuneTopLayerRenderer {
  private val mc: Minecraft = Minecraft.getMinecraft

  private def getLoadedChunks = mc.world.getChunkProvider.chunkMapping.values().iterator()

  @SubscribeEvent
  def onWorldRender(e: RenderWorldLastEvent): Unit = {
    val p = mc.player
    import net.minecraft.client.renderer.GlStateManager
    val doubleX = p.lastTickPosX + (p.posX - p.lastTickPosX) * e.getPartialTicks
    val doubleY = p.lastTickPosY + (p.posY - p.lastTickPosY) * e.getPartialTicks
    val doubleZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * e.getPartialTicks

    GlStateManager.pushMatrix()
    GlStateManager.translate(-doubleX, -doubleY, -doubleZ)
    //GlStateManager.alphaFunc(516, 0F)
    GlStateManager.enableBlend()
    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GlStateManager.disableLighting()
    //OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0xF000F0 % 65536f, 0xF000F0 % 65536f)

    getLoadedChunks.forEachRemaining {
      c: Chunk =>
        RuneIndex.getCapability(c).existingRunes()
          .asScala
          .filterKeys(!mc.world.isAirBlock(_))
          .foreach(renderRuneTopLayer)
    }

    GlStateManager.enableLighting()
    //GlStateManager.disableBlend()
    GlStateManager.popMatrix()
  }

  @SubscribeEvent
  def onClientTick(e: TickEvent.ClientTickEvent): Unit = {
    currentFrame = if (currentFrame >= frameCount) 0 else currentFrame + 1
  }

  private var currentFrame = 0
  private val frameCount = 8

  val renderRuneTopLayer: ((BlockPos, RuneState)) => Unit = {
    case (pos, runeState) =>
      GlStateManager.pushMatrix()
      GlStateManager.translate(pos.getX, pos.getY, pos.getZ)
      GlStateManager.color(1, 1, 1, 1)

      val distToPlayer = ((Math.min(10, Math.sqrt(mc.player.getDistanceSqToCenter(pos))) / 10) - 0.1) / 1.8 + 0.5

      val blockState = mc.world.getBlockState(pos)
      val model = mc.getBlockRendererDispatcher.getModelForState(blockState)

      val tessellator = Tessellator.getInstance
      val bufferbuilder = tessellator.getBuffer
      bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK)

      val edges: Map[EnumFacing, (Option[EnumFacing], BakedQuad)] = model match {
        case wrapper: RuneModelWrapper2 => wrapper.getAllEdges(blockState, 0).edges
        case _ => Map()
      }


      runeState.foreachRuneQuadsDataRecess((data, sourceSide) => {
        val quad = QuadDataCache.getQuadFor(data, edges(sourceSide)._2)
        net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(bufferbuilder, quad, 0xffdddddd)
      })

      val renderTopOn = true

      tessellator.draw()
      if (renderTopOn)
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM)

      EnumFacing.values().foreach { ef =>


        val rune = runeState.getRuneAtSide(ef)
        val headOption = edges.get(ef).map(_._2)

        rune.parts.foreach { case (coord: Vec2i, part: RunePart) =>
          val (x, y) = (coord.x, coord.y)
          val color = part.color()

          headOption.foreach(quad => {
            val partQuad = LazyUnpackedQuad(quad)
              .trivialSliceRect(
                x.toFloat / 16, y.toFloat / 16,
                (x + 1).toFloat / 16, (y + 1).toFloat / 16
              )
              .updated(atlas = color.atlasSpriteRune(), applyDiffuseLighting = false)

            if (renderTopOn)
              net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(
                bufferbuilder,
                partQuad
                  //.translate(-ef.getDirectionVec.getX * 1f / 64, -ef.getDirectionVec.getY * 1f / 64, -ef.getDirectionVec.getZ * 1f / 64)
                  .toBakedQuad,
                getColorForRune(rune, color, distToPlayer))
          })
        }
      }

      if (renderTopOn)
        tessellator.draw()

      GlStateManager.popMatrix()

  }

  private def getColorForRune(rune: Rune, color: RuneColor, distToPlayer: Double) = {
    if (rune.splashAnimation >= 0) {
      val b = (rune.splashAnimation.toFloat + Minecraft.getMinecraft.getRenderPartialTicks) / Rune.splashAnimationMax
      val ib = (100 * b).toInt
      val a = new RGBA(color.r, color.g, color.b, 128 + ib)
      a.argb()
    }
    else
      new RGBA(color.r, color.g, color.b, (255 * distToPlayer).toInt).argb()
  }
}
