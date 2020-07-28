package avaritia.mixins;

import avaritia._helpers.events.PlayerLoggedInEvent;
import avaritia._helpers.events.PlayerLoggedOutEvent;
import avaritia._helpers.events.PlayerRespawnEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(
        method = "respawnPlayer",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerPlayerEntity;setHealth(F)V",
            shift = At.Shift.AFTER
        )
    )
    public void respawnPlayer(ServerPlayerEntity player, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> callbackInfo) {
        PlayerRespawnEvent.EVENT.invoker().onPlayerRespawn(player, alive);
    }

    @Inject(
        method = "remove",
        at = @At("HEAD")
    )
    public void remove(ServerPlayerEntity player, CallbackInfo callbackInfo) {
        PlayerLoggedOutEvent.EVENT.invoker().onPlayerLoggedOut(player);
    }

    @Inject(
        method = "onPlayerConnect",
        at = @At("TAIL")
    )
    public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        PlayerLoggedInEvent.EVENT.invoker().onPlayerLoggedIn(player);
    }
}