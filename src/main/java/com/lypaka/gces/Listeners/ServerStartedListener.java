package com.lypaka.gces.Listeners;

import com.lypaka.gces.GCES;
import com.lypaka.gces.SaveTask;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

@Mod.EventBusSubscriber(modid = GCES.MOD_ID)
public class ServerStartedListener {

    @SubscribeEvent
    public static void onServerStarted (FMLServerStartedEvent event) {

        SaveTask.startAutoSave();

    }

}
