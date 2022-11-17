package com.lypaka.gces.Listeners.Reforged;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.BattleModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Modules.LevelingModule;
import com.lypaka.lypakautils.FancyText;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.events.raids.JoinRaidEvent;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class ReforgedBattleStartListener {

    @SubscribeEvent
    public void onRaidBattleStart (JoinRaidEvent event) {

        EntityPlayerMP player = event.getPlayer();
        String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
        if (!diff.equalsIgnoreCase("none")) {

            Difficulty difficulty = GCES.difficultyMap.get(diff);
            BattleModule battleModule = difficulty.getBattleModule();
            if (!battleModule.doCheckBattles()) return;

            PlayerPartyStorage storage = Pixelmon.storageManager.getParty(player);
            int maxLevel = storage.getHighestLevel();
            LevelingModule levelingModule = difficulty.getLevelingModule();
            Map<String, String> map = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString());
            int levelingLevel = Integer.parseInt(map.get("Leveling"));
            int maxLevelingLevel = levelingModule.getTierMap().get("Tier-" + levelingLevel);
            if (maxLevel > maxLevelingLevel) {

                event.setCanceled(true);
                player.sendMessage(FancyText.getFormattedText(ConfigGetters.battleMessage));

            }

        }

    }

    @SubscribeEvent
    public void onBattleStart (BattleStartedEvent event) {

        PlayerParticipant pp1 = null;
        PlayerParticipant pp2 = null;

        BattleControllerBase bcb = event.bc;
        if (bcb.participants.get(0) instanceof PlayerParticipant) {

            if (pp1 == null) {

                pp1 = (PlayerParticipant) bcb.participants.get(0);

            } else {

                pp2 = (PlayerParticipant) bcb.participants.get(0);

            }

        }
        if (bcb.participants.get(1) instanceof PlayerParticipant) {

            if (pp1 == null) {

                pp1 = (PlayerParticipant) bcb.participants.get(0);

            } else {

                pp2 = (PlayerParticipant) bcb.participants.get(0);

            }

        }

        if (pp1 != null) {

            EntityPlayerMP player = pp1.player;
            String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
            if (!diff.equalsIgnoreCase("none")) {

                Difficulty difficulty = GCES.difficultyMap.get(diff);
                BattleModule battleModule = difficulty.getBattleModule();
                if (!battleModule.doCheckBattles()) return;

                PlayerPartyStorage storage = Pixelmon.storageManager.getParty(player);
                int maxLevel = storage.getHighestLevel();
                LevelingModule levelingModule = difficulty.getLevelingModule();
                Map<String, String> map = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString());
                int levelingLevel = Integer.parseInt(map.get("Leveling"));
                int maxLevelingLevel = levelingModule.getTierMap().get("Tier-" + levelingLevel);
                if (maxLevel > maxLevelingLevel) {

                    event.setCanceled(true);
                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.battleMessage));
                    return;

                }

            }

        }
        if (pp2 != null) {

            EntityPlayerMP player = pp2.player;
            String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
            if (!diff.equalsIgnoreCase("none")) {

                Difficulty difficulty = GCES.difficultyMap.get(diff);
                BattleModule battleModule = difficulty.getBattleModule();
                if (!battleModule.doCheckBattles()) return;

                PlayerPartyStorage storage = Pixelmon.storageManager.getParty(player);
                int maxLevel = storage.getHighestLevel();
                LevelingModule levelingModule = difficulty.getLevelingModule();
                Map<String, String> map = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString());
                int levelingLevel = Integer.parseInt(map.get("Leveling"));
                int maxLevelingLevel = levelingModule.getTierMap().get("Tier-" + levelingLevel);
                if (maxLevel > maxLevelingLevel) {

                    event.setCanceled(true);
                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.battleMessage));

                }

            }

        }

    }

}
