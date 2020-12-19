package ru.mousecray.endmagic.client.render.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
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
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {

            Minecraft mc = Minecraft.getMinecraft();

            int current = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);

            if (framebuffer == null)
                framebuffer = new Framebuffer(100, 100, false);

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();

            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

            //set target to framebuffer
            framebuffer.bindFramebuffer(true);


            //use shader
            GL20.glUseProgram(ShaderMeteorShower.programID());
            GL20.glUniform1f(GL20.glGetUniformLocation(ShaderMeteorShower.programID(), "iTime"), ((float) (System.currentTimeMillis() - startTime) / (100 * 2)));
            GL20.glUniform2f(GL20.glGetUniformLocation(ShaderMeteorShower.programID(), "iResolution"), 100, 100);
            GL20.glUniform1f(GL20.glGetUniformLocation(ShaderMeteorShower.programID(), "seed"), (float) (startTime % 1000));
            //draw quad
            {
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
                bufferbuilder.pos(0, 100, 0).endVertex();
                bufferbuilder.pos(100, 100, 0).endVertex();
                bufferbuilder.pos(100, 0, 0).endVertex();
                bufferbuilder.pos(0, 0, 0).endVertex();
                tessellator.draw();
            }
            //use 0 shader
            GL20.glUseProgram(0);
            //set target to default
            mc.getFramebuffer().bindFramebuffer(true);

            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GlStateManager.popMatrix();
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GlStateManager.popMatrix();

            framebuffer.bindFramebufferTexture();
            {
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
                bufferbuilder.pos(0, 100, 0).endVertex();
                bufferbuilder.pos(100, 100, 0).endVertex();
                bufferbuilder.pos(100, 0, 0).endVertex();
                bufferbuilder.pos(0, 0, 0).endVertex();
                tessellator.draw();
            }
            framebuffer.unbindFramebufferTexture();
            framebuffer.framebufferClear();
            mc.getFramebuffer().bindFramebuffer(true);
            GL11.glBindTexture(GL_TEXTURE_2D, current);
        }
    }

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_N))
            startTime = System.currentTimeMillis();
    }

    //@SubscribeEvent
    public static void onRenderWorld(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (framebuffer == null)
            framebuffer = new Framebuffer(100, 100, false);

        //set target to framebuffer
        framebuffer.bindFramebuffer(false);
        //use shader
        GL20.glUseProgram(ShaderMeteorShower.programID());

        GL20.glUniform1f(GL20.glGetUniformLocation(ShaderMeteorShower.programID(), "iTime"), System.currentTimeMillis() % Float.MAX_VALUE);

        GL20.glUniform2f(GL20.glGetUniformLocation(ShaderMeteorShower.programID(), "iResolution"), 100, 100);
        //draw quad
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        buffer.pos(0, 0, 0).endVertex();
        buffer.pos(100, 0, 0).endVertex();
        buffer.pos(100, 100, 0).endVertex();
        buffer.pos(0, 100, 0).endVertex();
        tessellator.draw();
        //use 0 shader
        GL20.glUseProgram(0);
        //set target to default
        mc.getFramebuffer().bindFramebuffer(false);
        //bind framebuffer as texture
        GL11.glBindTexture(GL_TEXTURE_2D, framebuffer.framebufferTexture);

        //GlStateManager.disableCull();
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
        GlStateManager.color(1, 1, 1, 1);
    }
}

