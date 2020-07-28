package avaritia.proxy;

import avaritia.api.registration.IModelRegister;
import avaritia.init.ModItems;
import avaritia.mixins.accessors.MinecraftClientAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class ProxyClient extends Proxy {

    private Set<IModelRegister> modelRegisters = new HashSet<>();

    //@formatter:off
    public static final int[] SINGULARITY_COLOURS_FOREGROUND = new int[] {
        0xE6E7E8, 0xE8EF23, 0x5a82e2, 0xDF0000,
        0xffffff, 0xE47200, 0xA5C7DE, 0x444072,
        0xC0C0C0, 0xDEE187, 0x45ACA5, 0x5CBE34,
        0xD62306, 0x00BFFF, 0xE6E6FA
    };

    public static final int[] SINGULARITY_COLOURS_BACKGROUND = new int[] {
        0x7F7F7F, 0xdba213, 0x224baf, 0x900000,
        0x94867d, 0x89511A, 0x9BA9B2, 0x3E3D4E,
        0xD5D5D5, 0xC4C698, 0x8fcdc9, 0x8cd170,
        0xfffc95, 0x5a82e2, 0xE6E6FA
    };

    public static final int[][] SINGULARITY_COLOURS = new int[][] {
        SINGULARITY_COLOURS_FOREGROUND,
        SINGULARITY_COLOURS_BACKGROUND
    };
    //@formatter:on

    @Override
    public void preInit() {
        //super.preInit();

        //TextureUtils.addIconRegister(new AvaritiaTextures());
        //MinecraftForge.EVENT_BUS.register(new AvaritiaClientEventHandler());
//
        //for (IModelRegister register : modelRegisters) {
        //    register.registerModels();
        //}
        //ShaderHelper.initShaders();
        //Identifier tools = new Identifier("avaritia:tools");
        //Identifier resource = new Identifier("avaritia:resource");
//
        //{
        //    ModelIdentifier pickaxe = new ModelIdentifier(tools, "infinity_pickaxe=pickaxe");
        //    ModelIdentifier hammer = new ModelIdentifier(tools, "infinity_pickaxe=hammer");
        //    BuiltinItemRendererRegistry.INSTANCE.register();
        //    ModelLoader.registerItemVariants(ModItems.infinity_pickaxe, pickaxe, hammer);
        //    ModelLoader.setCustomMeshDefinition(ModItems.infinity_pickaxe, stack -> {
        //        if (stack.hasTagCompound()) {
        //            if (ItemNBTUtils.getBoolean(stack, "hammer")) {
        //                return hammer;
        //            }
        //        }
        //        return pickaxe;
        //    });
        //}
//
        //{
        //    ModelIdentifier shovel = new ModelIdentifier(tools, "infinity_shovel=shovel");
        //    ModelIdentifier destroyer = new ModelIdentifier(tools, "infinity_shovel=destroyer");
        //    ModelLoader.registerItemVariants(ModItems.infinity_shovel, shovel, destroyer);
        //    ModelLoader.setCustomMeshDefinition(ModItems.infinity_shovel, stack -> {
        //        if (stack.hasTagCompound()) {
        //            if (ItemNBTUtils.getBoolean(stack, "destroyer")) {
        //                return destroyer;
        //            }
        //        }
        //        return shovel;
        //    });
        //}
//
        //{
        //    ModelIdentifier axe = new ModelIdentifier(tools, "type=infinity_axe");
        //    ModelLoader.registerItemVariants(ModItems.infinity_axe, axe);
        //    ModelLoader.setCustomMeshDefinition(ModItems.infinity_axe, (ItemStack stack) -> axe);
        //}
//
        //{
        //    ModelIdentifier hoe = new ModelIdentifier(tools, "type=infinity_hoe");
        //    ModelLoader.registerItemVariants(ModItems.infinity_axe, hoe);
        //    ModelLoader.setCustomMeshDefinition(ModItems.infinity_hoe, (ItemStack stack) -> hoe);
        //}
//
        //{
        //    ModelIdentifier helmet = new ModelIdentifier(tools, "armor=helmet");
        //    ModelLoader.registerItemVariants(ModItems.infinity_helmet, helmet);
        //    ModelLoader.setCustomMeshDefinition(ModItems.infinity_helmet, (ItemStack stack) -> helmet);
        //}
//
        //{
        //    ModelIdentifier chestplate = new ModelIdentifier(tools, "armor=chestplate");
        //    ModelLoader.registerItemVariants(ModItems.infinity_chestplate, chestplate);
        //    ModelLoader.setCustomMeshDefinition(ModItems.infinity_chestplate, (ItemStack stack) -> chestplate);
        //}
//
        //{
        //    ModelIdentifier legs = new ModelIdentifier(tools, "armor=legs");
        //    ModelLoader.registerItemVariants(ModItems.infinity_pants, legs);
        //    ModelLoader.setCustomMeshDefinition(ModItems.infinity_pants, (ItemStack stack) -> legs);
        //}
//
        //{
        //    ModelIdentifier boots = new ModelIdentifier(tools, "armor=boots");
        //    ModelLoader.registerItemVariants(ModItems.infinity_boots, boots);
        //    ModelLoader.setCustomMeshDefinition(ModItems.infinity_boots, (ItemStack stack) -> boots);
        //}
//
        //{
        //    ModelIdentifier sword = new ModelIdentifier(tools, "type=skull_sword");
        //    ModelLoader.registerItemVariants(ModItems.skull_sword, sword);
        //    ModelLoader.setCustomMeshDefinition(ModItems.skull_sword, (ItemStack stack) -> sword);
        //}
//
        //{
        //    ModelIdentifier stew = new ModelIdentifier(resource, "type=ultimate_stew");
        //    ModelLoader.registerItemVariants(ModItems.ultimate_stew, stew);
        //    ModelLoader.setCustomMeshDefinition(ModItems.ultimate_stew, (ItemStack stack) -> stew);
        //}
//
        //{
        //    ModelIdentifier meatballs = new ModelIdentifier(resource, "type=cosmic_meatballs");
        //    ModelLoader.registerItemVariants(ModItems.cosmic_meatballs, meatballs);
        //    ModelLoader.setCustomMeshDefinition(ModItems.cosmic_meatballs, (ItemStack stack) -> meatballs);
        //}
//
        //{
        //    ModelIdentifier empty = new ModelIdentifier(resource, "matter_cluster=empty");
        //    ModelIdentifier full = new ModelIdentifier(resource, "matter_cluster=full");
        //    ModelLoader.registerItemVariants(ModItems.matter_cluster, empty, full);
        //    ModelLoader.setCustomMeshDefinition(ModItems.matter_cluster, (ItemStack stack) -> {
        //        if (ItemMatterCluster.getClusterSize(stack) == ItemMatterCluster.CAPACITY) {
        //            return full;
        //        }
        //        return empty;
        //    });
        //}
        //registerRenderers();
        //PacketCustom.assignHandler(NetworkDispatcher.NET_CHANNEL, new ClientPacketHandler());
    }

    @SuppressWarnings ("unchecked")
    @Override
    public void postInit() {
        super.postInit();

        ItemColors itemColors = ((MinecraftClientAccessor) MinecraftClient.getInstance()).getItemColors();
        //itemColors.register((stack, tintIndex) -> SINGULARITY_COLOURS[tintIndex ^ 1][stack.getDamage()], ModItems.singularity);
//
        //RenderManager manager = Minecraft.getMinecraft().getRenderManager();
//
        //Render<EntityItem> render = (Render<EntityItem>) manager.entityRenderMap.get(EntityItem.class);
        //if (render == null) {
        //    throw new RuntimeException("EntityItem does not have a Render bound... This is likely a bug..");
        //}
        //manager.entityRenderMap.put(EntityItem.class, new WrappedEntityItemRenderer(manager, render));
    }

    @Override
    public void addModelRegister(IModelRegister register) {
        modelRegisters.add(register);
    }

    private void registerRenderers() {
        //RenderingRegistry.registerEntityRenderingHandler(EntityEndestPearl.class, manager -> new RenderSnowball<>(manager, ModItems.endest_pearl, Minecraft.getMinecraft().getRenderItem()));
        //RenderingRegistry.registerEntityRenderingHandler(EntityGapingVoid.class, RenderGapingVoid::new);
        //RenderingRegistry.registerEntityRenderingHandler(EntityHeavenArrow.class, RenderHeavenArrow::new);
        //RenderingRegistry.registerEntityRenderingHandler(EntityHeavenSubArrow.class, RenderHeavenArrow::new);
    }

    @Override
    public boolean isClient() {
        return true;
    }

    @Override
    public boolean isServer() {
        return false;
    }

    @Override
    public World getClientWorld() {
        return MinecraftClient.getInstance().world;
    }
}
