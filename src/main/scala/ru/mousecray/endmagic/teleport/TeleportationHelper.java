package ru.mousecray.endmagic.teleport;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WilliHay
 * Based on https://github.com/willihay/teleportation-works/blob/1.12/src/main/java/org/bensam/tpworks/capability/teleportation/TeleportationHelper.java
 */
public class TeleportationHelper {

    public static class CustomTeleporter implements ITeleporter {
        protected final WorldServer teleportWorld;
        private final double x;
        private final double y;
        private final double z;

        public CustomTeleporter(WorldServer teleportWorld, double x, double y, double z) {
            this.teleportWorld = teleportWorld;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public void placeEntity(World teleportWorld, Entity entity, float yaw) {
            if (entity instanceof EntityPlayerMP) {
                ((EntityPlayerMP) entity).connection.setPlayerLocation(x, y, z, yaw, entity.rotationPitch);
            } else {
                entity.setLocationAndAngles(x, y, z, yaw, entity.rotationPitch);
            }
        }
    }

    /**
     * /**
     * Remount rider to entity ridden.
     */
    public static void remountRider(Entity rider, Entity entityRidden) {
        if (!rider.isRiding()
                && rider.dimension == entityRidden.dimension
                && (rider.getPosition().distanceSqToCenter(entityRidden.posX, entityRidden.posY, entityRidden.posZ) < 4.0D)) {
            rider.startRiding(entityRidden, true);
            if (rider instanceof EntityPlayerMP) {
                // Send an explicit vehicle move packet to update ridden entity with latest position info.
                // (If not done, then the normal move packet can override the teleport in some conditions (e.g. when they're close to the teleport destination) and send both player and vehicle back to their pre-teleport position!)
                ((EntityPlayerMP) rider).connection.netManager.sendPacket(new SPacketMoveVehicle(entityRidden));
            }
        }
    }

    public static void teleportEntityAndPassengers(Entity entity, int dimension, double x, double y, double z) {

        // Start a list of teleporting entities and add the target entity.
        List<Entity> teleportingEntities = getAllPassenges(entity);

        // Check to make sure none of the teleporting entities are already at the destination.
        // (If so, the group may have already teleported this tick.)
        for (Entity entityToTeleport : teleportingEntities) {
            if (entityToTeleport.dimension != dimension)
                continue;

            if (isPositionSame(x, y, z, entityToTeleport))
                return; // cancel the teleport because we found an entity that is already at the destination
        }

        // Get a map of all the entities that are riding other entities, so the pair can be remounted later, after teleportation.
        HashMap<Entity, Entity> riderMap = getRiders(teleportingEntities);

        // Teleport all entities.
        for (Entity entityToTeleport : teleportingEntities) {
            Entity teleportedEntity = null;
            boolean hasPassengers = riderMap.containsValue(entityToTeleport);

            teleportedEntity = teleport(entityToTeleport, dimension, x, y, z);

            // Non-player entities get cloned when they teleport across dimensions.
            // If the teleported entity had passengers, see if the object changed.
            if (hasPassengers && (entityToTeleport != teleportedEntity)) {
                // Update the riderMap with the new object.
                for (Map.Entry<Entity, Entity> riderSet : riderMap.entrySet()) {
                    if (riderSet.getValue() == entityToTeleport) {
                        riderSet.setValue(teleportedEntity);
                    }
                }
            }
        }

        // Take care of any remounting of rider to entity ridden.
        for (Map.Entry<Entity, Entity> riderSet : riderMap.entrySet()) {
            Entity rider = riderSet.getKey();
            Entity entityRidden = riderSet.getValue();
            remountRider(rider, entityRidden);
        }
    }

