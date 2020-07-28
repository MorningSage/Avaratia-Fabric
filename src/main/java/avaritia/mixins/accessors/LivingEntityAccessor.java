package avaritia.mixins.accessors;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor
    void setPlayerHitTimer(int playerHitTimer);
}
