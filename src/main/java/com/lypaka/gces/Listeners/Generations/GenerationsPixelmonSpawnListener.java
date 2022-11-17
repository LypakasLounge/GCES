package com.lypaka.gces.Listeners.Generations;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.CatchingModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Utils;
import com.pixelmongenerations.api.events.spawning.SpawnEvent;
import com.pixelmongenerations.common.entity.pixelmon.EntityPixelmon;
import com.pixelmongenerations.common.spawning.PlayerTrackingSpawner;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GenerationsPixelmonSpawnListener {

    @SubscribeEvent
    public void onSpawn (SpawnEvent event) {

        if (!ConfigGetters.scalePokemonSpawns) return;
        if (event.getSpawnAction().getOrCreateEntity() instanceof EntityPixelmon) {

            if (event.getSpawner() instanceof PlayerTrackingSpawner) {

                EntityPlayerMP player = ((PlayerTrackingSpawner) event.getSpawner()).getTrackedPlayer();
                if (player != null) {

                    String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
                    if (diff.equalsIgnoreCase("none")) return;
                    EntityPixelmon pokemon = (EntityPixelmon) event.getSpawnAction().getOrCreateEntity();
                    Difficulty difficulty = GCES.difficultyMap.get(diff);
                    CatchingModule module = difficulty.getCatchingModule();
                    int tierNum = Integer.parseInt(ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Catching"));
                    int maxLevel = module.getTierMap().get("Tier-" + tierNum);
                    String scale = ConfigGetters.pokemonSpawnScale;
                    pokemon.getLvl().setLevel(Utils.getNewLevel(maxLevel, scale));
                    pokemon.updateStats();

                }

            }

        }

    }

}
