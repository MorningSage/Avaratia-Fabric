package avaritia.mixins;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(World.class)
public abstract class WorldMixin {
    @Shadow public abstract boolean isClient();

    @Inject(
        method = "tickBlockEntities",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;remove(Ljava/lang/Object;)Z"),
        slice = @Slice(
            from = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/util/profiler/Profiler;pop()V"),
            to = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/world/chunk/WorldChunk;removeBlockEntity(Lnet/minecraft/util/math/BlockPos;)V")),
        locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    protected void onRemoveBlockEntity(CallbackInfo ci, Profiler profiler, Iterator iterator, BlockEntity blockEntity) {

    }
}
