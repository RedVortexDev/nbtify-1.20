package io.letschill.nbtify;

import io.letschill.nbtify.callback.NBTCallBack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Nbtify implements ModInitializer {

    public static final String MODID = "nbtify";


    private static final Logger LOGGER = LoggerFactory.getLogger(MODID);


    @Override
    public void onInitialize() {
        LOGGER.info("Mod has been initialized.");

        ItemTooltipCallback listener = new NBTCallBack();

        ItemTooltipCallback.EVENT.register(listener);

        LOGGER.info("NbtViewer has been registered.");
    }
}
