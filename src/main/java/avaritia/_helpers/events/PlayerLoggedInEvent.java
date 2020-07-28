package avaritia._helpers.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerLoggedInEvent {
    Event<PlayerLoggedInEvent> EVENT = EventFactory.createArrayBacked(PlayerLoggedInEvent.class,
        listeners -> (player) -> {
            for (PlayerLoggedInEvent event : listeners) {
                event.onPlayerLoggedIn(player);
            }
        });

    void onPlayerLoggedIn(PlayerEntity player);
}
