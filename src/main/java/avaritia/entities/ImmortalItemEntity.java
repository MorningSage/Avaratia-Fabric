package avaritia.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;

public class ImmortalItemEntity extends ItemEntity {
    public ImmortalItemEntity(World world, Entity original, ItemStack stack) {
        super(world, original.getX(), original.getY(), original.getZ(), stack);

        //setPickupDelay(20);
        setStack(stack);

        this.setVelocity(original.getVelocity());
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return source.isOutOfWorld();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isSubmergedIn(FluidTags.LAVA)) {
            this.setVelocity((random.nextFloat() - random.nextFloat()) * 0.2F, 0.2, (random.nextFloat() - random.nextFloat()) * 0.2F);
        }
    }
}
