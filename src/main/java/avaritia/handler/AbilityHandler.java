package avaritia.handler;

import avaritia.items.InfinityArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static net.minecraft.entity.EquipmentSlot.*;

/**
 * Handles all abilities for ANY LivingEntity.
 * Some abilities are player specific, but just don't give a zombie your boots..
 */
public class AbilityHandler {

    //@formatter:off
    public static final Set<String> entitiesWithHelmets =     new HashSet<>();
    public static final Set<String> entitiesWithChestplates = new HashSet<>();
    public static final Set<String> entitiesWithLeggings =    new HashSet<>();
    public static final Set<String> entitiesWithBoots =       new HashSet<>();
    public static final Set<String> entitiesWithFlight =      new HashSet<>();
    //@formatter:on

    public static boolean isPlayerWearing(LivingEntity entity, EquipmentSlot slot, Predicate<Item> predicate) {
        ItemStack stack = entity.getEquippedStack(slot);
        return !stack.isEmpty() && predicate.test(stack.getItem());
    }

    //Updates all ability states for an entity, Handles firing updates and state changes.
    public static ActionResult updateAbilities(LivingEntity entity) {
        if ((entity instanceof PlayerEntity)) {
            String key = entity.getUuidAsString() + "|" + entity.world.isClient;

            boolean hasHelmet = isPlayerWearing(entity, HEAD, item -> item instanceof InfinityArmor);
            boolean hasChestplate = isPlayerWearing(entity, CHEST, item -> item instanceof InfinityArmor);
            boolean hasLeggings = isPlayerWearing(entity, LEGS, item -> item instanceof InfinityArmor);
            boolean hasBoots = isPlayerWearing(entity, FEET, item -> item instanceof InfinityArmor);

            //Helmet toggle.
            if (hasHelmet) {
                entitiesWithHelmets.add(key);
                handleHelmetStateChange(entity, true);
            }
            if (!hasHelmet) {
                entitiesWithHelmets.remove(key);
                handleHelmetStateChange(entity, false);
            }

            //Chestplate toggle.
            if (hasChestplate) {
                entitiesWithChestplates.add(key);
                handleChestplateStateChange(entity, true);
            }
            if (!hasChestplate) {
                entitiesWithChestplates.remove(key);
                handleChestplateStateChange(entity, false);
            }

            //Leggings toggle.
            if (hasLeggings) {
                entitiesWithLeggings.add(key);
                handleLeggingsStateChange(entity, true);
            }
            if (!hasLeggings) {
                entitiesWithLeggings.remove(key);
                handleLeggingsStateChange(entity, false);
            }

            //Boots toggle.
            if (hasBoots) {
                handleBootsStateChange(entity);
                entitiesWithBoots.add(key);
            }
            if (!hasBoots) {
                handleBootsStateChange(entity);
                entitiesWithBoots.remove(key);
            }

            //Active ability ticking.
            if (entitiesWithHelmets.contains(key)) {
                tickHelmetAbilities(entity);
            }
            if (entitiesWithChestplates.contains(key)) {
                tickChestplateAbilities(entity);
            }
            if (entitiesWithLeggings.contains(key)) {
                tickLeggingsAbilities(entity);
            }
            if (entitiesWithBoots.contains(key)) {
                tickBootsAbilities(entity);
            }
        }

        return ActionResult.PASS;
    }

    /**
     * Strips all Abilities from an entity if the entity had any special abilities.
     *
     * @param entity EntityLivingBase we speak of.
     */
    private static void stripAbilities(LivingEntity entity) {
        String key = entity.getUuidAsString() + "|" + entity.world.isClient;

        if (entitiesWithHelmets.remove(key)) {
            handleHelmetStateChange(entity, false);
        }

        if (entitiesWithChestplates.remove(key)) {
            handleChestplateStateChange(entity, false);
        }

        if (entitiesWithLeggings.remove(key)) {
            handleLeggingsStateChange(entity, false);
        }

        if (entitiesWithBoots.remove(key)) {
            handleBootsStateChange(entity);
        }
    }

    //region StateChanging
    private static void handleHelmetStateChange(LivingEntity entity, boolean isNew) {
        //TODO, Helmet abilities? Water breathing, NightVision, Auto Eat or No Hunger, No bad effects.
    }

