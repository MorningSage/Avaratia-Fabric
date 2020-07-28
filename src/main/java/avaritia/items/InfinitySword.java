package avaritia.items;

import avaritia.Avaritia;
import avaritia._helpers.EnumHelper;
import avaritia.api.ICosmicRenderItem;
import avaritia.api.IHazCustomItemEntity;
import avaritia.api.registration.IModelRegister;
import avaritia.entities.ImmortalItemEntity;
import avaritia.handler.AvaritiaEventHandler;
import avaritia.init.AvaritiaTextures;
import avaritia.init.ModItems;
import avaritia.mixins.accessors.LivingEntityAccessor;
import avaritia.util.InfinitySwordDamageSource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.texture.Sprite;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InfinitySword extends SwordItem implements ICosmicRenderItem, IModelRegister, IHazCustomItemEntity {

    private static final ToolMaterial TOOL_MATERIAL = EnumHelper.addToolMaterial(
        "INFINITY_SWORD",
        32, 9999, 9999F, 3.0F, 200,
        () -> Ingredient.ofItems(Items.IRON_INGOT)
    );

    public InfinitySword() {
        super(
            TOOL_MATERIAL,
            3,
            -2.4F,
            new Settings().group(Avaritia.ITEM_GROUP).maxDamage(0).fireproof()
        );

        //
        ClientEntityEvents.ENTITY_LOAD.register(this::entityLoad);
        ServerEntityEvents.ENTITY_LOAD.register(this::entityLoad);
    }

    private void entityLoad(Entity entity, World world) {
        // To prevent StackOverflow (even though it's a great site)
        // We must determine the class and not instanceof because we
        // should be allowed to extend ItemEntity
        if (!entity.getClass().equals(ItemEntity.class)) return;

        ItemStack stack = ((ItemEntity) entity).getStack();
        if (!(stack.getItem() instanceof IHazCustomItemEntity)) return;

        IHazCustomItemEntity item = (IHazCustomItemEntity) ((ItemEntity) entity).getStack().getItem();
        if (!item.hasCustomEntity(stack)) return;

        Entity newEntity = item.createEntity(world, entity, stack);

        if (newEntity != null) {
            entity.remove();

            if (!world.isClient) {
                // Spawning causes an issue with the chunks when the world is first initializing.
                ((ServerWorld) world).loadEntity(newEntity);
            }
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity victim, LivingEntity player) {
        if (player.world.isClient) {
            return true;
        }
        if (victim instanceof PlayerEntity) {
            PlayerEntity pvp = (PlayerEntity) victim;
            if (AvaritiaEventHandler.isInfinite(pvp)) {
                victim.damage(new InfinitySwordDamageSource(player).publicSetBypassesArmor(), 4.0F);
                return true;
            }
            if (pvp.getStackInHand(Hand.MAIN_HAND) != null && pvp.getStackInHand(Hand.MAIN_HAND).getItem() == ModItems.infinity_sword && pvp.isUsingItem()) {
                return true;
            }
        }

        ((LivingEntityAccessor) victim).setPlayerHitTimer(60);
        victim.getDamageTracker().onDamage(new InfinitySwordDamageSource(player), victim.getHealth(), victim.getHealth());
        victim.setHealth(0);
        victim.onDeath(new EntityDamageSource("infinity", player));
        return true;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!entity.world.isClient && entity instanceof PlayerEntity) {
            PlayerEntity victim = (PlayerEntity) entity;
            if (victim.abilities.creativeMode && !victim.isDead() && victim.getHealth() > 0 && !AvaritiaEventHandler.isInfinite(victim)) {
                victim.getDamageTracker().onDamage(new InfinitySwordDamageSource(user), victim.getHealth(), victim.getHealth());
                victim.setHealth(0);
                victim.onDeath(new EntityDamageSource("infinity", user));
                //TODO
                //player.addStat(Achievements.creative_kill, 1);
                return ActionResult.success(user.world.isClient);
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return ModItems.COSMIC_RARITY;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Sprite getMaskTexture(ItemStack stack, LivingEntity player) {
        return AvaritiaTextures.INFINITY_SWORD_MASK;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public float getMaskOpacity(ItemStack stack, LivingEntity player) {
        return 1.0f;
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new ImmortalItemEntity(world, location, itemstack);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return false;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerModels() {
        //ModelResourceLocation sword = new ModelResourceLocation("avaritia:tools", "type=infinity_sword");
        //ModelLoader.registerItemVariants(ModItems.infinity_pickaxe, sword);
        //IBakedModel wrapped = new CosmicItemRender(TransformUtils.DEFAULT_TOOL, modelRegistry -> modelRegistry.getObject(sword));
        //ModelRegistryHelper.register(sword, wrapped);
        //ModelLoader.setCustomMeshDefinition(ModItems.infinity_sword, (ItemStack stack) -> sword);
    }
}
