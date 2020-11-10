package ru.mousecray.endmagic.teleport;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

/**
 * @author WilliHay
 *
 */
public class TeleportationHelper
{

    @Nullable
    public static BlockPos findSafeTeleportPos(World world, Entity entityToTeleport, TeleportDestination destination)
    {
        BlockPos safePos = null;
        BlockPos destinationPos = destination.position;

        if (destinationPos.equals(BlockPos.ORIGIN))
            return null;

        // Calculate a safe position at the teleport destination to which the entity can be teleported.
        switch (destination.destinationType)
        {
            case SPAWNBED:
                safePos = findSafeTeleportPosNearBed(world, destinationPos);
                break;
            case BEACON:
            case RAIL:
                safePos = findSafeTeleportPosToPassableBlock(world, destinationPos, entityToTeleport.height);
                break;
            case BLOCKPOS:
                safePos = findSafeTeleportPosToBlock(world, destinationPos, entityToTeleport.height);
                break;
            default:
                break;
        }

        return safePos;
    }

    @Nullable
    private static BlockPos findSafeTeleportPosNearBed(World world, BlockPos bedPos)
    {
        IBlockState blockState = world.getBlockState(bedPos);
        Block block = blockState.getBlock();

        if (block != Blocks.BED)
            return null; // not a bed

        return BlockBed.getSafeExitLocation(world, bedPos, 0);
    }

    @Nullable
    private static BlockPos findSafeTeleportPosToPassableBlock(World world, BlockPos blockPos, float height)
    {
        if (height <= 1.0F)
            return blockPos;

        int heightAboveInBlocks = MathHelper.ceil(height - 1.0F);

        for (int i = 1; i <= heightAboveInBlocks; ++i)
        {
            IBlockState teleportBlockState = world.getBlockState(blockPos.up(i));
            if (teleportBlockState.getMaterial().isSolid())
            {
                return null; // teleport position not safe
            }
        }

        return blockPos;
    }

    @Nullable
    private static BlockPos findSafeTeleportPosToBlock(World world, BlockPos blockPos, float height)
    {
        int heightInBlocks = MathHelper.ceil(height);

        for (int i = 0; i < heightInBlocks; ++i)
        {
            IBlockState teleportBlockState = world.getBlockState(blockPos.up(i));
            if (teleportBlockState.getMaterial().isSolid())
            {
                return null; // teleport position not safe
            }
        }

        return blockPos;
    }

    /**
     * Remount rider to entity ridden.
     */
    public static void remountRider(Entity rider, Entity entityRidden)
    {
        if (!rider.isRiding()
                && rider.dimension == entityRidden.dimension
                && (rider.getPosition().distanceSqToCenter(entityRidden.posX, entityRidden.posY, entityRidden.posZ) < 4.0D))
        {
            rider.startRiding(entityRidden, true);
            if (rider instanceof EntityPlayerMP)
            {
                // Send an explicit vehicle move packet to update ridden entity with latest position info.
                // (If not done, then the normal move packet can override the teleport in some conditions (e.g. when they're close to the teleport destination) and send both player and vehicle back to their pre-teleport position!)
                ((EntityPlayerMP) rider).connection.netManager.sendPacket(new SPacketMoveVehicle(entityRidden));
            }
        }
    }

    /**
     * Notify players near the teleport destination that an entity is about to teleport there.
     */
    public static void sendIncomingTeleportMessage(TeleportDestination destination)
    {/*
        World teleportWorld = ModUtil.getWorldServerForDimension(destination.dimension);
        TileEntity te = teleportWorld.getTileEntity(destination.position);

        if (te instanceof ITeleportationTileEntity)
        {
            TeleportationWorks.network.sendToAllAround(new PacketUpdateTeleportIncoming(destination.position, destination.dimension),
                    new NetworkRegistry.TargetPoint(destination.dimension, destination.position.getX(), destination.position.getY(), destination.position.getZ(), 50.0D));
        }*/
    }

