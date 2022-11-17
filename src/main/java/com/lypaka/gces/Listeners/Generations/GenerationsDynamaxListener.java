package com.lypaka.gces.Listeners.Generations;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.BattleModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.pixelmongenerations.api.events.DynamaxEvent;
import com.pixelmongenerations.common.battle.controller.participants.PlayerParticipant;
import com.pixelmongenerations.common.entity.pixelmon.EntityPixelmon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GenerationsDynamaxListener {

    @SubscribeEvent
    public void onDynamax (DynamaxEvent event) {

        //EntityPlayerMP player = (EntityPlayerMP) event.getBattleParticipant().getEntity();
        EntityPlayerMP player;
        if (event.getBattleParticipant() instanceof PlayerParticipant) {

            player = (EntityPlayerMP) event.getBattleParticipant().getEntity();

        } else if (event.getBattleParticipant().getEntity() instanceof EntityPixelmon) {

            player = (EntityPlayerMP) ((EntityPixelmon) event.getBattleParticipant().getEntity()).getOwner();

        } else {

            return;

        }
        String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
        if (!diff.equalsIgnoreCase("none")) {

            Difficulty difficulty = GCES.difficultyMap.get(diff);
            BattleModule battleModule = difficulty.getBattleModule();
            if (!battleModule.getDynamaxPermission().equalsIgnoreCase("")) {

                if (!PermissionHandler.hasPermission(player, battleModule.getDynamaxPermission())) {

                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.dynamaxMessage));
                    event.setCanceled(true);

                }

            }

        }

    }

}
