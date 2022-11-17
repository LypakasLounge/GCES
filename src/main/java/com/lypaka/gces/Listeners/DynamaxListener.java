package com.lypaka.gces.Listeners;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.BattleModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.pixelmonmod.pixelmon.api.events.DynamaxEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DynamaxListener {

    @SubscribeEvent
    public void onDynamax (DynamaxEvent.BattleEvolve event) {

        ServerPlayerEntity player = event.pw.getPlayerOwner();
        if (player != null) {

            String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
            if (!diff.equalsIgnoreCase("none")) {

                Difficulty difficulty = GCES.difficultyMap.get(diff);
                BattleModule battleModule = difficulty.getBattleModule();
                if (!battleModule.getDynamaxPermission().equalsIgnoreCase("")) {

                    if (!PermissionHandler.hasPermission(player, battleModule.getDynamaxPermission())) {

                        player.sendMessage(FancyText.getFormattedText(ConfigGetters.dynamaxMessage), player.getUniqueID());
                        event.setCanceled(true);

                    }

                }

            }

        }

    }

}
