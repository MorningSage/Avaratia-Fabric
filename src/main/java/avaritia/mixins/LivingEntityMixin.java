package avaritia.mixins;

import avaritia._helpers.events.LivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = LivingEntity.class, priority = 999)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
        method = "tick",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onTick(CallbackInfo callbackInfo) {
        ActionResult result = LivingEntityEvents.TICK_EVENT.invoker().onTick((LivingEntity) (Object) this);

        if (result != ActionResult.PASS) callbackInfo.cancel();
    }

    @Inject(
        method = "jump",
        at = @At("TAIL")
    )
    private void onJump(CallbackInfo callbackInfo) {
        LivingEntityEvents.JUMP_EVENT.invoker().onJump((LivingEntity) (Object) this);
    }

    @Inject(
        method = "onDeath",
        at = @At("TAIL"),
        cancellable = true
    )
    private void onDeath(DamageSource source, CallbackInfo callbackInfo) {
        ActionResult result = LivingEntityEvents.DEATH_EVENT.invoker().onDeath((LivingEntity) (Object) this, source);

        if (result != ActionResult.PASS) callbackInfo.cancel();
    }

    /**
     * Used to save the amount set by the event
     */
    private float newAmount;

    /**
     * Used to store the starting amount passed as a param to determine if the param has been modified
     *
     * Since anyone can inject and read the value before the first call to have it reassigned,
     * this is to determine if the param value has changed.  If it has, we will be returning
     * the default value instead of the new one.
     */
    private float oldAmount;

    /**
     * Raises the "onDamage" event and allows for the damage amount to be modified.
     *
     * The new and old amounts are saved for future reference
     */
    @Inject(
        at = @At("HEAD"),
        method = "applyDamage"
    )
    protected void applyDamageEvent(DamageSource source, float amount, CallbackInfo callbackInfo) {
        newAmount = amount + 50;
        oldAmount = amount;
    }

    /**
     * @param amount The value of the amount parameter.
     * @return Either the default value or the new value depending on if the parameter value has been updated
     */
    @ModifyVariable(
        at = @At("LOAD"),
        method = "applyDamage"
    )
    protected float applyDamage(float amount) {
        // Determine if the change to the parameter value has taken.
        // It may not have if the value was accessed by another mixin, for instance
        if (amount == oldAmount) {
            // The change has not taken place, so return the new value
            amount = newAmount;
        }

        // This way, We will continue to return the new value until the parameter value changes.

        return amount;
    }
}
