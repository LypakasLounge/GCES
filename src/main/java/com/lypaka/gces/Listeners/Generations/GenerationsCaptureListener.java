package com.lypaka.gces.Listeners.Generations;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.CatchingModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.pixelmongenerations.api.events.CaptureEvent;
import com.pixelmongenerations.common.entity.pixelmon.EntityPixelmon;
import com.pixelmongenerations.core.enums.EnumSpecies;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GenerationsCaptureListener {

    @SubscribeEvent
    public void onCapture (CaptureEvent.StartCaptureEvent event) {

        EntityPlayerMP player = event.getPlayer();
        String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
        if (diff.equalsIgnoreCase("none")) return;
        Difficulty difficulty = GCES.difficultyMap.get(diff);
        CatchingModule catchingModule = difficulty.getCatchingModule();
        EntityPixelmon pokemon = event.getPokemon();
        ItemStack ball = new ItemStack(event.getPokeBall().getType().getItem());
        ball.setCount(1);

        if (pokemon.isAlpha()) {

            if (!catchingModule.getAlphaPermission().equals("")) {

                if (!PermissionHandler.hasPermission(player, catchingModule.getAlphaPermission())) {

                    event.setCanceled(true);
                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.missingPermissionMessage));
                    player.addItemStackToInventory(ball);

                }
                return;

            }

        }

        if (pokemon.isShiny()) {

            if (!catchingModule.getShinyPermission().equals("")) {

                if (!PermissionHandler.hasPermission(player, catchingModule.getShinyPermission())) {

                    event.setCanceled(true);
                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.missingPermissionMessage));
                    player.addItemStackToInventory(ball);

                }
                return;

            }

        }

        if (EnumSpecies.legendaries.contains(pokemon.getPokemonName()) || EnumSpecies.ultrabeasts.contains(pokemon.getPokemonName())) {

            if (!catchingModule.getLegendaryPermission().equals("")) {

                if (!PermissionHandler.hasPermission(player, catchingModule.getLegendaryPermission())) {

                    event.setCanceled(true);
                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.missingPermissionMessage));
                    player.addItemStackToInventory(ball);

                }
                return;

            }

        }

        String evoStage = getEvoStage(pokemon);
        String perm;
        switch (evoStage) {

            case "Final":
                perm = catchingModule.getFinalStagePermission();
                break;

            case "First":
                perm = catchingModule.getFirstStagePermission();
                break;

            case "Middle":
                perm = catchingModule.getMiddleStagePermission();
                break;

            default:
                perm = catchingModule.getSingleStagePermission();
                break;

        }
        if (!perm.equals("")) {

            if (!PermissionHandler.hasPermission(player, perm)) {

                event.setCanceled(true);
                player.sendMessage(FancyText.getFormattedText(ConfigGetters.missingPermissionMessage));
                player.addItemStackToInventory(ball);
                return;

            }

        }

        int tierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Catching"));
        int maxLevel = catchingModule.getTierMap().get("Tier-" + tierLevel);
        if (pokemon.getLvl().getLevel() > maxLevel) {

            event.setCanceled(true);
            player.sendMessage(FancyText.getFormattedText(ConfigGetters.catchingTierMessage));
            player.addItemStackToInventory(ball);

        }

    }

    private static String getEvoStage (EntityPixelmon pokemon) {

        // Pokemon has no pre-evolutions and can evolve, Pokemon is baby-stage
        if (pokemon.baseStats.preEvolutions.length == 0 && pokemon.baseStats.evolutions.length != 0) {

            return "First";

        }

        // Pokemon has pre-evolutions and can evolve, Pokemon is middle-stage
        if (pokemon.baseStats.preEvolutions.length != 0 && pokemon.baseStats.evolutions.length != 0) {

            return "Middle";

        }

        // Pokemon has pre-evolutions and can not evolve, Pokemon is final-stage
        if (pokemon.baseStats.preEvolutions.length != 0 && pokemon.baseStats.evolutions.length == 0) {

            return "Final";

        }

        // Pokemon has no pre-evolutions and can not evolve, Pokemon is single-stage
        if (pokemon.baseStats.preEvolutions.length == 0 && pokemon.baseStats.evolutions.length == 0) {

            return "Single";

        }

        return "None";

    }

}
