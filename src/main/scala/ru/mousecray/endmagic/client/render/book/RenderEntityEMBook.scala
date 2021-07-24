package ru.mousecray.endmagic.client.render.book

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.entity.{Render, RenderManager}
import net.minecraft.util.ResourceLocation
import ru.mousecray.endmagic.EM
import ru.mousecray.endmagic.api.embook.BookApi
import ru.mousecray.endmagic.client.render.book.EMBookModel.Scale
import ru.mousecray.endmagic.entity.EntityEMBook
import ru.mousecray.endmagic.util.render.ClientTickHandler

class RenderEntityEMBook(renderManagerIn: RenderManager) extends Render[EntityEMBook](renderManagerIn) {
  val cover = new ResourceLocation(EM.ID, "textures/models/book/em_book2.png")
  val pages = new ResourceLocation(EM.ID, "textures/models/book/page.png")

  override def getEntityTexture(entity: EntityEMBook): ResourceLocation = cover

  val model = new EMBookModel

  override def doRender(entity: EntityEMBook, x: Double, y: Double, z: Double, entityYaw: Float, partialTicks: Float): Unit = {
    implicit val scale: Scale = 1f / 16

    GlStateManager.pushMatrix()
    GlStateManager.translate(x, y + 0.5, z)
    GlStateManager.enableTexture2D()

    val openProgress = 1 //(Math.sin(ClientTickHandler.fullTicks() / 100).toFloat + 1) / 2

    Minecraft.getMinecraft.getTextureManager.bindTexture(cover)
    model.renderCover(entity, ClientTickHandler.fullTicks() / 100, openProgress, 0)


    model.setRotationAnglesPages(ClientTickHandler.fullTicks() / 100, 0.7f, openProgress)
    val container = if(true)TestOverlay.testPage() else BookApi.mainChapter()
    model.renderPageContainerLeft(container)
    model.renderPageContainerRight(container)
    //model.renderPages(entity, ClientTickHandler.fullTicks() / 100, 0.7f, openProgress, 0)

    GlStateManager.color(1, 1, 1, 1)
    GlStateManager.bindTexture(0)
    GlStateManager.popMatrix()
  }
}
