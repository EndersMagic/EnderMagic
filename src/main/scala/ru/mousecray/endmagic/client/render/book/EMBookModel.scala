package ru.mousecray.endmagic.client.render.book

import net.minecraft.client.Minecraft
import net.minecraft.client.model.{ModelBase, ModelRenderer, PositionTextureVertex}
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.MathHelper
import ru.mousecray.endmagic.EM
import ru.mousecray.endmagic.api.embook.PageContainer

class EMBookModel extends ModelBase {
  var coverRight = new ModelRenderer(this).setTextureOffset(0, 0).addBox(-6, -5, 0, 6, 10, 0)

  var coverLeft = new ModelRenderer(this).setTextureOffset(16, 0).addBox(0, -5, 0, 6, 10, 0)

  var pagesRight = new ModelRenderer(this).setTextureOffset(0, 10).addBox(0, -4, -0.99F, 5, 8, 1)

  var pagesLeft = new ModelRenderer(this).setTextureOffset(12, 10).addBox(0, -4, -0.01F, 5, 8, 1)

  var flippingPageRight = new ModelRenderer(this).setTextureOffset(12, 0).setTextureSize(25, 20).addBox(0, -4, 0, 5, 8, 0);
  {
    val box = flippingPageRight.cubeList.get(0)
    box.quadList = Array(box.quadList(5))

    val (x, y, z) = (0, -4, 0)
    val (w, h, d) = (5, 8, 0)

    val x2 = x + w.toFloat
    val y2 = y + h.toFloat
    val z2 = z + d.toFloat

    val positiontexturevertex3 = new PositionTextureVertex(x, y, z2, 12.5f / 25, 0)
    val positiontexturevertex4 = new PositionTextureVertex(x2, y, f2, 12.5f / 25, 1)
    val positiontexturevertex5 = new PositionTextureVertex(x2, y2, f2, 1, 1)
    val positiontexturevertex6 = new PositionTextureVertex(x, y2, f2, 1, 0)

    val buffer = Tessellator.getInstance().getBuffer

    val vec3d = positiontexturevertex3.vector3D.subtractReverse(positiontexturevertex4.vector3D)
    val vec3d1 = positiontexturevertex3.vector3D.subtractReverse(positiontexturevertex6.vector3D)
    val vec3d2 = vec3d1.crossProduct(vec3d).normalize
    var f = vec3d2.x.toFloat
    var f1 = vec3d2.y.toFloat
    var f2 = vec3d2.z.toFloat

    buffer.begin(7, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL)

    buffer.pos(positiontexturevertex3.vector3D.x * scale.toDouble, positiontexturevertex3.vector3D.y * scale.toDouble, positiontexturevertex3.vector3D.z * scale.toDouble).tex(positiontexturevertex3.texturePositionX.toDouble, positiontexturevertex3.texturePositionY.toDouble).normal(f, f1, f2).endVertex()


    Tessellator.getInstance.draw()

  }

  var flippingPageLeft = new ModelRenderer(this).setTextureOffset(0, 0).setTextureSize(25, 20).addBox(0, -4, 0, 5, 8, 0);
  {
    val box = flippingPageLeft.cubeList.get(0)
    box.quadList = Array(box.quadList(4))
  }

  var rightPage = new ModelRenderer(this).setTextureOffset(12, 0).setTextureSize(25, 20).addBox(0, -4, 0, 5, 8, 0)
  var leftPage = new ModelRenderer(this).setTextureOffset(0, 0).setTextureSize(25, 20).addBox(0, -4, 0, 5, 8, 0)

  var bookSpine = new ModelRenderer(this).setTextureOffset(12, 0).addBox(-1, -5, 0, 2, 10, 0)

  coverRight.setRotationPoint(0, 0, -1)
  coverLeft.setRotationPoint(0, 0, 1)
  bookSpine.rotateAngleY = Math.PI.toFloat / 2F

  def renderCover(entityIn: Entity, limbSwing: Float, openProgress: Float, headPitch: Float, scale: Float): Unit = {
    setRotationAnglesCover(limbSwing, openProgress, headPitch, scale, entityIn)
    coverRight.render(scale)
    coverLeft.render(scale)
    bookSpine.render(scale)
    pagesRight.render(scale)
    pagesLeft.render(scale)
  }

  def renderPageContainerRight(scale: Float, pageContainer: PageContainer): Unit = {
    val texture = PageTextureHolder.getTexture(pageContainer)
    texture.bindFramebufferTexture()
    rightPage.render(scale)
    flippingPageRight.render(scale)
  }

  def renderPageContainerLeft(scale: Float, pageContainer: PageContainer): Unit = {
    val texture = PageTextureHolder.getTexture(pageContainer)
    texture.bindFramebufferTexture()
    leftPage.render(scale)
    flippingPageLeft.render(scale)
  }

  def renderPages(entityIn: Entity, limbSwing: Float, flipping: Float, netHeadYaw: Float, openProgress: Float, scale: Float): Unit = {
    setRotationAnglesPages(limbSwing, flipping, flipping, netHeadYaw, openProgress, scale, entityIn)

    Minecraft.getMinecraft.getTextureManager.bindTexture(new ResourceLocation(EM.ID, "textures/models/book/page.png"))
    rightPage.render(scale)
    flippingPageLeft.render(scale)

    Minecraft.getMinecraft.getTextureManager.bindTexture(new ResourceLocation(EM.ID, "textures/models/book/page2.png"))
    leftPage.render(scale)
    flippingPageRight.render(scale)
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

  def setRotationAnglesPages(limbSwing: Float, flippingRight: Float, flippingLeft: Float, openProgress: Float, headPitch: Float, scaleFactor: Float, entityIn: Entity): Unit = {
    val f = (MathHelper.sin(limbSwing * 0.02F) * 0.1F + 1.25F) * openProgress
    rightPage.rotateAngleY = f
    leftPage.rotateAngleY = -f
    flippingPageRight.rotateAngleY = f - f * 2 * flippingRight
    flippingPageLeft.rotateAngleY = f - f * 2 * flippingLeft
    rightPage.rotationPointX = MathHelper.sin(f)
    pagesLeft.rotationPointX = MathHelper.sin(f)
    leftPage.rotationPointX = MathHelper.sin(f)
    flippingPageRight.rotationPointX = MathHelper.sin(f)
    flippingPageLeft.rotationPointX = MathHelper.sin(f)

  }
}
