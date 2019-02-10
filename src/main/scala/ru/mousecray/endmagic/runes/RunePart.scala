package ru.mousecray.endmagic.runes

case class RunePart(x: Int, y: Int, partType: EnumPartType, colorId: Byte) {

}

object RunePart {
  def apply(x: Int, y: Int, partType: EnumPartType, colorId:Int): RunePart = new RunePart(x, y, partType, colorId.toByte)

}
