package ru.mousecray.endmagic.client.render.book

import net.minecraft.client.Minecraft
import net.minecraft.client.model.{ModelBase, ModelRenderer}
import net.minecraft.client.renderer.vertex.VertexFormat
import net.minecraft.client.renderer.{BufferBuilder, GlStateManager}
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.MathHelper
import ru.mousecray.endmagic.EM
import ru.mousecray.endmagic.api.embook.PageContainer
import ru.mousecray.endmagic.client.render.book.EMBookModel.Scale

class EMBookModel extends ModelBase {
  var coverRight = new ModelRenderer(this).setTextureOffset(0, 0).addBox(-6, -5, 0, 6, 10, 0)

  var coverLeft = new ModelRenderer(this).setTextureOffset(16, 0).addBox(0, -5, 0, 6, 10, 0)

  var pagesRight = new ModelRenderer(this).setTextureOffset(0, 10).addBox(0, -4, -0.99F, 5, 8, 1)

  var pagesLeft = new ModelRenderer(this).setTextureOffset(12, 10).addBox(0, -4, -0.01F, 5, 8, 1)

  var flippingPageRight = new PageModel(false)
  var flippingPageLeft = new PageModel(true)

  var rightPage = new PageModel(false)
  var leftPage = new PageModel(true)

  var bookSpine = new ModelRenderer(this).setTextureOffset(12, 0).addBox(-1, -5, 0, 2, 10, 0)

  coverRight.setRotationPoint(0, 0, -1)
  coverLeft.setRotationPoint(0, 0, 1)
  bookSpine.rotateAngleY = Math.PI.toFloat / 2F

  def renderCover(entityIn: Entity, limbSwing: Float, openProgress: Float, headPitch: Float)(implicit scale: Scale): Unit = {
    setRotationAnglesCover(limbSwing, openProgress, headPitch, scale, entityIn)
    coverRight.render(scale)
    coverLeft.render(scale)
    bookSpine.render(scale)
    pagesRight.render(scale)
    pagesLeft.render(scale)
  }

  def renderPageContainerRight(pageContainer: PageContainer)(implicit scale: Scale): Unit = {
    val texture = PageTextureHolder.getTexture(pageContainer)
    setupStandartAttributes()
    texture.bindFramebufferTexture()
    rightPage.render()
    flippingPageRight.render()
    texture.unbindFramebufferTexture()
  }

  def renderPageContainerLeft(pageContainer: PageContainer)(implicit scale: Scale): Unit = {
    val texture = PageTextureHolder.getTexture(pageContainer)
    setupStandartAttributes()

    texture.bindFramebufferTexture()
    FBODumper.dump()
    leftPage.render()
    flippingPageLeft.render()
    texture.unbindFramebufferTexture()
  }


  def setupStandartAttributes(): Unit = {
    GlStateManager.enableLighting()
    GlStateManager.enableAlpha()
    GlStateManager.enableBlend()
    GlStateManager.enableDepth()
  }

  def renderPages(entityIn: Entity, limbSwing: Float, flipping: Float, netHeadYaw: Float, openProgress: Float)(implicit scale: Scale): Unit = {
    setRotationAnglesPages(limbSwing, flipping, netHeadYaw)

    Minecraft.getMinecraft.getTextureManager.bindTexture(new ResourceLocation(EM.ID, "textures/models/book/page.png"))
    rightPage.render()
    flippingPageLeft.render()

    Minecraft.getMinecraft.getTextureManager.bindTexture(new ResourceLocation(EM.ID, "textures/models/book/page2.png"))
    leftPage.render()
    flippingPageRight.render()
  }

  def setRotationAnglesCover(limbSwing: Float, openProgress: Float, headPitch: Float, scaleFactor: Float, entityIn: Entity): Unit = {
    val f = (MathHelper.sin(limbSwing * 0.02F) * 0.1F + 1.25F) * openProgress
    coverRight.rotateAngleY = Math.PI.toFloat + f
    coverLeft.rotateAngleY = -f
    pagesRight.rotateAngleY = f
    pagesLeft.rotateAngleY = -f
    pagesRight.rotationPointX = MathHelper.sin(f)
    pagesLeft.rotationPointX = MathHelper.sin(f)
  }

  def setRotationAnglesPages(limbSwing: Scale, flipping: Scale, openProgress: Scale): Unit = {
    val f = (MathHelper.sin(limbSwing * 0.02F) * 0.1F + 1.25F) * openProgress

    rightPage.rotateAngleY = f
    leftPage.rotateAngleY = -f
    flippingPageRight.rotateAngleY = f - f * 2 * flipping
    flippingPageLeft.rotateAngleY = f - f * 2 * flipping

    rightPage.rotationPointX = MathHelper.sin(f)
    leftPage.rotationPointX = MathHelper.sin(f)
    flippingPageRight.rotationPointX = MathHelper.sin(f)
    flippingPageLeft.rotationPointX = MathHelper.sin(f)

  }
}

object EMBookModel {
  type Scale = Float

  implicit class BufferWrapper(val buffer: BufferBuilder) extends AnyVal {
    def pos(x: Double, y: Double, z: Double)(implicit scale: Scale): BufferBuilder =
      buffer.pos(x * scale, y * scale, z * scale)

    def begin(glMode: Int, format: VertexFormat): Unit = buffer.begin(glMode, format)
  }

}
