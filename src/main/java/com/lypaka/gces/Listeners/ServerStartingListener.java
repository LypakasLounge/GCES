package com.lypaka.gces.Listeners;

import com.lypaka.gces.GCES;
import com.lypaka.gces.Listeners.BetterPixelmonSpawner.BPSPokemonSpawnListener;
import com.lypaka.gces.Listeners.BetterPixelmonSpawner.BPSShinySpawnListener;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = GCES.MOD_ID)
public class ServerStartingListener {

    @SubscribeEvent
    public static void onServerStarting (FMLServerStartingEvent event) {

        MinecraftForge.EVENT_BUS.register(new LoginListener());
        Pixelmon.EVENT_BUS.register(new CaptureListener());
        Pixelmon.EVENT_BUS.register(new LevelingListener());
        Pixelmon.EVENT_BUS.register(new MegaListener());
        Pixelmon.EVENT_BUS.register(new BattleStartListener());
        Pixelmon.EVENT_BUS.register(new DynamaxListener());
        if (ModList.get().isLoaded("betterpixelmonspawner")) {

            MinecraftForge.EVENT_BUS.register(new BPSPokemonSpawnListener());
            MinecraftForge.EVENT_BUS.register(new BPSShinySpawnListener());

        } else {

            Pixelmon.EVENT_BUS.register(new SpawnListener());

        }

    }

}
