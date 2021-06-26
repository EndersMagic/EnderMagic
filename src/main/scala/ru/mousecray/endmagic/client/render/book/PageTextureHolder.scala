package ru.mousecray.endmagic.client.render.book

import net.minecraft.client.shader.Framebuffer
import ru.mousecray.endmagic.api.embook.PageContainer
import ru.mousecray.endmagic.client.render.book.PageRenderer._
import ru.mousecray.endmagic.client.render.book.Refs._

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object PageTextureHolder {

  def getTexture(pageContainer: PageContainer): Framebuffer = {
    textures.getOrElseUpdate(pageContainer, {
      if (freeTextures.isEmpty)
        throw new IllegalStateException("Attempt to use more that 4 framebuffers")
      val fb = freeTextures.remove(freeTextures.size - 1)
      drawPageTo(pageContainer, fb)
      fb
    })
  }

  def freeTexture(pageContainer: PageContainer): Unit = {
    textures.get(pageContainer).foreach {
      fb =>
        textures -= pageContainer
        freeTextures += fb
    }
  }

  private val textures = new mutable.HashMap[PageContainer, Framebuffer]

  private lazy val freeTextures = {
    val r = new ArrayBuffer[Framebuffer]
    for (i <- 1 to 4)
      r += new Framebuffer(pageContainerWidth, pageHeight, false)
    r
  }
}
