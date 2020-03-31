package ru.mousecray.endmagic.client.render.rune

import java.util.function

import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ChunkProviderClient
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{EnumFacing, ResourceLocation}
import net.minecraft.world.chunk.Chunk
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.GL_QUADS
import ru.mousecray.endmagic.EM
import ru.mousecray.endmagic.capability.chunk.{RunePart, RuneState, RuneStateCapabilityProvider}
import ru.mousecray.endmagic.util.Vec2i

import scala.language.implicitConversions

class RuneTopLayerRenderer {
  private val chunkMapping = {
    val field = classOf[ChunkProviderClient].getDeclaredField("chunkMapping")
    field.setAccessible(true)
    field
  }

  private def getLoadedChunks =
    chunkMapping.get(Minecraft.getMinecraft.world.getChunkProvider).asInstanceOf[Long2ObjectMap[Chunk]].values().iterator()

  @SubscribeEvent
  def onWorldRender(e: RenderWorldLastEvent): Unit = {
    val p = Minecraft.getMinecraft.player
    import net.minecraft.client.renderer.GlStateManager
    val doubleX = p.lastTickPosX + (p.posX - p.lastTickPosX) * e.getPartialTicks
    val doubleY = p.lastTickPosY + (p.posY - p.lastTickPosY) * e.getPartialTicks
    val doubleZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * e.getPartialTicks

    GlStateManager.pushMatrix()
    GlStateManager.translate(-doubleX, -doubleY, -doubleZ)
    getLoadedChunks.forEachRemaining { c: Chunk =>
      c.getCapability(RuneStateCapabilityProvider.runeStateCapability, null).states
        .forEach(renderRuneTopLayer _)
    }
    GL11.glPopMatrix()
  }

  @SubscribeEvent
  def onClientTick(e: TickEvent.ClientTickEvent): Unit = {
      currentFrame = if (currentFrame >= frameCount) 0 else currentFrame + 1
  }

  val resourceLocation = new ResourceLocation(EM.ID, "textures/blocks/rune.png")
  private var currentFrame = 0
  private val frameCount = 8

  def renderRuneTopLayer(pos: BlockPos, runeState: RuneState): Unit = {
    GL11.glPushMatrix()
    GL11.glTranslated(pos.getX, pos.getY + 1, pos.getZ)
    GL11.glColor4d(1, 0, 0, 1)

    Minecraft.getMinecraft.getTextureManager.bindTexture(resourceLocation)

    val tessellator = Tessellator.getInstance()
    val buffer = tessellator.getBuffer

    buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX)

    val v = currentFrame.toFloat / frameCount

    buffer.pos(0, 0, 0).tex(0, v).endVertex()
    buffer.pos(1, 0, 0).tex(0, v + 1f / frameCount).endVertex()
    buffer.pos(1, 1, 0).tex(1, v + 1f / frameCount).endVertex()
    buffer.pos(0, 1, 0).tex(1, v).endVertex()


    tessellator.draw()

    EnumFacing.values().foreach { ef =>
      runeState.getRuneAtSide(ef).parts.forEach { (coord: Vec2i, part: RunePart) =>


      }

    }

    GL11.glPopMatrix()

  }


  implicit def consumer2Java[A](f: A => Unit): function.Consumer[A] = new function.Consumer[A] {
    override def accept(t: A): Unit = f(t)
  }

  implicit def biConsumer2Java[A, B](f: (A, B) => Unit): function.BiConsumer[A, B] = new function.BiConsumer[A, B] {
    override def accept(t: A, u: B): Unit = f(t, u)
  }

}
