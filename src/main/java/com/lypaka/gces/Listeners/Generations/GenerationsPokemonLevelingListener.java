package com.lypaka.gces.Listeners.Generations;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Modules.LevelingModule;
import com.lypaka.lypakautils.FancyText;
import com.pixelmongenerations.api.events.ExperienceGainEvent;
import com.pixelmongenerations.api.events.RareCandyEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GenerationsPokemonLevelingListener {

    @SubscribeEvent
    public void onEXPGain (ExperienceGainEvent event) {

        EntityPlayerMP player = event.getPokemon().getPlayerOwner();
        String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
        if (diff.equalsIgnoreCase("none")) return;
        Difficulty difficulty = GCES.difficultyMap.get(diff);
        LevelingModule levelingModule = difficulty.getLevelingModule();
        int tierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Leveling"));
        int maxLevel = levelingModule.getTierMap().get("Tier-" + tierLevel);
        int pokemonLevel = event.getPokemon().getLevel();

        if (pokemonLevel >= maxLevel) {

            event.setExperience(0);
            player.sendMessage(FancyText.getFormattedText(ConfigGetters.levelingTierMessage));

        }

    }

    @SubscribeEvent
    public void onRareCandy (RareCandyEvent event) {

        EntityPlayerMP player = event.getPlayer();
        String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
        if (diff.equalsIgnoreCase("none")) return;
        Difficulty difficulty = GCES.difficultyMap.get(diff);
        LevelingModule levelingModule = difficulty.getLevelingModule();
        int tierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Leveling"));
        int maxLevel = levelingModule.getTierMap().get("Tier-" + tierLevel);
        int pokemonLevel = event.getPokemon().getLvl().getLevel();

        if (pokemonLevel >= maxLevel) {

            event.setCanceled(true);
            player.sendMessage(FancyText.getFormattedText(ConfigGetters.levelingTierMessage));

        }

    }

}
