package ru.mousecray.endmagic.api.embook.components;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.api.embook.ChapterComponent;
import ru.mousecray.endmagic.api.embook.ComponentType;

public class TextComponent extends ChapterComponent {

	public TextComponent(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	@SideOnly(Side.CLIENT) 
	public void render(Minecraft mc) {
		
	}

	@Override
	public ComponentType getComponentType() {
		return ComponentType.TEXT;
	}
}