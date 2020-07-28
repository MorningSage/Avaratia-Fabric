package avaritia.items;

import avaritia.Avaritia;
import avaritia.api.IHaloRenderItem;
import avaritia.api.registration.IModelRegister;
import avaritia.init.AvaritiaTextures;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;

/**
 * Created by covers1624 on 11/04/2017.
 */
public class Singularity extends Item implements IHaloRenderItem, IModelRegister {

    public Singularity() {
        super(new Settings()
            .maxDamage(0)
            .group(Avaritia.ITEM_GROUP)
            .rarity(Rarity.UNCOMMON)
        );

        //setHasSubtypes(true);
        //setUnlocalizedName(new ResourceLocation(registryName).getResourcePath());
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldDrawHalo(ItemStack stack) {
        return true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Sprite getHaloTexture(ItemStack stack) {
        return AvaritiaTextures.HALO;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getHaloSize(ItemStack stack) {
        return 4;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getHaloColour(ItemStack stack) {
        return 0xFF000000;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldDrawPulse(ItemStack stack) {
        return false;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerModels() {
        //ModelResourceLocation location = new ModelResourceLocation("avaritia:singularity", "type=singularity");
        //ModelLoader.registerItemVariants(this, location);
        //IBakedModel wrappedModel = new HaloRenderItem(TransformUtils.DEFAULT_ITEM, modelRegistry -> modelRegistry.getObject(location));
        //ModelRegistryHelper.register(location, wrappedModel);
        //ModelLoader.setCustomMeshDefinition(this, stack -> location);
    }
}
