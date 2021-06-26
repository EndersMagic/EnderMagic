package ru.mousecray.endmagic.client.render.book

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.{GlStateManager, Tessellator}
import net.minecraft.client.shader.Framebuffer
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import ru.mousecray.endmagic.EM
import ru.mousecray.endmagic.api.embook.PageContainer

object PageRenderer {

  lazy val mc = Minecraft.getMinecraft

  def drawPageTo(pageContainer: PageContainer, framebuffer: Framebuffer): Unit = {
    GlStateManager.enableTexture2D()
    GlStateManager.disableLighting()
    GlStateManager.enableAlpha()
    GlStateManager.enableBlend()
    GlStateManager.disableDepth()

    framebuffer.bindFramebuffer(true)

    saveMatrices()

    identityMatrices()

    setupProjectionArea()

    drawPageContainerBackRect()
    drawPageContainerContent()

    restoreMatrices()

    mc.getFramebuffer.bindFramebuffer(true)

  }

  def drawPageContainerContent(): Unit = {

    GlStateManager.disableTexture2D()
    val buffer = Tessellator.getInstance.getBuffer
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR)
    buffer.pos(0, 0, 0).color(0, 0, 255, 255).endVertex()
    buffer.pos(0, 50, 0).color(0, 0, 255, 255).endVertex()
    buffer.pos(50, 50, 0).color(0, 0, 255, 255).endVertex()
    buffer.pos(50, 0, 0).color(0, 0, 255, 255).endVertex()
    Tessellator.getInstance.draw()

    GlStateManager.enableTexture2D()
    mc.fontRenderer.drawString("Абв Testlol", 10, 10, 0xff00ff)
  }

  val w = 260
  val h = 208

  val tw = 128
  val th = 64

  val uw = 20
  val vh = 16

  val u0 = 48d
  val v0 = 20d
  val u1 = u0 + uw
  val v1 = v0 + vh

  def drawPageContainerBackRect(): Unit = {
    Minecraft.getMinecraft.getTextureManager.bindTexture(new ResourceLocation(EM.ID, "textures/models/book/em_book.png"))
    val buffer = Tessellator.getInstance.getBuffer
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    buffer.pos(0, 0, 0).tex(u0 / tw, v0 / th).color(255, 255, 255, 255).endVertex()
    buffer.pos(0, h, 0).tex(u0 / tw, v1 / th).color(255, 255, 255, 255).endVertex()
    buffer.pos(w, h, 0).tex(u1 / tw, v1 / th).color(255, 255, 255, 255).endVertex()
    buffer.pos(w, 0, 0).tex(u1 / tw, v0 / th).color(255, 255, 255, 255).endVertex()
    Tessellator.getInstance.draw()
  }

  def setupProjectionArea(): Unit = {
    GL11.glOrtho(0, w, h, 0, -10, 10)
  }

  def identityMatrices(): Unit = {
    GL11.glMatrixMode(GL11.GL_MODELVIEW)
    GL11.glLoadIdentity()
    GL11.glMatrixMode(GL11.GL_PROJECTION)
    GL11.glLoadIdentity()
  }

  def saveMatrices(): Unit = {
    GL11.glMatrixMode(GL11.GL_MODELVIEW)
    GL11.glPushMatrix()
    GL11.glMatrixMode(GL11.GL_PROJECTION)
    GL11.glPushMatrix()
  }

  def restoreMatrices(): Unit = {
    GL11.glMatrixMode(GL11.GL_PROJECTION)
    GL11.glPopMatrix()
    GL11.glMatrixMode(GL11.GL_MODELVIEW)
    GL11.glPopMatrix()
  }


}
