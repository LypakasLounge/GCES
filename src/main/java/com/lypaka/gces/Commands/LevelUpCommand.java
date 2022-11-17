package com.lypaka.gces.Commands;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.CatchingModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Modules.LevelingModule;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Map;

public class LevelUpCommand {

    public LevelUpCommand (CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(
                Commands.literal("gces")
                        .then(
                                Commands.argument("player", EntityArgument.player())
                                        .then(
                                                Commands.argument("module", StringArgumentType.word())
                                                        .executes(c -> {

                                                            if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                if (!PermissionHandler.hasPermission(player, "gces.command.admin")) {

                                                                    player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                    return 0;

                                                                }

                                                            }

                                                            ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                            String module = StringArgumentType.getString(c, "module");
                                                            String diff = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Difficulty");
                                                            if (diff.equalsIgnoreCase("none")) {

                                                                c.getSource().sendErrorMessage(FancyText.getFormattedText("&eTarget player does not have a difficulty!"));
                                                                return 0;

                                                            }

                                                            Difficulty difficulty = GCES.difficultyMap.get(diff);
                                                            int level;
                                                            int nextLevel;
                                                            if (module.equalsIgnoreCase("catching") || module.equalsIgnoreCase("catch")) {

                                                                CatchingModule catchingModule = difficulty.getCatchingModule();
                                                                Map<String, String> map = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString());
                                                                level = Integer.parseInt(map.get("Catching"));
                                                                nextLevel = level + 1;
                                                                int maxLevel = catchingModule.getTierMap().size();
                                                                if (nextLevel > maxLevel) {

                                                                    c.getSource().sendErrorMessage(FancyText.getFormattedText("&eLevel cannot be higher than max level!"));
                                                                    return 0;

                                                                }

                                                                map.put("Catching", String.valueOf(nextLevel));
                                                                ConfigGetters.playerAccountsMap.put(target.getUniqueID().toString(), map);
                                                                target.sendMessage(FancyText.getFormattedText("&aYour tier in catching has increased to " + nextLevel + "!"), target.getUniqueID());
                                                                c.getSource().sendFeedback(FancyText.getFormattedText("&eSuccessfully set " + target.getName() + "'s catching tier to " + nextLevel + "."), true);

                                                            } else if (module.equalsIgnoreCase("leveling") || module.equalsIgnoreCase("level")) {

                                                                LevelingModule levelingModule = difficulty.getLevelingModule();
                                                                Map<String, String> map = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString());
                                                                level = Integer.parseInt(map.get("Leveling"));
                                                                nextLevel = level + 1;
                                                                int maxLevel = levelingModule.getTierMap().size();
                                                                if (nextLevel > maxLevel) {

                                                                    c.getSource().sendErrorMessage(FancyText.getFormattedText("&eLevel cannot be higher than max level!"));
                                                                    return 0;

                                                                }

                                                                map.put("Leveling", String.valueOf(nextLevel));
                                                                ConfigGetters.playerAccountsMap.put(target.getUniqueID().toString(), map);
                                                                target.sendMessage(FancyText.getFormattedText("&aYour tier in leveling has increased to " + nextLevel + "!"), target.getUniqueID());
                                                                c.getSource().sendFeedback(FancyText.getFormattedText("&eSuccessfully set " + target.getName() + "'s leveling tier to " + nextLevel + "."), true);

                                                            } else {

                                                                c.getSource().sendErrorMessage(FancyText.getFormattedText("&cInvalid module!"));
                                                                return 0;

                                                            }

                                                            return 1;

                                                        })
                                        )
                        )
        );

    }

}
