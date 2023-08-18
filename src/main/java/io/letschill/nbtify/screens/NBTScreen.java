package io.letschill.nbtify.screens;

import io.letschill.nbtify.utils.ToolTipUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.Timer;
import java.util.TimerTask;

public class NBTScreen extends Screen {
    private static Screen lastScreen;
    public static String jsonNBT;
    private static int yPos;

    public NBTScreen(Screen lastScreen, String jsonNBT) {
        super(Component.literal(""));

        NBTScreen.lastScreen = lastScreen;
        NBTScreen.jsonNBT = jsonNBT;
    }

    @Override
    public void init() {

        this.addRenderableWidget(
                Button.builder(
                        Component.literal("Copy"),
                        (button -> {
                            assert this.minecraft != null;
                            this.minecraft.keyboardHandler.setClipboard(jsonNBT);

                            SystemToast.add(
                                    Minecraft.getInstance().getToasts(),
                                    SystemToast.SystemToastIds.TUTORIAL_HINT,
                                    Component.literal("NBTIFY"),
                                    Component.literal("Your NBT data has been copied!")
                            );
                        })
                ).bounds((this.width / 2) - 90, this.height - 60, 200, 20).build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.literal("Back"),
                        (button -> {
                            assert this.minecraft != null;
                            this.minecraft.setScreen(lastScreen);
                        })
                ).bounds((this.width / 2) - 90, this.height - 40, 200, 20).build()
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        guiGraphics.fill(5, 20, this.width - 5, this.height - 10, -16777216);
        renderBackground(guiGraphics);
        super.render(guiGraphics, i, j, f);

        int k=0;
        for (String line: ToolTipUtils.Colorize(jsonNBT).lines().toList()) {

            int y = 25 + (10 * k) - yPos;

            if (y <= 18 || y >= this.height - 18) {
                k++;
                continue;
            }

            guiGraphics.drawString(this.font, line, 10, y, -1);
            k++;
        }
    }

    protected static long getOffscreenRows() {
        if (jsonNBT.lines().count() < 47) {
            return 0;
        }
        return jsonNBT.lines().count() * 8;
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f) {
        yPos = (int) Mth.clamp((int) (yPos - (f * 10)), 0, getOffscreenRows());

        Timer time = new Timer();
        final int[] runCount = {0};
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                NBTScreen.yPos = (int) Mth.clamp((int) (yPos - (f * 5)), 0, NBTScreen.getOffscreenRows());
                runCount[0]++;

                if (runCount[0] > 5) this.cancel();
            }
        };

        time.schedule(task, 0, 50);

        return true;
    }

    @Override
    public boolean mouseDragged(double d, double e, int f, double g, double h) {
        yPos = (int) Mth.clamp(yPos - (h * 2), 0, getOffscreenRows());
        return true;
    }
}