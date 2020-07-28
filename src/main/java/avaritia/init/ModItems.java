package avaritia.init;

import avaritia.Avaritia;
import avaritia._helpers.EnumHelper;
import avaritia.items.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

/**
 * Created by covers1624 on 11/04/2017.
 */
public class ModItems {

    public static Rarity COSMIC_RARITY = EnumHelper.addRarity("COSMIC", Formatting.RED);

    //public static ItemResource resource;

    /**
     * 0 = Iron,
     * 1 = Gold,
     * 2 = Lapis,
     * 3 = Redstone,
     * 4 = Nether Quartz,
     * 5 = Copper,
     * 6 = Tin,
     * 7 = Lead,
     * 8 = Silver,
     * 9 = Nickel,
     * 10 = Diamond,
     * 11 = Emerald
     * 12 = Fluxed
     */
    public static Singularity singularity;

    public static InfinitySword infinity_sword;
    public static InfinityBow infinity_bow;
    public static InfinityPickaxe infinity_pickaxe;
    //public static ItemShovelInfinity infinity_shovel;
    //public static ItemAxeInfinity infinity_axe;
    //public static ItemHoeInfinity infinity_hoe;

    public static InfinityArmor infinity_helmet;
    public static InfinityArmor infinity_chestplate;
    public static InfinityArmor infinity_pants;
    public static InfinityArmor infinity_boots;

    //public static ItemSwordSkulls skull_sword;
//
    //public static ItemEndestPearl endest_pearl;
    //public static ItemFood ultimate_stew;
    //public static ItemFood cosmic_meatballs;
//
    //public static ItemFracturedOre fractured_ore;
//
    //public static ItemMatterCluster matter_cluster;

    public static ItemStack ironSingularity;
    public static ItemStack goldSingularity;
    public static ItemStack lapisSingularity;
    public static ItemStack redstoneSingularity;
    public static ItemStack quartzSingularity;
    public static ItemStack copperSingularity;
    public static ItemStack tinSingularity;
    public static ItemStack leadSingularity;
    public static ItemStack silverSingularity;
    public static ItemStack nickelSingularity;
    public static ItemStack diamondSingularity;
    public static ItemStack emeraldSingularity;
    public static ItemStack fluxedSingularity;
    public static ItemStack platinumSingularity;
    public static ItemStack iridiumSingularity;

    public static ItemStack diamond_lattice;
    public static ItemStack crystal_matrix_ingot;
    public static ItemStack neutron_pile;
    public static ItemStack neutron_nugget;
    public static ItemStack neutronium_ingot;
    public static ItemStack infinity_catalyst;
    public static ItemStack infinity_ingot;
    public static ItemStack record_fragment;

    public static void init() {

        //resource = registerItem(new ItemResource(Avaritia.tab, "resource"));
        ////0
        //diamond_lattice = resource.registerItem("diamond_lattice", EnumRarity.UNCOMMON);
        ////1
        //crystal_matrix_ingot = resource.registerItem("crystal_matrix_ingot", EnumRarity.RARE);
        ////2
        //neutron_pile = resource.registerItem("neutron_pile", EnumRarity.UNCOMMON);
        ////3
        //neutron_nugget = resource.registerItem("neutron_nugget", EnumRarity.UNCOMMON);
        ////4
        //neutronium_ingot = resource.registerItem("neutronium_ingot", EnumRarity.RARE);
        ////5
        //infinity_catalyst = resource.registerItem("infinity_catalyst", EnumRarity.EPIC);
        ////6
        //infinity_ingot = resource.registerItem("infinity_ingot", COSMIC_RARITY);
        ////7
        //record_fragment = resource.registerItem("record_fragment", COSMIC_RARITY);
//
        //singularity = registerItem(new ItemSingularity(Avaritia.tab, "singularity"));
        //ironSingularity = singularity.registerItem("iron");
        //goldSingularity = singularity.registerItem("gold");
        //lapisSingularity = singularity.registerItem("lapis");
        //redstoneSingularity = singularity.registerItem("redstone");
        //quartzSingularity = singularity.registerItem("quartz");
        //copperSingularity = singularity.registerItem("copper");
        //tinSingularity = singularity.registerItem("tin");
        //leadSingularity = singularity.registerItem("lead");
        //silverSingularity = singularity.registerItem("silver");
        //nickelSingularity = singularity.registerItem("nickel");
        //diamondSingularity = singularity.registerItem("diamond");
        //emeraldSingularity = singularity.registerItem("emerald");
        //fluxedSingularity = singularity.registerItem("fluxed");
        //platinumSingularity = singularity.registerItem("platinum");
        //iridiumSingularity = singularity.registerItem("iridium");

        infinity_sword = registerItem(new InfinitySword(), new Identifier(Avaritia.MOD_ID, "infinity_sword"));

        infinity_bow = registerItem(new InfinityBow(), new Identifier(Avaritia.MOD_ID, "infinity_bow"));

        infinity_pickaxe = registerItem(new InfinityPickaxe(), new Identifier(Avaritia.MOD_ID, "infinity_pickaxe"));

        //infinity_shovel = registerItem(new ItemShovelInfinity());

        //infinity_axe = registerItem(new ItemAxeInfinity());

        //infinity_hoe = registerItem(new ItemHoeInfinity());

        infinity_helmet = new InfinityArmor(EquipmentSlot.HEAD);
        registerItem(infinity_helmet, new Identifier(Avaritia.MOD_ID, "infinity_helmet"));

        infinity_helmet = new InfinityArmor(EquipmentSlot.CHEST);
        registerItem(infinity_helmet, new Identifier(Avaritia.MOD_ID, "infinity_chestplate"));

        infinity_helmet = new InfinityArmor(EquipmentSlot.LEGS);
        registerItem(infinity_helmet, new Identifier(Avaritia.MOD_ID, "infinity_pants"));

        infinity_helmet = new InfinityArmor(EquipmentSlot.FEET);
        registerItem(infinity_helmet, new Identifier(Avaritia.MOD_ID, "infinity_boots"));

        //skull_sword = registerItem(new ItemSwordSkulls());
//
        //endest_pearl = registerItem(new ItemEndestPearl());
//
        //ultimate_stew = new ItemFood(20, 20, false);
        //registerItem(ultimate_stew.setRegistryName("ultimate_stew"));
        //ultimate_stew.setPotionEffect(new PotionEffect(MobEffects.REGENERATION, 300, 1), 1.0F).setUnlocalizedName("avaritia:ultimate_stew").setCreativeTab(Avaritia.tab);
//
        //cosmic_meatballs = new ItemFood(20, 20, false);
        //registerItem(cosmic_meatballs.setRegistryName("cosmic_meatballs"));
        //cosmic_meatballs.setPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 1), 1.0F).setUnlocalizedName("avaritia:cosmic_meatballs").setCreativeTab(Avaritia.tab);
//
        //if (ConfigHandler.fracturedOres) {
        //    fractured_ore = registerItem(new ItemFracturedOre());
        //    ItemFracturedOre.parseOreDictionary();
        //}
//
        //matter_cluster = registerItem(new ItemMatterCluster());
    }

    public static <V extends Item> V registerItem(V item, Identifier identifier) {
        return registerImpl(item, v -> Registry.register(Registry.ITEM, identifier, item));
    }

    public static <V extends Item> V registerImpl(V registryObject, Consumer<V> registerCallback) {
        registerCallback.accept(registryObject);



        //if (registryObject instanceof IModelRegister) {
        //    Avaritia.proxy.addModelRegister((IModelRegister) registryObject);
        //}

        return registryObject;
    }

}

