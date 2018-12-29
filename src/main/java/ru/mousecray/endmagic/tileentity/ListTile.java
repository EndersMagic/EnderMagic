package ru.mousecray.endmagic.tileentity;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.mousecray.endmagic.client.renderer.RendererTileAltar;

public class ListTile {

	public static void onRegister() {
		GameRegistry.registerTileEntity(TileTPBlock.class, "TileTPBlock");
		GameRegistry.registerTileEntity(TileTPBlockH.class, "TileTPBlockH");
		GameRegistry.registerTileEntity(TileAltar.class, "TileAltar");
		GameRegistry.registerTileEntity(TileArtifactor.class, "TileArtifactor");
	}
	
	public static void onRender() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileAltar.class, new RendererTileAltar());
	}
}
