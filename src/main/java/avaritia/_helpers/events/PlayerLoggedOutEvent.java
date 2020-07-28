package avaritia._helpers.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface PlayerLoggedOutEvent {
    Event<PlayerLoggedOutEvent> EVENT = EventFactory.createArrayBacked(PlayerLoggedOutEvent.class,
        listeners -> (player) -> {
            for (PlayerLoggedOutEvent event : listeners) {
                event.onPlayerLoggedOut(player);
            }
        });

    void onPlayerLoggedOut(PlayerEntity player);
}
