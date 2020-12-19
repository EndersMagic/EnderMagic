package ru.mousecray.endmagic.client.render.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.Sphere;
import ru.mousecray.endmagic.EM;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.util.glu.GLU.*;

@Mod.EventBusSubscriber(modid = EM.ID, value = Side.CLIENT)
public class SkyRenderer {

    private static Framebuffer framebuffer;

    private static long startTime = 0;

    @SubscribeEvent
    public static void ovRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (false && event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {

            Minecraft mc = Minecraft.getMinecraft();

            createFBOTexture(mc);


            int current = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D); //save prev texture

            //draw with fbo texture
            framebuffer.bindFramebufferTexture();
            {
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                bufferbuilder.pos(0, 100, 0).tex(0, 1).endVertex();
                bufferbuilder.pos(100, 100, 0).tex(1, 1).endVertex();
                bufferbuilder.pos(100, 0, 0).tex(1, 0).endVertex();
                bufferbuilder.pos(0, 0, 0).tex(0, 0).endVertex();
                tessellator.draw();
            }
            framebuffer.unbindFramebufferTexture();

            //optional: clear fbo and restore texture
            {
                //framebuffer.framebufferClear();
                //mc.getFramebuffer().bindFramebuffer(true);
                GL11.glBindTexture(GL_TEXTURE_2D, current);
            }
        }
    }

    private static void createFBOTexture(Minecraft mc) {
        int textureSize = 500;
        if (framebuffer == null) //lazy init
            framebuffer = new Framebuffer(textureSize, textureSize, false);


        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); //optional

        //set target to framebuffer
        framebuffer.bindFramebuffer(true);

        //identity matrix
        {
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPushMatrix();
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glPushMatrix();

            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
        }

        //setup projection area
        GL11.glOrtho(0, textureSize, textureSize, 0, -10, 10);


        //use shader
        GL20.glUseProgram(ShaderMeteorShower.programID());
        GL20.glUniform1f(GL20.glGetUniformLocation(ShaderMeteorShower.programID(), "iTime"), ((float) (System.currentTimeMillis() - startTime) / (100 * 16)));
        GL20.glUniform2f(GL20.glGetUniformLocation(ShaderMeteorShower.programID(), "iResolution"), textureSize, textureSize);
        GL20.glUniform1f(GL20.glGetUniformLocation(ShaderMeteorShower.programID(), "seed"), (float) (startTime % 1000));
        //draw quad
        {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
            bufferbuilder.pos(0, textureSize, 0).endVertex();
            bufferbuilder.pos(textureSize, textureSize, 0).endVertex();
            bufferbuilder.pos(textureSize, 0, 0).endVertex();
            bufferbuilder.pos(0, 0, 0).endVertex();
            tessellator.draw();
        }
        //use 0 shader
        GL20.glUseProgram(0);


        //restore matrices
        {
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPopMatrix();
        }

        //set target to default
        mc.getFramebuffer().bindFramebuffer(true);
    }

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_N))
            startTime = System.currentTimeMillis();
    }

    @SubscribeEvent
    public static void onRenderWorld(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        createFBOTexture(mc);
        renderSkySphere(mc);

    }

    private static void renderSkySphere(Minecraft mc) {

        int current = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D); //save prev texture
        //GlStateManager.disableCull();
        framebuffer.bindFramebufferTexture();

        //mc.getTextureManager().bindTexture(new ResourceLocation(EM.ID, "textures/test_sky.png"));
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.pushMatrix();
        {
            //GlStateManager.color(0, 1, 1, 0.5f);
            Sphere sphere = new Sphere();
            sphere.setDrawStyle(GLU_FILL);
            sphere.setOrientation(GLU_INSIDE);
            sphere.setTextureFlag(true);
            sphere.setNormals(GLU_SMOOTH);
            sphere.draw(150, 70, 70);
        }
        GlStateManager.popMatrix();
        //GlStateManager.enableCull();

        framebuffer.unbindFramebufferTexture();
        //optional: clear fbo and restore texture
        {
            //framebuffer.framebufferClear();
            //mc.getFramebuffer().bindFramebuffer(true);
            GL11.glBindTexture(GL_TEXTURE_2D, current);
        }
    }
}

