package avaritia.handler;

import avaritia.util.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Set;

public class AEOCrawlerTask {

    final World world;
    final PlayerEntity player;
    final ItemStack stack;
    final int steps;
    final BlockPos origin;
    final boolean leaves;
    final boolean force;
    final Set<BlockPos> posChecked;

    AEOCrawlerTask(World world, PlayerEntity player, ItemStack stack, BlockPos origin, int steps, boolean leaves, boolean force, Set<BlockPos> posChecked) {
        this.world = world;
        this.player = player;
        this.stack = stack;
        this.origin = origin;
        this.steps = steps;
        this.leaves = leaves;
        this.force = force;
        this.posChecked = posChecked;
    }

    void tick() {
        BlockState originState = world.getBlockState(origin);
        Block originBlock = originState.getBlock();
        if (!force && world.isAir(origin)) { //originBlock.is(originState, world, origin)) {
            return;
        }
        ToolHelper.removeBlockWithDrops(player, stack, world, origin, null, ToolHelper.materialsAxe);
        if (steps == 0) {
            return;
        }
        for (Direction dir : Direction.values()) {
            BlockPos stepPos = origin.offset(dir);
            if (posChecked.contains(stepPos)) {
                continue;
            }
            BlockState stepState = world.getBlockState(stepPos);
            Block stepBlock = stepState.getBlock();
            boolean log = stepBlock.isIn(BlockTags.LOGS);
            boolean leaf = stepBlock.isIn(BlockTags.LEAVES);
            if (log || leaf) {
                int steps = this.steps - 1;
                steps = leaf ? leaves ? steps : 3 : steps;
                AvaritiaEventHandler.startCrawlerTask(world, player, stack, stepPos, steps, leaf, false, posChecked);
                posChecked.add(stepPos);
            }
        }
    }
}

