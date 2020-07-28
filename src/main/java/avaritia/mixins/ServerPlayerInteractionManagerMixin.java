package avaritia.mixins;

import avaritia._helpers.events.AttackBlockEvent;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerActionResponseS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {

    @Shadow public ServerPlayerEntity player;
    @Shadow public ServerWorld world;

    @Inject(
        at = @At("HEAD"),
        method = "processBlockBreakingAction",
        cancellable = true
    )
    public void processBlockBreakingAction(BlockPos pos, PlayerActionC2SPacket.Action action, Direction direction, int worldHeight, CallbackInfo callbackInfo) {
        ActionResult result = AttackBlockEvent.EVENT.invoker().onAttackBlock(this.player, pos, direction);

        if (result != ActionResult.PASS) { // Restore block and be data
            player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, world.getBlockState(pos), action, false, "mod canceled"));
            world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            callbackInfo.cancel();
        }
    }
}