package com.lypaka.gces.Listeners;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

@Mod.EventBusSubscriber(modid = GCES.MOD_ID)
public class ServerShuttingDownListener {

    @SubscribeEvent
    public static void onShuttingDown (FMLServerStoppingEvent event) {

        GCES.configManager.getConfigNode(1, "Accounts").setValue(ConfigGetters.playerAccountsMap);
        GCES.configManager.save();

    }

}
