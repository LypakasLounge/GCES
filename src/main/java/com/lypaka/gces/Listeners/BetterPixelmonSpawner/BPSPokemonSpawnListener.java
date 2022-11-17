package com.lypaka.gces.Listeners.BetterPixelmonSpawner;

import com.lypaka.betterpixelmonspawner.API.PokemonSpawnEvent;
import com.lypaka.betterpixelmonspawner.Utils.PokemonUtils.PokemonUtils;
import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.CatchingModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Utils;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BPSPokemonSpawnListener {

    @SubscribeEvent
    public void onPokemonSpawn (PokemonSpawnEvent event) {

        if (!ConfigGetters.scalePokemonSpawns) return;
        ServerPlayerEntity player = event.getPlayer();
        String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
        if (diff.equalsIgnoreCase("none")) return;
        PixelmonEntity pokemon = event.getPokemon();
        Difficulty difficulty = GCES.difficultyMap.get(diff);
        CatchingModule module = difficulty.getCatchingModule();
        int tierNum = Integer.parseInt(ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Catching"));
        int maxLevel = module.getTierMap().get("Tier-" + tierNum);
        String scale = ConfigGetters.pokemonSpawnScale;
        int newLevel = Utils.getNewLevel(maxLevel, scale);
        pokemon.getLvl().setLevel(newLevel);
        pokemon = PokemonUtils.validatePokemon(pokemon, newLevel);
        pokemon.updateStats();
        event.setPokemon(pokemon);

    }

}
