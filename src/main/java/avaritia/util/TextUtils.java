package avaritia.util;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.time.LocalDateTime;
import java.util.List;

import static net.minecraft.util.Formatting.*;

@Environment(EnvType.CLIENT)
public class TextUtils {

    private static final Formatting[] fabulousness = new Formatting[] {
        RED, GOLD, YELLOW, GREEN, AQUA, BLUE, LIGHT_PURPLE
    };

    private static final Formatting[] sanic = new Formatting[] {
        BLUE, BLUE, BLUE, BLUE, WHITE, BLUE, WHITE, WHITE, BLUE, WHITE, WHITE, BLUE, RED, WHITE, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY
    };

    public static MutableText makeFabulous(String input) {
        return ludicrousFormatting(input, fabulousness, 80.0, 1, 1);
    }

    public static MutableText makeSANIC(String input) {
        return ludicrousFormatting(input, sanic, 50.0, 2, 1);
    }

    public static MutableText ludicrousFormatting(String input, Formatting[] colours, double delay, int step, int posstep) {
        MutableText text = new LiteralText("");

        if (delay <= 0) {
            delay = 0.001;
        }

        long sysTime = System.nanoTime() / 1000000 - MinecraftClient.getInstance().getSnooper().getStartTime();

        int offset = (int) Math.floor(sysTime / delay) % colours.length;

        for (int i = 0; i < input.length(); i++) {
            int col = ((i * posstep) + colours.length - offset) % colours.length;

            text.append(new LiteralText(input.substring(i, i + 1)).formatted((colours[col])));
        }

        return text;
    }
}

