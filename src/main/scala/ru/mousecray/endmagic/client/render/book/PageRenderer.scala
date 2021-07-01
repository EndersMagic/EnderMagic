package ru.mousecray.endmagic.client.render.book

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.{GlStateManager, Tessellator}
import net.minecraft.client.shader.Framebuffer
import net.minecraft.util.ResourceLocation
import org.lwjgl.input.Keyboard
import org.lwjgl.opengl.GL11
import ru.mousecray.endmagic.EM
import ru.mousecray.endmagic.api.embook.{IPage, PageContainer}
import ru.mousecray.endmagic.client.render.book.Refs._

import scala.collection.JavaConverters._

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

    GL11.glMatrixMode(GL11.GL_MODELVIEW)
    drawPageContainerBackRect(pageContainer)
    drawPageContainerContent(pageContainer)

    restoreMatrices()

    mc.getFramebuffer.bindFramebuffer(true)

  }

  def drawPageContainerContent(pageContainer: PageContainer): Unit = {
    drawPageContent(pageContainer.page1, leftPageStartX, backgroundPixelSize)
    drawPageContent(pageContainer.page2, rightPageStartX, backgroundPixelSize)
  }

  def drawPageContent(page: IPage, x: Double, y: Double): Unit = {
    GlStateManager.pushMatrix()

    GlStateManager.translate(x, y, 0)

    page.elements.asScala.foreach(e => {
      GlStateManager.translate(-e.fixPoint.x, -e.fixPoint.y, 0)
      e.render(0 - x.toInt, 0 - y.toInt)
      GlStateManager.translate(e.fixPoint.x, e.fixPoint.y, 0)
    })

    GlStateManager.popMatrix()
  }

  val tw = 128
  val th = 64

  def drawPageContainerBackRect(pageContainer: PageContainer): Unit = {

    GlStateManager.enableTexture2D()

    Minecraft.getMinecraft.getTextureManager.bindTexture(new ResourceLocation(EM.ID, "textures/models/book/page.png"))
    val buffer = Tessellator.getInstance.getBuffer
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    buffer.pos(0, 0, 0).tex(0, 0).color(255, 255, 255, 255).endVertex()
    buffer.pos(0, h, 0).tex(0, 1).color(255, 255, 255, 255).endVertex()
    buffer.pos(w, h, 0).tex(1, 1).color(255, 255, 255, 255).endVertex()
    buffer.pos(w, 0, 0).tex(1, 0).color(255, 255, 255, 255).endVertex()
    Tessellator.getInstance.draw()


    if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
      GlStateManager.disableTexture2D()

      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR)
      buffer.pos(leftPageStartX, backgroundPixelSize, 0).color(0, 255, 255, 128).endVertex()
      buffer.pos(leftPageStartX, backgroundPixelSize + contentHeight, 0).color(0, 255, 255, 128).endVertex()
      buffer.pos(leftPageStartX + contentWidth, backgroundPixelSize + contentHeight, 0).color(0, 255, 255, 128).endVertex()
      buffer.pos(leftPageStartX + contentWidth, backgroundPixelSize, 0).color(0, 255, 255, 128).endVertex()
      Tessellator.getInstance.draw()

      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR)
      buffer.pos(rightPageStartX, backgroundPixelSize, 0).color(0, 255, 255, 128).endVertex()
      buffer.pos(rightPageStartX, backgroundPixelSize + contentHeight, 0).color(0, 255, 255, 128).endVertex()
      buffer.pos(rightPageStartX + contentWidth, backgroundPixelSize + contentHeight, 0).color(0, 255, 255, 128).endVertex()
      buffer.pos(rightPageStartX + contentWidth, backgroundPixelSize, 0).color(0, 255, 255, 128).endVertex()
      Tessellator.getInstance.draw()
    }
  }

  def setupProjectionArea(): Unit = {
    GL11.glOrtho(0, w, h, 0, -300, 300)
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
