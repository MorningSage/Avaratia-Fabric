package avaritia.mixins;

import avaritia._helpers.events.EntityJoinWorldEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
@Environment(EnvType.CLIENT)
public class ClientWorldMixin {
    @Inject(
        at = @At("HEAD"),
        method = "addEntityPrivate",
        cancellable = true
    )
    private void addEntityPrivate(int id, Entity entity, CallbackInfo callbackInfo) {
        ActionResult result = EntityJoinWorldEvent.EVENT.invoker().onEntityJoinWorld(entity, (ClientWorld) (Object) this);

        if (result != ActionResult.PASS) callbackInfo.cancel();
    }
}