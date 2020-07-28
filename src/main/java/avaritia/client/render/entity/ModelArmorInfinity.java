package avaritia.client.render.entity;


import avaritia.init.ModItems;
import avaritia.mixins.accessors.ModelPartAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;

import java.util.ArrayList;
import java.util.Random;

import static com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import static com.mojang.blaze3d.platform.GlStateManager.SrcFactor;

@Environment(EnvType.CLIENT)
public class ModelArmorInfinity extends BipedEntityModel<LivingEntity> {

    public static final ModelArmorInfinity armorModel = new ModelArmorInfinity(1.0f);
    public static final ModelArmorInfinity legModel = new ModelArmorInfinity(0.5f).setLegs(true);

    public static Identifier eyeTex = new Identifier("avaritia", "textures/models/infinity_armor_eyes.png");
    public static Identifier wingTex = new Identifier("avaritia", "textures/models/infinity_armor_wing.png");
    public static Identifier wingGlowTex = new Identifier("avaritia", "textures/models/infinity_armor_wingglow.png");
    public static int itempagewidth = 0;
    public static int itempageheight = 0;
    public boolean legs = false;

    public EquipmentSlot currentSlot = EquipmentSlot.HEAD;

    private final Random randy = new Random();

    //private final Overlay overlay;
    //private final Overlay invulnOverlay;
    private boolean invulnRender = true;
    private boolean showHat;
    private boolean showChest;
    private boolean showLeg;
    private boolean showFoot;

    private final float expand;

    //public ModelPart bipedLeftWing;
    //public ModelPart bipedRightWing;

    public ModelArmorInfinity(float scale) {
        super(scale, 0, 64, 64);
        this.expand = scale;
        //overlay = new Overlay(this, scale);
        //invulnOverlay = new Overlay(this, 0);

        helmet = new ModelPart(this, 32, 0);
        helmet.addCuboid(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale * 0.5F);
        helmet.setPivot(0.0F, 0.0F + 0, 0.0F);
    }

    public ModelArmorInfinity setLegs(boolean islegs) {
        legs = islegs;

        int heightoffset = 0;
        int legoffset = islegs ? 32 : 0;

        torso = new ModelPart(this, 16, 16 + legoffset);
        torso.addCuboid(-4.0F, 0.0F, -2.0F, 8, 12, 4, expand);
        torso.setPivot(0.0F, 0.0F + heightoffset, 0.0F);
        rightLeg = new ModelPart(this, 0, 16 + legoffset);
        rightLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4, expand);
        rightLeg.setPivot(-1.9F, 12.0F + heightoffset, 0.0F);
        leftLeg = new ModelPart(this, 0, 16 + legoffset);
        leftLeg.mirror = true;
        leftLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4, expand);
        leftLeg.setPivot(1.9F, 12.0F + heightoffset, 0.0F);

        return this;
    }

    //SuppressWarnings ("rawtypes")
    //ublic void rebuildWings() {
    //   // remove the old items from the list so that the new ones don't just stack up
    //   if (((ModelPartAccessor) torso).getChildren() == null) {
    //       ((ModelPartAccessor) torso).setChildren(new ObjectArrayList<>());
    //   }
    //   if (bipedLeftWing != null) {
    //       ((ModelPartAccessor) torso).getChildren().remove(bipedLeftWing);
    //   }
    //   if (bipedRightWing != null) {
    //       ((ModelPartAccessor) torso).getChildren().remove(bipedRightWing);
    //   }

    //   // define new
    //   bipedLeftWing = new ModelRendererWing(this, 0, 0);
    //   bipedLeftWing.mirror = true;
    //   bipedLeftWing.addCuboid(0f, -11.6f, 0f, 0, 32, 32);
    //   bipedLeftWing.setPivot(-1.5f, 0.0f, 2.0f);
    //   bipedLeftWing.pivotY = (float) (Math.PI * 0.4);
    //   torso.addChild(bipedLeftWing);

    //   bipedRightWing = new ModelRendererWing(this, 0, 0);
    //   bipedRightWing.addCuboid(0f, -11.6f, 0f, 0, 32, 32);
    //   bipedRightWing.setPivot(1.5f, 0.0f, 2.0f);
    //   bipedRightWing.pivotY = (float) (-Math.PI * 0.4);
    //   torso.addChild(bipedRightWing);
    //



    //@Override
    ////public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    //public void animateModel(LivingEntity livingEntity, float f, float g, float h) {
    //    MinecraftClient mc = MinecraftClient.getInstance();
    //    boolean isFlying = livingEntity instanceof PlayerEntity && ((PlayerEntity) livingEntity).abilities.flying && livingEntity.velocityDirty;
