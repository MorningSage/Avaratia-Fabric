package avaritia.mixins.accessors;

import it.unimi.dsi.fastutil.objects.ObjectList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModelPart.class)
@Environment(EnvType.CLIENT)
public interface ModelPartAccessor {
    @Accessor
    ObjectList<ModelPart> getChildren();

    @Accessor
    void setChildren(ObjectList<ModelPart> children);
}
