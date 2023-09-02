package io.letschill.nbtify.callback;

import com.mojang.blaze3d.platform.InputConstants;
import io.letschill.nbtify.screens.NBTScreen;
import io.letschill.nbtify.utils.ToolTipUtils;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Objects;

public final class NBTCallBack implements ItemTooltipCallback {

    public static boolean flag = false;

    @Override
    public void getTooltip(ItemStack stack, TooltipFlag context, List<Component> lines) {
        if (!context.isAdvanced()) {
            return;
        }

        if (stack.getTag() == null) {
            return;
        }
        if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 258)) {
            lines.add(Component.empty());
            getGuide(lines);

            String string = ToolTipUtils.format(stack);
            Minecraft.getInstance().setScreen(new NBTScreen(
                    Minecraft.getInstance().screen, string
            ));
            return;
        }

        if(Screen.hasAltDown()) {
            lines.remove(lines.size() - 1);

            String string = ToolTipUtils.Colorize(Objects.requireNonNull(ToolTipUtils.format(stack)));

            for (String line: string.lines().toList()) {
                lines.add(Component.literal(line));
            }
            return;
        }

        if (Screen.hasControlDown()) {
            if (!flag) {
                String data = ToolTipUtils.format(stack);

                if (data == null) {
                    return;
                }

                Minecraft.getInstance().keyboardHandler.setClipboard(data);

                SystemToast.add(
                        Minecraft.getInstance().getToasts(),
                        SystemToast.SystemToastIds.TUTORIAL_HINT,
                        Component.literal("NBTify"),
                        Component.literal("Your NBT data has been copied!")
                );

                lines.add(Component.empty());
                getGuide(lines);

                flag = true;
                return;
            }
            lines.add(Component.empty());
            getGuide(lines);
            return;
        }

        flag = false;

        lines.add(Component.empty());
    }
}
