package hohserg.endothermic

import hohserg.endothermic.BaseUnpackedQuad._
import hohserg.endothermic.format.AttributeRepresentation._
import hohserg.endothermic.format.UnpackEvaluations
import hohserg.endothermic.ops.QuadOps
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.vertex.{DefaultVertexFormats, VertexFormat, VertexFormatElement}

trait BaseUnpackedQuad extends QuadOps {
  type Self <: BaseUnpackedQuad

  def toBakedQuad: BakedQuad

  private[endothermic] def quad: BakedQuad

  def format: VertexFormat = quad.getFormat

  val evaluations: Map[(VertexFormatElement, Int, Vertex), (BakedQuad => Float, (Float, Array[Int]) => Unit)] = UnpackEvaluations.getFormatParseRule(format)


  private[endothermic] var initFlag1: Int = 0
  private[endothermic] var initFlag2: Int = 0
  private[endothermic] var initFlag3: Int = 0
  private[endothermic] var initFlag4: Int = 0

  private[endothermic] var changeFlag1: Int = 0
  private[endothermic] var changeFlag2: Int = 0
  private[endothermic] var changeFlag3: Int = 0
  private[endothermic] var changeFlag4: Int = 0


  private[endothermic] var _v1_x: Float = defaultValue

  def v1_x: Float = {
    if ((initFlag1 & (1 << x_1)) == 0) {
      initFlag1 |= (1 << x_1)

      evaluations.get(x_1).foreach(i => _v1_x = i._1(quad))
    }
    _v1_x
  }


  private[endothermic] var _v1_y: Float = defaultValue

  def v1_y: Float = {
    if ((initFlag1 & (1 << y_1)) == 0) {
      initFlag1 |= (1 << y_1)

      evaluations.get(y_1).foreach(i => _v1_y = i._1(quad))
    }
    _v1_y
  }

  private[endothermic] var _v1_z: Float = defaultValue

  def v1_z: Float = {
    if ((initFlag1 & (1 << z_1)) == 0) {
      initFlag1 |= (1 << z_1)

      evaluations.get(z_1).foreach(i => _v1_z = i._1(quad))
    }
    _v1_z
  }

  private[endothermic] var _v1_r: Float = defaultValue

  def v1_r: Float = {
    if ((initFlag1 & (1 << r_1)) == 0) {
      initFlag1 |= (1 << r_1)

      evaluations.get(r_1).foreach(i => _v1_r = i._1(quad))
    }
    _v1_r
  }

  private[endothermic] var _v1_g: Float = defaultValue

  def v1_g: Float = {
    if ((initFlag1 & (1 << g_1)) == 0) {
      initFlag1 |= (1 << g_1)

      evaluations.get(g_1).foreach(i => _v1_g = i._1(quad))
    }
    _v1_g
  }

  private[endothermic] var _v1_b: Float = defaultValue

  def v1_b: Float = {
    if ((initFlag1 & (1 << b_1)) == 0) {
      initFlag1 |= (1 << b_1)

      evaluations.get(b_1).foreach(i => _v1_b = i._1(quad))
    }
    _v1_b
  }

  private[endothermic] var _v1_a: Float = defaultValue

  def v1_a: Float = {
    if ((initFlag1 & (1 << a_1)) == 0) {
      initFlag1 |= (1 << a_1)

      evaluations.get(a_1).foreach(i => _v1_a = i._1(quad))
    }
    _v1_a
  }

  private[endothermic] var _v1_u: Float = defaultValue

  def v1_u: Float = {
    if ((initFlag1 & (1 << u_1)) == 0) {
      initFlag1 |= (1 << u_1)

      evaluations.get(u_1).foreach(i => _v1_u = i._1(quad))
    }
    _v1_u
  }

  private[endothermic] var _v1_v: Float = defaultValue

  def v1_v: Float = {
    if ((initFlag1 & (1 << v_1)) == 0) {
      initFlag1 |= (1 << v_1)

      evaluations.get(v_1).foreach(i => _v1_v = i._1(quad))
    }
    _v1_v
  }

  private[endothermic] var _v1_lu: Float = defaultValue

  def v1_lu: Float = {
    if ((initFlag1 & (1 << lu_1)) == 0) {
      initFlag1 |= (1 << lu_1)

      evaluations.get(lu_1).foreach(i => _v1_lu = i._1(quad))
    }
    _v1_lu
  }

  private[endothermic] var _v1_lv: Float = defaultValue

  def v1_lv: Float = {
    if ((initFlag1 & (1 << lv_1)) == 0) {
      initFlag1 |= (1 << lv_1)

      evaluations.get(lv_1).foreach(i => _v1_lv = i._1(quad))
    }
    _v1_lv
  }

  private[endothermic] var _v1_nx: Float = defaultValue

  def v1_nx: Float = {
    if ((initFlag1 & (1 << nx_1)) == 0) {
      initFlag1 |= (1 << nx_1)

      evaluations.get(nx_1).foreach(i => _v1_nx = i._1(quad))
    }
    _v1_nx
  }

  private[endothermic] var _v1_ny: Float = defaultValue

  def v1_ny: Float = {
    if ((initFlag1 & (1 << ny_1)) == 0) {
      initFlag1 |= (1 << ny_1)

      evaluations.get(ny_1).foreach(i => _v1_ny = i._1(quad))
    }
    _v1_ny
  }

  private[endothermic] var _v1_nz: Float = defaultValue

  def v1_nz: Float = {
    if ((initFlag1 & (1 << nz_1)) == 0) {
      initFlag1 |= (1 << nz_1)

      evaluations.get(nz_1).foreach(i => _v1_nz = i._1(quad))
    }
    _v1_nz
  }

  private[endothermic] var _v1_p: Float = defaultValue

  def v1_p: Float = {
    if ((initFlag1 & (1 << p_1)) == 0) {
      initFlag1 |= (1 << p_1)

      evaluations.get(p_1).foreach(i => _v1_p = i._1(quad))
    }
    _v1_p
  }


  private[endothermic] var _v2_x: Float = defaultValue

  def v2_x: Float = {
    if ((initFlag2 & (1 << x_1)) == 0) {
      initFlag2 |= (1 << x_1)

      evaluations.get(x_2).foreach(i => _v2_x = i._1(quad))
    }
    _v2_x
  }

  private[endothermic] var _v2_y: Float = defaultValue

  def v2_y: Float = {
    if ((initFlag2 & (1 << y_1)) == 0) {
      initFlag2 |= (1 << y_1)

      evaluations.get(y_2).foreach(i => _v2_y = i._1(quad))
    }
    _v2_y
  }

  private[endothermic] var _v2_z: Float = defaultValue

  def v2_z: Float = {
    if ((initFlag2 & (1 << z_1)) == 0) {
      initFlag2 |= (1 << z_1)

      evaluations.get(z_2).foreach(i => _v2_z = i._1(quad))
    }
    _v2_z
  }

