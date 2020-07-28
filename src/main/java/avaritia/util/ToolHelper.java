package avaritia.util;


import avaritia.handler.AvaritiaEventHandler;
import avaritia.init.ModItems;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class ToolHelper {

    public static Material[] materialsPick = new Material[] { Material.STONE, Material.METAL, Material.ICE, Material.GLASS, Material.PISTON, Material.REPAIR_STATION };
    public static Material[] materialsShovel = new Material[] { Material.SOLID_ORGANIC, Material.SOIL, Material.AGGREGATE, Material.SNOW_BLOCK, Material.SNOW_LAYER, Material.ORGANIC_PRODUCT };
    public static Set<Material> materialsAxe = Sets.newHashSet(Material.UNUSED_PLANT, Material.LEAVES, Material.PLANT, Material.WOOD, Material.REPLACEABLE_PLANT);

    public static void aoeBlocks(PlayerEntity player, ItemStack stack, World world, BlockPos origin, BlockPos min, BlockPos max, Block target, Set<Material> validMaterials, boolean filterTrash) {

        AvaritiaEventHandler.enableItemCapture();

        for (int lx = min.getX(); lx < max.getX(); lx++) {
            for (int ly = min.getY(); ly < max.getY(); ly++) {
                for (int lz = min.getZ(); lz < max.getZ(); lz++) {
                    BlockPos pos = origin.add(lx, ly, lz);
                    removeBlockWithDrops(player, stack, world, pos, target, validMaterials);
                }
            }
        }

        AvaritiaEventHandler.stopItemCapture();
        Set<ItemStack> drops = AvaritiaEventHandler.getCapturedDrops();
        if (filterTrash) {
            drops = removeTrash(stack, drops);
        }
        if (!world.isClient) {
            //List<ItemStack> clusters = ItemMatterCluster.makeClusters(drops);
            //for (ItemStack cluster : clusters) {
            //    ItemUtils.dropItem(world, origin, cluster);
            //}
        }

    }

    public static void removeBlockWithDrops(PlayerEntity player, ItemStack stack, World world, BlockPos pos, Block target, Set<Material> validMaterials) {
        //if (!world.isBlockLoaded(pos)) {
        //    return;
        //}
        //BlockState state = world.getBlockState(pos);
        //Block block = state.getBlock();
        //if (!world.isClient) {
        //    if ((target != null && target != state.getBlock()) || world.isAir(pos)) {
        //        return;
        //    }
        //    Material material = state.getMaterial();
        //    if (block == Blocks.GRASS && stack.getItem() == ModItems.infinity_axe) {
        //        world.setBlockState(pos, Blocks.DIRT.getDefaultState());
        //    }
        //    if (!block.canHarvestBlock(world, pos, player) || !validMaterials.contains(material)) {
        //        return;
        //    }
        //    BreakEvent event = new BreakEvent(world, pos, state, player);
        //    MinecraftForge.EVENT_BUS.post(event);
        //    if (!event.isCanceled()) {
        //        if (!player.abilities.creativeMode) {
        //            BlockEntity tile = world.getBlockEntity(pos);
        //            block.onBreak(world, pos, state, player);
        //            if (block.removedByPlayer(state, world, pos, player, true)) {
        //                block.onBroken(world, pos, state);
        //                block.harvestBlock(world, player, pos, state, tile, stack);
        //            }
        //        } else {
        //            world.setBlockToAir(pos);
        //        }
        //    }
        //}
    }

    public static Set<ItemStack> removeTrash(ItemStack holdingStack, Set<ItemStack> drops) {
        Set<ItemStack> trashItems = new HashSet<>();
        for (ItemStack drop : drops) {
            if (isTrash(holdingStack, drop)) {
                //Lumberjack.info("Removing: " + drop.toString());
                trashItems.add(drop);
            }
        }
        drops.removeAll(trashItems);
        return drops;
    }

    private static boolean isTrash(ItemStack holdingStack, ItemStack suspect) {
        boolean isTrash = false;
        //for (int id : OreDictionary.getOreIDs(suspect)) {
        //    for (String ore : AvaritiaEventHandler.defaultTrashOres) {
        //        if (OreDictionary.getOreName(id).equals(ore)) {
        //            return true;
        //        }
        //    }
        //}

        return isTrash;
    }

    public static List<ItemStack> collateDropList(Set<ItemStack> input) {
        return collateMatterClusterContents(collateMatterCluster(input));
    }

    public static List<ItemStack> collateMatterClusterContents(Map<ItemStackWrapper, Integer> input) {
        List<ItemStack> collated = new ArrayList<>();

        for (Map.Entry<ItemStackWrapper, Integer> e : input.entrySet()) {
            int count = e.getValue();
            ItemStackWrapper wrap = e.getKey();

            int size = wrap.stack.getMaxCount();
            int fullstacks = (int) Math.floor(count / size);

            for (int i = 0; i < fullstacks; i++) {
                count -= size;
                ItemStack stack = wrap.stack.copy();
                stack.setCount(size);
                collated.add(stack);
            }

            if (count > 0) {
                ItemStack stack = wrap.stack.copy();
                stack.setCount(count);
                collated.add(stack);
            }
        }

        return collated;
    }

    public static Map<ItemStackWrapper, Integer> collateMatterCluster(Set<ItemStack> input) {
        Map<ItemStackWrapper, Integer> counts = new HashMap<>();

        if (input != null) {
            for (ItemStack stack : input) {
                ItemStackWrapper wrap = new ItemStackWrapper(stack);
                if (!counts.containsKey(wrap)) {
                    counts.put(wrap, 0);
                }

                counts.put(wrap, counts.get(wrap) + stack.getCount());
            }
        }

        return counts;
    }
}

