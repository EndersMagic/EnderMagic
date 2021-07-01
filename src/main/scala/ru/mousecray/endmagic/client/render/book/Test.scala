package ru.mousecray.endmagic.client.render.book

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.{GlStateManager, Tessellator}
import net.minecraft.client.shader.Framebuffer
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.GL_TEXTURE_2D
import ru.mousecray.endmagic.client.render.book.PageRenderer._

object Test {

  lazy val mc = Minecraft.getMinecraft
  val width = 6 * 2 + 2 + 2
  val height = 16
  private lazy val fbo = new Framebuffer(width, height, false)

  def test(): Unit = {

    createTexture()
    drawFBOContent()

  }

  private def createTexture(): Unit = {
    GlStateManager.disableLighting()
    GlStateManager.enableAlpha()
    GlStateManager.enableBlend()
    GlStateManager.disableDepth()
    fbo.framebufferClear()
    fbo.bindFramebuffer(true)

    saveMatrices()

    identityMatrices()

    setupProjectionArea()

    GlStateManager.disableTexture2D()

    val buffer = Tessellator.getInstance.getBuffer
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR)
    buffer.pos(0, 0, 0).color(0, 255, 255, 128).endVertex()
    buffer.pos(0, height, 0).color(0, 255, 255, 128).endVertex()
    buffer.pos(width / 2, height, 0).color(0, 255, 255, 128).endVertex()
    buffer.pos(width / 2, 0, 0).color(0, 255, 255, 128).endVertex()
    Tessellator.getInstance.draw()

    GlStateManager.enableTexture2D()
    mc.fontRenderer.drawString("Абв", 0, 0, 0xff00ff)

    restoreMatrices()

    mc.getFramebuffer.bindFramebuffer(true)
  }

  def setupProjectionArea(): Unit = {
    GL11.glOrtho(0, width / 2, height / 2, 0, -10, 10)
  }

  def drawFBOContent(): Unit = {

    GlStateManager.enableTexture2D()

    val current = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D)

    //mc().getTextureManager().bindTexture(new ResourceLocation(EM.ID, "textures/models/book/page2.png"));
    fbo.bindFramebufferTexture()
    val buffer = Tessellator.getInstance.getBuffer
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    buffer.pos(x, y, 0).tex(0, 1).color(255, 255, 255, 255).endVertex()
    buffer.pos(x, y + h, 0).tex(0, 0).color(255, 255, 255, 255).endVertex()
    buffer.pos(x + w, y + h, 0).tex(1, 0).color(255, 255, 255, 255).endVertex()
    buffer.pos(x + w, y, 0).tex(1, 1).color(255, 255, 255, 255).endVertex()
    Tessellator.getInstance.draw()

    fbo.unbindFramebufferTexture()

    //framebuffer.framebufferClear();
    //mc.getFramebuffer().bindFramebuffer(true);
    GL11.glBindTexture(GL_TEXTURE_2D, current)

  }

  val (x, y, w, h) = (0, 0, width * 10, height * 10)

}
