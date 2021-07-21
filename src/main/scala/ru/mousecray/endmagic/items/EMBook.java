package ru.mousecray.endmagic.items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.client.gui.GuiScreenEMBook;
import ru.mousecray.endmagic.entity.EntityEMBook;
import ru.mousecray.endmagic.init.EMItems;

@Mod.EventBusSubscriber(modid = EM.ID)
public class EMBook extends Item implements ItemOneWhiteEMTextured {

    public EMBook() {
        setMaxStackSize(1);
    }

    @Override
    public String getCustomName() {
        return "em_book";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (world.isRemote) Minecraft.getMinecraft().displayGuiScreen(GuiScreenEMBook.instance);
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public String texture() {
        return "em_book";
    }

    @SubscribeEvent
    public static void onItemSpawn(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityItem && !(event.getEntity() instanceof EntityEMBook)) {
            EntityItem entityItem = (EntityItem) event.getEntity();
            if (entityItem.getItem().getItem() == EMItems.emBook) {
                event.setCanceled(true);
                event.getWorld().spawnEntity(new EntityEMBook(event.getWorld(), entityItem));
            }
        }
    }
/*
    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new EntityEMBook(world);
    }*/
}
