package ru.mousecray.endmagic.client.render.rune

import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ChunkProviderClient
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.{GlStateManager, Tessellator}
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{EnumFacing, ResourceLocation}
import net.minecraft.world.chunk.Chunk
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import ru.mousecray.endmagic.EM
import ru.mousecray.endmagic.capability.chunk.{RunePart, RuneState}
import ru.mousecray.endmagic.client.render.rune.VolumetricBakedQuad.atlasSpriteRune
import ru.mousecray.endmagic.rune.RuneIndex
import ru.mousecray.endmagic.util.Java2Scala._
import ru.mousecray.endmagic.util.Vec2i
import ru.mousecray.endmagic.util.render.endothermic.immutable.UnpackedQuad

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

  val resourceLocation = new ResourceLocation(EM.ID, "textures/blocks/rune.png")
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
      bufferbuilder.begin(7, DefaultVertexFormats.ITEM)

      EnumFacing.values().foreach { ef =>
        runeState.getRuneAtSide(ef).parts.foreach { case (coord: Vec2i, part: RunePart) =>
          val (x, y) = (coord.x, coord.y)
          val color = part.color()

          val quad = model.getQuads(blockState, ef, 0).get(0)

          val richQuad = UnpackedQuad(quad)
          val center1 = richQuad
            .trivialSliceRect(
              x.toFloat / 16, y.toFloat / 16,
              (x + 1).toFloat / 16, (y + 1).toFloat / 16
            )

          val centerTop = center1
            .updated(atlas = atlasSpriteRune)
            .reconstruct(
              v1_a = 128,
              v2_a = 128,
              v3_a = 128,
              v4_a = 128,

              v1_r = color.r,
              v2_r = color.r,
              v3_r = color.r,
              v4_r = color.r,

              v1_g = color.g,
              v2_g = color.g,
              v3_g = color.g,
              v4_g = color.g,

              v1_b = color.b,
              v2_b = color.b,
              v3_b = color.b,
              v4_b = color.b
            )

          net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(bufferbuilder, centerTop.toBakedQuad, 0xffffffff)


        }

      }

      tessellator.draw()

      GlStateManager.popMatrix()

  }

}
