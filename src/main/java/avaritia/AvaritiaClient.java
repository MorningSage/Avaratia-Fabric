package avaritia;

import avaritia.proxy.Proxy;
import avaritia.proxy.ProxyClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class AvaritiaClient implements ClientModInitializer {
    public static Proxy proxy;

    @Override
    public void onInitializeClient() {
        proxy = new ProxyClient();

        proxy.preInit();
        proxy.init();
        proxy.postInit();
    }
}
