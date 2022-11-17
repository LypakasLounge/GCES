package com.lypaka.gces.Listeners.Reforged;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.BattleModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.pixelmonmod.pixelmon.api.events.MegaEvolutionEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ReforgedMegaListener {

    @SubscribeEvent
    public void onMega (MegaEvolutionEvent.BattleEvolve event) {

        EntityPlayerMP player = event.pw.getPlayerOwner();
        if (player != null) {

            String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
            if (!diff.equalsIgnoreCase("none")) {

                Difficulty difficulty = GCES.difficultyMap.get(diff);
                BattleModule battleModule = difficulty.getBattleModule();
                if (!battleModule.getMegaPermission().equalsIgnoreCase("")) {

                    if (!PermissionHandler.hasPermission(player, battleModule.getMegaPermission())) {

                        player.sendMessage(FancyText.getFormattedText(ConfigGetters.megaMessage));
                        event.setCanceled(true);

                    }

                }

            }

        }

    }

}
