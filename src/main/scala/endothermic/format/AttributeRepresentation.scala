package endothermic.format

import net.minecraft.client.renderer.vertex.{DefaultVertexFormats, VertexFormatElement}

object AttributeRepresentation {

  sealed trait VertexAttribute {
    def element: VertexFormatElement
  }

  sealed trait POSITION_3F extends VertexAttribute

  implicit val POSITION_3F: POSITION_3F = new POSITION_3F {
    override val element: VertexFormatElement = DefaultVertexFormats.POSITION_3F
  }

  sealed trait COLOR_4UB extends VertexAttribute

  implicit val COLOR_4UB: COLOR_4UB = new COLOR_4UB {
    override val element: VertexFormatElement = DefaultVertexFormats.COLOR_4UB
  }

  sealed trait TEX_2F extends VertexAttribute

  implicit val TEX_2F: TEX_2F = new TEX_2F {
    override val element: VertexFormatElement = DefaultVertexFormats.TEX_2F
  }

  sealed trait TEX_2S extends VertexAttribute

  implicit val TEX_2S: TEX_2S = new TEX_2S {
    override val element: VertexFormatElement = DefaultVertexFormats.TEX_2S
  }

  sealed trait NORMAL_3B extends VertexAttribute

  implicit val NORMAL_3B: NORMAL_3B = new NORMAL_3B {
    override val element: VertexFormatElement = DefaultVertexFormats.NORMAL_3B
  }

  sealed trait PADDING_1B extends VertexAttribute

  implicit val PADDING_1B: PADDING_1B = new PADDING_1B {
    override val element: VertexFormatElement = DefaultVertexFormats.PADDING_1B
  }


  sealed trait Vertex {
    def index: Int
  }

  sealed trait _1 extends Vertex

  val _1: _1 = new _1 {
    override val index: Int = 0
  }

  sealed trait _2 extends Vertex

  val _2: _2 = new _2 {
    override val index: Int = 1
  }

  sealed trait _3 extends Vertex

  val _3: _3 = new _3 {
    override val index: Int = 2
  }

  sealed trait _4 extends Vertex

  val _4: _4 = new _4 {
    override val index: Int = 3
  }

}
