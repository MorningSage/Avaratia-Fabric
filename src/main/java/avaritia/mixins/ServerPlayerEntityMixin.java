package avaritia.mixins;

import avaritia._helpers.events.DimensionChangeEvents;
import avaritia._helpers.events.LivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Shadow public abstract ServerWorld getServerWorld();

    private RegistryKey<World> beforeChangeDimension = null;
    private RegistryKey<World> beforeTeleport        = null;

    @Inject(
        method = "changeDimension",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onChangeDimensionPre(ServerWorld destination, CallbackInfoReturnable<Entity> callbackInfo) {
        beforeChangeDimension = this.getServerWorld().getRegistryKey();

        ActionResult result = DimensionChangeEvents.DIMENSION_CHANGING.invoker().onDimensionChanging((ServerPlayerEntity) (Object) this, beforeChangeDimension, destination.getRegistryKey());

        if (result != ActionResult.PASS) callbackInfo.setReturnValue((ServerPlayerEntity) (Object) this);
    }

    @Inject(
        method = "changeDimension",
        at = @At("TAIL"),
        slice = @Slice(
            from = @At(
                value = "NEW",
                target = "net/minecraft/network/packet/s2c/play/WorldEventS2CPacket"
            )
        )
    )
    private void onChangeDimension(ServerWorld destination, CallbackInfoReturnable<Entity> callbackInfo) {
        DimensionChangeEvents.DIMENSION_CHANGED.invoker().onDimensionChanged((ServerPlayerEntity) (Object) this, beforeChangeDimension, destination.getRegistryKey());
    }

    @Inject(
        method = "teleport",
        at = @At("HEAD"),
        cancellable = true
    )
    public void teleportPre(ServerWorld targetWorld, double x, double y, double z, float yaw, float pitch, CallbackInfo callbackInfo) {
        beforeTeleport = this.getServerWorld().getRegistryKey();

        ActionResult result = DimensionChangeEvents.DIMENSION_CHANGING.invoker().onDimensionChanging((ServerPlayerEntity) (Object) this, beforeTeleport, targetWorld.getRegistryKey());

        if (result != ActionResult.PASS) callbackInfo.cancel();
    }

    @Inject(
        method = "teleport",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/PlayerManager;sendPlayerStatus(Lnet/minecraft/server/network/ServerPlayerEntity;)V",
            shift = At.Shift.AFTER
        )
    )
    public void teleport(ServerWorld targetWorld, double x, double y, double z, float yaw, float pitch, CallbackInfo callbackInfo) {
        DimensionChangeEvents.DIMENSION_CHANGED.invoker().onDimensionChanged((ServerPlayerEntity) (Object) this, beforeTeleport, targetWorld.getRegistryKey());
    }

    @Inject(
        method = "onDeath",
        at = @At("HEAD"),
        cancellable = true
    )
    public void onDeath(DamageSource source, CallbackInfo callbackInfo) {
        ActionResult result = LivingEntityEvents.DEATH_EVENT.invoker().onDeath((PlayerEntity) (Object) this, source);

        if (result != ActionResult.PASS) callbackInfo.cancel();
    }
}
