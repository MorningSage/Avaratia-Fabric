package avaritia.api;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.Sprite;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Any item implementing this that also binds itself to TODO INPUT MODEL NAME HERE.
 * Will automatically have the cosmic shader applied to the mask with the given opacity.
 */
public interface ICosmicRenderItem {

    /**
     * The mask where the cosmic overlay will be.
     *
     * @param stack  The stack being rendered.
     * @param player The entity holding the item, May be null, If null assume either inventory, or ground.
     * @return The masked area where the cosmic overlay will be.
     */
    @Environment(EnvType.CLIENT)
    Sprite getMaskTexture(ItemStack stack, @Nullable LivingEntity player);

    /**
     * The opacity that the mask overlay will be rendered with.
     *
     * @param stack  The stack being rendered.
     * @param player The entity holding the item, May be null, If null assume either inventory, or ground.
     * @return The opacity that the mask overlay will be rendered with.
     */
    @Environment(EnvType.CLIENT)
    float getMaskOpacity(ItemStack stack, @Nullable LivingEntity player);
}

