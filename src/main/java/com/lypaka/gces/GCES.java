package com.lypaka.gces;

import com.google.common.reflect.TypeToken;
import com.lypaka.gces.Commands.GCESCommand;
import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.Config.ConfigUpdater;
import com.lypaka.gces.Listeners.EventRegistry;
import com.lypaka.gces.Listeners.LoginListener;
import com.lypaka.gces.Modules.*;
import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import com.lypaka.lypakautils.PixelmonHandlers.PixelmonVersionDetector;
import com.pixelmongenerations.core.config.PixelmonConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Mod(
        modid = GCES.MOD_ID,
        name = GCES.MOD_NAME,
        version = GCES.VERSION,
        acceptableRemoteVersions = "*",
        dependencies = "required-after:lypakautils@[0.0.5,);required-after:pixelmon"
)
public class GCES {

    public static final String MOD_ID = "gces";
    public static final String MOD_NAME = "GCES";
    public static final String VERSION = "10.1.0";
    public static Logger logger = LogManager.getLogger(MOD_NAME);
    public static BasicConfigManager configManager;
    public static Map<String, Difficulty> difficultyMap = new HashMap<>();
    public static int maxPokemonLevel;

    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent event) throws IOException, ObjectMappingException {

        Path dir = ConfigUtils.checkDir(Paths.get("./config/gces"));
        String[] mainFiles = new String[]{"gces.conf", "player-accounts.conf"};
        configManager = new BasicConfigManager(mainFiles, dir, GCES.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        ConfigGetters.load();
        loadDifficulties();
        ConfigUpdater.updateConfig();

    }

    @Mod.EventHandler
    public void onServerStarting (FMLServerStartingEvent event) {

        event.registerServerCommand(new GCESCommand());
        if (PixelmonVersionDetector.VERSION.contains("Generations")) {

            maxPokemonLevel = PixelmonConfig.maxLevel;

        } else {

            maxPokemonLevel = com.pixelmonmod.pixelmon.config.PixelmonConfig.maxLevel;

        }

        MinecraftForge.EVENT_BUS.register(new LoginListener());
        if (PixelmonVersionDetector.VERSION.contains("Generations")) {

            EventRegistry.registerGenerationsEvents();

        } else {

            EventRegistry.registerReforgedEvents();

        }

    }

    @Mod.EventHandler
    public void onServerStarted (FMLServerStartedEvent event) {

        SaveTask.startAutoSave();

    }

    @Mod.EventHandler
    public void onShuttingDown (FMLServerStoppingEvent event) {

        configManager.getConfigNode(1, "Accounts").setValue(ConfigGetters.playerAccountsMap);
        configManager.save();

    }

    public static void loadDifficulties() throws IOException, ObjectMappingException {

        difficultyMap = new HashMap<>();
        logger.info("Loading difficulties...");
        String[] diffFiles = new String[]{"catching.conf", "leveling.conf", "battles.conf"};
        for (String diff : ConfigGetters.difficulties) {

            Path diffDir = ConfigUtils.checkDir(Paths.get("./config/gces/difficulties/" + diff));
            BasicConfigManager bcm = new BasicConfigManager(diffFiles, diffDir, GCES.class, MOD_NAME, MOD_ID, logger);
            bcm.init();

            String alphaPermission = bcm.getConfigNode(0, "Settings", "Alpha-Permission").getString();
            String finalStagePermission = bcm.getConfigNode(0, "Settings", "Evolution-Stage", "Final").getString();
            String firstStagePermission = bcm.getConfigNode(0, "Settings", "Evolution-Stage", "First").getString();
            String middleStagePermission = bcm.getConfigNode(0, "Settings", "Evolution-Stage", "Middle").getString();
            String singleStagePermission = bcm.getConfigNode(0, "Settings", "Evolution-Stage", "Single").getString();
            String legendaryPermission = bcm.getConfigNode(0, "Settings", "Legendary-Permission").getString();
            String shinyPermission = bcm.getConfigNode(0, "Settings", "Shiny-Permission").getString();
            Map<String, Integer> catchingTierMap = bcm.getConfigNode(0, "Tiers").getValue(new TypeToken<Map<String, Integer>>() {});
            CatchingModule catchingModule = new CatchingModule(alphaPermission, finalStagePermission, firstStagePermission, middleStagePermission, singleStagePermission, legendaryPermission, shinyPermission, catchingTierMap);

            Map<String, Integer> levelingTierMap = bcm.getConfigNode(1, "Tiers").getValue(new TypeToken<Map<String, Integer>>() {});
            LevelingModule levelingModule = new LevelingModule(levelingTierMap);

            boolean checkBattles = bcm.getConfigNode(2, "Check-Battles").getBoolean();
            String dynamaxPermission = bcm.getConfigNode(2, "Permissions", "Dynamax-Permission").getString();
            String megaPermission = bcm.getConfigNode(2, "Permissions", "Mega-Permission").getString();
            String zMovePermission = bcm.getConfigNode(2, "Permissions", "Z-Move-Permission").getString();
            BattleModule battleModule = new BattleModule(checkBattles, dynamaxPermission, megaPermission, zMovePermission);

            Difficulty difficulty = new Difficulty(diff, bcm, catchingModule, levelingModule, battleModule);
            difficulty.add();

        }

        logger.info("Successfully loaded all difficulties!");

    }

}
