package ru.mousecray.endmagic.runes

case class RunePart(partType: EnumPartType, colorId: Byte) {

}

object RunePart {

  object EmptyPart extends RunePart(EnumPartType.Empty, 0)

  def apply(partType: EnumPartType, colorId: Int): RunePart = new RunePart(partType, colorId.toByte)

}
