package com.lypaka.gces.Config;

import com.google.common.reflect.TypeToken;
import com.lypaka.gces.GCES;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static List<String> difficulties;
    public static String defaultDifficulty;
    public static boolean restrictionOptional;
    public static String pokemonSpawnScale;
    public static boolean scalePokemonSpawns;
    public static String battleMessage;
    public static String catchingTierMessage;
    public static String dynamaxMessage;
    public static String levelingTierMessage;
    public static String megaMessage;
    public static String missingPermissionMessage;

    public static boolean doAutoSaves;
    public static int autoSaveInterval;

    public static Map<String, Map<String, String>> playerAccountsMap;

    public static void load() throws ObjectMappingException {

        difficulties = GCES.configManager.getConfigNode(0, "General-Settings", "Difficulties").getList(TypeToken.of(String.class));
        defaultDifficulty = GCES.configManager.getConfigNode(0, "General-Settings", "Restrictions", "Default-Difficulty").getString();
        restrictionOptional = GCES.configManager.getConfigNode(0, "General-Settings", "Restrictions", "Optional").getBoolean();
        pokemonSpawnScale = GCES.configManager.getConfigNode(0, "General-Settings", "Spawn-Settings", "Pokemon-Scale").getString();
        scalePokemonSpawns = GCES.configManager.getConfigNode(0, "General-Settings", "Spawn-Settings", "Scale-Wild-Pokemon").getBoolean();
        if (GCES.configManager.getConfigNode(0, "Messages", "Battle-Error").isVirtual()) {

            GCES.configManager.getConfigNode(0, "Messages", "Battle-Error").setValue("&cOne or more of your Pokemon exceeds your leveling cap! Please deposit it!");
            GCES.configManager.save();

        }
        battleMessage = GCES.configManager.getConfigNode(0, "Messages", "Battle-Error").getString();
        catchingTierMessage = GCES.configManager.getConfigNode(0, "Messages", "Catching-Tier-Error").getString();
        if (GCES.configManager.getConfigNode(0, "Messages", "Dynamax-Error").isVirtual()) {

            GCES.configManager.getConfigNode(0, "Messages", "Dynamax-Error").setValue("&cYou don't have permission to Dynamax!");
            GCES.configManager.save();

        }
        dynamaxMessage = GCES.configManager.getConfigNode(0, "Messages", "Dynamax-Error").getString();
        levelingTierMessage = GCES.configManager.getConfigNode(0, "Messages", "Leveling-Tier-Error").getString();
        if (GCES.configManager.getConfigNode(0, "Messages", "Mega-Error").isVirtual()) {

            GCES.configManager.getConfigNode(0, "Messages", "Mega-Error").setValue("&cYou don't have permission to Mega Evolve!");
            GCES.configManager.save();

        }
        megaMessage = GCES.configManager.getConfigNode(0, "Messages", "Mega-Error").getString();
        missingPermissionMessage = GCES.configManager.getConfigNode(0, "Messages", "Missing-Permission").getString();
        if (GCES.configManager.getConfigNode(0, "Messages", "Z-Move-Error").isVirtual()) {

            GCES.configManager.getConfigNode(0, "Messages", "Z-Move-Error").setValue("&cYou don't have permission to use Z-Moves!");
            GCES.configManager.save();

        }

        playerAccountsMap = GCES.configManager.getConfigNode(1, "Accounts").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

        doAutoSaves = GCES.configManager.getConfigNode(0, "General-Settings", "Task", "Enable-Auto-Saves").getBoolean();
        autoSaveInterval = GCES.configManager.getConfigNode(0, "General-Settings", "Task", "Interval").getInt();

    }

}
