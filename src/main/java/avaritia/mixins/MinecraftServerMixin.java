package avaritia.mixins;

import avaritia._helpers.events.WorldTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    ServerWorld currentWorld;

    @Redirect(
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/Iterator;next()Ljava/lang/Object;"
        ),
        method = "tickWorlds"
    )
    private Object getNext(Iterator<ServerWorld> iterator) {
        currentWorld = iterator.next();

        return currentWorld;
    }

    @Inject(
        at = @At(
            value = "INVOKE:FIRST",
            target = "Lnet/minecraft/util/profiler/Profiler;pop()V",
            ordinal = 0
        ),
        slice = @Slice(
          from = @At(
              value = "NEW",
              target = "net/minecraft/util/crash/CrashException"
          )
        ),
        method = "tickWorlds"
    )
    protected void tickWorlds(BooleanSupplier shouldKeepTicking, CallbackInfo callbackInfo) {
        WorldTickEvents.POST_SERVER_WORLD_TICK.invoker().onPostServerWorldTick(currentWorld);
    }
}
