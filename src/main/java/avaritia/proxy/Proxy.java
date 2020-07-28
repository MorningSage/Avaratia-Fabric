package avaritia.proxy;

import avaritia._helpers.events.*;
import avaritia.api.IHazCustomItemEntity;
import avaritia.api.registration.IModelRegister;
import avaritia.handler.AbilityHandler;
import avaritia.handler.AvaritiaEventHandler;
import avaritia.init.ModItems;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.UUID;

public class Proxy {

    public static final GameProfile avaritiaFakePlayer = new GameProfile(UUID.fromString("32283731-bbef-487c-bb69-c7e32f84ed27"), "[Avaritia]");

    public void preInit() {
        //ConfigHandler.init(event.getSuggestedConfigurationFile());
        ModItems.init();
        //ModBlocks.init();
        //NetworkRegistry.INSTANCE.registerGuiHandler(Avaritia.instance, new GUIHandler());

        LivingEntityEvents.TICK_EVENT.register(AbilityHandler::updateAbilities);
        LivingEntityEvents.JUMP_EVENT.register(AbilityHandler::jumpBoost);
        DimensionChangeEvents.DIMENSION_CHANGED.register(AbilityHandler::onPlayerDimensionChange);
        PlayerRespawnEvent.EVENT.register(AbilityHandler::onPlayerRespawn);
        PlayerLoggedOutEvent.EVENT.register(AbilityHandler::onPlayerLoggedOut);
        PlayerLoggedInEvent.EVENT.register(AbilityHandler::onPlayerLoggedIn);
        LivingEntityEvents.DEATH_EVENT.register(AbilityHandler::onEntityDeath);

        EntityJoinWorldEvent.EVENT.register(AvaritiaEventHandler::onEntityJoinWorld);
        WorldTickEvents.POST_SERVER_WORLD_TICK.register(AvaritiaEventHandler::onTickEnd);
        AttackBlockEvent.EVENT.register(AvaritiaEventHandler::onPlayerMine);
        ItemTooltipCallback.EVENT.register(AvaritiaEventHandler::onTooltip);

        //EntityJoinWorldEvent.EVENT.register((entity, world) -> {
            //if (entity.getClass().equals(ItemEntity.class) && ((ItemEntity) entity).getStack().getItem() instanceof IHazCustomItemEntity) {
            //    ItemStack stack = ((ItemEntity) entity).getStack();
            //    IHazCustomItemEntity item = (IHazCustomItemEntity) ((ItemEntity) entity).getStack().getItem();
//
            //    if (item.hasCustomEntity(stack)) {
            //        Entity newEntity = item.createEntity(world, entity, stack);
//
            //        if (newEntity != null) {
            //            entity.remove();
            //            world.spawnEntity(newEntity);
            //            return ActionResult.CONSUME;
            //        }
            //    }
            //}
            //return ActionResult.PASS;
        //});

        //MinecraftForge.EVENT_BUS.register(new AvaritiaEventHandler());

        //EntityRegistry.registerModEntity(new Identifier("avaritia:endest_pearl"), EntityEndestPearl.class, "EndestPearl", 1, Avaritia.instance, 64, 10, true);
        //EntityRegistry.registerModEntity(new Identifier("avaritia:gaping_void"), EntityGapingVoid.class, "GapingVoid", 2, Avaritia.instance, 256, 10, false);
        //EntityRegistry.registerModEntity(new Identifier("avaritia:heaven_arrow"), EntityHeavenArrow.class, "HeavenArrow", 3, Avaritia.instance, 32, 1, true);
        //EntityRegistry.registerModEntity(new Identifier("avaritia:heaven_sub_arrow"), EntityHeavenSubArrow.class, "HeavenSubArrow", 4, Avaritia.instance, 32, 2, true);
    }

    public void init() {
    }

    public void postInit() {

    }

    public void addModelRegister(IModelRegister register) {

    }

    public boolean isClient() {
        return false;
    }

    public boolean isServer() {
        return true;
    }

    public World getClientWorld() {
        return null;
    }

}
