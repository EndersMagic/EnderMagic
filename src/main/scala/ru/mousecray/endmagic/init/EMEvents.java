package ru.mousecray.endmagic.init;

import codechicken.lib.packet.PacketCustom;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.capability.chunk.IRuneChunkCapability;
import ru.mousecray.endmagic.capability.chunk.RuneStateCapabilityProvider;
import ru.mousecray.endmagic.capability.world.PhantomAvoidingGroup;
import ru.mousecray.endmagic.capability.world.PhantomAvoidingGroupCapability;
import ru.mousecray.endmagic.capability.world.PhantomAvoidingGroupCapabilityProvider;
import ru.mousecray.endmagic.entity.EntityEnderArrow;
import ru.mousecray.endmagic.entity.UnexplosibleEntityItem;
import ru.mousecray.endmagic.items.EnderArrow;
import ru.mousecray.endmagic.network.PacketTypes;
import ru.mousecray.endmagic.rune.RuneIndex;
import ru.mousecray.endmagic.tileentity.TilePhantomAvoidingBlockBase;
import ru.mousecray.endmagic.util.EnderBlockTypes;
import ru.mousecray.endmagic.util.worldgen.WorldGenUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import static ru.mousecray.endmagic.capability.chunk.RuneStateCapabilityProvider.runeStateCapability;
import static ru.mousecray.endmagic.init.EMBlocks.enderLeaves;
import static ru.mousecray.endmagic.init.EMBlocks.enderLog;
import static ru.mousecray.endmagic.network.PacketTypes.UPDATE_COMPAS_TARGET;
import static ru.mousecray.endmagic.network.PacketTypes.UPDATE_PHANROM_AVOIDINCAPABILITY;
import static ru.mousecray.endmagic.tileentity.TilePhantomAvoidingBlockBase.maxAvoidTicks;
import static ru.mousecray.endmagic.worldgen.trees.WorldGenPhantomTree.areaRequirementsMax;
import static ru.mousecray.endmagic.worldgen.trees.WorldGenPhantomTree.areaRequirementsMin;

@EventBusSubscriber(modid = EM.ID)
public class EMEvents {

    @SubscribeEvent
    public static void onChunkWatch(ChunkWatchEvent event) {
        Chunk chunk = event.getChunkInstance();
        IRuneChunkCapability capability = RuneIndex.getCapability(chunk);
        NBTBase nbt = runeStateCapability.writeNBT(capability, null);
        if (nbt != null)
            PacketTypes.SYNC_RUNE_CAPABILITY.packet().writeInt(chunk.x).writeInt(chunk.z).writeNBTTagCompound((NBTTagCompound) nbt).sendToPlayer(event.getPlayer());
    }

    @SubscribeEvent
    public static void onCapaAttachToWorld(AttachCapabilitiesEvent<World> event) {
        event.addCapability(PhantomAvoidingGroupCapabilityProvider.name, new PhantomAvoidingGroupCapabilityProvider());
    }

    @SubscribeEvent
    public static void onCapaAttachToChunk(AttachCapabilitiesEvent<Chunk> event) {
        event.addCapability(RuneStateCapabilityProvider.name, new RuneStateCapabilityProvider());
    }

    @SubscribeEvent
    public static void onPhantomTreeCutting(PlayerEvent.BreakSpeed event) {
        World world = event.getEntityPlayer().world;
        BlockPos pos = event.getPos();
        IBlockState blockState = world.getBlockState(pos);
        if (/*!event.getEntityPlayer().world.isRemote && */(blockState.getBlock() == enderLog || blockState.getBlock() == enderLeaves) && blockState.getValue(enderLog.blockType) == EnderBlockTypes.EnderTreeType.PHANTOM) {
            PhantomAvoidingGroupCapability capability = world.getCapability(PhantomAvoidingGroupCapabilityProvider.avoidingGroupCapability, null);
            if (capability != null) {
                PhantomAvoidingGroup tree = capability.groupAtPos.get(event.getPos());
                if (tree == null) {
                    tree = new PhantomAvoidingGroup();
                    collectNewGroup(tree, pos, world);

                    for (BlockPos p : tree.blocks)
                        capability.groupAtPos.put(p, tree);

                    capability.allGroups.add(tree);
                }
                tree.avoidingStarted = true;
                System.out.print("");
            }
        }
    }

