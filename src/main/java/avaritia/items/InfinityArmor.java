package avaritia.items;

import avaritia.Avaritia;
import avaritia._helpers.EnumHelper;
import avaritia._helpers.events.InventoryTickEvent;
import avaritia.api.IHazArmorModel;
import avaritia.api.IHazArmorTexture;
import avaritia.client.render.entity.ModelArmorInfinity;
import avaritia.mixins.accessors.StatusEffectAccessor;
import avaritia.mixins.accessors.StatusEffectInstanceAccessor;
import avaritia.util.ModHelper;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

import java.util.List;

public class InfinityArmor extends ArmorItem implements IHazArmorTexture, IHazArmorModel {
    public static ArmorMaterial infinite_armor = EnumHelper.addArmorMaterial(
        "AVARITIA_INFINITY",
        "infinity",
        9999,
        new int[] { 6, 16, 12, 6 },
        1000,
        SoundEvents.ITEM_ARMOR_EQUIP_IRON,
        1.0F,
        0.5F,
        () -> Ingredient.ofItems(Items.IRON_INGOT)
    );

    public InfinityArmor(EquipmentSlot slot) {
        super(infinite_armor, slot, new Item.Settings().group(Avaritia.ITEM_GROUP).maxDamage(0));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;

        if (slot == 3) {
            entity.setAir(300);
            if (player != null) {
                player.getHungerManager().add(20, 20F);
                StatusEffectInstance nv = player.getStatusEffect(StatusEffects.NIGHT_VISION);
                if(nv == null) {
                    nv = new StatusEffectInstance(StatusEffects.NIGHT_VISION, 300,  0, false, false);
                    player.addStatusEffect(nv);
                }
                ((StatusEffectInstanceAccessor) nv).setDuration(300);
            }
        } else if (slot == 2) {
            if (player != null) {
                player.abilities.allowFlying = true;
                List<StatusEffectInstance> effects = Lists.newArrayList(player.getStatusEffects());
                for (StatusEffectInstance potion : Collections2.filter(effects, potion -> ((StatusEffectAccessor) potion.getEffectType()).getTypeSafe() == StatusEffectType.BENEFICIAL)) {
                    if (ModHelper.isHoldingCleaver(player) && potion.getEffectType().equals(StatusEffects.MINING_FATIGUE)) {
                        continue;
                    }
                    player.removeStatusEffect(potion.getEffectType());
                }
            }
        } else if (slot == 1) {
            if (entity.isOnFire()) {
                entity.extinguish();
            }
        }
    }

    @Override
    public String getArmorTexture(/*ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type*/) {
        return "avaritia:textures/models/infinity_armor.png";
    }


    @Override
    public Rarity getRarity(ItemStack stack) {
        return super.getRarity(stack);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public <T extends LivingEntity, A extends BipedEntityModel<T>> A getArmorModel(T entityLiving, ItemStack itemstack, EquipmentSlot armorSlot, A _deafult) {
        ModelArmorInfinity model = armorSlot == EquipmentSlot.LEGS ? ModelArmorInfinity.legModel : ModelArmorInfinity.armorModel;

        model.update(entityLiving, itemstack, armorSlot);

        return (A) model;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return super.getAttributeModifiers(slot);
    }
}
