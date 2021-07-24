package ru.mousecray.endmagic.client.render.book

import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.{GlStateManager, Tessellator}
import org.lwjgl.opengl.GL11
import ru.mousecray.endmagic.client.render.book.EMBookModel._

class PageModel(isLeft: Boolean) {

  val (x, y, z) = (0, -4, 0)
  val (w, h) = (5, 8)

  val (u1, u2): (Double, Double) = if (isLeft) (0, 0.5) else (0.5, 1)
  val (v1, v2) = (0, 1)

  val nz = if (isLeft) 1 else -1

  var rotateAngleY: Float = 0
  var rotationPointX: Float = 0

  def render()(implicit scale: Scale): Unit = {

    GlStateManager.pushMatrix()
    GlStateManager.translate(rotationPointX * scale, 0, 0)

    if (rotateAngleY != 0.0F)
      GlStateManager.rotate(rotateAngleY * (180F / Math.PI.toFloat), 0.0F, 1.0F, 0.0F)

    val buffer = BufferWrapper(Tessellator.getInstance().getBuffer)
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL)

    if (isLeft) {
      buffer.pos(x, y + h, z).tex(u2, v2).color(255, 255, 255, 255).normal(0, 0, nz).endVertex()
      buffer.pos(x + w, y + h, z).tex(u1, v2).color(255, 255, 255, 255).normal(0, 0, nz).endVertex()
      buffer.pos(x + w, y, z).tex(u1, v1).color(255, 255, 255, 255).normal(0, 0, nz).endVertex()
      buffer.pos(x, y, z).tex(u2, v1).color(255, 255, 255, 255).normal(0, 0, nz).endVertex()

    } else {
      buffer.pos(x, y, z).tex(u1, v1).color(255, 255, 255, 255).normal(0, 0, nz).endVertex()
      buffer.pos(x + w, y, z).tex(u2, v1).color(255, 255, 255, 255).normal(0, 0, nz).endVertex()
      buffer.pos(x + w, y + h, z).tex(u2, v2).color(255, 255, 255, 255).normal(0, 0, nz).endVertex()
      buffer.pos(x, y + h, z).tex(u1, v2).color(255, 255, 255, 255).normal(0, 0, nz).endVertex()
    }

    Tessellator.getInstance().draw()

    GlStateManager.popMatrix()

  }

}
