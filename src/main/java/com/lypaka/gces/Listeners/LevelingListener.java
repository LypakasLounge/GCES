package com.lypaka.gces.Listeners;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Modules.LevelingModule;
import com.lypaka.lypakautils.FancyText;
import com.pixelmonmod.pixelmon.api.events.ExperienceGainEvent;
import com.pixelmonmod.pixelmon.api.events.RareCandyEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LevelingListener {

    @SubscribeEvent
    public void onEXP (ExperienceGainEvent event) {

        ServerPlayerEntity player = event.pokemon.getPlayerOwner();
        String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
        if (diff.equalsIgnoreCase("none")) return;
        Difficulty difficulty = GCES.difficultyMap.get(diff);
        LevelingModule levelingModule = difficulty.getLevelingModule();
        int tierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Leveling"));
        int maxLevel = levelingModule.getTierMap().get("Tier-" + tierLevel);
        int pokemonLevel = event.pokemon.getPokemonLevel();

        if (pokemonLevel >= maxLevel) {

            event.setExperience(0);
            player.sendMessage(FancyText.getFormattedText(ConfigGetters.levelingTierMessage), player.getUniqueID());

        }

    }

    @SubscribeEvent
    public void onRareCandy (RareCandyEvent event) {

        ServerPlayerEntity player = event.getPlayer();
        String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
        if (diff.equalsIgnoreCase("none")) return;
        Difficulty difficulty = GCES.difficultyMap.get(diff);
        LevelingModule levelingModule = difficulty.getLevelingModule();
        int tierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Leveling"));
        int maxLevel = levelingModule.getTierMap().get("Tier-" + tierLevel);
        int pokemonLevel = event.getPixelmon().getPokemon().getPokemonLevel();

        if (pokemonLevel >= maxLevel) {

            event.setCanceled(true);
            player.sendMessage(FancyText.getFormattedText(ConfigGetters.levelingTierMessage), player.getUniqueID());

        }

    }

}
