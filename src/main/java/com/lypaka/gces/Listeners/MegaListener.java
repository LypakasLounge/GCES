package com.lypaka.gces.Listeners;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.BattleModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.pixelmonmod.pixelmon.api.events.MegaEvolutionEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MegaListener {

    @SubscribeEvent
    public void onMega (MegaEvolutionEvent.Battle event) {

        ServerPlayerEntity player = event.getPlayer();
        String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
        if (!diff.equalsIgnoreCase("none")) {

            Difficulty difficulty = GCES.difficultyMap.get(diff);
            BattleModule battleModule = difficulty.getBattleModule();
            if (!battleModule.getMegaPermission().equalsIgnoreCase("")) {

                if (!PermissionHandler.hasPermission(player, battleModule.getMegaPermission())) {

                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.megaMessage), player.getUniqueID());
                    event.setCanceled(true);

                }

            }

        }

    }

}
