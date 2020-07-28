package avaritia.mixins;

import avaritia._helpers.events.LivingEntityEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
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
