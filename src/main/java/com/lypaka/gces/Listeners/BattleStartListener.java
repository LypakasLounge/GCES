package com.lypaka.gces.Listeners;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.BattleModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Modules.LevelingModule;
import com.lypaka.lypakautils.FancyText;
import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Map;

public class BattleStartListener {

    @SubscribeEvent
    public void onBattleStart (BattleStartedEvent event) {

        List<PlayerParticipant> players = event.bc.getPlayers();
        if (players.size() == 0) return;

        for (PlayerParticipant pp : players) {

            ServerPlayerEntity player = pp.player;
            String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
            if (!diff.equalsIgnoreCase("none")) {

                Difficulty difficulty = GCES.difficultyMap.get(diff);
                BattleModule battleModule = difficulty.getBattleModule();
                if (!battleModule.doCheckBattles()) return;

                PlayerPartyStorage storage = StorageProxy.getParty(player);
                int maxLevel = storage.getHighestLevel();
                LevelingModule levelingModule = difficulty.getLevelingModule();
                Map<String, String> map = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString());
                int levelingLevel = Integer.parseInt(map.get("Leveling"));
                int maxLevelingLevel = levelingModule.getTierMap().get("Tier-" + levelingLevel);
                if (maxLevel > maxLevelingLevel) {

                    event.setCanceled(true);
                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.battleMessage), player.getUniqueID());

                }

            }

        }

    }

}
