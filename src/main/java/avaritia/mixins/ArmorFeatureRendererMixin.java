package avaritia.mixins;

import avaritia.api.IHazArmorModel;
import avaritia.api.IHazArmorTexture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ArmorFeatureRenderer.class)
@Environment(EnvType.CLIENT)
public class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> {
    @Shadow @Final private static Map<String, Identifier> ARMOR_TEXTURE_CACHE;

    @Inject(
        method = "getArmor",
        at = @At("RETURN"),
        cancellable = true
    )
    private void getArmor(EquipmentSlot slot, CallbackInfoReturnable<A> callbackInfo) {
        T player = (T) MinecraftClient.getInstance().player;
        ItemStack equippedStack = player.getEquippedStack(slot);

        if ((equippedStack.getItem() instanceof IHazArmorModel)) {
            A customTexture = ((IHazArmorModel) equippedStack.getItem()).getArmorModel(player, equippedStack, slot, callbackInfo.getReturnValue());
            callbackInfo.setReturnValue(customTexture);
        }
    }

    @Inject(
        method = "getArmorTexture",
        at = @At("HEAD"),
        cancellable = true
    )
    private void getCorrectTexture(ArmorItem armorItem, boolean bl, @Nullable String string, CallbackInfoReturnable<Identifier> callbackInfo) {
        if ((armorItem instanceof IHazArmorTexture)) {
            String customTexture = ((IHazArmorTexture) armorItem).getArmorTexture();

            callbackInfo.setReturnValue(ARMOR_TEXTURE_CACHE.computeIfAbsent(customTexture, Identifier::new));
        }
    }
}
