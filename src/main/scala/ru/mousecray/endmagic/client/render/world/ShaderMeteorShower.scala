package ru.mousecray.endmagic.client.render.world

import java.io.{BufferedReader, InputStreamReader}
import java.util.stream.Collectors

import org.lwjgl.opengl.{GL11, GL20}
import ru.mousecray.endmagic.EM

object ShaderMeteorShower {

  lazy val shaderCode: String = {
    val stream = getClass.getResourceAsStream("/assets/" + EM.ID + "/shaders/meteor_shower.frag")
    new BufferedReader(new InputStreamReader(stream))
      .lines().collect(Collectors.joining("\n"))
  }

  lazy val shaderID: Int = {
    val id = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER)
    GL20.glShaderSource(id, shaderCode)
    GL20.glCompileShader(id)

    if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
      throw new Exception("Shader compilation error!\n" +
        GL20.glGetShaderInfoLog(id, GL20.
          glGetShaderi(id, GL20.GL_INFO_LOG_LENGTH)))
      GL20.glDeleteShader(id)
      0
    } else
      id
  }

  lazy val programID: Int = {
    val id = GL20.glCreateProgram()
    GL20.glAttachShader(id, shaderID)
    GL20.glLinkProgram(id)

    if (GL20.glGetProgrami(id, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
      throw new Exception("Shader link error!\n" +
        GL20.glGetProgramInfoLog(id, GL20.
          glGetProgrami(id, GL20.GL_INFO_LOG_LENGTH)))
      0
    } else {
      GL20.glValidateProgram(id)

      if (GL20.glGetProgrami(id, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
        throw new Exception("Shader validate error!\n" +
          GL20.glGetProgramInfoLog(id, GL20.
            glGetProgrami(id, GL20.GL_INFO_LOG_LENGTH)))
        0
      } else
        id
    }
  }

}
