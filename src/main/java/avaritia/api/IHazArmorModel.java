package avaritia.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface IHazArmorModel {
    @Environment(EnvType.CLIENT)
    <T extends LivingEntity, A extends BipedEntityModel<T>> A getArmorModel(T entityLiving, ItemStack itemstack, EquipmentSlot armorSlot, A _deafult);
}
