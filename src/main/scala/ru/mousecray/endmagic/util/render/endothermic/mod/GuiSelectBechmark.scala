package ru.mousecray.endmagic.util.render.endothermic.mod

import net.minecraft.client.gui.{GuiButton, GuiScreen, GuiTextField}

class GuiSelectBechmark(tile: TestingStand.TileTestingStand) extends GuiScreen {

  var b: GuiTextField = _
  var i: GuiTextField = _

  override def initGui(): Unit = {
    super.initGui()
    val center = this.width / 2
    b = new GuiTextField(0, mc.fontRenderer, center - 50, 10, 100, 20)
    i = new GuiTextField(1, mc.fontRenderer, center - 50, 40, 100, 20)
    b.setFocused(false)
    b.setCanLoseFocus(true)
    i.setFocused(true)
    i.setCanLoseFocus(true)
    b.setText(tile.current._1)
    i.setText(tile.current._2)
    buttonList.add(new GuiButton(2, center - 10, 70, "Confirm"))
  }

  override def actionPerformed(button: GuiButton): Unit = {
    if (button.id == 2) {
      this.mc.displayGuiScreen(null.asInstanceOf[GuiScreen])
      if (this.mc.currentScreen == null)
        this.mc.setIngameFocus()
    }
  }

  override def onGuiClosed(): Unit = {
    tile.current = b.getText -> i.getText
  }

  override def drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float): Unit = {
    super.drawScreen(mouseX, mouseY, partialTicks)
    val center = this.width / 2
    mc.fontRenderer.drawString("bechmark", center - 100, 10, 0xFFFFFF)
    mc.fontRenderer.drawString("influence", center - 100, 40, 0xFFFFFF)
    b.drawTextBox()
    i.drawTextBox()
  }

  override def mouseClicked(x: Int, y: Int, mouseButton: Int): Unit = {
    super.mouseClicked(x, y, mouseButton)
    b.mouseClicked(x, y, mouseButton)
    i.mouseClicked(x, y, mouseButton)
    if (mouseButton == 1) {
      val bool = x >= b.x && x < b.x + b.width && y >= b.y && y < b.y + b.height
      if (bool) b.setText("")
      if (x >= i.x && x < i.x + i.width && y >= i.y && y < i.y + i.height) i.setText("")
    }
  }

  override def updateScreen(): Unit = {
    super.updateScreen()
    b.updateCursorCounter()
    i.updateCursorCounter()
  }

  override def keyTyped(typedChar: Char, keyCode: Int): Unit = {
    super.keyTyped(typedChar, keyCode)
    b.textboxKeyTyped(typedChar, keyCode)
    i.textboxKeyTyped(typedChar, keyCode)
  }

}