  private[endothermic] var _v2_r: Float = defaultValue

  def v2_r: Float = {
    if ((initFlag2 & (1 << r_1)) == 0) {
      initFlag2 |= (1 << r_1)

      evaluations.get(r_2).foreach(i => _v2_r = i._1(quad))
    }
    _v2_r
  }

  private[endothermic] var _v2_g: Float = defaultValue

  def v2_g: Float = {
    if ((initFlag2 & (1 << g_1)) == 0) {
      initFlag2 |= (1 << g_1)

      evaluations.get(g_2).foreach(i => _v2_g = i._1(quad))
    }
    _v2_g
  }

  private[endothermic] var _v2_b: Float = defaultValue

  def v2_b: Float = {
    if ((initFlag2 & (1 << b_1)) == 0) {
      initFlag2 |= (1 << b_1)

      evaluations.get(b_2).foreach(i => _v2_b = i._1(quad))
    }
    _v2_b
  }

  private[endothermic] var _v2_a: Float = defaultValue

  def v2_a: Float = {
    if ((initFlag2 & (1 << a_1)) == 0) {
      initFlag2 |= (1 << a_1)

      evaluations.get(a_2).foreach(i => _v2_a = i._1(quad))
    }
    _v2_a
  }

  private[endothermic] var _v2_u: Float = defaultValue

  def v2_u: Float = {
    if ((initFlag2 & (1 << u_1)) == 0) {
      initFlag2 |= (1 << u_1)

      evaluations.get(u_2).foreach(i => _v2_u = i._1(quad))
    }
    _v2_u
  }

  private[endothermic] var _v2_v: Float = defaultValue

  def v2_v: Float = {
    if ((initFlag2 & (1 << v_1)) == 0) {
      initFlag2 |= (1 << v_1)

      evaluations.get(v_2).foreach(i => _v2_v = i._1(quad))
    }
    _v2_v
  }

  private[endothermic] var _v2_lu: Float = defaultValue

  def v2_lu: Float = {
    if ((initFlag2 & (1 << lu_1)) == 0) {
      initFlag2 |= (1 << lu_1)

      evaluations.get(lu_2).foreach(i => _v2_lu = i._1(quad))
    }
    _v2_lu
  }

  private[endothermic] var _v2_lv: Float = defaultValue

  def v2_lv: Float = {
    if ((initFlag2 & (1 << lv_1)) == 0) {
      initFlag2 |= (1 << lv_1)

      evaluations.get(lv_2).foreach(i => _v2_lv = i._1(quad))
    }
    _v2_lv
  }

  private[endothermic] var _v2_nx: Float = defaultValue

  def v2_nx: Float = {
    if ((initFlag2 & (1 << nx_1)) == 0) {
      initFlag2 |= (1 << nx_1)

      evaluations.get(nx_2).foreach(i => _v2_nx = i._1(quad))
    }
    _v2_nx
  }

  private[endothermic] var _v2_ny: Float = defaultValue

  def v2_ny: Float = {
    if ((initFlag2 & (1 << ny_1)) == 0) {
      initFlag2 |= (1 << ny_1)

      evaluations.get(ny_2).foreach(i => _v2_ny = i._1(quad))
    }
    _v2_ny
  }

  private[endothermic] var _v2_nz: Float = defaultValue

  def v2_nz: Float = {
    if ((initFlag2 & (1 << nz_1)) == 0) {
      initFlag2 |= (1 << nz_1)

      evaluations.get(nz_2).foreach(i => _v2_nz = i._1(quad))
    }
    _v2_nz
  }

  private[endothermic] var _v2_p: Float = defaultValue

  def v2_p: Float = {
    if ((initFlag2 & (1 << p_1)) == 0) {
      initFlag2 |= (1 << p_1)

      evaluations.get(p_2).foreach(i => _v2_p = i._1(quad))
    }
    _v2_p
  }


  private[endothermic] var _v3_x: Float = defaultValue

  def v3_x: Float = {
    if ((initFlag3 & (1 << x_1)) == 0) {
      initFlag3 |= (1 << x_1)

      evaluations.get(x_3).foreach(i => _v3_x = i._1(quad))
    }
    _v3_x
  }

  private[endothermic] var _v3_y: Float = defaultValue

  def v3_y: Float = {
    if ((initFlag3 & (1 << y_1)) == 0) {
      initFlag3 |= (1 << y_1)

      evaluations.get(y_3).foreach(i => _v3_y = i._1(quad))
    }
    _v3_y
  }

  private[endothermic] var _v3_z: Float = defaultValue

  def v3_z: Float = {
    if ((initFlag3 & (1 << z_1)) == 0) {
      initFlag3 |= (1 << z_1)

      evaluations.get(z_3).foreach(i => _v3_z = i._1(quad))
    }
    _v3_z
  }

  private[endothermic] var _v3_r: Float = defaultValue

  def v3_r: Float = {
    if ((initFlag3 & (1 << r_1)) == 0) {
      initFlag3 |= (1 << r_1)

      evaluations.get(r_3).foreach(i => _v3_r = i._1(quad))
    }
    _v3_r
  }

  private[endothermic] var _v3_g: Float = defaultValue

  def v3_g: Float = {
    if ((initFlag3 & (1 << g_1)) == 0) {
      initFlag3 |= (1 << g_1)

      evaluations.get(g_3).foreach(i => _v3_g = i._1(quad))
    }
    _v3_g
  }

  private[endothermic] var _v3_b: Float = defaultValue

  def v3_b: Float = {
    if ((initFlag3 & (1 << b_1)) == 0) {
      initFlag3 |= (1 << b_1)

      evaluations.get(b_3).foreach(i => _v3_b = i._1(quad))
    }
    _v3_b
  }

  private[endothermic] var _v3_a: Float = defaultValue

  def v3_a: Float = {
    if ((initFlag3 & (1 << a_1)) == 0) {
      initFlag3 |= (1 << a_1)

      evaluations.get(a_3).foreach(i => _v3_a = i._1(quad))
    }
    _v3_a
  }

  private[endothermic] var _v3_u: Float = defaultValue

  def v3_u: Float = {
    if ((initFlag3 & (1 << u_1)) == 0) {
      initFlag3 |= (1 << u_1)

      evaluations.get(u_3).foreach(i => _v3_u = i._1(quad))
    }
    _v3_u
  }

  private[endothermic] var _v3_v: Float = defaultValue

  def v3_v: Float = {
    if ((initFlag3 & (1 << v_1)) == 0) {
      initFlag3 |= (1 << v_1)

      evaluations.get(v_3).foreach(i => _v3_v = i._1(quad))
    }
    _v3_v
  }

  private[endothermic] var _v3_lu: Float = defaultValue

  def v3_lu: Float = {
    if ((initFlag3 & (1 << lu_1)) == 0) {
      initFlag3 |= (1 << lu_1)

      evaluations.get(lu_3).foreach(i => _v3_lu = i._1(quad))
    }
    _v3_lu
  }

