package ru.mousecray.endmagic.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//idea only
public abstract class ShaderProgram {
    private int programId;
    private int vertexShaderID = -1;
    private int fragmentShaderID = -1;

    protected static InputStream getStream(ResourceLocation location) {
        try {
            return Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ShaderProgram(InputStream fragmentShaderSource) {
        fragmentShaderID = loadShader(fragmentShaderSource);
        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, fragmentShaderID);
        GL20.glLinkProgram(programId);
        GL20.glValidateProgram(programId);
        cleanShader();
    }

    private static int loadShader(InputStream file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int shaderId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(shaderId, stringBuilder);
        GL20.glCompileShader(shaderId);
        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.print("Could not compile fragment shader.");
            System.err.println();
            System.err.print(new Throwable().getStackTrace()[2].getClassName());
            System.err.println();
            System.err.print(GL20.glGetShaderInfoLog(shaderId, 2000));
            System.err.println();
        } else {
            try {
                System.out.print("Compiled " + Class.forName(new Throwable().getStackTrace()[2].getClassName()).getSimpleName() + "(fragment" + ")");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String str = GL20.glGetShaderInfoLog(shaderId, 2000);
            if (!str.trim().isEmpty()) {
                System.out.println();
                System.out.print(str);
            }
            System.out.println();
        }

        return shaderId;
    }

    public void start() {
        GL20.glUseProgram(programId);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void cleanShader() {
        GL20.glDetachShader(programId, fragmentShaderID);
        GL20.glDeleteShader(fragmentShaderID);
    }

    public int pid() {
        return programId;
    }
}