    /**
     * Teleport an entity, anything it is riding, and all its passengers, remounting them as needed after teleporting them all.
     */
    public static void teleportEntityAndPassengers(Entity entity, TeleportDestination destination)
    {
        World teleportWorld = entity.getServer().getWorld(destination.dimension);

        // Start a list of teleporting entities and add the target entity.
        List<Entity> teleportingEntities = new ArrayList<>();
        teleportingEntities.add(entity);

        // Add any other entity that the target entity is riding.
        if (entity.isRiding())
        {
            teleportingEntities.add(entity.getRidingEntity());
        }

        // Add all the passengers of the entity to the list of teleporting entities.
        for (Entity passenger : entity.getPassengers())
        {
            teleportingEntities.add(passenger);
            for (Entity passengerOfPassenger : passenger.getPassengers())
            {
                teleportingEntities.add(passengerOfPassenger);
            }
        }

        // Check to make sure none of the teleporting entities are already at the destination.
        // (If so, the group may have already teleported this tick.)
        for (Entity entityToTeleport : teleportingEntities)
        {
            if (entityToTeleport.dimension != destination.dimension)
            {
                continue;
            }

            if (entityToTeleport.getPosition().equals(findSafeTeleportPos(teleportWorld, entityToTeleport, destination)))
            {
                return; // cancel the teleport because we found an entity that is already at the destination
            }
        }

        // Get a map of all the entities that are riding other entities, so the pair can be remounted later, after teleportation.
        HashMap<Entity, Entity> riderMap = getRiders(teleportingEntities);

        // Teleport all entities.
        for (Entity entityToTeleport : teleportingEntities)
        {
            Entity teleportedEntity = null;
            boolean hasPassengers = riderMap.containsValue(entityToTeleport);

            teleportedEntity = teleport(entityToTeleport, destination);

            // Non-player entities get cloned when they teleport across dimensions.
            // If the teleported entity had passengers, see if the object changed.
            if (hasPassengers && (entityToTeleport != teleportedEntity))
            {
                // Update the riderMap with the new object.
                for (Map.Entry<Entity, Entity> riderSet : riderMap.entrySet())
                {
                    if (riderSet.getValue() == entityToTeleport)
                    {
                        riderSet.setValue(teleportedEntity);
                    }
                }
            }
        }

        // Take care of any remounting of rider to entity ridden.
        for (Map.Entry<Entity, Entity> riderSet : riderMap.entrySet())
        {
            Entity rider = riderSet.getKey();
            Entity entityRidden = riderSet.getValue();
            remountRider(rider, entityRidden);
        }
    }
    public static HashMap<Entity, Entity> getRiders(List<Entity> list)
    {
        HashMap<Entity, Entity> riderMap = new HashMap<Entity, Entity>();

        for (Entity entity : list)
        {
            if (entity.isRiding())
            {
                riderMap.put(entity, entity.getRidingEntity());
            }
        }

        return riderMap;
    }

    /**
     * Teleport an entity to a destination. Returns the entity object after teleportation, since it may have been cloned in the process.
     */
    public static Entity teleport(Entity entityToTeleport, TeleportDestination destination)
    {
        World currentWorld = entityToTeleport.world;
        int teleportDimension = destination.dimension;
        World teleportWorld = entityToTeleport.getServer().getWorld(teleportDimension);
        BlockPos destinationPos = destination.position;
        BlockPos safePos = findSafeTeleportPos(teleportWorld, entityToTeleport, destination);
        float rotationYaw = entityToTeleport.rotationYaw;

        if (safePos == null)
            return entityToTeleport; // no safe position found - do an early return instead of the requested teleport

        TileEntity te = teleportWorld.getTileEntity(destinationPos);

        // Notify players near the teleport destination that an entity is about to teleport there.
        sendIncomingTeleportMessage(destination);

        // Teleport the entity.
        Entity teleportedEntity = teleport(currentWorld, entityToTeleport, teleportDimension, safePos, rotationYaw);

        // If entity teleported to a cube, add it to the list of entities that have teleported there.
        /*if (te instanceof TileEntityTeleportCube)
        {
            ((TileEntityTeleportCube) te).addTeleportedEntity(teleportedEntity);
        }*/

        return teleportedEntity;
    }

    private static Entity teleport(World currentWorld, Entity entityToTeleport, int teleportDimension,
                                   BlockPos teleportPos, float playerRotationYaw)
    {
        if (currentWorld.isRemote) // running on client
            return entityToTeleport;

        int entityCurrentDimension = entityToTeleport.dimension;
        WorldServer teleportWorld = entityToTeleport.getServer().getWorld(teleportDimension);

        // Dismount teleporting entity or passengers riding this entity, if applicable.
        if (entityToTeleport.isRiding())
        {
            entityToTeleport.dismountRidingEntity();
        }
        if (entityToTeleport.isBeingRidden())
        {
            entityToTeleport.removePassengers();
        }

        BlockPos preTeleportPosition = entityToTeleport.getPosition();

        // Set entity facing direction (yaw - N/S/E/W).
        entityToTeleport.setPositionAndRotation(entityToTeleport.posX, entityToTeleport.posY, entityToTeleport.posZ, playerRotationYaw, entityToTeleport.rotationPitch);

        if (entityCurrentDimension != teleportDimension)
        {
            // Transfer teleporting entity to teleport destination in different dimension.
            //entityToTeleport = entityToTeleport.changeDimension(teleportDimension, new CustomTeleporter(teleportWorld, teleportPos));
        }
        else
        {
            // Teleport entity to destination.

            // Try attemptTeleport first because it has some extra, interesting render effects.
            // Note that the Y-coordinate is specified one block HIGHER because of how the attemptTeleport function
            //   starts looking for safe teleport positions one block BELOW the specified Y-coordinate.
            if (entityToTeleport instanceof EntityLivingBase && ((EntityLivingBase) entityToTeleport)
                    .attemptTeleport(teleportPos.getX() + 0.5D, teleportPos.up().getY() + 0.25D, teleportPos.getZ() + 0.5D))
            {
            }
            else
            {
                // If we can't do it the "pretty way", just force it!
                // This should be a safe teleport position. Hopefully they survive teh magiks. :P
                entityToTeleport.setPositionAndUpdate(teleportPos.getX() + 0.5D, teleportPos.getY() + 0.25D,
                        teleportPos.getZ() + 0.5D);
            }
        }

        // Play teleport sound at the starting position and final position of the teleporting entity.
        currentWorld.playSound((EntityPlayer) null, preTeleportPosition, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT,
                SoundCategory.PLAYERS, 1.0F, 1.0F);
        teleportWorld.playSound((EntityPlayer) null, teleportPos, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT,
                SoundCategory.PLAYERS, 1.0F, 1.0F);

        return entityToTeleport;
    }
}