  private[endothermic] var _v3_lv: Float = defaultValue

  def v3_lv: Float = {
    if ((initFlag3 & (1 << lv_1)) == 0) {
      initFlag3 |= (1 << lv_1)

      evaluations.get(lv_3).foreach(i => _v3_lv = i._1(quad))
    }
    _v3_lv
  }

  private[endothermic] var _v3_nx: Float = defaultValue

  def v3_nx: Float = {
    if ((initFlag3 & (1 << nx_1)) == 0) {
      initFlag3 |= (1 << nx_1)

      evaluations.get(nx_3).foreach(i => _v3_nx = i._1(quad))
    }
    _v3_nx
  }

  private[endothermic] var _v3_ny: Float = defaultValue

  def v3_ny: Float = {
    if ((initFlag3 & (1 << ny_1)) == 0) {
      initFlag3 |= (1 << ny_1)

      evaluations.get(ny_3).foreach(i => _v3_ny = i._1(quad))
    }
    _v3_ny
  }

  private[endothermic] var _v3_nz: Float = defaultValue

  def v3_nz: Float = {
    if ((initFlag3 & (1 << nz_1)) == 0) {
      initFlag3 |= (1 << nz_1)

      evaluations.get(nz_3).foreach(i => _v3_nz = i._1(quad))
    }
    _v3_nz
  }

  private[endothermic] var _v3_p: Float = defaultValue

  def v3_p: Float = {
    if ((initFlag3 & (1 << p_1)) == 0) {
      initFlag3 |= (1 << p_1)

      evaluations.get(p_3).foreach(i => _v3_p = i._1(quad))
    }
    _v3_p
  }


  private[endothermic] var _v4_x: Float = defaultValue

  def v4_x: Float = {
    if ((initFlag4 & (1 << x_1)) == 0) {
      initFlag4 |= (1 << x_1)

      evaluations.get(x_4).foreach(i => _v4_x = i._1(quad))
    }
    _v4_x
  }

  private[endothermic] var _v4_y: Float = defaultValue

  def v4_y: Float = {
    if ((initFlag4 & (1 << y_1)) == 0) {
      initFlag4 |= (1 << y_1)

      evaluations.get(y_4).foreach(i => _v4_y = i._1(quad))
    }
    _v4_y
  }

  private[endothermic] var _v4_z: Float = defaultValue

  def v4_z: Float = {
    if ((initFlag4 & (1 << z_1)) == 0) {
      initFlag4 |= (1 << z_1)

      evaluations.get(z_4).foreach(i => _v4_z = i._1(quad))
    }
    _v4_z
  }

  private[endothermic] var _v4_r: Float = defaultValue

  def v4_r: Float = {
    if ((initFlag4 & (1 << r_1)) == 0) {
      initFlag4 |= (1 << r_1)

      evaluations.get(r_4).foreach(i => _v4_r = i._1(quad))
    }
    _v4_r
  }

  private[endothermic] var _v4_g: Float = defaultValue

  def v4_g: Float = {
    if ((initFlag4 & (1 << g_1)) == 0) {
      initFlag4 |= (1 << g_1)

      evaluations.get(g_4).foreach(i => _v4_g = i._1(quad))
    }
    _v4_g
  }

  private[endothermic] var _v4_b: Float = defaultValue

  def v4_b: Float = {
    if ((initFlag4 & (1 << b_1)) == 0) {
      initFlag4 |= (1 << b_1)

      evaluations.get(b_4).foreach(i => _v4_b = i._1(quad))
    }
    _v4_b
  }

  private[endothermic] var _v4_a: Float = defaultValue

  def v4_a: Float = {
    if ((initFlag4 & (1 << a_1)) == 0) {
      initFlag4 |= (1 << a_1)

      evaluations.get(a_4).foreach(i => _v4_a = i._1(quad))
    }
    _v4_a
  }

  private[endothermic] var _v4_u: Float = defaultValue

  def v4_u: Float = {
    if ((initFlag4 & (1 << u_1)) == 0) {
      initFlag4 |= (1 << u_1)

      evaluations.get(u_4).foreach(i => _v4_u = i._1(quad))
    }
    _v4_u
  }

  private[endothermic] var _v4_v: Float = defaultValue

  def v4_v: Float = {
    if ((initFlag4 & (1 << v_1)) == 0) {
      initFlag4 |= (1 << v_1)

      evaluations.get(v_4).foreach(i => _v4_v = i._1(quad))
    }
    _v4_v
  }

  private[endothermic] var _v4_lu: Float = defaultValue

  def v4_lu: Float = {
    if ((initFlag4 & (1 << lu_1)) == 0) {
      initFlag4 |= (1 << lu_1)

      evaluations.get(lu_4).foreach(i => _v4_lu = i._1(quad))
    }
    _v4_lu
  }

  private[endothermic] var _v4_lv: Float = defaultValue

  def v4_lv: Float = {
    if ((initFlag4 & (1 << lv_1)) == 0) {
      initFlag4 |= (1 << lv_1)

      evaluations.get(lv_4).foreach(i => _v4_lv = i._1(quad))
    }
    _v4_lv
  }

  private[endothermic] var _v4_nx: Float = defaultValue

  def v4_nx: Float = {
    if ((initFlag4 & (1 << nx_1)) == 0) {
      initFlag4 |= (1 << nx_1)

      evaluations.get(nx_4).foreach(i => _v4_nx = i._1(quad))
    }
    _v4_nx
  }

  private[endothermic] var _v4_ny: Float = defaultValue

  def v4_ny: Float = {
    if ((initFlag4 & (1 << ny_1)) == 0) {
      initFlag4 |= (1 << ny_1)

      evaluations.get(ny_4).foreach(i => _v4_ny = i._1(quad))
    }
    _v4_ny
  }

  private[endothermic] var _v4_nz: Float = defaultValue

  def v4_nz: Float = {
    if ((initFlag4 & (1 << nz_1)) == 0) {
      initFlag4 |= (1 << nz_1)

      evaluations.get(nz_4).foreach(i => _v4_nz = i._1(quad))
    }
    _v4_nz
  }

  private[endothermic] var _v4_p: Float = defaultValue

  def v4_p: Float = {
    if ((initFlag4 & (1 << p_1)) == 0) {
      initFlag4 |= (1 << p_1)

      evaluations.get(p_4).foreach(i => _v4_p = i._1(quad))
    }
    _v4_p
  }

  /*
  
  private[endothermic] var _v$1_$2: Float = defaultValue
def v$1_$2:Float = {
    if ((initFlag$1 & (1 << $2)) == 0) {
      initFlag$1 |= (1 << $2)
println("v$1_$2",evaluations(flatAttributeKey($2,_$1))._2)
      _v$1_$2 = evaluations(flatAttributeKey($2,_$1))._1(quad)
    }
    _v$1_$2
  }

  
   */


