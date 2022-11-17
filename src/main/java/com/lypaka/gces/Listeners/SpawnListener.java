package com.lypaka.gces.Listeners;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.CatchingModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Utils;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import com.pixelmonmod.pixelmon.spawning.PlayerTrackingSpawner;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SpawnListener {

    @SubscribeEvent
    public void onSpawn (SpawnEvent event) {

        if (!ConfigGetters.scalePokemonSpawns) return;
        if (event.action.getOrCreateEntity() instanceof PixelmonEntity) {

            if (event.spawner instanceof PlayerTrackingSpawner) {

                ServerPlayerEntity player = ((PlayerTrackingSpawner) event.spawner).getTrackedPlayer();
                if (player != null) {

                    String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
                    if (diff.equalsIgnoreCase("none")) return;
                    PixelmonEntity pokemon = (PixelmonEntity) event.action.getOrCreateEntity();
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