//
    //    //copyBipedAngles(this, this.overlay);
    //    //copyBipedAngles(this, this.invulnOverlay);
//
    //    super.animateModel(livingEntity, f, g, h);
//
    //    RenderSystem.color4f(1, 1, 1, 1);
    //    //CosmicShaderHelper.useShader();
    //    mc.getTextureManager().bindTexture(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
    //    RenderSystem.disableAlphaTest();
    //    RenderSystem.enableBlend();
    //    RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
    //    RenderSystem.depthMask(false);
    //    if (invulnRender) {
    //        RenderSystem.color4f(1, 1, 1, 0.2F);
    //        invulnOverlay.render(entity, f, f1, f2, f3, f4, f5);
    //    }
    //    overlay.render(entity, f, f1, f2, f3, f4, f5);
//
    //    //CosmicShaderHelper.releaseShader();
//
    //    mc.renderEngine.bindTexture(eyeTex);
    //    RenderSystem.disableLighting();
    //    mc.entityRenderer.disableLightmap();
//
    //    long time = livingEntity.world.getTime();
//
    //    setGems();
//
    //    double pulse = Math.sin(time / 10.0) * 0.5 + 0.5;
    //    double pulse_mag_sqr = pulse * pulse * pulse * pulse * pulse * pulse;
    //    RenderSystem.color4f(0.84F, 1F, 0.95F, (float) (pulse_mag_sqr * 0.5F));
//
    //    RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
    //    super.render(entity, f, f1, f2, f3, f4, f5);
    //    RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
//
    //    RenderSystem.depthMask(true);
    //    RenderSystem.disableBlend();
    //    RenderSystem.enableAlphaTest();
//
    //    if (invulnRender) {
    //        long frame = time / 3;
    //        randy.setSeed(frame * 1723609L);
    //        float o = randy.nextFloat() * 6.0f;
    //        float[] col = ColourHelper.HSVtoRGB(o, 1.0f, 1.0f);
//
    //        RenderSystem.color4f(col[0], col[1], col[2], 1);
    //        setEyes();
    //        super.render(entity, f, f1, f2, f3, f4, f5);
    //    }
//
    //    if (!CosmicShaderHelper.inventoryRender) {
    //        mc.entityRenderer.enableLightmap();
    //    }
    //    RenderSystem.enableLighting();
    //    RenderSystem.color4f(1, 1, 1, 1);
//
    //    // WINGS
    //    if (isFlying && !CosmicShaderHelper.inventoryRender) {
    //        setWings();
    //        mc.renderEngine.bindTexture(wingTex);
    //        super.render(entity, f, f1, f2, f3, f4, f5);
//
    //        CosmicShaderHelper.useShader();
    //        TextureUtils.bindBlockTexture();
    //        RenderSystem.disableAlpha();
    //        RenderSystem.enableBlend();
    //        RenderSystem.depthMask(false);
    //        overlay.render(entity, f, f1, f2, f3, f4, f5);
//
    //        CosmicShaderHelper.releaseShader();
//
    //        mc.renderEngine.bindTexture(wingGlowTex);
    //        RenderSystem.disableLighting();
    //        mc.entityRenderer.disableLightmap();
//
    //        RenderSystem.color4f(0.84F, 1F, 0.95F, (float) (pulse_mag_sqr * 0.5));
//
    //        RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
    //        super.render(entity, f, f1, f2, f3, f4, f5);
    //        RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
//
    //        RenderSystem.depthMask(true);
    //        RenderSystem.disableBlend();
    //        RenderSystem.enableAlpha();
    //        if (!CosmicShaderHelper.inventoryRender) {
    //            mc.entityRenderer.enableLightmap();
    //        }
    //        RenderSystem.enableLighting();
    //        RenderSystem.color(1, 1, 1, 1);
//
    //    }
