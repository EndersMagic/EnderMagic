package ru.mousecray.endmagic.util.baked.`lazy`

import java.util.function.Supplier

import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.VertexFormatElement
import net.minecraft.util.EnumFacing
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad
import ru.mousecray.endmagic.util.elix_x.ecomms.color.RGBA
import ru.mousecray.endmagic.util.elix_x.ecomms.reflection.ReflectionHelper

import scala.collection.JavaConverters._

class LazyUnpackedQuad(_vertices: => Seq[Vertex], tintIndex: Int, face: EnumFacing, sprite: TextureAtlasSprite, applyDiffuseLighting: Boolean) {
  lazy val vertices: Seq[Vertex] = _vertices

}

object LazyUnpackedQuad {
  private val unpackedData = new ReflectionHelper.AClass[UnpackedBakedQuad](classOf[UnpackedBakedQuad]).getDeclaredField[Array[Array[Array[Float]]]]("unpackedData").orElseThrow(new Supplier[Throwable] {
    override def get(): Throwable = new IllegalArgumentException("Failed to reflect forge.UnpackedBakedQuad#unpackedData necessary for excore.UnpackedBakedQuad")
  }).setAccessible(true)

  def lazyUnpack(bakedQuad: BakedQuad): LazyUnpackedQuad = {
    lazy val unpacked = {
      val unpackedQuad = {
        bakedQuad match {
          case quad: UnpackedBakedQuad => quad
          case _ =>
            val builder: UnpackedBakedQuad.Builder = new UnpackedBakedQuad.Builder(bakedQuad.getFormat)
            bakedQuad.pipe(builder)
            builder.build()
        }
      }

      unpackedData.get(unpackedQuad).get()
    }

    lazy val usages = bakedQuad.getFormat.getElements.asScala.zipWithIndex.map { case (vfe, index) => (vfe.getType, vfe.getUsage) -> index }.toMap

    new LazyUnpackedQuad(unpacked.map(pv => {

      lazy val pos = pv(usages(VertexFormatElement.EnumType.FLOAT -> VertexFormatElement.EnumUsage.POSITION))
      lazy val color = pv(usages(VertexFormatElement.EnumType.UBYTE -> VertexFormatElement.EnumUsage.COLOR))
      lazy val uv = pv(usages(VertexFormatElement.EnumType.FLOAT -> VertexFormatElement.EnumUsage.UV))
      lazy val lightmap = pv(usages(VertexFormatElement.EnumType.SHORT -> VertexFormatElement.EnumUsage.UV))
      lazy val normal = pv(usages(VertexFormatElement.EnumType.BYTE -> VertexFormatElement.EnumUsage.NORMAL))


      new Vertex(
        (pos(0), pos(1), pos(2)),
        new RGBA(color(0), color(1), color(2), color(3)),
        (uv(0), uv(1)),
        (lightmap(0).toShort, lightmap(1).toShort),
        (normal(0).toByte, normal(1).toByte, normal(2).toByte)
      )
    }),
      bakedQuad.getTintIndex, bakedQuad.getFace, bakedQuad.getSprite, bakedQuad.shouldApplyDiffuseLighting())
  }

}