    private static void handleChestplateStateChange(LivingEntity entity, boolean isNew) {
        String key = entity.getUuidAsString() + "|" + entity.world.isClient;
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = ((PlayerEntity) entity);
            if (isNew) {
                player.abilities.allowFlying = true;
                entitiesWithFlight.add(key);
            } else {
                if (!player.abilities.creativeMode && entitiesWithFlight.contains(key)) {
                    player.abilities.allowFlying = false;
                    player.abilities.flying = false;
                    entitiesWithFlight.remove(key);
                }
            }
        }
    }

    private static void handleLeggingsStateChange(LivingEntity entity, boolean isNew) {

    }

    private static void handleBootsStateChange(LivingEntity entity) {
        String temp_key = entity.getUuidAsString() + "|" + entity.world.isClient;
        boolean hasBoots = isPlayerWearing(entity, FEET, item -> item instanceof InfinityArmor);
        if (hasBoots) {
            entity.stepHeight = 1.0625F;//Step 17 pixels, Allows for stepping directly from a path to the top of a block next to the path.
            if (!entitiesWithBoots.contains(temp_key)) {
                entitiesWithBoots.add(temp_key);
            }
        } else {
            if (entitiesWithBoots.contains(temp_key)) {
                entity.stepHeight = 0.5F;
                entitiesWithBoots.remove(temp_key);
            }
        }
    }
    //endregion

    //region Ability Ticking
    private static void tickHelmetAbilities(LivingEntity entity) {

    }

    private static void tickChestplateAbilities(LivingEntity entity) {

    }

    private static void tickLeggingsAbilities(LivingEntity entity) {

    }

    private static void tickBootsAbilities(LivingEntity entity) {
        boolean flying = entity instanceof PlayerEntity && ((PlayerEntity) entity).abilities.flying;
        boolean swimming = entity.isSubmergedInWater() || entity.isSwimming();
        if (entity.isOnGround() || flying || swimming) {
            boolean sneaking = entity.isSneaking();

            float speed = 0.15f * (flying ? 1.1f : 1.0f)
                //* (swimming ? 1.2f : 1.0f)
                * (sneaking ? 0.1f : 1.0f);

            if (entity.forwardSpeed > 0f) {
                entity.updateVelocity(speed, new Vec3d(0, 0, 1));
                //entity.moveRelative(0f, 0f, 1f, speed);
            } else if (entity.forwardSpeed < 0f) {
                entity.updateVelocity(-speed * 0.3f, new Vec3d(0, 0, 1));
            }

            if (entity.sidewaysSpeed != 0f) {
                entity.updateVelocity(speed * 0.5f * Math.signum(entity.sidewaysSpeed), new Vec3d(1, 0, 0));
            }
        }
    }
    //endregion

    //region Ability Specific Events
    public static void jumpBoost(LivingEntity entity) {
        if (entitiesWithBoots.contains( entity.getUuidAsString() + "|" + entity.world.isClient)) {
            Vec3d vec3d = entity.getVelocity();
            entity.setVelocity(vec3d.x, vec3d.y + 0.4f, vec3d.z);
        }
    }
    //endregion

    //region Ability Striping Events
    //These are anything that should strip all abilities from an entity, Anything that creates an entity.
    public static void onPlayerDimensionChange(ServerPlayerEntity player, RegistryKey<World> from, RegistryKey<World> to) {
        stripAbilities(player);
    }

    public static void onPlayerRespawn(PlayerEntity player, boolean alive) {
        stripAbilities(player);
    }

    public static void onPlayerLoggedOut(PlayerEntity player) {
        stripAbilities(player);
    }

    public static void onPlayerLoggedIn(PlayerEntity player) {
        stripAbilities(player);
    }

    //@SubscribeEvent
    //public void entityContstructedEvent(EntityConstructing event, Entity entity) {
    //    if (entity instanceof LivingEntity) {
    //        //stripAbilities((EntityLivingBase) event.getEntity());
    //    }
    //}

    public static ActionResult onEntityDeath(LivingEntity entity, DamageSource source) {
        stripAbilities(entity);

        return ActionResult.PASS;
    }
    //endregion
}
