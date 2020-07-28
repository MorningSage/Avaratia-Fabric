package avaritia.mixins;

import avaritia._helpers.events.InventoryTickEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Shadow @Final public PlayerEntity player;

    @Shadow @Final public DefaultedList<ItemStack> armor;

    @Inject(
        at = @At("TAIL"),
        method = "updateItems"
    )
    private void updateItems(CallbackInfo callbackInfo) {
        for (ItemStack stack : armor) {
            if (!stack.isEmpty()) {
                InventoryTickEvent.EVENT.invoker().onInventoryTick(player.world, player, stack);
            }
        }
    }
}
