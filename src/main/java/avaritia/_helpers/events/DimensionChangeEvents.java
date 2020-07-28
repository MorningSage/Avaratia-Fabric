package avaritia._helpers.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public final class DimensionChangeEvents {
    public static final Event<DimensionChanging> DIMENSION_CHANGING = EventFactory.createArrayBacked(DimensionChanging.class,
        listeners -> (player, from, to) -> {
            for (DimensionChanging event : listeners) {
                ActionResult result = event.onDimensionChanging(player, from, to);

                if (result != ActionResult.PASS) {
                    return result;
                }
            }

            return ActionResult.PASS;
        });

    public static final Event<DimensionChanged> DIMENSION_CHANGED = EventFactory.createArrayBacked(DimensionChanged.class,
        listeners -> (player, from, to) -> {
            for (DimensionChanged event : listeners) {
                event.onDimensionChanged(player, from, to);
            }
        });

    public interface DimensionChanging {
        ActionResult onDimensionChanging(ServerPlayerEntity player, RegistryKey<World> from, RegistryKey<World> to);
    }

    public interface DimensionChanged {
        void onDimensionChanged(ServerPlayerEntity player, RegistryKey<World> from, RegistryKey<World> to);
    }
}