    public static List<Entity> getAllPassenges(Entity entity) {
        List<Entity> teleportingEntities = new ArrayList<>();
        teleportingEntities.add(entity);

        // Add any other entity that the target entity is riding.
        if (entity.isRiding()) {
            teleportingEntities.add(entity.getRidingEntity());
        }

        // Add all the passengers of the entity to the list of teleporting entities.
        for (Entity passenger : entity.getPassengers()) {
            teleportingEntities.add(passenger);
            for (Entity passengerOfPassenger : passenger.getPassengers()) {
                teleportingEntities.add(passengerOfPassenger);
            }
        }
        return teleportingEntities;
    }

    public static HashMap<Entity, Entity> getRiders(List<Entity> list) {
        HashMap<Entity, Entity> riderMap = new HashMap<>();

        for (Entity entity : list) {
            if (entity.isRiding()) {
                riderMap.put(entity, entity.getRidingEntity());
            }
        }

        return riderMap;
    }

    private static boolean isPositionSame(double x, double y, double z, Entity entityToTeleport) {
        return entityToTeleport.getPosition().equals(new BlockPos(x, y, z));
    }

    /**
     * Teleport an entity, anything it is riding, and all its passengers, remounting them as needed after teleporting them all.
     */
    public static void teleportEntityAndPassengers(Entity entity, Location destination) {
        teleportEntityAndPassengers(entity, destination.dim, destination.x + 0.5, destination.y + 0.25, destination.z + 0.5);
    }

    /**
     * Teleport an entity to a destination. Returns the entity object after teleportation, since it may have been cloned in the process.
     */

    private static Entity teleport(Entity entityToTeleport, int teleportDimension,
                                   double x, double y, double z) {
        if (entityToTeleport.world.isRemote) // running on client
            return entityToTeleport;

        int entityCurrentDimension = entityToTeleport.dimension;
        WorldServer teleportWorld = getWorldServerForDimension(teleportDimension);

        // Dismount teleporting entity or passengers riding this entity, if applicable.
        if (entityToTeleport.isRiding()) {
            entityToTeleport.dismountRidingEntity();
        }
        if (entityToTeleport.isBeingRidden()) {
            entityToTeleport.removePassengers();
        }

        BlockPos preTeleportPosition = entityToTeleport.getPosition();

        // Set entity facing direction (yaw - N/S/E/W).
        entityToTeleport.setPositionAndRotation(entityToTeleport.posX, entityToTeleport.posY, entityToTeleport.posZ, entityToTeleport.rotationYaw, entityToTeleport.rotationPitch);

        if (entityCurrentDimension != teleportDimension) {
            System.out.println(String.format("Using CustomTeleporter to teleport %s to dimension %s",
                    entityToTeleport.getDisplayName().getFormattedText(),
                    teleportDimension));

            // Transfer teleporting entity to teleport destination in different dimension.
            entityToTeleport = entityToTeleport.changeDimension(teleportDimension, new CustomTeleporter(teleportWorld, x, y, z));
        } else {
            // Teleport entity to destination.

            // Try attemptTeleport first because it has some extra, interesting render effects.
            // Note that the Y-coordinate is specified one block HIGHER because of how the attemptTeleport function
            //   starts looking for safe teleport positions one block BELOW the specified Y-coordinate.
            if (entityToTeleport instanceof EntityLivingBase && ((EntityLivingBase) entityToTeleport)
                    .attemptTeleport(x, y + 1, z)) {
                System.out.println(String.format("Teleported %s in dimension %s",
                        entityToTeleport.getDisplayName().getFormattedText(),
                        teleportDimension));
            } else {
                // If we can't do it the "pretty way", just force it!
                // This should be a safe teleport position. Hopefully they survive teh magiks. :P
                entityToTeleport.setPositionAndUpdate(x, y,
                        z);
                System.out.println(String.format("Force-Teleported %s in dimension %s",
                        entityToTeleport.getDisplayName().getFormattedText(),
                        teleportDimension));
            }
        }

        return entityToTeleport;
    }

    public static WorldServer getWorldServerForDimension(int dimension) {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(dimension);
    }
}