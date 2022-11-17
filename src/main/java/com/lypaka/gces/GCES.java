package com.lypaka.gces;

import com.google.common.reflect.TypeToken;
import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.Config.ConfigUpdater;
import com.lypaka.gces.Modules.BattleModule;
import com.lypaka.gces.Modules.CatchingModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Modules.LevelingModule;
import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import net.minecraftforge.fml.common.Mod;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("gces")
public class GCES {

    public static final String MOD_ID = "gces";
    public static final String MOD_NAME = "GCES";
    public static final String VERSION = "10.5.0";
    public static Logger logger = LogManager.getLogger(MOD_NAME);
    public static BasicConfigManager configManager;
    public static Map<String, Difficulty> difficultyMap = new HashMap<>();

    public GCES() throws IOException, ObjectMappingException {

        Path dir = ConfigUtils.checkDir(Paths.get("./config/gces"));
        String[] mainFiles = new String[]{"gces.conf", "player-accounts.conf"};
        configManager = new BasicConfigManager(mainFiles, dir, GCES.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        ConfigGetters.load();
        loadDifficulties();
        ConfigUpdater.updateConfig();

    }

    public static void loadDifficulties() throws IOException, ObjectMappingException {

        difficultyMap = new HashMap<>();
        logger.info("Loading difficulties...");
        String[] diffFiles = new String[]{"catching.conf", "leveling.conf", "battles.conf"};
        for (String diff : ConfigGetters.difficulties) {

            Path diffDir = ConfigUtils.checkDir(Paths.get("./config/gces/difficulties/" + diff));
            BasicConfigManager bcm = new BasicConfigManager(diffFiles, diffDir, GCES.class, MOD_NAME, MOD_ID, logger);
            bcm.init();

            String finalStagePermission = bcm.getConfigNode(0, "Settings", "Evolution-Stage", "Final").getString();
            String firstStagePermission = bcm.getConfigNode(0, "Settings", "Evolution-Stage", "First").getString();
            String middleStagePermission = bcm.getConfigNode(0, "Settings", "Evolution-Stage", "Middle").getString();
            String singleStagePermission = bcm.getConfigNode(0, "Settings", "Evolution-Stage", "Single").getString();
            String legendaryPermission = bcm.getConfigNode(0, "Settings", "Legendary-Permission").getString();
            String shinyPermission = bcm.getConfigNode(0, "Settings", "Shiny-Permission").getString();
            Map<String, Integer> catchingTierMap = bcm.getConfigNode(0, "Tiers").getValue(new TypeToken<Map<String, Integer>>() {});
            CatchingModule catchingModule = new CatchingModule(finalStagePermission, firstStagePermission, middleStagePermission, singleStagePermission, legendaryPermission, shinyPermission, catchingTierMap);

            Map<String, Integer> levelingTierMap = bcm.getConfigNode(1, "Tiers").getValue(new TypeToken<Map<String, Integer>>() {});
            LevelingModule levelingModule = new LevelingModule(levelingTierMap);

            boolean checkBattles = bcm.getConfigNode(2, "Check-Battles").getBoolean();
            String dynamaxPermission = bcm.getConfigNode(2, "Permissions", "Dynamax-Permission").getString();
            String megaPermission = bcm.getConfigNode(2, "Permissions", "Mega-Permission").getString();
            BattleModule battleModule = new BattleModule(checkBattles, dynamaxPermission, megaPermission);

            Difficulty difficulty = new Difficulty(diff, bcm, catchingModule, levelingModule, battleModule);
            difficulty.add();

        }

        logger.info("Successfully loaded all difficulties!");

    }

}