  lazy val toRawArray: Array[Int] = {
    val result = quad.getVertexData.clone()

    if ((changeFlag1 & (1 << x_1)) != 0)
      evaluations.get(x_1).foreach(i => i._2(_v1_x, result))
    if ((changeFlag1 & (1 << y_1)) != 0)
      evaluations.get(y_1).foreach(i => i._2(_v1_y, result))
    if ((changeFlag1 & (1 << z_1)) != 0)
      evaluations.get(z_1).foreach(i => i._2(_v1_z, result))
    if ((changeFlag1 & (1 << r_1)) != 0)
      evaluations.get(r_1).foreach(i => i._2(_v1_r, result))
    if ((changeFlag1 & (1 << g_1)) != 0)
      evaluations.get(g_1).foreach(i => i._2(_v1_g, result))
    if ((changeFlag1 & (1 << b_1)) != 0)
      evaluations.get(b_1).foreach(i => i._2(_v1_b, result))
    if ((changeFlag1 & (1 << a_1)) != 0)
      evaluations.get(a_1).foreach(i => i._2(_v1_a, result))
    if ((changeFlag1 & (1 << u_1)) != 0)
      evaluations.get(u_1).foreach(i => i._2(_v1_u, result))
    if ((changeFlag1 & (1 << v_1)) != 0)
      evaluations.get(v_1).foreach(i => i._2(_v1_v, result))
    if ((changeFlag1 & (1 << lu_1)) != 0)
      evaluations.get(lu_1).foreach(i => i._2(_v1_lu, result))
    if ((changeFlag1 & (1 << lv_1)) != 0)
      evaluations.get(lv_1).foreach(i => i._2(_v1_lv, result))
    if ((changeFlag1 & (1 << nx_1)) != 0)
      evaluations.get(nx_1).foreach(i => i._2(_v1_nx, result))
    if ((changeFlag1 & (1 << ny_1)) != 0)
      evaluations.get(ny_1).foreach(i => i._2(_v1_ny, result))
    if ((changeFlag1 & (1 << nz_1)) != 0)
      evaluations.get(nz_1).foreach(i => i._2(_v1_nz, result))
    if ((changeFlag1 & (1 << p_1)) != 0)
      evaluations.get(p_1).foreach(i => i._2(_v1_p, result))

    if ((changeFlag2 & (1 << x_1)) != 0)
      evaluations.get(x_2).foreach(i => i._2(_v2_x, result))
    if ((changeFlag2 & (1 << y_1)) != 0)
      evaluations.get(y_2).foreach(i => i._2(_v2_y, result))
    if ((changeFlag2 & (1 << z_1)) != 0)
      evaluations.get(z_2).foreach(i => i._2(_v2_z, result))
    if ((changeFlag2 & (1 << r_1)) != 0)
      evaluations.get(r_2).foreach(i => i._2(_v2_r, result))
    if ((changeFlag2 & (1 << g_1)) != 0)
      evaluations.get(g_2).foreach(i => i._2(_v2_g, result))
    if ((changeFlag2 & (1 << b_1)) != 0)
      evaluations.get(b_2).foreach(i => i._2(_v2_b, result))
    if ((changeFlag2 & (1 << a_1)) != 0)
      evaluations.get(a_2).foreach(i => i._2(_v2_a, result))
    if ((changeFlag2 & (1 << u_1)) != 0)
      evaluations.get(u_2).foreach(i => i._2(_v2_u, result))
    if ((changeFlag2 & (1 << v_1)) != 0)
      evaluations.get(v_2).foreach(i => i._2(_v2_v, result))
    if ((changeFlag2 & (1 << lu_1)) != 0)
      evaluations.get(lu_2).foreach(i => i._2(_v2_lu, result))
    if ((changeFlag2 & (1 << lv_1)) != 0)
      evaluations.get(lv_2).foreach(i => i._2(_v2_lv, result))
    if ((changeFlag2 & (1 << nx_1)) != 0)
      evaluations.get(nx_2).foreach(i => i._2(_v2_nx, result))
    if ((changeFlag2 & (1 << ny_1)) != 0)
      evaluations.get(ny_2).foreach(i => i._2(_v2_ny, result))
    if ((changeFlag2 & (1 << nz_1)) != 0)
      evaluations.get(nz_2).foreach(i => i._2(_v2_nz, result))
    if ((changeFlag2 & (1 << p_1)) != 0)
      evaluations.get(p_2).foreach(i => i._2(_v2_p, result))

    if ((changeFlag3 & (1 << x_1)) != 0)
      evaluations.get(x_3).foreach(i => i._2(_v3_x, result))
    if ((changeFlag3 & (1 << y_1)) != 0)
      evaluations.get(y_3).foreach(i => i._2(_v3_y, result))
    if ((changeFlag3 & (1 << z_1)) != 0)
      evaluations.get(z_3).foreach(i => i._2(_v3_z, result))
    if ((changeFlag3 & (1 << r_1)) != 0)
      evaluations.get(r_3).foreach(i => i._2(_v3_r, result))
    if ((changeFlag3 & (1 << g_1)) != 0)
      evaluations.get(g_3).foreach(i => i._2(_v3_g, result))
    if ((changeFlag3 & (1 << b_1)) != 0)
      evaluations.get(b_3).foreach(i => i._2(_v3_b, result))
    if ((changeFlag3 & (1 << a_1)) != 0)
      evaluations.get(a_3).foreach(i => i._2(_v3_a, result))
    if ((changeFlag3 & (1 << u_1)) != 0)
      evaluations.get(u_3).foreach(i => i._2(_v3_u, result))
    if ((changeFlag3 & (1 << v_1)) != 0)
      evaluations.get(v_3).foreach(i => i._2(_v3_v, result))
    if ((changeFlag3 & (1 << lu_1)) != 0)
      evaluations.get(lu_3).foreach(i => i._2(_v3_lu, result))
    if ((changeFlag3 & (1 << lv_1)) != 0)
      evaluations.get(lv_3).foreach(i => i._2(_v3_lv, result))
    if ((changeFlag3 & (1 << nx_1)) != 0)
      evaluations.get(nx_3).foreach(i => i._2(_v3_nx, result))
    if ((changeFlag3 & (1 << ny_1)) != 0)
      evaluations.get(ny_3).foreach(i => i._2(_v3_ny, result))
    if ((changeFlag3 & (1 << nz_1)) != 0)
      evaluations.get(nz_3).foreach(i => i._2(_v3_nz, result))
    if ((changeFlag3 & (1 << p_1)) != 0)
      evaluations.get(p_3).foreach(i => i._2(_v3_p, result))

    if ((changeFlag4 & (1 << x_1)) != 0)
      evaluations.get(x_4).foreach(i => i._2(_v4_x, result))
    if ((changeFlag4 & (1 << y_1)) != 0)
      evaluations.get(y_4).foreach(i => i._2(_v4_y, result))
    if ((changeFlag4 & (1 << z_1)) != 0)
      evaluations.get(z_4).foreach(i => i._2(_v4_z, result))
    if ((changeFlag4 & (1 << r_1)) != 0)
      evaluations.get(r_4).foreach(i => i._2(_v4_r, result))
    if ((changeFlag4 & (1 << g_1)) != 0)
      evaluations.get(g_4).foreach(i => i._2(_v4_g, result))
    if ((changeFlag4 & (1 << b_1)) != 0)
      evaluations.get(b_4).foreach(i => i._2(_v4_b, result))
    if ((changeFlag4 & (1 << a_1)) != 0)
      evaluations.get(a_4).foreach(i => i._2(_v4_a, result))
    if ((changeFlag4 & (1 << u_1)) != 0)
      evaluations.get(u_4).foreach(i => i._2(_v4_u, result))
    if ((changeFlag4 & (1 << v_1)) != 0)
      evaluations.get(v_4).foreach(i => i._2(_v4_v, result))
    if ((changeFlag4 & (1 << lu_1)) != 0)
      evaluations.get(lu_4).foreach(i => i._2(_v4_lu, result))
    if ((changeFlag4 & (1 << lv_1)) != 0)
      evaluations.get(lv_4).foreach(i => i._2(_v4_lv, result))
    if ((changeFlag4 & (1 << nx_1)) != 0)
      evaluations.get(nx_4).foreach(i => i._2(_v4_nx, result))
    if ((changeFlag4 & (1 << ny_1)) != 0)
      evaluations.get(ny_4).foreach(i => i._2(_v4_ny, result))
    if ((changeFlag4 & (1 << nz_1)) != 0)
      evaluations.get(nz_4).foreach(i => i._2(_v4_nz, result))
    if ((changeFlag4 & (1 << p_1)) != 0)
      evaluations.get(p_4).foreach(i => i._2(_v4_p, result))


    result
  }

