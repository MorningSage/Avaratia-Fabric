package avaritia.handler;

import avaritia.init.ModItems;
import avaritia.items.InfinityArmor;
import avaritia.items.InfinitySword;
import avaritia.util.TextUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class AvaritiaEventHandler {

    private static Map<DimensionType, List<AEOCrawlerTask>> crawlerTasks = new HashMap<>();

    private static Set<ItemStack> capturedDrops = new LinkedHashSet<>();
    private static boolean doItemCapture = false;

    //These are defaults, loaded from config.
    public static final Set<String> defaultTrashOres = new HashSet<>();

    public static boolean isInfinite(PlayerEntity player) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) {
                continue;
            }
            ItemStack stack = player.getEquippedStack(slot);
            if (stack.isEmpty() || !(stack.getItem() instanceof InfinityArmor)) {
                return false;
            }
        }
        return true;
    }

    //region EntityItem capture.
    public static void enableItemCapture() {
        doItemCapture = true;
    }

    public static void stopItemCapture() {
        doItemCapture = false;
    }

    public static boolean isItemCaptureEnabled() {
        return doItemCapture;
    }

    public static Set<ItemStack> getCapturedDrops() {
        Set<ItemStack> dropsCopy = new LinkedHashSet<>(capturedDrops);
        capturedDrops.clear();
        return dropsCopy;
    }

    public static ActionResult onEntityJoinWorld(Entity entity, World world) {
        if (doItemCapture) {
            if (entity instanceof ItemEntity) {
                ItemStack stack = ((ItemEntity) entity).getStack();
                capturedDrops.add(stack);
                return ActionResult.CONSUME;
            }
        }

        return ActionResult.PASS;
    }
    //endregion

    public static AEOCrawlerTask startCrawlerTask(World world, PlayerEntity player, ItemStack stack, BlockPos coords, int steps, boolean leaves, boolean force, Set<BlockPos> posChecked) {
        AEOCrawlerTask swapper = new AEOCrawlerTask(world, player, stack, coords, steps, leaves, force, posChecked);
        DimensionType dim = world.getDimension();
        if (!crawlerTasks.containsKey(dim)) {
            crawlerTasks.put(dim, new ArrayList<>());
        }
        crawlerTasks.get(dim).add(swapper);
        return swapper;
    }

    public static void onTickEnd(World world) {//TODO, clamp at specific num ops per tick.
        DimensionType dim = world.getDimension();
        if (crawlerTasks.containsKey(dim)) {
            List<AEOCrawlerTask> swappers = crawlerTasks.get(dim);
            List<AEOCrawlerTask> swappersSafe = new ArrayList<>(swappers);
            swappers.clear();
            for (AEOCrawlerTask s : swappersSafe) {
                if (s != null) {
                    s.tick();
                }
            }
        }
    }

    public static ActionResult onPlayerMine(PlayerEntity playerEntity, BlockPos pos, Direction direction) {
        if (/*ConfigHandler.bedrockBreaker &&*/ direction != null && !playerEntity.getStackInHand(Hand.MAIN_HAND).isEmpty() && !playerEntity.abilities.creativeMode) {
            BlockState state = playerEntity.world.getBlockState(pos);

            if (state.getHardness(playerEntity.world, pos) <= -1 && playerEntity.getStackInHand(Hand.MAIN_HAND).getItem() == ModItems.infinity_pickaxe && (state.getMaterial() == Material.STONE || state.getMaterial() == Material.METAL)) {
                if (playerEntity.getStackInHand(Hand.MAIN_HAND).hasTag() && playerEntity.getStackInHand(Hand.MAIN_HAND).getTag().getBoolean("hammer")) {
                    playerEntity.sendMessage(new LiteralText("ToDo: Not added yet"), false);
                    //ModItems.infinity_pickaxe.canMine(playerEntity.getStackInHand(Hand.MAIN_HAND), pos, playerEntity);
                }
            }
        }

        return ActionResult.PASS;
    }

    //@SubscribeEvent
    //public void handleExtraLuck(HarvestDropsEvent event) {
    //    if (event.getHarvester() == null) {
    //        return;
    //    }
    //    ItemStack mainHand = event.getHarvester().getHeldItem(EnumHand.MAIN_HAND);
