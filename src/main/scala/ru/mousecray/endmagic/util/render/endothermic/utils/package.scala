package ru.mousecray.endmagic.util.render.endothermic

import scala.language.implicitConversions

package object utils {

  @inline implicit def double2Float(v: Double): Float = v.toFloat

  final val standard_pixel = 1f / 16

}
