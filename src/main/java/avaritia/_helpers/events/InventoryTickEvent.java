package avaritia._helpers.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface InventoryTickEvent {
    Event<InventoryTickEvent> EVENT = EventFactory.createArrayBacked(InventoryTickEvent.class,
        listeners -> (world, player, stack) -> {
            for (InventoryTickEvent event : listeners) {
                event.onInventoryTick(world, player, stack);
            }
        });

    void onInventoryTick(World world, PlayerEntity player, ItemStack stack);
}