//
    //    if (!mainHand.isEmpty() && mainHand.getItem() == ModItems.infinity_pickaxe) {
    //        applyLuck(event, 4);
    //    }
    //}

    public static void applyLuck(BlockState state, DefaultedList<ItemStack> drops, int multiplier) {
        //Only do stuff on rock.
        if (state.getMaterial() == Material.STONE) {
            List<ItemStack> adds = new ArrayList<>();
            List<ItemStack> removals = new ArrayList<>();

            for (ItemStack drop : drops) {
                //We are a drop that is not the same as the Blocks ItemBlock and the drop itself is not an ItemBlock, AKA, Redstone, Lapis.
                if (drop.getItem() != state.getBlock().asItem() && !(drop.getItem() instanceof BlockItem)) {
                    //Apply standard Luck modifier
                    drop.setCount(Math.min(drop.getCount() * multiplier, drop.getMaxCount()));
                } else if (/*ConfigHandler.fracturedOres &&*/ drop.getItem() == state.getBlock().asItem()) {
                    //kk, we are an ore block, Lets test for oreDict and add fractured ores.
                    //ItemFracturedOre fracturedOre = ModItems.fractured_ore;
                    //int[] iDs = OreDictionary.getOreIDs(drop);
                    //for (int id : iDs) {
                    //    String oreName = OreDictionary.getOreName(id);
                    //    if (oreName.startsWith("ore")) {
                    //        // add the fractured ores
                    //        adds.add(fracturedOre.getStackForOre(drop, Math.min(drop.getCount() * (multiplier + 1), drop.getMaxStackSize())));
                    //        removals.add(drop);
                    //        break;
                    //    }
                    //}
                }
            }
            drops.addAll(adds);
            drops.removeAll(removals);
        }
    }

    public static void onTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
        if (stack.getItem() instanceof InfinitySword) {
            for (int x = 0; x < lines.size(); x++) {
                if (lines.get(x).toString().contains(EntityAttributes.GENERIC_ATTACK_DAMAGE.getTranslationKey())) {
                    lines.set(x, new LiteralText("+").formatted(Formatting.DARK_GREEN)
                        .append(TextUtils.makeFabulous(I18n.translate("tip.infinity") + " "))
                        .append(new TranslatableText("attribute.name.generic.attack_damage").formatted(Formatting.DARK_GREEN)));

                    return;
                }
            }
        }
    }

    //@SubscribeEvent
    //public void onGetHurt(LivingHurtEvent event) {
    //    if (!(event.getEntityLiving() instanceof EntityPlayer)) {
    //        return;
    //    }
    //    EntityPlayer player = (EntityPlayer) event.getEntityLiving();
    //    if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == ModItems.infinity_sword && player.isHandActive()) {//TODO Blocking? Maybe add a shield?
    //        event.setCanceled(true);
    //    }
    //    if (isInfinite(player) && !event.getSource().damageType.equals("infinity")) {
    //        event.setCanceled(true);
    //    }
    //}

    //@SubscribeEvent
    //public void onAttacked(LivingAttackEvent event) {
    //    if (!(event.getEntityLiving() instanceof EntityPlayer)) {
    //        return;
    //    }
    //    if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
    //        return;
    //    }
    //    EntityPlayer player = (EntityPlayer) event.getEntityLiving();
    //    if (isInfinite(player) && !event.getSource().damageType.equals("infinity")) {
    //        event.setCanceled(true);
    //    }
    //}

    //@SubscribeEvent
    //public void onLivingDrops(LivingDropsEvent event) {
    //    if (event.isRecentlyHit() && event.getEntityLiving() instanceof EntitySkeleton && event.getSource().getTrueSource() instanceof EntityPlayer) {
    //        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
    //        if (!player.getHeldItem(EnumHand.MAIN_HAND).isEmpty() && player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.skull_sword) {
    //            // ok, we need to drop a skull then.
    //            if (event.getDrops().isEmpty()) {
    //                addDrop(event, new ItemStack(Items.SKULL, 1, 1));
    //            } else {
    //                int skulls = 0;
