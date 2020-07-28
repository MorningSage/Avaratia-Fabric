package avaritia;

import avaritia._helpers.ItemGroupHelper;
import avaritia.api.IHazCustomItemEntity;
import avaritia.proxy.Proxy;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class Avaritia implements ModInitializer {
	public static final String MOD_ID = "avaritia";
	public static Proxy proxy;

	public static final ItemGroup ITEM_GROUP = new ItemGroupHelper(MOD_ID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Blocks.COBBLESTONE);
		}
	};

	@Override
	public void onInitialize() {
		proxy = new Proxy();

		proxy.preInit();
		proxy.init();
		proxy.postInit();

	}
}
