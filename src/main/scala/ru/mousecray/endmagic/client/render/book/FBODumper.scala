package ru.mousecray.endmagic.client.render.book

import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL12}

import scala.util.Try

object FBODumper {

  def dump(): Unit = {
    if (!dumped) {
      dumped = true

      val w = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH)
      val h = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT)

      val buf = BufferUtils.createIntBuffer(w * h)

      buf.clear()
      GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL11.GL_UNSIGNED_BYTE, buf)

      val array = new Array[Int](w * h)
      buf.get(array)

      val tmp = new File("./test_fbo.png")
      if(tmp.exists())
        tmp.delete()
      val img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
      img.setRGB(0, 0, w, h, array, 0, w)

      println("testlol", Try {
        ImageIO.write(img, "png", tmp)
      })

    }
  }

  var dumped = false

}
