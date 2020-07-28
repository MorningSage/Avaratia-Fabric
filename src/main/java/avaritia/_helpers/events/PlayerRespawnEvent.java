package avaritia._helpers.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerRespawnEvent {
    Event<PlayerRespawnEvent> EVENT = EventFactory.createArrayBacked(PlayerRespawnEvent.class,
        listeners -> (player, alive) -> {
            for (PlayerRespawnEvent event : listeners) {
                event.onPlayerRespawn(player, alive);
            }
        });

    void onPlayerRespawn(PlayerEntity player, boolean alive);
}
