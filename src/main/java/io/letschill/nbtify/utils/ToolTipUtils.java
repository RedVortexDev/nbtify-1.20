package io.letschill.nbtify.utils;

import com.google.gson.*;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolTipUtils {

    public static String format(ItemStack stack) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (stack.getTag() == null) {
            return null;
        }

        String nbtList = String.valueOf(stack.getTag()).replaceAll("'", "\"");

        Pattern p = Pattern.compile("(?<!\")\\b(\\w+)\\b(?!\"):");
        Matcher m = p.matcher(nbtList);

        while (m.find()) nbtList = nbtList.replace(
                "(?<!\")\\b" + m.group(1) + "\\b(?!\")",
                '"' + m.group(1) + "\""
        );

        nbtList = nbtList.replaceAll("\"\\{", "{").replaceAll("}\"", "}");

        try {
            JsonElement object = JsonParser.parseString(nbtList);

            return gson.toJson(object);
        } catch (JsonSyntaxException error) {
            return "{\"parser\": \"failed!\"}";
        }
    }

    public static String Colorize(String string) {

        return string
                .replaceAll(" \"", ToolTipUtils.Color("red").toString() + "\"")
                .replaceAll(":§c", "§f:§a ")
                .replaceAll("\\{", ToolTipUtils.Color("white") + "\\{")
                .replaceAll("}", ToolTipUtils.Color("white") + "}")
                .replaceAll("\\[", ToolTipUtils.Color("white") + "\\[")
                .replaceAll("]", ToolTipUtils.Color("white") + "]")
                .replaceAll(",", ToolTipUtils.Color("white") + ",");
    }

    public static ChatFormatting Color(String color) {

        return switch (color) {
            case "black" -> ChatFormatting.BLACK;
            case "dark_blue" -> ChatFormatting.DARK_BLUE;
            case "dark_green" -> ChatFormatting.DARK_GREEN;
            case "dark_aqua" -> ChatFormatting.DARK_AQUA;
            case "dark_red" -> ChatFormatting.DARK_RED;
            case "dark_purple" -> ChatFormatting.DARK_PURPLE;
            case "gold" -> ChatFormatting.GOLD;
            case "gray" -> ChatFormatting.GRAY;
            case "dark_gray" -> ChatFormatting.DARK_GRAY;
            case "blue" -> ChatFormatting.BLUE;
            case "green" -> ChatFormatting.GREEN;
            case "aqua" -> ChatFormatting.AQUA;
            case "red" -> ChatFormatting.RED;
            case "light_purple" -> ChatFormatting.LIGHT_PURPLE;
            case "yellow" -> ChatFormatting.YELLOW;
            default -> ChatFormatting.WHITE;
        };
    }
}