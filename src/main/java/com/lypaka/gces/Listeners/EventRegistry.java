package com.lypaka.gces.Listeners;

import com.lypaka.gces.Listeners.Generations.*;
import com.lypaka.gces.Listeners.Generations.BetterPixelmonSpawner.AlphaSpawnListener;
import com.lypaka.gces.Listeners.Generations.BetterPixelmonSpawner.GenerationsPokemonSpawnListener;
import com.lypaka.gces.Listeners.Generations.BetterPixelmonSpawner.GenerationsShinySpawnListener;
import com.lypaka.gces.Listeners.Reforged.*;
import com.lypaka.gces.Listeners.Reforged.BetterPixelmonSpawner.ReforgedPokemonSpawnListener;
import com.lypaka.gces.Listeners.Reforged.BetterPixelmonSpawner.ReforgedShinySpawnListener;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;

public class EventRegistry {

    public static void registerGenerationsEvents() {

        MinecraftForge.EVENT_BUS.register(new GenerationsCaptureListener());
        MinecraftForge.EVENT_BUS.register(new GenerationsPokemonLevelingListener());
        MinecraftForge.EVENT_BUS.register(new GenerationsBattleStartListener());
        MinecraftForge.EVENT_BUS.register(new GenerationsMegaListener());
        MinecraftForge.EVENT_BUS.register(new GenerationsDynamaxListener());
        MinecraftForge.EVENT_BUS.register(new GenerationsZMoveListener());
        if (Loader.isModLoaded("betterpixelmonspawner")) {

            MinecraftForge.EVENT_BUS.register(new AlphaSpawnListener());
            MinecraftForge.EVENT_BUS.register(new GenerationsPokemonSpawnListener());
            MinecraftForge.EVENT_BUS.register(new GenerationsShinySpawnListener());

        } else {

            MinecraftForge.EVENT_BUS.register(new GenerationsPixelmonSpawnListener());

        }

    }

    public static void registerReforgedEvents() {

        Pixelmon.EVENT_BUS.register(new ReforgedCaptureListener());
        Pixelmon.EVENT_BUS.register(new ReforgedPokemonLevelingListener());
        Pixelmon.EVENT_BUS.register(new ReforgedBattleStartListener());
        Pixelmon.EVENT_BUS.register(new ReforgedMegaListener());
        Pixelmon.EVENT_BUS.register(new ReforgedDynamaxListener());
        if (Loader.isModLoaded("betterpixelmonspawner")) {

            MinecraftForge.EVENT_BUS.register(new ReforgedPokemonSpawnListener());
            MinecraftForge.EVENT_BUS.register(new ReforgedShinySpawnListener());

        } else {

            Pixelmon.EVENT_BUS.register(new ReforgedPixelmonSpawnListener());

        }

    }

}
