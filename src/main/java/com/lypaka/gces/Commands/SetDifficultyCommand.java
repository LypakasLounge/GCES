package com.lypaka.gces.Commands;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Map;

public class SetDifficultyCommand {

    public SetDifficultyCommand (CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(
                Commands.literal("gces")
                        .then(
                                Commands.literal("setdiff")
                                        .then(
                                                Commands.argument("player", EntityArgument.player())
                                                        .then(
                                                                Commands.argument("difficulty", StringArgumentType.word())
                                                                        .executes(c -> {

                                                                            if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                if (!PermissionHandler.hasPermission(player, "gces.command.admin")) {

                                                                                    player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                    return 0;

                                                                                }

                                                                            }

                                                                            ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                                            String diff = StringArgumentType.getString(c, "difficulty");
                                                                            String difficulty = null;

                                                                            for (String d : ConfigGetters.difficulties) {

                                                                                if (d.equalsIgnoreCase(diff)) {

                                                                                    difficulty = d;
                                                                                    break;

                                                                                }

                                                                            }

                                                                            if (difficulty == null) {

                                                                                c.getSource().sendErrorMessage(FancyText.getFormattedText("&cInvalid difficulty!"));
                                                                                return 0;

                                                                            }

                                                                            Map<String, String> map = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString());
                                                                            map.put("Difficulty", difficulty);
                                                                            ConfigGetters.playerAccountsMap.put(target.getUniqueID().toString(), map);
                                                                            target.sendMessage(FancyText.getFormattedText("&eYour difficulty has been set to: " + difficulty), target.getUniqueID());
                                                                            c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully set " + target.getName() + "'s difficulty to " + difficulty + "."), true);
                                                                            return 1;

                                                                        })
                                                        )
                                        )
                        )
        );

    }

}
