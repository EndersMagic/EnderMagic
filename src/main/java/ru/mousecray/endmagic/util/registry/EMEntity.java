package ru.mousecray.endmagic.util.registry;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Render must have constructor with only RenderManager
 * EG:
 * public MyRenderClass(RenderManager manager) {
 *     super(manager)
 * }
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EMEntity {
    Class<? extends Render<? extends Entity>> renderClass();
}