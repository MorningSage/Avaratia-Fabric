package avaritia._helpers.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;

public final class LivingEntityEvents {
    public static Event<LivingEntityTick> TICK_EVENT = EventFactory.createArrayBacked(LivingEntityTick.class,
        listeners -> (player) -> {
            for (LivingEntityTick event : listeners) {
                ActionResult result = event.onTick(player);

                if (result != ActionResult.PASS) {
                    return result;
                }
            }

            return ActionResult.PASS;
        });

    public static Event<LivingEntityJump> JUMP_EVENT = EventFactory.createArrayBacked(LivingEntityJump.class,
        listeners -> (player) -> {
            for (LivingEntityJump event : listeners) {
                event.onJump(player);
            }
        });

    public static Event<LivingEntityDeath> DEATH_EVENT = EventFactory.createArrayBacked(LivingEntityDeath.class,
        listeners -> (player, cause) -> {
            for (LivingEntityDeath event : listeners) {
                ActionResult result = event.onDeath(player, cause);

                if (result != ActionResult.PASS) {
                    return result;
                }
            }

            return ActionResult.PASS;
        });

    public static Event<LivingEntityHurt> HURT_EVENT = EventFactory.createArrayBacked(LivingEntityHurt.class,
        listeners -> (source, amount) -> {
            float modifiedAmount = amount;

            for (LivingEntityHurt event : listeners) {
                modifiedAmount = event.onHurt(source, modifiedAmount);

            }

            return modifiedAmount;
        });

    public interface LivingEntityTick {
        ActionResult onTick(LivingEntity player);
    }

    public interface LivingEntityJump {
        void onJump(LivingEntity player);
    }

    public interface LivingEntityDeath {
        ActionResult onDeath(LivingEntity player, DamageSource cause);
    }

    public interface LivingEntityHurt {
        float onHurt(DamageSource source, float amount);
    }
}