    private static void collectNewGroup(PhantomAvoidingGroup tree, BlockPos pos, World world) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TilePhantomAvoidingBlockBase) {
            BlockPos saplingPos = pos.subtract(((TilePhantomAvoidingBlockBase) tileEntity).offsetFromSapling);
            WorldGenUtils.generateInArea(saplingPos.add(areaRequirementsMin), saplingPos.add(areaRequirementsMax), p -> {
                TileEntity tileEntity1 = world.getTileEntity(p);
                if (tileEntity1 instanceof TilePhantomAvoidingBlockBase) {
                    tree.blocks.add(p.toImmutable());
                }
            });
        }
    }

    private static void updateWorldCapability(World world) {
        PhantomAvoidingGroupCapability capability = world.getCapability(PhantomAvoidingGroupCapabilityProvider.avoidingGroupCapability, null);
        if (capability != null) {

            capability.allGroups.removeAll(capability.forRemove);
            capability.forRemove.forEach(g -> g.blocks.forEach(p -> capability.groupAtPos.remove(p)));
            capability.allGroups.addAll(capability.forAdded);
            capability.forAdded.forEach(g -> g.blocks.forEach(p -> capability.groupAtPos.put(p, g)));

            capability.forRemove.clear();
            capability.forAdded.clear();

            for (PhantomAvoidingGroup group : capability.allGroups) {
                if (group.avoidingStarted) {

                    if (group.increment > 0) {
                        if (group.avoidTicks >= maxAvoidTicks) {
                            if (!world.isRemote)
                                teleportTree(world, group, capability);
                            capability.forRemove.add(group);
                            group.avoidingStarted = false;
                        } else
                            group.avoidTicks += group.increment;
                    } else if (group.increment < 0) {
                        if (group.avoidTicks <= 0) {
                            capability.forRemove.add(group);
                            group.avoidingStarted = false;
                        } else
                            group.avoidTicks += group.increment;
                    }
                    for (BlockPos pos : group.blocks) {
                        TileEntity tileEntity = world.getTileEntity(pos);
                        if (tileEntity instanceof TilePhantomAvoidingBlockBase) {
                            TilePhantomAvoidingBlockBase tilePhantomAvoidingBlock = (TilePhantomAvoidingBlockBase) tileEntity;
                            tilePhantomAvoidingBlock.avoidTicks = group.avoidTicks;
                            tilePhantomAvoidingBlock.increment = group.increment;
                        }
                    }
                }
            }
        }
    }

    private static final int teleportRadius = 40;

    private static void teleportTree(World world, PhantomAvoidingGroup group, PhantomAvoidingGroupCapability capability) {
        Optional<TilePhantomAvoidingBlockBase> anyTile = group.blocks.stream().map(world::getTileEntity).filter(t -> t instanceof TilePhantomAvoidingBlockBase).map(t -> (TilePhantomAvoidingBlockBase) t).findAny();
        anyTile.ifPresent(t -> {
            BlockPos saplingPos = t.getPos().subtract(t.offsetFromSapling);
            BlockPos newSaplingPos;
            do {
                newSaplingPos = world.getTopSolidOrLiquidBlock(saplingPos.add(world.rand.nextInt(2 * teleportRadius) - teleportRadius, 0, world.rand.nextInt(2 * teleportRadius) - teleportRadius));
            } while (newSaplingPos.getY() == -1);
            System.out.println(world.getBlockState(newSaplingPos).getBlock());

            BlockPos teleportOffset = newSaplingPos.subtract(saplingPos);

            PhantomAvoidingGroup newGroup = new PhantomAvoidingGroup();
            newGroup.avoidTicks = group.avoidTicks;
            newGroup.increment = -1;
            newGroup.avoidingStarted = true;

            for (BlockPos pos : group.blocks) {
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof TilePhantomAvoidingBlockBase)
                    newGroup.blocks.add(((TilePhantomAvoidingBlockBase) tileEntity).teleportBlock(teleportOffset));
            }
            capability.forAdded.add(newGroup);
            syncCapability(world, capability, newGroup);
        });
    }

    private static void syncCapability(World world, PhantomAvoidingGroupCapability capability, PhantomAvoidingGroup newGroup) {
        int toDimension = world.provider.getDimension();
        PacketCustom packet = UPDATE_PHANROM_AVOIDINCAPABILITY.packet()
                .writeInt(toDimension)
                .writeBoolean(newGroup.avoidingStarted)
                .writeInt(newGroup.avoidTicks)
                .writeInt(newGroup.increment)
                .writeInt(newGroup.blocks.size());
        newGroup.blocks.forEach(packet::writePos);
        packet.sendToDimension(toDimension);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onWorldTickClient(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world != null)
            updateWorldCapability(Minecraft.getMinecraft().world);
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        updateWorldCapability(event.world);
    }

    @SubscribeEvent
    public static void onPlayerEnter(EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote)
            if (event.getEntity() instanceof EntityPlayer) {
                Optional.ofNullable(((WorldServer) event.getWorld()).getChunkProvider()
                        .getNearestStructurePos(event.getWorld(), "Stronghold", new BlockPos(event.getEntity()), false))
                        .map(pos ->
                                UPDATE_COMPAS_TARGET.packet()
                                        .writeInt(0)
                                        .writePos(pos))
                        .ifPresent(p -> p.sendToPlayer((EntityPlayer) event.getEntity()));
            }
    }

    @SideOnly(Side.CLIENT)
    //    @SubscribeEvent
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

    //TODO: onUseBonemeal
    @SubscribeEvent
    public static void onUseBonemeal(BonemealEvent event) {
//    	World world  = event.getWorld();
//    	BlockPos pos = event.getPos();
//    	Random rand = event.getEntityPlayer().getRNG();
//    	if(event.getBlock().getBlock() instanceof IEndSoil) {
//    		IEndSoil soil = (IEndSoil)event.getBlock().getBlock();
//    		if(soil.canUseBonemeal()) {
//	    		List<BlockPos> existPos = EMUtils.isSoil(world, pos.add(-1, 0, -1), pos.add(1, 0, 1), false, true, EndSoilType.DIRT, EndSoilType.GRASS);
//	    		for(BlockPos pos2 : existPos) {
//	    			IEndSoil soil2 = (IEndSoil)world.getBlockState(pos2).getBlock();
//	    			IBlockState state = soil2.getBonemealCrops(rand, event.getEntityPlayer(), world.getBlockState(pos2));
//	    			if (state.getBlock() != Blocks.AIR && world.isAirBlock(pos2.up())) world.setBlockState(pos2.up(), state);
//	        		ItemDye.spawnBonemealParticles(world, pos, 5);
//	    		}
//	    		event.setResult(Result.ALLOW);
//    		}
//    	}
//    	else if (event.getBlock().getBlock() == Blocks.END_STONE) {
//    		for (int x = -1; x < 2; ++x) {
//    			for (int z = -1; z < 2; ++z) {
//        			if (world.isAirBlock(pos.add(x, 1, z)) && world.getBlockState(pos.add(x, 0, z)).getBlock() == Blocks.END_STONE && event.getEntityPlayer().getRNG().nextInt(500) > 498) {
//        				world.setBlockState(pos.add(x, 1, z), EMBlocks.enderTallgrass.getDefaultState());
//        	    		ItemDye.spawnBonemealParticles(world, pos, 5);
//        			}
//    			}
//    		}
//    		event.setResult(Result.ALLOW);
//    	}
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
