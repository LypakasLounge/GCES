package com.lypaka.gces.Listeners.Reforged.BetterPixelmonSpawner;

import com.lypaka.betterpixelmonspawner.API.Spawning.Reforged.ReforgedShinySpawnEvent;
import com.lypaka.betterpixelmonspawner.Utils.PokemonUtils.PokemonUtils;
import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.CatchingModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Utils;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ReforgedShinySpawnListener {

    @SubscribeEvent
    public void onShinySpawn (ReforgedShinySpawnEvent event) {

        if (!ConfigGetters.scalePokemonSpawns) return;
        EntityPlayerMP player = event.getPlayer();
        String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
        if (diff.equalsIgnoreCase("none")) return;
        EntityPixelmon pokemon = event.getPokemon();
        Difficulty difficulty = GCES.difficultyMap.get(diff);
        CatchingModule module = difficulty.getCatchingModule();
        int tierNum = Integer.parseInt(ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Catching"));
        int maxLevel = module.getTierMap().get("Tier-" + tierNum);
        String scale = ConfigGetters.pokemonSpawnScale;
        int newLevel = Utils.getNewLevel(maxLevel, scale);
        pokemon.getLvl().setLevel(newLevel);
        pokemon = PokemonUtils.validateReforgedPokemon(pokemon, newLevel);
        pokemon.updateStats();
        event.setPokemon(pokemon);

    }

}
