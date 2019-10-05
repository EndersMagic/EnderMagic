package ru.mousecray.endmagic.init;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import com.google.common.collect.ImmutableMap;

import codechicken.lib.packet.PacketCustom;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListWorldSelection;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.IEndSoil;
import ru.mousecray.endmagic.entity.EntityEnderArrow;
import ru.mousecray.endmagic.entity.UnexplosibleEntityItem;
import ru.mousecray.endmagic.items.EnderArrow;
import ru.mousecray.endmagic.network.ClientPacketHandler;

@EventBusSubscriber(modid = EM.ID)
public class EMEvents {

    @SubscribeEvent
    public static void onPlayerEnter(EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote)
            if (event.getEntity() instanceof EntityPlayer) {
                Optional.ofNullable(((WorldServer) event.getWorld()).getChunkProvider()
                        .getNearestStructurePos(event.getWorld(), "Stronghold", new BlockPos(event.getEntity()), false))
                        .map(pos ->
                                new PacketCustom(EM.ID, ClientPacketHandler.UPDATE_COMPAS_TARGET)
                                        .writeInt(0)
                                        .writePos(pos))
                        .ifPresent(p -> p.sendToPlayer((EntityPlayer) event.getEntity()));
            }
    }

    @SubscribeEvent
    public static void loadLastWorld(GuiOpenEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (event.getGui() instanceof GuiMainMenu) {
            mc.displayGuiScreen(new GuiWorldSelection((GuiMainMenu) event.getGui()));
        } else if (event.getGui() instanceof GuiWorldSelection) {
            GuiListWorldSelection guiListWorldSelection = new GuiListWorldSelection((GuiWorldSelection) event.getGui(), mc, 100, 100, 32, 100 - 64, 36);
            try {
                guiListWorldSelection.getListEntry(0).joinWorld();
            } catch (Exception ignore) {
            }
        }
    }
    
    @SubscribeEvent
    public static void onUseBonemeal(BonemealEvent event) {
    	World world  = event.getWorld();
    	BlockPos pos = event.getPos();
    	Random rand = event.getEntityPlayer().getRNG();
    	if (EMUtils.isSoil(world, pos, false)) ((IEndSoil)event.getBlock().getBlock()).onUseBonemeal(world, pos, event.getEntityPlayer().getRNG(), event.getEntityPlayer());
    	else if (event.getBlock().getBlock() == Blocks.END_STONE) {
    		for (int x = -1; x < 2; ++x) {
    			for (int z = -1; z < 2; ++z) {
        			if (world.isAirBlock(pos.add(x, 1, z)) && event.getEntityPlayer().getRNG().nextInt(10) > 7) {
        				world.setBlockState(pos.add(x, 1, z), EMBlocks.enderOrchid.getDefaultState());	
        			}
    			}
    		}
    		for (int i = 0; i < 32; ++i) world.spawnParticle(EnumParticleTypes.PORTAL, pos.up().getX(), pos.up().getY() + rand.nextDouble() * 2.0D, pos.up().getZ(), rand.nextGaussian(), 0.0D, rand.nextGaussian());
    	}
    }

    @SubscribeEvent
    public static void onPressureExplosionCoal(ExplosionEvent.Start event) {
        Vec3d vec = event.getExplosion().getPosition();
        World world = event.getWorld();
        findPressureStructure(world, new BlockPos(vec.x, vec.y, vec.z))
                .flatMap(i -> {
                    Optional<EntityItem> diamond = getDiamond(world, i);
                    world.setBlockToAir(i);
                    return diamond;
                })
                .ifPresent(world::spawnEntity);
    }

    private static ImmutableMap<Block, Item> coal2diamond = ImmutableMap.of(
            EMBlocks.dragonCoal, EMItems.dragonDiamond,
            EMBlocks.immortalCoal, EMItems.naturalDiamond,
            EMBlocks.naturalCoal, EMItems.phantomDiamond,
            EMBlocks.phantomCoal, EMItems.immortalDiamond
    );

    private static Optional<EntityItem> getDiamond(World world, BlockPos i) {
        return Optional.ofNullable(coal2diamond.get(world.getBlockState(i).getBlock()))
                .map(item -> new UnexplosibleEntityItem(world, i.getX() + 0.5, i.getY() + 0.5, i.getZ() + 0.5,
                        new ItemStack(item)));
    }

    private static Optional<BlockPos> findPressureStructure(World world, BlockPos explosionPosition) {
        Optional<BlockPos> coal = findCoal(world, explosionPosition);
        Optional<Integer> obsidiancount = coal.map(coalPos ->
                getCountOfObsidianAround(world, explosionPosition) + getCountOfObsidianAround(world, coalPos))
                .filter(i -> i >= 10);
        return obsidiancount.flatMap(__ -> coal);
    }

    private static int getCountOfObsidianAround(World world, BlockPos explosionPosition) {
        return (int) Arrays.stream(EnumFacing.values()).filter(side -> {
            BlockPos pos = explosionPosition.add(side.getDirectionVec());
            return world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN;
        }).count();
    }

    private static Optional<BlockPos> findCoal(World world, BlockPos explosionPosition) {
        return Arrays.stream(EnumFacing.values())
                .map(side -> explosionPosition.add(side.getDirectionVec()))
                .filter(pos -> isCoal(world.getBlockState(pos).getBlock()))
                .findFirst();
    }

    private static boolean isCoal(Block block) {
        return block == EMBlocks.dragonCoal ||
                block == EMBlocks.immortalCoal ||
                block == EMBlocks.naturalCoal ||
                block == EMBlocks.phantomCoal;
    }

    //Not finished.
    @SubscribeEvent
    public static void onPlayerLooseArrow(ArrowLooseEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        //get has arrow
        boolean flag = event.hasAmmo();
        //get has ender arrow
        ItemStack stack = findAmmo(player);

        if (flag && stack.getItem() == EMItems.enderArrow) {
            //canceling vanilla event
            event.setCanceled(true);

            World world = event.getWorld();

            float f = ItemBow.getArrowVelocity(event.getCharge());

            if ((double) f >= 0.1D) {
                boolean flag1 = player.capabilities.isCreativeMode || ((EnderArrow) stack.getItem()).isInfinite(stack, stack, player);

                if (!world.isRemote) {
                    EnderArrow arrow = (EnderArrow) (stack.getItem());
                    EntityEnderArrow entityarrow = arrow.createArrow(world, player);
                    entityarrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, f * 3.0F, 1.0F);

                    if (f == 1.0F) entityarrow.setIsCritical(true);

                    int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

                    if (j > 0) entityarrow.setDamage(entityarrow.getDamage() + (double) j * 0.5D + 0.5D);

                    int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

                    if (k > 0) entityarrow.setKnockbackStrength(k);

                    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) entityarrow.setFire(100);

                    stack.damageItem(1, player);

                    if (flag1 || player.capabilities.isCreativeMode)
                        entityarrow.pickupStatus = PickupStatus.CREATIVE_ONLY;

                    world.spawnEntity(entityarrow);
                }

                world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (new Random().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                if (!flag1 && !player.capabilities.isCreativeMode) {
                    stack.shrink(1);

                    if (stack.isEmpty()) {
                        player.inventory.deleteStack(stack);
                    }
                }

                player.addStat(StatList.getObjectUseStats(stack.getItem()));
            }
        }
    }

    //TODO: Publishing standard ItemBow's method
    private static ItemStack findAmmo(EntityPlayer player) {
        if (EMUtils.isArrow(player.getHeldItem(EnumHand.OFF_HAND))) return player.getHeldItem(EnumHand.OFF_HAND);
        else if (EMUtils.isArrow(player.getHeldItem(EnumHand.MAIN_HAND))) return player.getHeldItem(EnumHand.MAIN_HAND);
        else for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (EMUtils.isArrow(stack)) return stack;
            }

        return ItemStack.EMPTY;
    }
}
