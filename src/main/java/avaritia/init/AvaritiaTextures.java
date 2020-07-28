package avaritia.init;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.Sprite;

/**
 * Created by covers1624 on 17/04/2017.
 */
@Environment(EnvType.CLIENT)
public class AvaritiaTextures {

    public void registerIcons() {
        //map = textureMap;

        //@formatter:off
        HALO = register(ITEMS_ + "halo");
        HALO_NOISE = register(ITEMS_ + "halo_noise");

        INFINITY_ARMOR_MASK = register(MODELS_ + "infinity_armor_mask");
        INFINITY_ARMOR_MASK_INV = register(MODELS_ + "infinity_armor_mask_inv");
        INFINITY_ARMOR_MASK_WINGS = register(MODELS_ + "infinity_armor_mask_wings");

        COSMIC_0 = register(SHADER_ + "cosmic_0");
        COSMIC_1 = register(SHADER_ + "cosmic_1");
        COSMIC_2 = register(SHADER_ + "cosmic_2");
        COSMIC_3 = register(SHADER_ + "cosmic_3");
        COSMIC_4 = register(SHADER_ + "cosmic_4");
        COSMIC_5 = register(SHADER_ + "cosmic_5");
        COSMIC_6 = register(SHADER_ + "cosmic_6");
        COSMIC_7 = register(SHADER_ + "cosmic_7");
        COSMIC_8 = register(SHADER_ + "cosmic_8");
        COSMIC_9 = register(SHADER_ + "cosmic_9");

        INFINITY_SWORD_MASK   = register(TOOLS_ + "infinity_sword/mask");

        INFINITY_BOW_IDLE = register(TOOLS_ + "infinity_bow/idle");
        INFINITY_BOW_PULL_0 = register(TOOLS_ + "infinity_bow/pull_0");
        INFINITY_BOW_PULL_1 = register(TOOLS_ + "infinity_bow/pull_1");
        INFINITY_BOW_PULL_2 = register(TOOLS_ + "infinity_bow/pull_2");

        INFINITY_BOW_IDLE_MASK = register(TOOLS_ + "infinity_bow/idle_mask");
        INFINITY_BOW_PULL_0_MASK = register(TOOLS_ + "infinity_bow/pull_0_mask");
        INFINITY_BOW_PULL_1_MASK = register(TOOLS_ + "infinity_bow/pull_1_mask");
        INFINITY_BOW_PULL_2_MASK = register(TOOLS_ + "infinity_bow/pull_2_mask");


        INFINITY_BOW_PULL = new Sprite[] {
            INFINITY_BOW_PULL_0,
            INFINITY_BOW_PULL_1,
            INFINITY_BOW_PULL_2
        };

        INFINITY_BOW_PULL_MASK = new Sprite[] {
            INFINITY_BOW_PULL_0_MASK,
            INFINITY_BOW_PULL_1_MASK,
            INFINITY_BOW_PULL_2_MASK
        };


        COSMIC = new Sprite[] {
            COSMIC_0,
            COSMIC_1,
            COSMIC_2,
            COSMIC_3,
            COSMIC_4,
            COSMIC_5,
            COSMIC_6,
            COSMIC_7,
            COSMIC_8,
            COSMIC_9
        };
        //@formatter:on

    }

    // Bouncer to make the class readable.
    private static Sprite register(String sprite) {
        //return map.registerSprite(new ResourceLocation(sprite));
        return null;
    }

    //Assign the TextureMap to a file to make things even more readable!.
    //private static TextureMap map;

    private static final String ITEMS_ = "avaritia:items/";
    private static final String MODELS_ = "avaritia:models/";
    private static final String SHADER_ = "avaritia:shader/";
    private static final String TOOLS_ = ITEMS_ + "tools/";

    public static Sprite HALO;
    public static Sprite HALO_NOISE;

    public static Sprite INFINITY_ARMOR_MASK;
    public static Sprite INFINITY_ARMOR_MASK_INV;
    public static Sprite INFINITY_ARMOR_MASK_WINGS;

    public static Sprite[] COSMIC;
    public static Sprite COSMIC_0;
    public static Sprite COSMIC_1;
    public static Sprite COSMIC_2;
    public static Sprite COSMIC_3;
    public static Sprite COSMIC_4;
    public static Sprite COSMIC_5;
    public static Sprite COSMIC_6;
    public static Sprite COSMIC_7;
    public static Sprite COSMIC_8;
    public static Sprite COSMIC_9;

    public static Sprite INFINITY_SWORD_MASK;

    public static Sprite INFINITY_BOW_IDLE;
    public static Sprite[] INFINITY_BOW_PULL;
    public static Sprite INFINITY_BOW_PULL_0;
    public static Sprite INFINITY_BOW_PULL_1;
    public static Sprite INFINITY_BOW_PULL_2;

    public static Sprite INFINITY_BOW_IDLE_MASK;
    public static Sprite[] INFINITY_BOW_PULL_MASK;
    public static Sprite INFINITY_BOW_PULL_0_MASK;
    public static Sprite INFINITY_BOW_PULL_1_MASK;
    public static Sprite INFINITY_BOW_PULL_2_MASK;

}