//
    //}

    public void update(LivingEntity entityLiving, ItemStack itemstack, EquipmentSlot armorSlot) {
        currentSlot = armorSlot;

        invulnRender = false;

        ItemStack hat = entityLiving.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = entityLiving.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leg = entityLiving.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack foot = entityLiving.getEquippedStack(EquipmentSlot.FEET);

        boolean hasHat = hat != null && hat.getItem() == ModItems.infinity_helmet; //&& !((ItemArmorInfinity) (ModItems.infinity_helmet)).hasPhantomInk(hat);
        boolean hasChest = chest != null && chest.getItem() == ModItems.infinity_chestplate; // && !((ItemArmorInfinity) (ModItems.infinity_chestplate)).hasPhantomInk(chest);
        boolean hasLeg = leg != null && leg.getItem() == ModItems.infinity_pants; // && !((ItemArmorInfinity) (ModItems.infinity_pants)).hasPhantomInk(leg);
        boolean hasFoot = foot != null && foot.getItem() == ModItems.infinity_boots; // && !((ItemArmorInfinity) (ModItems.infinity_boots)).hasPhantomInk(foot);

        if (armorSlot == EquipmentSlot.HEAD) {//TODO, Wot.
            if (hasHat && hasChest && hasLeg && hasFoot) {
                invulnRender = true;
            }
        }

        showHat = hasHat && armorSlot == EquipmentSlot.HEAD;
        showChest = hasChest && armorSlot == EquipmentSlot.CHEST;
        showLeg = hasLeg && armorSlot == EquipmentSlot.LEGS;
        showFoot = hasFoot && armorSlot == EquipmentSlot.FEET;

        head.visible = showHat;
        helmet.visible = showHat;
        torso.visible = showChest || showLeg;
        rightArm.visible = showChest;
        leftArm.visible = showChest;
        rightLeg.visible = showLeg || showFoot;
        leftLeg.visible = showLeg || showFoot;

        //overlay.bipedHead.visible = showHat;
        //overlay.bipedHeadwear.visible = showHat;
        //overlay.bipedBody.visible = showChest || showLeg;
        //overlay.bipedRightArm.visible = showChest;
        //overlay.bipedLeftArm.visible = showChest;
        //overlay.bipedRightLeg.visible = showLeg || showFoot;
        //overlay.bipedLeftLeg.visible = showLeg || showFoot;

        //bipedLeftWing.visible = false;
        //bipedRightWing.visible = false;
        //overlay.bipedLeftWing.visible = false;
        //overlay.bipedRightWing.visible = false;

        isSneaking = entityLiving.isSneaking();
        riding = entityLiving.hasVehicle();
        child = entityLiving.isBaby();

        //overlay.isSneaking = entityLiving.isSneaking();
        //overlay.riding = entityLiving.hasVehicle();
        //overlay.child = entityLiving.isBaby();

        //invulnOverlay.isSneaking = entityLiving.isSneaking();
        //invulnOverlay.riding = entityLiving.hasVehicle();
        //invulnOverlay.child = entityLiving.isBaby();

        //overlay.swingProgress = handSwingProgress;
        //invulnOverlay.swingProgress = handSwingProgress;

        leftArmPose = ArmPose.EMPTY;
        rightArmPose = ArmPose.EMPTY;

        //overlay.leftArmPose = ArmPose.EMPTY;
        //overlay.rightArmPose = ArmPose.EMPTY;

        //invulnOverlay.leftArmPose = ArmPose.EMPTY;
        //invulnOverlay.rightArmPose = ArmPose.EMPTY;

        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;

            ItemStack main_hand = player.getStackInHand(Hand.MAIN_HAND);

            if (main_hand != null) {
                rightArmPose = ArmPose.ITEM;
                //overlay.rightArmPose = ArmPose.ITEM;
                //invulnOverlay.rightArmPose = ArmPose.ITEM;

                if (player.getItemUseTimeLeft() > 0) {

                    UseAction enumaction = main_hand.getUseAction();

                    if (enumaction == UseAction.BOW) {
                        rightArmPose = ArmPose.BOW_AND_ARROW;
                        //overlay.rightArmPose = ArmPose.BOW_AND_ARROW;
                        //invulnOverlay.rightArmPose = ArmPose.BOW_AND_ARROW;
                    } else if (enumaction == UseAction.BLOCK) {
                        rightArmPose = ArmPose.BLOCK;
                        //overlay.rightArmPose = ArmPose.BLOCK;
                        //invulnOverlay.rightArmPose = ArmPose.BLOCK;
                    }

                }

            }

            ItemStack off_hand = player.getStackInHand(Hand.OFF_HAND);
            if (off_hand != null) {
                leftArmPose = ArmPose.ITEM;
                //overlay.leftArmPose = ArmPose.ITEM;
                //invulnOverlay.leftArmPose = ArmPose.ITEM;

                if (player.getItemUseTimeLeft() > 0) {

                    UseAction enumaction = off_hand.getUseAction();

                    if (enumaction == UseAction.BOW) {
                        leftArmPose = ArmPose.BOW_AND_ARROW;
                        //overlay.leftArmPose = ArmPose.BOW_AND_ARROW;
                        //invulnOverlay.leftArmPose = ArmPose.BOW_AND_ARROW;
                    } else if (enumaction == UseAction.BLOCK) {
                        leftArmPose = ArmPose.BLOCK;
                        //overlay.leftArmPose = ArmPose.BLOCK;
                        //invulnOverlay.leftArmPose = ArmPose.BLOCK;
                    }

                }

            }
        }

    }

    //@Override
    //public void setRotationAngles(float f1, float speed, float ticks, float headYaw, float headPitch, float f6, Entity entity) {
    //    super.setRotationAngles(f1, speed, ticks, headYaw, headPitch, f6, entity);
    //    overlay.setRotationAngles(f1, speed, ticks, headYaw, headPitch, f6, entity);
    //    invulnOverlay.setRotationAngles(f1, speed, ticks, headYaw, headPitch, f6, entity);
    //    RenderManager manager = Minecraft.getMinecraft().getRenderManager();
    //    if (manager.entityRenderMap.containsKey(entity.getClass())) {
    //        Render r = manager.entityRenderMap.get(entity.getClass());