  private[endothermic] def reconstructResult(): Self

  def reconstruct(v1_x: Float = this.v1_x,
                  v1_y: Float = this.v1_y,
                  v1_z: Float = this.v1_z,
                  v1_r: Float = this.v1_r,
                  v1_g: Float = this.v1_g,
                  v1_b: Float = this.v1_b,
                  v1_a: Float = this.v1_a,
                  v1_u: Float = this.v1_u,
                  v1_v: Float = this.v1_v,
                  v1_lu: Float = this.v1_lu,
                  v1_lv: Float = this.v1_lv,
                  v1_nx: Float = this.v1_nx,
                  v1_ny: Float = this.v1_ny,
                  v1_nz: Float = this.v1_nz,
                  v1_p: Float = this.v1_p,

                  v2_x: Float = this.v2_x,
                  v2_y: Float = this.v2_y,
                  v2_z: Float = this.v2_z,
                  v2_r: Float = this.v2_r,
                  v2_g: Float = this.v2_g,
                  v2_b: Float = this.v2_b,
                  v2_a: Float = this.v2_a,
                  v2_u: Float = this.v2_u,
                  v2_v: Float = this.v2_v,
                  v2_lu: Float = this.v2_lu,
                  v2_lv: Float = this.v2_lv,
                  v2_nx: Float = this.v2_nx,
                  v2_ny: Float = this.v2_ny,
                  v2_nz: Float = this.v2_nz,
                  v2_p: Float = this.v2_p,

                  v3_x: Float = this.v3_x,
                  v3_y: Float = this.v3_y,
                  v3_z: Float = this.v3_z,
                  v3_r: Float = this.v3_r,
                  v3_g: Float = this.v3_g,
                  v3_b: Float = this.v3_b,
                  v3_a: Float = this.v3_a,
                  v3_u: Float = this.v3_u,
                  v3_v: Float = this.v3_v,
                  v3_lu: Float = this.v3_lu,
                  v3_lv: Float = this.v3_lv,
                  v3_nx: Float = this.v3_nx,
                  v3_ny: Float = this.v3_ny,
                  v3_nz: Float = this.v3_nz,
                  v3_p: Float = this.v3_p,

                  v4_x: Float = this.v4_x,
                  v4_y: Float = this.v4_y,
                  v4_z: Float = this.v4_z,
                  v4_r: Float = this.v4_r,
                  v4_g: Float = this.v4_g,
                  v4_b: Float = this.v4_b,
                  v4_a: Float = this.v4_a,
                  v4_u: Float = this.v4_u,
                  v4_v: Float = this.v4_v,
                  v4_lu: Float = this.v4_lu,
                  v4_lv: Float = this.v4_lv,
                  v4_nx: Float = this.v4_nx,
                  v4_ny: Float = this.v4_ny,
                  v4_nz: Float = this.v4_nz,
                  v4_p: Float = this.v4_p): Self = {
    val result = reconstructResult()
    if (v1_x != _v1_x || ((initFlag1 & (1 << x_1)) == 0 && v1_x != defaultValue)) {
      result.initFlag1 |= (1 << x_1)
      result.changeFlag1 |= (1 << x_1)
      result._v1_x = v1_x
    }
    if (v1_y != _v1_y || ((initFlag1 & (1 << y_1)) == 0 && v1_y != defaultValue)) {
      result.initFlag1 |= (1 << y_1)
      result.changeFlag1 |= (1 << y_1)
      result._v1_y = v1_y
    }
    if (v1_z != _v1_z || ((initFlag1 & (1 << z_1)) == 0 && v1_z != defaultValue)) {
      result.initFlag1 |= (1 << z_1)
      result.changeFlag1 |= (1 << z_1)
      result._v1_z = v1_z
    }
    if (v1_r != _v1_r || ((initFlag1 & (1 << r_1)) == 0 && v1_r != defaultValue)) {
      result.initFlag1 |= (1 << r_1)
      result.changeFlag1 |= (1 << r_1)
      result._v1_r = v1_r
    }
    if (v1_g != _v1_g || ((initFlag1 & (1 << g_1)) == 0 && v1_g != defaultValue)) {
      result.initFlag1 |= (1 << g_1)
      result.changeFlag1 |= (1 << g_1)
      result._v1_g = v1_g
    }
    if (v1_b != _v1_b || ((initFlag1 & (1 << b_1)) == 0 && v1_b != defaultValue)) {
      result.initFlag1 |= (1 << b_1)
      result.changeFlag1 |= (1 << b_1)
      result._v1_b = v1_b
    }
    if (v1_a != _v1_a || ((initFlag1 & (1 << a_1)) == 0 && v1_a != defaultValue)) {
      result.initFlag1 |= (1 << a_1)
      result.changeFlag1 |= (1 << a_1)
      result._v1_a = v1_a
    }
    if (v1_u != _v1_u || ((initFlag1 & (1 << u_1)) == 0 && v1_u != defaultValue)) {
      result.initFlag1 |= (1 << u_1)
      result.changeFlag1 |= (1 << u_1)
      result._v1_u = v1_u
    }
    if (v1_v != _v1_v || ((initFlag1 & (1 << v_1)) == 0 && v1_v != defaultValue)) {
      result.initFlag1 |= (1 << v_1)
      result.changeFlag1 |= (1 << v_1)
      result._v1_v = v1_v
    }
    if (v1_lu != _v1_lu || ((initFlag1 & (1 << lu_1)) == 0 && v1_lu != defaultValue)) {
      result.initFlag1 |= (1 << lu_1)
      result.changeFlag1 |= (1 << lu_1)
      result._v1_lu = v1_lu
    }
    if (v1_lv != _v1_lv || ((initFlag1 & (1 << lv_1)) == 0 && v1_lv != defaultValue)) {
      result.initFlag1 |= (1 << lv_1)
      result.changeFlag1 |= (1 << lv_1)
      result._v1_lv = v1_lv
    }
    if (v1_nx != _v1_nx || ((initFlag1 & (1 << nx_1)) == 0 && v1_nx != defaultValue)) {
      result.initFlag1 |= (1 << nx_1)
      result.changeFlag1 |= (1 << nx_1)
      result._v1_nx = v1_nx
    }
    if (v1_ny != _v1_ny || ((initFlag1 & (1 << ny_1)) == 0 && v1_ny != defaultValue)) {
      result.initFlag1 |= (1 << ny_1)
      result.changeFlag1 |= (1 << ny_1)
      result._v1_ny = v1_ny
    }
    if (v1_nz != _v1_nz || ((initFlag1 & (1 << nz_1)) == 0 && v1_nz != defaultValue)) {
      result.initFlag1 |= (1 << nz_1)
      result.changeFlag1 |= (1 << nz_1)
      result._v1_nz = v1_nz
    }
    if (v1_p != _v1_p || ((initFlag1 & (1 << p_1)) == 0 && v1_p != defaultValue)) {
      result.initFlag1 |= (1 << p_1)
      result.changeFlag1 |= (1 << p_1)
      result._v1_p = v1_p
    }

    if (v2_x != _v2_x || ((initFlag2 & (1 << x_1)) == 0 && v2_x != defaultValue)) {
      result.initFlag2 |= (1 << x_1)
      result.changeFlag2 |= (1 << x_1)
      result._v2_x = v2_x
    }
    if (v2_y != _v2_y || ((initFlag2 & (1 << y_1)) == 0 && v2_y != defaultValue)) {
      result.initFlag2 |= (1 << y_1)
      result.changeFlag2 |= (1 << y_1)
      result._v2_y = v2_y
    }
    if (v2_z != _v2_z || ((initFlag2 & (1 << z_1)) == 0 && v2_z != defaultValue)) {
      result.initFlag2 |= (1 << z_1)
      result.changeFlag2 |= (1 << z_1)
      result._v2_z = v2_z
    }
    if (v2_r != _v2_r || ((initFlag2 & (1 << r_1)) == 0 && v2_r != defaultValue)) {
      result.initFlag2 |= (1 << r_1)
      result.changeFlag2 |= (1 << r_1)
      result._v2_r = v2_r
    }
    if (v2_g != _v2_g || ((initFlag2 & (1 << g_1)) == 0 && v2_g != defaultValue)) {
      result.initFlag2 |= (1 << g_1)
      result.changeFlag2 |= (1 << g_1)
      result._v2_g = v2_g
    }
    if (v2_b != _v2_b || ((initFlag2 & (1 << b_1)) == 0 && v2_b != defaultValue)) {
      result.initFlag2 |= (1 << b_1)
      result.changeFlag2 |= (1 << b_1)
      result._v2_b = v2_b
    }
    if (v2_a != _v2_a || ((initFlag2 & (1 << a_1)) == 0 && v2_a != defaultValue)) {
      result.initFlag2 |= (1 << a_1)
      result.changeFlag2 |= (1 << a_1)
      result._v2_a = v2_a
    }
    if (v2_u != _v2_u || ((initFlag2 & (1 << u_1)) == 0 && v2_u != defaultValue)) {
      result.initFlag2 |= (1 << u_1)
      result.changeFlag2 |= (1 << u_1)
      result._v2_u = v2_u
    }
    if (v2_v != _v2_v || ((initFlag2 & (1 << v_1)) == 0 && v2_v != defaultValue)) {
      result.initFlag2 |= (1 << v_1)
      result.changeFlag2 |= (1 << v_1)
      result._v2_v = v2_v
    }
    if (v2_lu != _v2_lu || ((initFlag2 & (1 << lu_1)) == 0 && v2_lu != defaultValue)) {
      result.initFlag2 |= (1 << lu_1)
      result.changeFlag2 |= (1 << lu_1)
      result._v2_lu = v2_lu
    }
    if (v2_lv != _v2_lv || ((initFlag2 & (1 << lv_1)) == 0 && v2_lv != defaultValue)) {
      result.initFlag2 |= (1 << lv_1)
      result.changeFlag2 |= (1 << lv_1)
      result._v2_lv = v2_lv
    }
    if (v2_nx != _v2_nx || ((initFlag2 & (1 << nx_1)) == 0 && v2_nx != defaultValue)) {
      result.initFlag2 |= (1 << nx_1)
      result.changeFlag2 |= (1 << nx_1)
      result._v2_nx = v2_nx
    }
    if (v2_ny != _v2_ny || ((initFlag2 & (1 << ny_1)) == 0 && v2_ny != defaultValue)) {
      result.initFlag2 |= (1 << ny_1)
      result.changeFlag2 |= (1 << ny_1)
      result._v2_ny = v2_ny
    }
    if (v2_nz != _v2_nz || ((initFlag2 & (1 << nz_1)) == 0 && v2_nz != defaultValue)) {
      result.initFlag2 |= (1 << nz_1)
      result.changeFlag2 |= (1 << nz_1)
      result._v2_nz = v2_nz
    }
    if (v2_p != _v2_p || ((initFlag2 & (1 << p_1)) == 0 && v2_p != defaultValue)) {
      result.initFlag2 |= (1 << p_1)
      result.changeFlag2 |= (1 << p_1)
      result._v2_p = v2_p
    }

    if (v3_x != _v3_x || ((initFlag3 & (1 << x_1)) == 0 && v3_x != defaultValue)) {
      result.initFlag3 |= (1 << x_1)
      result.changeFlag3 |= (1 << x_1)
      result._v3_x = v3_x
    }
    if (v3_y != _v3_y || ((initFlag3 & (1 << y_1)) == 0 && v3_y != defaultValue)) {
      result.initFlag3 |= (1 << y_1)
      result.changeFlag3 |= (1 << y_1)
      result._v3_y = v3_y
    }
    if (v3_z != _v3_z || ((initFlag3 & (1 << z_1)) == 0 && v3_z != defaultValue)) {
      result.initFlag3 |= (1 << z_1)
      result.changeFlag3 |= (1 << z_1)
      result._v3_z = v3_z
    }
    if (v3_r != _v3_r || ((initFlag3 & (1 << r_1)) == 0 && v3_r != defaultValue)) {
      result.initFlag3 |= (1 << r_1)
      result.changeFlag3 |= (1 << r_1)
      result._v3_r = v3_r
    }
    if (v3_g != _v3_g || ((initFlag3 & (1 << g_1)) == 0 && v3_g != defaultValue)) {
      result.initFlag3 |= (1 << g_1)
      result.changeFlag3 |= (1 << g_1)
      result._v3_g = v3_g
    }
    if (v3_b != _v3_b || ((initFlag3 & (1 << b_1)) == 0 && v3_b != defaultValue)) {
      result.initFlag3 |= (1 << b_1)
      result.changeFlag3 |= (1 << b_1)
      result._v3_b = v3_b
    }
    if (v3_a != _v3_a || ((initFlag3 & (1 << a_1)) == 0 && v3_a != defaultValue)) {
      result.initFlag3 |= (1 << a_1)
      result.changeFlag3 |= (1 << a_1)
      result._v3_a = v3_a
    }
    if (v3_u != _v3_u || ((initFlag3 & (1 << u_1)) == 0 && v3_u != defaultValue)) {
      result.initFlag3 |= (1 << u_1)
      result.changeFlag3 |= (1 << u_1)
      result._v3_u = v3_u
    }
    if (v3_v != _v3_v || ((initFlag3 & (1 << v_1)) == 0 && v3_v != defaultValue)) {
      result.initFlag3 |= (1 << v_1)
      result.changeFlag3 |= (1 << v_1)
      result._v3_v = v3_v
    }
    if (v3_lu != _v3_lu || ((initFlag3 & (1 << lu_1)) == 0 && v3_lu != defaultValue)) {
      result.initFlag3 |= (1 << lu_1)
      result.changeFlag3 |= (1 << lu_1)
      result._v3_lu = v3_lu
    }
    if (v3_lv != _v3_lv || ((initFlag3 & (1 << lv_1)) == 0 && v3_lv != defaultValue)) {
      result.initFlag3 |= (1 << lv_1)
      result.changeFlag3 |= (1 << lv_1)
      result._v3_lv = v3_lv
    }
    if (v3_nx != _v3_nx || ((initFlag3 & (1 << nx_1)) == 0 && v3_nx != defaultValue)) {
      result.initFlag3 |= (1 << nx_1)
      result.changeFlag3 |= (1 << nx_1)
      result._v3_nx = v3_nx
    }
    if (v3_ny != _v3_ny || ((initFlag3 & (1 << ny_1)) == 0 && v3_ny != defaultValue)) {
      result.initFlag3 |= (1 << ny_1)
      result.changeFlag3 |= (1 << ny_1)
      result._v3_ny = v3_ny
    }
    if (v3_nz != _v3_nz || ((initFlag3 & (1 << nz_1)) == 0 && v3_nz != defaultValue)) {
      result.initFlag3 |= (1 << nz_1)
      result.changeFlag3 |= (1 << nz_1)
      result._v3_nz = v3_nz
    }
    if (v3_p != _v3_p || ((initFlag3 & (1 << p_1)) == 0 && v3_p != defaultValue)) {
      result.initFlag3 |= (1 << p_1)
      result.changeFlag3 |= (1 << p_1)
      result._v3_p = v3_p
    }

    if (v4_x != _v4_x || ((initFlag4 & (1 << x_1)) == 0 && v4_x != defaultValue)) {
      result.initFlag4 |= (1 << x_1)
      result.changeFlag4 |= (1 << x_1)
      result._v4_x = v4_x
    }
    if (v4_y != _v4_y || ((initFlag4 & (1 << y_1)) == 0 && v4_y != defaultValue)) {
      result.initFlag4 |= (1 << y_1)
      result.changeFlag4 |= (1 << y_1)
      result._v4_y = v4_y
    }
    if (v4_z != _v4_z || ((initFlag4 & (1 << z_1)) == 0 && v4_z != defaultValue)) {
      result.initFlag4 |= (1 << z_1)
      result.changeFlag4 |= (1 << z_1)
      result._v4_z = v4_z
    }
    if (v4_r != _v4_r || ((initFlag4 & (1 << r_1)) == 0 && v4_r != defaultValue)) {
      result.initFlag4 |= (1 << r_1)
      result.changeFlag4 |= (1 << r_1)
      result._v4_r = v4_r
    }
    if (v4_g != _v4_g || ((initFlag4 & (1 << g_1)) == 0 && v4_g != defaultValue)) {
      result.initFlag4 |= (1 << g_1)
      result.changeFlag4 |= (1 << g_1)
      result._v4_g = v4_g
    }
    if (v4_b != _v4_b || ((initFlag4 & (1 << b_1)) == 0 && v4_b != defaultValue)) {
      result.initFlag4 |= (1 << b_1)
      result.changeFlag4 |= (1 << b_1)
      result._v4_b = v4_b
    }
    if (v4_a != _v4_a || ((initFlag4 & (1 << a_1)) == 0 && v4_a != defaultValue)) {
      result.initFlag4 |= (1 << a_1)
      result.changeFlag4 |= (1 << a_1)
      result._v4_a = v4_a
    }
    if (v4_u != _v4_u || ((initFlag4 & (1 << u_1)) == 0 && v4_u != defaultValue)) {
      result.initFlag4 |= (1 << u_1)
      result.changeFlag4 |= (1 << u_1)
      result._v4_u = v4_u
    }
    if (v4_v != _v4_v || ((initFlag4 & (1 << v_1)) == 0 && v4_v != defaultValue)) {
      result.initFlag4 |= (1 << v_1)
      result.changeFlag4 |= (1 << v_1)
      result._v4_v = v4_v
    }
    if (v4_lu != _v4_lu || ((initFlag4 & (1 << lu_1)) == 0 && v4_lu != defaultValue)) {
      result.initFlag4 |= (1 << lu_1)
      result.changeFlag4 |= (1 << lu_1)
      result._v4_lu = v4_lu
    }
    if (v4_lv != _v4_lv || ((initFlag4 & (1 << lv_1)) == 0 && v4_lv != defaultValue)) {
      result.initFlag4 |= (1 << lv_1)
      result.changeFlag4 |= (1 << lv_1)
      result._v4_lv = v4_lv
    }
    if (v4_nx != _v4_nx || ((initFlag4 & (1 << nx_1)) == 0 && v4_nx != defaultValue)) {
      result.initFlag4 |= (1 << nx_1)
      result.changeFlag4 |= (1 << nx_1)
      result._v4_nx = v4_nx
    }
    if (v4_ny != _v4_ny || ((initFlag4 & (1 << ny_1)) == 0 && v4_ny != defaultValue)) {
      result.initFlag4 |= (1 << ny_1)
      result.changeFlag4 |= (1 << ny_1)
      result._v4_ny = v4_ny
    }
    if (v4_nz != _v4_nz || ((initFlag4 & (1 << nz_1)) == 0 && v4_nz != defaultValue)) {
      result.initFlag4 |= (1 << nz_1)
      result.changeFlag4 |= (1 << nz_1)
      result._v4_nz = v4_nz
    }
    if (v4_p != _v4_p || ((initFlag4 & (1 << p_1)) == 0 && v4_p != defaultValue)) {
      result.initFlag4 |= (1 << p_1)
      result.changeFlag4 |= (1 << p_1)
      result._v4_p = v4_p
    }

    result
  }

