package com.lypaka.gces.Listeners.Generations;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.BattleModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.pixelmongenerations.api.events.battles.UseMoveEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GenerationsZMoveListener {

    @SubscribeEvent
    public void onZMove (UseMoveEvent.UseZMoveEvent event) {

        EntityPlayerMP player = event.getPixelmonWrapper().getPlayerOwner();
        if (player != null) {

            String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
            if (!diff.equalsIgnoreCase("none")) {

                Difficulty difficulty = GCES.difficultyMap.get(diff);
                BattleModule battleModule = difficulty.getBattleModule();
                if (!battleModule.getZMovePermission().equalsIgnoreCase("")) {

                    if (!PermissionHandler.hasPermission(player, battleModule.getZMovePermission())) {

                        player.sendMessage(FancyText.getFormattedText(ConfigGetters.zMoveMessage));
                        event.setCanceled(true);

                    }

                }

            }

        }

    }

}
