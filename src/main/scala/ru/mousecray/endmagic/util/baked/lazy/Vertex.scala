package ru.mousecray.endmagic.util.baked.`lazy`

import ru.mousecray.endmagic.util.elix_x.ecomms.color.RGBA

class Vertex(_pos: => (Float, Float, Float), _color: => RGBA, _uv: => (Float, Float), _lightmap: => (Short, Short), _normal: => (Byte, Byte, Byte)) {
  lazy val pos: (Float, Float, Float) = _pos
  lazy val color: RGBA = _color
  lazy val uv: (Float, Float) = _uv
  lazy val lightmap: (Short, Short) = _lightmap
  lazy val normal: (Byte, Byte, Byte) = _normal

}