  /*
  
  r._v$1_x=v$1
r._v$1_y=v$1
r._v$1_z=v$1
r._v$1_r=v$1
r._v$1_g=v$1
r._v$1_b=v$1
r._v$1_a=v$1
r._v$1_u=v$1
r._v$1_v=v$1
r._v$1_lu=v$1
r._v$1_lv=v$1
r._v$1_nx=v$1
r._v$1_ny=v$1
r._v$1_nz=v$1
r._v$1_p=v$1


if (v$1_$2 != _v$1_$2 || ((initFlag$1 & (1 << $2)) == 0 && v$1_$2 != defaultValue)) {
      result.initFlag$1 |= (1 << $2)
      result.changeFlag$1 |= (1 << $2)
      result._v$1_$2 = v$1_$2
    }

  
   */

}

object BaseUnpackedQuad {

  val defaultValue = Float.MinValue + 11

  implicit def flagIndex(vfei: (VertexFormatElement, Int, Vertex)): Int =
    UnpackEvaluations.flagsIndices(vfei._1) + vfei._2

  val x_1 = (DefaultVertexFormats.POSITION_3F, 0, _1)
  val x_2 = (DefaultVertexFormats.POSITION_3F, 0, _2)
  val x_3 = (DefaultVertexFormats.POSITION_3F, 0, _3)
  val x_4 = (DefaultVertexFormats.POSITION_3F, 0, _4)

