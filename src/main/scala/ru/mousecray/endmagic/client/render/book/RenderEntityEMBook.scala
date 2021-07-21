package ru.mousecray.endmagic.client.render.book

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.entity.{Render, RenderManager}
import net.minecraft.util.ResourceLocation
import ru.mousecray.endmagic.EM
import ru.mousecray.endmagic.entity.EntityEMBook
import ru.mousecray.endmagic.util.render.ClientTickHandler

class RenderEntityEMBook(renderManagerIn: RenderManager) extends Render[EntityEMBook](renderManagerIn) {
  val cover = new ResourceLocation(EM.ID, "textures/models/book/em_book2.png")
  val pages = new ResourceLocation(EM.ID, "textures/models/book/page.png")

  override def getEntityTexture(entity: EntityEMBook): ResourceLocation = cover

  val model = new EMBookModel

  override def doRender(entity: EntityEMBook, x: Double, y: Double, z: Double, entityYaw: Float, partialTicks: Float): Unit = {
    GlStateManager.pushMatrix()
    GlStateManager.translate(x, y + 0.5, z)

    val openProgress = 1//(Math.sin(ClientTickHandler.fullTicks() / 100).toFloat + 1) / 2

    Minecraft.getMinecraft.getTextureManager.bindTexture(cover)
    model.renderCover(entity, ClientTickHandler.fullTicks() / 100, openProgress, 0, 1f / 16)

    Minecraft.getMinecraft.getTextureManager.bindTexture(pages)
    //model.renderPageContainerLeft(1f / 16, BookApi.mainChapter())
    //model.renderPageContainerRight(1f / 16, BookApi.mainChapter())
    model.renderPages(entity, ClientTickHandler.fullTicks() / 100, 0.7f, openProgress, 0, 1f / 16)

    GlStateManager.bindTexture(0)
    GlStateManager.popMatrix()
  }
}
