package avaritia._helpers.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface EntityJoinWorldEvent {
    Event<EntityJoinWorldEvent> EVENT = EventFactory.createArrayBacked(EntityJoinWorldEvent.class,
        listeners -> (entity, world) -> {
            for (EntityJoinWorldEvent event : listeners) {
                ActionResult result = event.onEntityJoinWorld(entity, world);

                if (result != ActionResult.PASS) {
                    return result;
                }
            }

            return ActionResult.PASS;
        });

    ActionResult onEntityJoinWorld(Entity entity, World world);
}