  val y_1 = (DefaultVertexFormats.POSITION_3F, 1, _1)
  val y_2 = (DefaultVertexFormats.POSITION_3F, 1, _2)
  val y_3 = (DefaultVertexFormats.POSITION_3F, 1, _3)
  val y_4 = (DefaultVertexFormats.POSITION_3F, 1, _4)

  val z_1 = (DefaultVertexFormats.POSITION_3F, 2, _1)
  val z_2 = (DefaultVertexFormats.POSITION_3F, 2, _2)
  val z_3 = (DefaultVertexFormats.POSITION_3F, 2, _3)
  val z_4 = (DefaultVertexFormats.POSITION_3F, 2, _4)

  val r_1 = (DefaultVertexFormats.COLOR_4UB, 0, _1)
  val r_2 = (DefaultVertexFormats.COLOR_4UB, 0, _2)
  val r_3 = (DefaultVertexFormats.COLOR_4UB, 0, _3)
  val r_4 = (DefaultVertexFormats.COLOR_4UB, 0, _4)

  val g_1 = (DefaultVertexFormats.COLOR_4UB, 1, _1)
  val g_2 = (DefaultVertexFormats.COLOR_4UB, 1, _2)
  val g_3 = (DefaultVertexFormats.COLOR_4UB, 1, _3)
  val g_4 = (DefaultVertexFormats.COLOR_4UB, 1, _4)

