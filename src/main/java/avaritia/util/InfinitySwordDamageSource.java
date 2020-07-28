package avaritia.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;

public class InfinitySwordDamageSource extends EntityDamageSource {
    public InfinitySwordDamageSource(Entity source) {
        super("infinity", source);
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        ItemStack itemstack = source instanceof LivingEntity ? ((LivingEntity) source).getStackInHand(Hand.MAIN_HAND) : null;
        String s = "death.attack.infinity";

        int rando = entity.getEntityWorld().random.nextInt(5);
        if (rando != 0) {
            s = s + "." + rando;
        }
        return new TranslatableText(s, entity.getDisplayName(), itemstack.getName());
    }

    public DamageSource publicSetBypassesArmor() {
        return setBypassesArmor();
    }
}
