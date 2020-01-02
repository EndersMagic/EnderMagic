package endothermic.ops

import hohserg.endothermic.BaseUnpackedVertex

import scala.language.higherKinds

trait ReconstructOpsQuad {
  type Self <: ReconstructOpsQuad
  type VertexType <: BaseUnpackedVertex[VertexType]

  def v1: VertexType

  def v2: VertexType

  def v3: VertexType

  def v4: VertexType

  def reconstruct(v1: VertexType,
                  v2: VertexType,
                  v3: VertexType,
                  v4: VertexType): Self


  def foreachVertex(f: VertexType => VertexType): Self =
    reconstruct(
      f(v1).asInstanceOf[VertexType],
      f(v2).asInstanceOf[VertexType],
      f(v3).asInstanceOf[VertexType],
      f(v4).asInstanceOf[VertexType]
    )
}