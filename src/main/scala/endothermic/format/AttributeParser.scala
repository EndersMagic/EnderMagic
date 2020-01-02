package endothermic.format

import hohserg.endothermic.format.AttributeRepresentation._


trait AttributeParser[A <: VertexAttribute] {
  def parse(bits: Int, mask: Int): Float

}

object AttributeParser {

  implicit case object positionParser extends AttributeParser[POSITION_3F] {
    override def parse(bits: Int, mask: Int): Float = java.lang.Float.intBitsToFloat(bits)
  }


  implicit case object colorParser extends AttributeParser[COLOR_4UB] {
    override def parse(bits: Int, mask: Int): Float = bits.toFloat / mask
  }


  implicit case object tex2fParser extends AttributeParser[TEX_2F] {
    override def parse(bits: Int, mask: Int): Float = java.lang.Float.intBitsToFloat(bits)
  }


  implicit case object tex2sParser extends AttributeParser[TEX_2S] {
    override def parse(bits: Int, mask: Int): Float = bits.toShort.toFloat / (mask >> 1)
  }


  implicit case object normalParser extends AttributeParser[NORMAL_3B] {
    override def parse(bits: Int, mask: Int): Float = bits.toByte.toFloat / (mask >> 1)
  }


  implicit case object paddingParser extends AttributeParser[PADDING_1B] {
    override def parse(bits: Int, mask: Int): Float = bits.toByte.toFloat / (mask >> 1)
  }

}
