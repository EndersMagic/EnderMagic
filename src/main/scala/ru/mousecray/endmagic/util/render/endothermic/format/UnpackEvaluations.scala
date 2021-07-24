package ru.mousecray.endmagic.util.render.endothermic.format

import java.util.function

import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.vertex.{DefaultVertexFormats, VertexFormat, VertexFormatElement}
import ru.mousecray.endmagic.util.render.endothermic.quad.mutable.LazyUnpackedQuad

import scala.collection.JavaConverters._

object UnpackEvaluations {

  def memoize[A, B](f: A => B): A => B = {
    val cache = new java.util.HashMap[A, B]()
    val javaFunctionWrapper = new function.Function[A, B] {
      override def apply(t: A): B = f(t)
    }
    cache.computeIfAbsent(_, javaFunctionWrapper)
  }

  val vertexDataSize = memoize(vertexDataSize1)

  private def vertexDataSize1(format: VertexFormat): Int = format.getElements.asScala.map(i => i.getSize).sum * 4

  val defaultVertexData = memoize(defaultVertexData1)

  private def defaultVertexData1(format: VertexFormat) = LazyUnpackedQuad(new BakedQuad(new Array[Int](UnpackEvaluations.vertexDataSize(format)), -1, null, null, false, format))
    .reconstruct(
      0, 0, 0, 255, 255, 255, 255, 0, 0, 0, 0, 1, 0, 0, 0,
      0, 0, 0, 255, 255, 255, 255, 1, 0, 0, 0, 1, 0, 0, 0,
      0, 0, 0, 255, 255, 255, 255, 1, 1, 0, 0, 1, 0, 0, 0,
      0, 0, 0, 255, 255, 255, 255, 0, 1, 0, 0, 1, 0, 0, 0
    ).toRawArray


  val flagsIndices = Map(
    DefaultVertexFormats.POSITION_3F -> 0, //1,2
    DefaultVertexFormats.COLOR_4UB -> 3, //4,5,6
    DefaultVertexFormats.TEX_2F -> 7, //8
    DefaultVertexFormats.TEX_2S -> 9, //10
    DefaultVertexFormats.NORMAL_3B -> 11, //12,13
    DefaultVertexFormats.PADDING_1B -> 14
  )

  val getFormatParseRule = memoize(getFormatParseRule1)

  type AttributeUnpacker = Array[Int] => Float
  type AttributePacker = (Float, Array[Int]) => Unit

  case class AttributeIsomorphism(unpack: AttributeUnpacker, pack: AttributePacker)

  private def getFormatParseRule1(format: VertexFormat): Map[AttributeId, AttributeIsomorphism] = {
    (for (vfe <- format.getElements.asScala.toList; i <- 0 until vfe.getElementCount; vertex <- VertexRepr.values()) yield {
      /*
      Position
        x
          1 0
          2 1
          3 2
          4 3
        y
          1 4
          2 5
          3 6
          4 7
        z
          1 8
          2 9
          3 10
          4 11
      Color
        r
          1 12
          2 13
          3 14
          4 15
        g
          1 16
          2 17
          3 18
          4 19
        b
          1 20
          2 21
          3 22
          4 23
        a
          1 24
          2 25
          3 26
          4 27
       */

      val unpack = {
        val element = vfe
        val v = vertex.index
        val formatFrom = format
        val vertexStart = v * formatFrom.getNextOffset + formatFrom.getOffset(formatFrom.getElements.indexOf(element))
        val elementType = element.getType
        val size = elementType.getSize
        val mask = (256 << (8 * (size - 1))) - 1
        val pos = vertexStart + size * i
        val index = pos >> 2
        val offset = pos & 3

        val evaluation: Array[Int] => Int = if ((pos + size - 1) / 4 != index) {
          from: Array[Int] => {
            var bits = from(index)
            bits = bits >>> (offset * 8)

            bits |= from(index + 1) << ((4 - offset) * 8)

            bits &= mask
            bits
          }
        } else {

          from: Array[Int] => {
            var bits = from(index)
            bits = bits >>> (offset * 8)

            bits &= mask
            bits
          }

        }

        if (elementType == VertexFormatElement.EnumType.FLOAT) {
          evaluation andThen java.lang.Float.intBitsToFloat
        }
        else if (elementType == VertexFormatElement.EnumType.UBYTE || elementType == VertexFormatElement.EnumType.USHORT) {
          evaluation andThen (_.toFloat / mask)
        }
        else if (elementType == VertexFormatElement.EnumType.UINT) {
          evaluation andThen (bits => ((bits & 0xFFFFFFFFL).toDouble / 0xFFFFFFFFL).toFloat)
        }
        else if (elementType == VertexFormatElement.EnumType.BYTE) {
          evaluation andThen (bits => bits.toByte.toFloat / (mask >> 1))
        }
        else if (elementType == VertexFormatElement.EnumType.SHORT) {
          evaluation andThen (bits => bits.toShort.toFloat / (mask >> 1))
        }
        else if (elementType == VertexFormatElement.EnumType.INT) {
          evaluation andThen (bits => ((bits & 0xFFFFFFFFL).toDouble / (0xFFFFFFFFL >> 1)).toFloat)
        } else
          (_: Array[Int]) => 0f
      }

      val pack = {
        val element = vfe
        val v = vertex.index
        val formatTo = format

        val vertexStart = v * formatTo.getNextOffset + formatTo.getOffset(formatTo.getElements.indexOf(element))
        val count = element.getElementCount
        val elementType = element.getType
        val size = elementType.getSize
        val mask = (256 << (8 * (size - 1))) - 1
        val pos = vertexStart + size * i
        val index = pos >> 2
        val offset = pos & 3

        val prepareBits = if (elementType == VertexFormatElement.EnumType.FLOAT) {
          java.lang.Float.floatToRawIntBits _
        } else if (elementType == VertexFormatElement.EnumType.UBYTE || elementType == VertexFormatElement.EnumType.USHORT || elementType == VertexFormatElement.EnumType.UINT) {
          value: Float => {
            Math.round(value * mask)
          }
        } else {
          value: Float => {
            Math.round(value * (mask >> 1))
          }
        }
        (value: Float, data: Array[Int]) => {
          val bits = prepareBits(value)
          data(index) &= ~(mask << (offset * 8))
          data(index) |= (bits & mask) << (offset * 8)
        }
      }
      AttributeId(vfe, i, vertex) -> AttributeIsomorphism(unpack, pack)
    }).toMap
  }
}
