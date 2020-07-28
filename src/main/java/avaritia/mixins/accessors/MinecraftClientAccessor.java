package avaritia.mixins.accessors;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public interface MinecraftClientAccessor {
    @Accessor
    ItemColors getItemColors();
}