  val b_1 = (DefaultVertexFormats.COLOR_4UB, 2, _1)
  val b_2 = (DefaultVertexFormats.COLOR_4UB, 2, _2)
  val b_3 = (DefaultVertexFormats.COLOR_4UB, 2, _3)
  val b_4 = (DefaultVertexFormats.COLOR_4UB, 2, _4)

  val a_1 = (DefaultVertexFormats.COLOR_4UB, 3, _1)
  val a_2 = (DefaultVertexFormats.COLOR_4UB, 3, _2)
  val a_3 = (DefaultVertexFormats.COLOR_4UB, 3, _3)
  val a_4 = (DefaultVertexFormats.COLOR_4UB, 3, _4)

  val u_1 = (DefaultVertexFormats.TEX_2F, 0, _1)
  val u_2 = (DefaultVertexFormats.TEX_2F, 0, _2)
  val u_3 = (DefaultVertexFormats.TEX_2F, 0, _3)
  val u_4 = (DefaultVertexFormats.TEX_2F, 0, _4)

  val v_1 = (DefaultVertexFormats.TEX_2F, 1, _1)
  val v_2 = (DefaultVertexFormats.TEX_2F, 1, _2)
  val v_3 = (DefaultVertexFormats.TEX_2F, 1, _3)
  val v_4 = (DefaultVertexFormats.TEX_2F, 1, _4)

  val lu_1 = (DefaultVertexFormats.TEX_2S, 0, _1)
  val lu_2 = (DefaultVertexFormats.TEX_2S, 0, _2)
  val lu_3 = (DefaultVertexFormats.TEX_2S, 0, _3)
  val lu_4 = (DefaultVertexFormats.TEX_2S, 0, _4)

  val lv_1 = (DefaultVertexFormats.TEX_2S, 1, _1)
  val lv_2 = (DefaultVertexFormats.TEX_2S, 1, _2)
  val lv_3 = (DefaultVertexFormats.TEX_2S, 1, _3)
  val lv_4 = (DefaultVertexFormats.TEX_2S, 1, _4)

  val nx_1 = (DefaultVertexFormats.NORMAL_3B, 0, _1)
  val nx_2 = (DefaultVertexFormats.NORMAL_3B, 0, _2)
  val nx_3 = (DefaultVertexFormats.NORMAL_3B, 0, _3)
  val nx_4 = (DefaultVertexFormats.NORMAL_3B, 0, _4)

  val ny_1 = (DefaultVertexFormats.NORMAL_3B, 1, _1)
  val ny_2 = (DefaultVertexFormats.NORMAL_3B, 1, _2)
  val ny_3 = (DefaultVertexFormats.NORMAL_3B, 1, _3)
  val ny_4 = (DefaultVertexFormats.NORMAL_3B, 1, _4)

  val nz_1 = (DefaultVertexFormats.NORMAL_3B, 2, _1)
  val nz_2 = (DefaultVertexFormats.NORMAL_3B, 2, _2)
  val nz_3 = (DefaultVertexFormats.NORMAL_3B, 2, _3)
  val nz_4 = (DefaultVertexFormats.NORMAL_3B, 2, _4)

  val p_1 = (DefaultVertexFormats.PADDING_1B, 3, _1)
  val p_2 = (DefaultVertexFormats.PADDING_1B, 3, _2)
  val p_3 = (DefaultVertexFormats.PADDING_1B, 3, _3)
  val p_4 = (DefaultVertexFormats.PADDING_1B, 3, _4)


}