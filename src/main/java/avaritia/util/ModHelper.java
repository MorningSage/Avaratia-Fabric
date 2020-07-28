package avaritia.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Objects;
import java.util.function.Predicate;

public class ModHelper {

    public static final boolean isTinkersLoaded = FabricLoader.getInstance().isModLoaded("tconstruct");
    private static Item tinkersCleaver;

    public static boolean isHoldingCleaver(LivingEntity entity) {
        if (!isTinkersLoaded) {
            return false;
        }

        FabricLoader.getInstance().getAllMods().forEach(modContainer -> {
            modContainer.getMetadata().getVersion().getFriendlyString();
        });

        if (tinkersCleaver == null) {
            tinkersCleaver = Registry.ITEM.get(new Identifier("tconstruct", "cleaver"));
        }
        return isPlayerHolding(entity, item -> Objects.equals(item, tinkersCleaver));
    }

    //TODO, move to ccl -covers
    public static boolean isPlayerHolding(LivingEntity entity, Predicate<Item> predicate) {
        for (Hand hand : Hand.values()) {
            ItemStack stack = entity.getStackInHand(hand);
            if (stack != null) {
                if (predicate.test(stack.getItem())) {
                    return true;
                }
            }
        }
        return false;
    }
}

