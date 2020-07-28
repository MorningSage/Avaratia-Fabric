package avaritia.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;

public interface IHaloRenderItem {

    @Environment(EnvType.CLIENT)
    boolean shouldDrawHalo(ItemStack stack);

    @Environment(EnvType.CLIENT)
    Sprite getHaloTexture(ItemStack stack);

    @Environment(EnvType.CLIENT)
    int getHaloColour(ItemStack stack);

    @Environment(EnvType.CLIENT)
    int getHaloSize(ItemStack stack);

    @Environment(EnvType.CLIENT)
    boolean shouldDrawPulse(ItemStack stack);

}
