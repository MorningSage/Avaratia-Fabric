package avaritia.mixins;

import avaritia._helpers.events.EntityJoinWorldEvent;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(
        at = @At("HEAD"),
        method = "addPlayer",
        cancellable = true
    )
    private void addPlayer(ServerPlayerEntity player, CallbackInfo callbackInfo) {
        ActionResult result = EntityJoinWorldEvent.EVENT.invoker().onEntityJoinWorld(player, (ServerWorld) (Object) this);

        if (result != ActionResult.PASS) callbackInfo.cancel();
    }

    @Inject(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;loadEntityUnchecked(Lnet/minecraft/entity/Entity;)V"
        ),
        method = "loadEntity",
        cancellable = true
    )
    public void loadEntity(Entity entity, CallbackInfoReturnable<Boolean> callbackInfo) {
        ActionResult result = EntityJoinWorldEvent.EVENT.invoker().onEntityJoinWorld(entity, (ServerWorld) (Object) this);

        if (result != ActionResult.PASS) callbackInfo.setReturnValue(false);
    }

    @Inject(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;getChunk(IILnet/minecraft/world/chunk/ChunkStatus;Z)Lnet/minecraft/world/chunk/Chunk;"
        ),
        method = "addEntity",
        cancellable = true
    )
    private void addEntity(Entity entity, CallbackInfoReturnable<Boolean> callbackInfo) {
        ActionResult result = EntityJoinWorldEvent.EVENT.invoker().onEntityJoinWorld(entity, (ServerWorld) (Object) this);

        if (result != ActionResult.PASS) callbackInfo.setReturnValue(false);
    }
}
