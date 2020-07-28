package avaritia._helpers.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.function.BooleanSupplier;

public final class WorldTickEvents {
    public static final Event<PostServerWorldTick> POST_SERVER_WORLD_TICK = EventFactory.createArrayBacked(PostServerWorldTick.class,
        listeners -> (world) -> {
            for (PostServerWorldTick event : listeners) {
                event.onPostServerWorldTick(world);
            }
        });


    /**
     * The main difference between this and the standard fabric tick event is
     * the when the event is raised.  Fabric's is raised at the end of {@link World#tickBlockEntities}
     * whereas this event is raised just after the try catch in {@link MinecraftServer#tickWorlds(BooleanSupplier)}
     * which guarantees that the event is fired regardless of
     */
    public interface PostServerWorldTick {
        void onPostServerWorldTick(World world);
    }
}
