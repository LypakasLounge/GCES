package com.lypaka.gces.Commands;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.CatchingModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Modules.LevelingModule;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

public class CheckCommand {

    public CheckCommand (CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(
                Commands.literal("gces")
                        .then(
                                Commands.literal("check")
                                        .then(
                                                Commands.argument("player", EntityArgument.players())
                                                        .executes(c -> {

                                                            ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                            String message;
                                                            String name;
                                                            if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                if (!PermissionHandler.hasPermission(player, "gces.command.check")) {

                                                                    player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                    return 0;

                                                                }

                                                                if (!target.getName().getString().equalsIgnoreCase(player.getName().getString())) {

                                                                    if (!PermissionHandler.hasPermission(player, "gces.command.check.others")) {

                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                        return 0;

                                                                    } else {

                                                                        message = target.getName().getString() + " does not have a difficulty!";
                                                                        name = target.getName().getString() + "'s";

                                                                    }

                                                                } else {

                                                                    message = "You do not have a difficulty!";
                                                                    name = "Your";

                                                                }

                                                            } else {

                                                                message = target.getName().getString() + " does not have a difficulty!";
                                                                name = target.getName().getString() + "'s";

                                                            }

                                                            String difficulty = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Difficulty");
                                                            if (difficulty.equalsIgnoreCase("none")) {

                                                                c.getSource().sendFeedback(FancyText.getFormattedText("&e" + message), true);

                                                            } else {

                                                                Difficulty diff = GCES.difficultyMap.get(difficulty);
                                                                CatchingModule catching = diff.getCatchingModule();
                                                                LevelingModule leveling = diff.getLevelingModule();
                                                                int catchTierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Catching"));
                                                                int maxCatchLevel = catching.getTierMap().get("Tier-" + catchTierLevel);
                                                                int levelTierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Leveling"));
                                                                int maxLevelingLevel = leveling.getTierMap().get("Tier-" + levelTierLevel);
                                                                c.getSource().sendFeedback(FancyText.getFormattedText("&e" + name + " current Catch level is: &a" + catchTierLevel + " &ewith a max catching level of: &a" + maxCatchLevel + "&e, and current Level level is: &a" + levelTierLevel + " &ewith a max leveling level of: &a" + maxLevelingLevel + "&e."), true);

                                                            }

                                                            return 1;

                                                        })
                                        )
                        )
        );

    }

}
