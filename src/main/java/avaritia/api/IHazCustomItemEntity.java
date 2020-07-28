package avaritia.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IHazCustomItemEntity {
    boolean hasCustomEntity(ItemStack stack);

    Entity createEntity(World world, Entity location, ItemStack itemstack);
}
