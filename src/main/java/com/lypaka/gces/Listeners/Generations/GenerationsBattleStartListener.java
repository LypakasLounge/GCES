package com.lypaka.gces.Listeners.Generations;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.BattleModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Modules.LevelingModule;
import com.lypaka.lypakautils.FancyText;
import com.pixelmongenerations.api.events.BattleStartEvent;
import com.pixelmongenerations.common.battle.controller.BattleControllerBase;
import com.pixelmongenerations.common.battle.controller.participants.PlayerParticipant;
import com.pixelmongenerations.core.storage.PixelmonStorage;
import com.pixelmongenerations.core.storage.PlayerStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class GenerationsBattleStartListener {

    @SubscribeEvent
    public void onBattleStart (BattleStartEvent event) {

        PlayerParticipant pp1 = null;
        PlayerParticipant pp2 = null;

        BattleControllerBase bcb = event.getBattleController();
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

                PlayerStorage storage = PixelmonStorage.pokeBallManager.getPlayerStorage(player).get();
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

                PlayerStorage storage = PixelmonStorage.pokeBallManager.getPlayerStorage(player).get();
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
