package com.lypaka.gces.Listeners;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.HashMap;
import java.util.Map;

public class LoginListener {

    @SubscribeEvent
    public void onJoin (PlayerEvent.PlayerLoggedInEvent event) {

        if (!ConfigGetters.playerAccountsMap.containsKey(event.player.getUniqueID().toString())) {

            Map<String, String> map = new HashMap<>();
            if (ConfigGetters.restrictionOptional) {

                map.put("Difficulty", "none");

            } else {

                map.put("Difficulty", ConfigGetters.defaultDifficulty);

            }

            map.put("Catching", "1");
            map.put("Leveling", "1");
            ConfigGetters.playerAccountsMap.put(event.player.getUniqueID().toString(), map);

        }

    }

    @SubscribeEvent
    public void onLeave (PlayerEvent.PlayerLoggedOutEvent event) {

        GCES.configManager.getConfigNode(1, "Accounts", event.player.getUniqueID().toString()).setValue(ConfigGetters.playerAccountsMap.get(event.player.getUniqueID().toString()));

    }

}
