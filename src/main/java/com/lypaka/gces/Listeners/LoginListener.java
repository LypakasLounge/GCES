package com.lypaka.gces.Listeners;

import com.lypaka.gces.Config.ConfigGetters;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class LoginListener {

    @SubscribeEvent
    public void onJoin (PlayerEvent.PlayerLoggedInEvent event) {

        if (!ConfigGetters.playerAccountsMap.containsKey(event.getPlayer().getUniqueID().toString())) {

            Map<String, String> map = new HashMap<>();
            if (ConfigGetters.restrictionOptional) {

                map.put("Difficulty", "none");

            } else {

                map.put("Difficulty", ConfigGetters.defaultDifficulty);

            }

            map.put("Catching", "1");
            map.put("Leveling", "1");
            ConfigGetters.playerAccountsMap.put(event.getPlayer().getUniqueID().toString(), map);

        }

    }

}
