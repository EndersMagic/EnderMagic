package endothermic.ops

import hohserg.endothermic.immutable.UnpackedQuad

object Test {

  val quad = UnpackedQuad(???, ???)
  val q2 = quad.translate(1, 1, 1)

  trait ReconstructOps {
    type Self <: ReconstructOps

    def x: Int

    def y: Int

    def reconstruct(x: Int, y: Int): Self

    def translate1(x: Int, y: Int): Self = reconstruct(this.x + x, this.y + y)
  }

  class Test1(
               val x: Int,
               val y: Int
             ) extends ReconstructOps {
    override type Self = Test1

    override def reconstruct(x: Int = x, y: Int = y): Test1 = new Test1(x, y)
  }

  //mutable
  class Test2(
               var x: Int,
               var y: Int
             ) extends ReconstructOps {
    override type Self = Test2

    override def reconstruct(x: Int = x, y: Int = y): Test2 = {
      this.x = x
      this.y = y
      this
    }

  }


}