//
    //        if (r instanceof RenderBiped) {
    //            ModelBiped m = (ModelBiped) ((RenderBiped) r).getMainModel();
//
    //            copyBipedAngles(m, this);
    //        }
    //    }
    //}

    //public void setEyes() {
    //    bipedHead.showModel = false;
    //    bipedBody.showModel = false;
    //    bipedRightArm.showModel = false;
    //    bipedLeftArm.showModel = false;
    //    bipedRightLeg.showModel = false;
    //    bipedLeftLeg.showModel = false;
    //    bipedHeadwear.showModel = showHat ? true : false;
    //}

    //public void setGems() {
    //    bipedHead.showModel = false;
    //    bipedHeadwear.showModel = false;
    //    bipedBody.showModel = legs ? false : (showChest ? true : false);
    //    bipedRightArm.showModel = legs ? false : (showChest ? true : false);
    //    bipedLeftArm.showModel = legs ? false : (showChest ? true : false);
    //    bipedRightLeg.showModel = legs ? (showLeg ? true : false) : false;
    //    bipedLeftLeg.showModel = legs ? (showLeg ? true : false) : false;
    //}

    //public void setWings() {
    //    bipedBody.showModel = legs ? false : (showChest ? true : false);
    //    bipedLeftWing.showModel = true;
    //    bipedRightWing.showModel = true;
    //    bipedHeadwear.showModel = false;
    //    bipedRightArm.showModel = false;
    //    bipedLeftArm.showModel = false;
    //    bipedRightLeg.showModel = false;
    //    bipedLeftLeg.showModel = false;
    //    bipedHeadwear.showModel = false;
    //    bipedHead.showModel = false;
//
    //    overlay.bipedBody.showModel = legs ? false : (showChest ? true : false);
    //    overlay.bipedLeftWing.showModel = true;
    //    overlay.bipedRightWing.showModel = true;
    //    overlay.bipedHead.showModel = false;
    //    overlay.bipedHeadwear.showModel = false;
    //}

    //public void rebuildOverlay() {
    //    rebuildWings();
    //    overlay.rebuild(AvaritiaTextures.INFINITY_ARMOR_MASK, AvaritiaTextures.INFINITY_ARMOR_MASK_WINGS);
    //    invulnOverlay.rebuild(AvaritiaTextures.INFINITY_ARMOR_MASK_INV, null);
    //}

    //public static void copyPartAngles(ModelRenderer source, ModelRenderer dest) {
    //    dest.rotateAngleX = source.rotateAngleX;
    //    dest.rotateAngleY = source.rotateAngleY;
    //    dest.rotateAngleZ = source.rotateAngleZ;
    //}

    //public static void copyBipedAngles(ModelBiped source, ModelBiped dest) {
    //    copyPartAngles(source.bipedBody, dest.bipedBody);
    //    copyPartAngles(source.bipedHead, dest.bipedHead);
    //    copyPartAngles(source.bipedHeadwear, dest.bipedHeadwear);
    //    copyPartAngles(source.bipedLeftArm, dest.bipedLeftArm);
    //    copyPartAngles(source.bipedLeftLeg, dest.bipedLeftLeg);
    //    copyPartAngles(source.bipedRightArm, dest.bipedRightArm);
    //    copyPartAngles(source.bipedRightLeg, dest.bipedRightLeg);
    //}

    //public class Overlay extends ModelBiped {

//    //    public ModelArmorInfinity parent;
    //    public float expand;

//    //    public ModelRenderer bipedLeftWing;
    //    public ModelRenderer bipedRightWing;

//    //    public Overlay(ModelArmorInfinity parent, float expand) {
    //        this.parent = parent;
    //        this.expand = expand;
    //    }

