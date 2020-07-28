package avaritia._helpers.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public interface AttackBlockEvent {
    Event<AttackBlockEvent> EVENT = EventFactory.createArrayBacked(AttackBlockEvent.class,
        listeners -> (PlayerEntity player, BlockPos blockPos, Direction face) -> {
            for (AttackBlockEvent event : listeners) {
                ActionResult result = event.onAttackBlock(player, blockPos, face);

                if (result != ActionResult.PASS) {
                    return result;
                }
            }

            return ActionResult.PASS;
        });

    ActionResult onAttackBlock(PlayerEntity player, BlockPos blockPos, Direction face);
}
