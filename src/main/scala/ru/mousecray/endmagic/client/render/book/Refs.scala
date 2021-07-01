package ru.mousecray.endmagic.client.render.book

import net.minecraft.client.Minecraft

object Refs {
  val resolutionMultiplier = 3

  val backgroundPixelSize = Minecraft.getMinecraft.fontRenderer.FONT_HEIGHT

  val textureW = 25
  val textureH = 20

  val pageContainerWidth = textureW * backgroundPixelSize * 2 * resolutionMultiplier
  val pageHeight = textureH * backgroundPixelSize * 2 * resolutionMultiplier

  val w = pageContainerWidth / 2 / resolutionMultiplier
  val h = pageHeight / 2 / resolutionMultiplier

  val contentWidth = (textureW / 2 - 1) * backgroundPixelSize
  val contentHeight = (textureH - 2) * backgroundPixelSize

  val leftPageStartX = 1 * backgroundPixelSize
  val rightPageStartX = (textureW / 2 + 1) * backgroundPixelSize

}
