package com.lypaka.gces.Listeners;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.CatchingModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CaptureListener {

    @SubscribeEvent
    public void onCapture (CaptureEvent.StartCapture event) {

        ServerPlayerEntity player = event.player;
        String diff = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
        if (diff.equalsIgnoreCase("none")) return;
        Difficulty difficulty = GCES.difficultyMap.get(diff);
        CatchingModule catchingModule = difficulty.getCatchingModule();
        PixelmonEntity pokemon = event.getPokemon();
        ItemStack ball = event.pokeball.getBallType().getBallItem().getDefaultInstance();
        ball.setCount(1);

        if (pokemon.getPokemon().isShiny()) {

            if (!catchingModule.getShinyPermission().equals("")) {

                if (!PermissionHandler.hasPermission(player, catchingModule.getShinyPermission())) {

                    event.setCanceled(true);
                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.missingPermissionMessage), player.getUniqueID());
                    player.addItemStackToInventory(ball);
                    return;

                }

            }

        }

        if (pokemon.getPokemon().isLegendary() || pokemon.getPokemon().isMythical() || pokemon.getPokemon().isUltraBeast()) {

            if (!catchingModule.getLegendaryPermission().equals("")) {

                if (!PermissionHandler.hasPermission(player, catchingModule.getLegendaryPermission())) {

                    event.setCanceled(true);
                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.missingPermissionMessage), player.getUniqueID());
                    player.addItemStackToInventory(ball);
                    return;

                }

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
                player.sendMessage(FancyText.getFormattedText(ConfigGetters.missingPermissionMessage), player.getUniqueID());
                player.addItemStackToInventory(ball);
                return;

            }

        }

        int tierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Catching"));
        int maxLevel = catchingModule.getTierMap().get("Tier-" + tierLevel);
        if (pokemon.getLvl().getPokemonLevel() > maxLevel) {

            event.setCanceled(true);
            player.sendMessage(FancyText.getFormattedText(ConfigGetters.catchingTierMessage), player.getUniqueID());
            player.addItemStackToInventory(ball);

        }

    }

    private static String getEvoStage (PixelmonEntity pokemon) {

        // Pokemon has no pre-evolutions and can evolve, Pokemon is baby-stage
        if (pokemon.getForm().getPreEvolutions().size() == 0 && pokemon.getForm().getEvolutions().size() != 0) {

            return "First";

        }

        // Pokemon has pre-evolutions and can evolve, Pokemon is middle-stage
        if (pokemon.getForm().getPreEvolutions().size() != 0 && pokemon.getForm().getEvolutions().size() != 0) {

            return "Middle";

        }

        // Pokemon has pre-evolutions and can not evolve, Pokemon is final-stage
        if (pokemon.getForm().getPreEvolutions().size() != 0 && pokemon.getForm().getEvolutions().size() == 0) {

            return "Final";

        }

        // Pokemon has no pre-evolutions and can not evolve, Pokemon is single-stage
        if (pokemon.getForm().getPreEvolutions().size() == 0 && pokemon.getForm().getEvolutions().size() == 0) {

            return "Single";

        }

        return "None";

    }

}