//    //    @SuppressWarnings ("rawtypes")
    //    public void rebuild(TextureAtlasSprite icon, TextureAtlasSprite wingicon) {
    //        int ox = MathHelper.floor(icon.getMinU() * itempagewidth);
    //        int oy = MathHelper.floor(icon.getMinV() * itempageheight);

//    //        float heightoffset = 0.0f;
    //        int legoffset = parent.legs ? 32 : 0;

//    //        textureWidth = itempagewidth;
    //        textureHeight = itempageheight;
    //        bipedHead = new ModelRenderer(this, 0 + ox, 0 + legoffset + oy);
    //        bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, expand);
    //        bipedHead.setRotationPoint(0.0F, 0.0F + heightoffset, 0.0F);
    //        bipedHeadwear = new ModelRenderer(this, 32 + ox, 0 + legoffset + oy);
    //        bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, expand * 0.5F);
    //        bipedHeadwear.setRotationPoint(0.0F, 0.0F + heightoffset, 0.0F);
    //        bipedBody = new ModelRenderer(this, 16 + ox, 16 + legoffset + oy);
    //        bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, expand);
    //        bipedBody.setRotationPoint(0.0F, 0.0F + heightoffset, 0.0F);
    //        bipedRightArm = new ModelRenderer(this, 40 + ox, 16 + legoffset + oy);
    //        bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, expand);
    //        bipedRightArm.setRotationPoint(-5.0F, 2.0F + heightoffset, 0.0F);
    //        bipedLeftArm = new ModelRenderer(this, 40 + ox, 16 + legoffset + oy);
    //        bipedLeftArm.mirror = true;
    //        bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, expand);
    //        bipedLeftArm.setRotationPoint(5.0F, 2.0F + heightoffset, 0.0F);
    //        bipedRightLeg = new ModelRenderer(this, 0 + ox, 16 + legoffset + oy);
    //        bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, expand);
    //        bipedRightLeg.setRotationPoint(-1.9F, 12.0F + heightoffset, 0.0F);
    //        bipedLeftLeg = new ModelRenderer(this, 0 + ox, 16 + legoffset + oy);
    //        bipedLeftLeg.mirror = true;
    //        bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, expand);
    //        bipedLeftLeg.setRotationPoint(1.9F, 12.0F + heightoffset, 0.0F);

//    //        // rebuild wings!
    //        if (wingicon != null) {
    //            int oxw = MathHelper.floor(wingicon.getMinU() * itempagewidth);
    //            int oyw = MathHelper.floor(wingicon.getMinV() * itempageheight);

//    //            if (bipedBody.childModels == null) {
    //                bipedBody.childModels = new ArrayList();
    //            }
    //            if (bipedLeftWing != null) {
    //                bipedBody.childModels.remove(bipedLeftWing);
    //            }
    //            if (bipedRightWing != null) {
    //                bipedBody.childModels.remove(bipedRightWing);
    //            }

//    //            bipedLeftWing = new ModelRendererWing(this, oxw, oyw);
    //            bipedLeftWing.mirror = true;
    //            bipedLeftWing.addBox(0f, -11.6f, 0f, 0, 32, 32);
    //            bipedLeftWing.setRotationPoint(-1.5f, 0.0f, 2.0f);
    //            bipedLeftWing.rotateAngleY = (float) (Math.PI * 0.4);
    //            bipedBody.addChild(bipedLeftWing);

//    //            bipedRightWing = new ModelRendererWing(this, oxw, oyw);
    //            bipedRightWing.addBox(0f, -11.6f, 0f, 0, 32, 32);
    //            bipedRightWing.setRotationPoint(1.5f, 0.0f, 2.0f);
    //            bipedRightWing.rotateAngleY = (float) (-Math.PI * 0.4);
    //            bipedBody.addChild(bipedRightWing);
    //        }
    //    }

//    //    @Override
    //    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    //        copyBipedAngles(parent, this);

//    //        super.render(entity, f, f1, f2, f3, f4, f5);
    //    }

//    //    @Override
    //    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6, Entity entity) {
    //        super.setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
    //        RenderManager manager = Minecraft.getMinecraft().getRenderManager();
    //        if (manager.entityRenderMap.containsKey(entity.getClass())) {
    //            Render r = manager.entityRenderMap.get(entity.getClass());

//    //            if (r instanceof RenderBiped) {
    //                ModelBiped m = (ModelBiped) ((RenderBiped) r).getMainModel();

//    //                copyBipedAngles(m, this);
    //            }
    //        }
    //    }
    //}
}

