package com.lypaka.gces.Commands;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.CatchingModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Modules.LevelingModule;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Map;

public class SetLevelCommand {

    public SetLevelCommand (CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(
                Commands.literal("gces")
                        .then(
                                Commands.literal("setlvl")
                                        .then(
                                                Commands.argument("player", EntityArgument.player())
                                                        .then(
                                                                Commands.argument("module", StringArgumentType.word())
                                                                        .then(
                                                                                Commands.argument("level", IntegerArgumentType.integer(1))
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
                                                                                            int level = IntegerArgumentType.getInteger(c, "level");

                                                                                            if (!ConfigGetters.playerAccountsMap.containsKey(target.getUniqueID().toString())) {

                                                                                                c.getSource().sendErrorMessage(FancyText.getFormattedText("&eTarget player does not have an account, which is not a good thing!"));
                                                                                                return 0;

                                                                                            }

                                                                                            String diff = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Difficulty");
                                                                                            if (diff.equalsIgnoreCase("none")) {

                                                                                                c.getSource().sendErrorMessage(FancyText.getFormattedText("&eTarget player does not have a difficulty!"));
                                                                                                return 0;

                                                                                            }

                                                                                            Difficulty difficulty = GCES.difficultyMap.get(diff);
                                                                                            if (module.equalsIgnoreCase("level") || module.equalsIgnoreCase("leveling")) {

                                                                                                LevelingModule levelingModule = difficulty.getLevelingModule();
                                                                                                int maxLevel = levelingModule.getTierMap().size();
                                                                                                if (level > maxLevel) {

                                                                                                    c.getSource().sendErrorMessage(FancyText.getFormattedText("&eLevel cannot be higher than max level!"));
                                                                                                    return 0;

                                                                                                }

                                                                                                Map<String, String> map = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString());
                                                                                                map.put("Leveling", String.valueOf(level));
                                                                                                ConfigGetters.playerAccountsMap.put(target.getUniqueID().toString(), map);
                                                                                                target.sendMessage(FancyText.getFormattedText("&aYour tier in leveling has increased to " + level + "!"), target.getUniqueID());
                                                                                                c.getSource().sendFeedback(FancyText.getFormattedText("&eSuccessfully set " + target.getName() + "'s leveling tier to " + level + "."), true);
                                                                                                return 1;

                                                                                            } else if (module.equalsIgnoreCase("catch") || module.equalsIgnoreCase("catching")) {

                                                                                                CatchingModule catchingModule = difficulty.getCatchingModule();
                                                                                                int maxLevel = catchingModule.getTierMap().size();
                                                                                                if (level > maxLevel) {

                                                                                                    c.getSource().sendErrorMessage(FancyText.getFormattedText("&eLevel cannot be higher than max level!"));
                                                                                                    return 0;

                                                                                                }

                                                                                                Map<String, String> map = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString());
                                                                                                map.put("Catching", String.valueOf(level));
                                                                                                ConfigGetters.playerAccountsMap.put(target.getUniqueID().toString(), map);
                                                                                                target.sendMessage(FancyText.getFormattedText("&aYour tier in catching has increased to " + level + "!"), target.getUniqueID());
                                                                                                c.getSource().sendFeedback(FancyText.getFormattedText("&eSuccessfully set " + target.getName() + "'s catching tier to " + level + "."), true);
                                                                                                return 1;

                                                                                            } else {

                                                                                                c.getSource().sendErrorMessage(FancyText.getFormattedText("&cInvalid module! Use \"catching\" or \"leveling\"!"));
                                                                                                return 0;

                                                                                            }

                                                                                        })
                                                                        )
                                                        )
                                        )
                        )
        );

    }

}