//
    //                for (int i = 0; i < event.getDrops().size(); i++) {
    //                    EntityItem drop = event.getDrops().get(i);
    //                    ItemStack stack = drop.getItem();
    //                    if (stack.getItem() == Items.SKULL) {
    //                        if (stack.getItemDamage() == 1) {
    //                            skulls++;
    //                        } else if (stack.getItemDamage() == 0) {
    //                            skulls++;
    //                            stack.setItemDamage(1);
    //                        }
    //                    }
    //                }
//
    //                if (skulls == 0) {
    //                    addDrop(event, new ItemStack(Items.SKULL, 1, 1));
    //                }
    //            }
//
    //        }
    //    }
    //}

    //@SubscribeEvent
    //public void diggity(BreakSpeed event) {
    //    if (!event.getEntityLiving().getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
    //        ItemStack held = event.getEntityLiving().getHeldItem(EnumHand.MAIN_HAND);
    //        if (held.getItem() == ModItems.infinity_pickaxe || held.getItem() == ModItems.infinity_shovel) {
    //            if (!event.getEntityLiving().onGround) {
    //                event.setNewSpeed(event.getNewSpeed() * 5);
    //            }
    //            if (!event.getEntityLiving().isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier(event.getEntityLiving())) {
    //                event.setNewSpeed(event.getNewSpeed() * 5);
    //            }
    //            if (held.getTagCompound() != null) {
    //                if (held.getTagCompound().getBoolean("hammer") || held.getTagCompound().getBoolean("destroyer")) {
    //                    event.setNewSpeed(event.getNewSpeed() * 0.5F);
    //                }
    //            }
    //        }
    //    }
    //}

    //@SubscribeEvent
    //public void canHarvest(PlayerEvent.HarvestCheck event) {
    //    if (!event.getEntityLiving().getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
    //        ItemStack held = event.getEntityLiving().getHeldItem(EnumHand.MAIN_HAND);
    //        if (held.getItem() == ModItems.infinity_shovel && event.getTargetBlock().getMaterial() == Material.ROCK) {
    //            if (held.getTagCompound() != null && held.getTagCompound().getBoolean("destroyer") && isGarbageBlock(event.getTargetBlock().getBlock())) {
    //                event.setResult(Event.Result.ALLOW);
    //            }
    //        }
    //    }
    //}

    @Environment(EnvType.CLIENT)
    private static boolean isGarbageBlock(Block block) {
        // In case there's no tag for these
        if (block == Blocks.COBBLESTONE || block == Blocks.STONE || block == Blocks.NETHERRACK) return true;

        // ToDo: Add generic tags for this?  There doesn't seem to be a built in tag for cobble, et al.
        for (Identifier id : BlockTags.getContainer().getTagsFor(block)) {
            Tag<Block> blockTag = BlockTags.getContainer().get(id);

            if (blockTag == null) continue;

            if (blockTag.contains(Blocks.COBBLESTONE) || blockTag.contains(Blocks.STONE) || blockTag.contains(Blocks.NETHERRACK)) {
                return true;
            }
        }

        return false;
    }

    //@SubscribeEvent
    //public void onDeath(LivingDeathEvent event) {
    //    if (event.getEntityLiving() instanceof EntityPlayer) {
    //        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
    //        if (isInfinite(player) && !event.getSource().getDamageType().equals("infinity")) {
    //            event.setCanceled(true);
    //            player.setHealth(player.getMaxHealth());
    //        }
    //    }
    //}

    //private void addDrop(LivingDropsEvent event, ItemStack drop) {
    //    EntityItem entityitem = new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, drop);
    //    entityitem.setDefaultPickupDelay();
    //    event.getDrops().add(entityitem);
    //}

    //@SubscribeEvent
    //public void clusterClustererererer(EntityItemPickupEvent event) {
    //    if (event.getEntityPlayer() != null && event.getItem().getItem().getItem() == ModItems.matter_cluster) {
    //        ItemStack stack = event.getItem().getItem();
    //        EntityPlayer player = event.getEntityPlayer();
//
    //        for (ItemStack slot : player.inventory.mainInventory) {
    //            if (stack.isEmpty()) {
    //                break;
    //            }
    //            if (slot.getItem() == ModItems.matter_cluster) {
    //                ItemMatterCluster.mergeClusters(stack, slot);
    //            }
    //        }
    //    }
    //}

}
