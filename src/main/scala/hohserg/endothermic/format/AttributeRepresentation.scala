package hohserg.endothermic.format

import net.minecraft.client.renderer.vertex.{DefaultVertexFormats, VertexFormatElement}

object AttributeRepresentation {

  sealed trait VertexAttribute {
    def element: VertexFormatElement

    def index: Int

    def default(i: Int): Float = -1
  }

  val POSITION_3F_X = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.POSITION_3F

    override def index: Int = 0
  }

  val POSITION_3F_Y = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.POSITION_3F

    override def index: Int = 1
  }

  val POSITION_3F_Z = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.POSITION_3F

    override def index: Int = 2
  }


  val COLOR_4UB_R = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.COLOR_4UB

    override def index: Int = 0
  }

  val COLOR_4UB_G = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.COLOR_4UB

    override def index: Int = 1
  }

  val COLOR_4UB_B = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.COLOR_4UB

    override def index: Int = 2
  }

  val COLOR_4UB_A = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.COLOR_4UB

    override def index: Int = 3
  }


  val TEX_2F_U = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.TEX_2F

    override def index: Int = 0
  }

  val TEX_2F_V = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.TEX_2F

    override def index: Int = 1
  }


  val TEX_2S_U = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.TEX_2S

    override def index: Int = 0
  }

  val TEX_2S_V = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.TEX_2S

    override def index: Int = 1
  }

  val NORMAL_3B_X = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.NORMAL_3B

    override def index: Int = 0
  }

  val NORMAL_3B_Y = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.NORMAL_3B

    override def index: Int = 1
  }

  val NORMAL_3B_Z = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.NORMAL_3B

    override def index: Int = 2
  }


  val PADDING_1B = new VertexAttribute {
    override def element: VertexFormatElement = DefaultVertexFormats.PADDING_1B

    override def index: Int = 0
  }


  sealed trait Vertex {
    def index: Int

    override def toString: String = "Vertex("+index+")"
  }

  object Vertex {
    val vertices = Seq(_1, _2, _3, _4)
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
