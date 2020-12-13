package ru.mousecray.endmagic.client.render.rune

import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ChunkProviderClient
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
  private val chunkMapping = {
    val field = classOf[ChunkProviderClient].getDeclaredField("chunkMapping")
    field.setAccessible(true)
    field
  }

  private val mc: Minecraft = Minecraft.getMinecraft

  private def getLoadedChunks =
    chunkMapping.get(mc.world.getChunkProvider).asInstanceOf[Long2ObjectMap[Chunk]].values().iterator()

  @SubscribeEvent
  def onWorldRender(e: RenderWorldLastEvent): Unit = {
    val p = mc.player
    import net.minecraft.client.renderer.GlStateManager
    val doubleX = p.lastTickPosX + (p.posX - p.lastTickPosX) * e.getPartialTicks
    val doubleY = p.lastTickPosY + (p.posY - p.lastTickPosY) * e.getPartialTicks
    val doubleZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * e.getPartialTicks

    GlStateManager.pushMatrix()
    GlStateManager.translate(-doubleX, -doubleY, -doubleZ)
    GlStateManager.alphaFunc(516, 0F)
    GlStateManager.enableBlend()

    getLoadedChunks.forEachRemaining {
      c: Chunk =>
        RuneIndex.getCapability(c).existingRunes()
          .asScala
          .filterKeys(!mc.world.isAirBlock(_))
          .foreach(renderRuneTopLayer)
    }

    GlStateManager.disableBlend()
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
      GlStateManager.color(1, 1, 1, 0.5f)

      val blockState = mc.world.getBlockState(pos)
      val model = mc.getBlockRendererDispatcher.getModelForState(blockState)

      val tessellator = Tessellator.getInstance
      val bufferbuilder = tessellator.getBuffer
      bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM)

      EnumFacing.values().foreach { ef =>
        val rune = runeState.getRuneAtSide(ef)
        rune.parts.foreach { case (coord: Vec2i, part: RunePart) =>
          val (x, y) = (coord.x, coord.y)
          val color = part.color()

          model.getQuads(blockState, ef, 0).asScala.headOption.foreach(quad =>
            net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(
              bufferbuilder,
              LazyUnpackedQuad(quad)
                .trivialSliceRect(
                  x.toFloat / 16, y.toFloat / 16,
                  (x + 1).toFloat / 16, (y + 1).toFloat / 16
                )
                .updated(atlas = color.atlasSpriteRune())
                .toBakedQuad,
              getColorForRune(rune, color)))
        }
      }

      tessellator.draw()

      GlStateManager.popMatrix()

  }

  private def getColorForRune(rune: Rune, color: RuneColor) = {
    if (rune.splashAnimation >= 0) {
      val b = (rune.splashAnimation.toFloat + Minecraft.getMinecraft.getRenderPartialTicks) / Rune.splashAnimationMax
      println(b)
      val ib = (100 * b).toInt
      val a = new RGBA(color.r, color.g, color.b, 128 + ib)
      //println(a)
      a.argb()
    }
    else
      new RGBA(color.r, color.g, color.b, 128).argb()
  }
}
