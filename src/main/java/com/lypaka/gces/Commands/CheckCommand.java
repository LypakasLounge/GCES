package com.lypaka.gces.Commands;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.gces.Modules.CatchingModule;
import com.lypaka.gces.Modules.Difficulty;
import com.lypaka.gces.Modules.LevelingModule;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.JoinListener;
import com.lypaka.lypakautils.PermissionHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.Map;
import java.util.UUID;

public class CheckCommand extends CommandBase {

    @Override
    public String getName() {

        return "check";

    }

    @Override
    public String getUsage (ICommandSender sender) {

        return "/gces check [<player>]";

    }

    @Override
    public void execute (MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (sender instanceof EntityPlayerMP) {

            EntityPlayerMP player = (EntityPlayerMP) sender;
            if (args.length == 2) {

                String targetString = args[1];
                if (!targetString.equalsIgnoreCase(player.getName())) {

                    if (!PermissionHandler.hasPermission(player, "gces.command.check.others")) {

                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command on others!"));
                        return;

                    }

                    EntityPlayerMP target = null;
                    for (Map.Entry<UUID, EntityPlayerMP> entry : JoinListener.playerMap.entrySet()) {

                        if (entry.getValue().getName().equalsIgnoreCase(targetString)) {

                            target = entry.getValue();
                            break;

                        }

                    }
                    if (target == null) {

                        player.sendMessage(FancyText.getFormattedText("&cInvalid player name!"));
                        return;

                    }

                    String difficulty = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Difficulty");
                    if (difficulty.equalsIgnoreCase("none")) {

                        player.sendMessage(FancyText.getFormattedText("&e" + target.getName() + " does not have a difficulty!"));

                    } else {

                        Difficulty diff = GCES.difficultyMap.get(difficulty);
                        CatchingModule catching = diff.getCatchingModule();
                        LevelingModule leveling = diff.getLevelingModule();
                        int catchTierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Catching"));
                        int maxCatchLevel = catching.getTierMap().get("Tier-" + catchTierLevel);
                        int levelTierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Leveling"));
                        int maxLevelingLevel = leveling.getTierMap().get("Tier-" + levelTierLevel);
                        player.sendMessage(FancyText.getFormattedText("&e" + target.getName() + "'s current Catch level is: &a" + catchTierLevel + " &ewith a max catching level of: &a" + maxCatchLevel + "&e, and current Level level is: &a" + levelTierLevel + " &ewith a max leveling level of: &a" + maxLevelingLevel + "&e."));

                    }

                } else {

                    if (!PermissionHandler.hasPermission(player, "gces.command.check")) {

                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"));
                        return;

                    }
                    String difficulty = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
                    if (difficulty.equalsIgnoreCase("none")) {

                        player.sendMessage(FancyText.getFormattedText("&eYou don't have a difficulty!"));

                    } else {

                        Difficulty diff = GCES.difficultyMap.get(difficulty);
                        CatchingModule catching = diff.getCatchingModule();
                        LevelingModule leveling = diff.getLevelingModule();
                        int catchTierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Catching"));
                        int maxCatchLevel = catching.getTierMap().get("Tier-" + catchTierLevel);
                        int levelTierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Leveling"));
                        int maxLevelingLevel = leveling.getTierMap().get("Tier-" + levelTierLevel);
                        player.sendMessage(FancyText.getFormattedText("&eYour current Catch level is: &a" + catchTierLevel + " &ewith a max catching level of: &a" + maxCatchLevel + "&e, and current Level level is: &a" + levelTierLevel + " &ewith a max leveling level of: &a" + maxLevelingLevel + "&e."));

                    }

                }

            } else {

                if (!PermissionHandler.hasPermission(player, "gces.command.check")) {

                    player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"));
                    return;

                }
                String difficulty = ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Difficulty");
                if (difficulty.equalsIgnoreCase("none")) {

                    player.sendMessage(FancyText.getFormattedText("&eYou don't have a difficulty!"));

                } else {

                    Difficulty diff = GCES.difficultyMap.get(difficulty);
                    CatchingModule catching = diff.getCatchingModule();
                    LevelingModule leveling = diff.getLevelingModule();
                    int catchTierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Catching"));
                    int maxCatchLevel = catching.getTierMap().get("Tier-" + catchTierLevel);
                    int levelTierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(player.getUniqueID().toString()).get("Leveling"));
                    int maxLevelingLevel = leveling.getTierMap().get("Tier-" + levelTierLevel);
                    player.sendMessage(FancyText.getFormattedText("&eYour current Catch level is: &a" + catchTierLevel + " &ewith a max catching level of: &a" + maxCatchLevel + "&e, and current Level level is: &a" + levelTierLevel + " &ewith a max leveling level of: &a" + maxLevelingLevel + "&e."));

                }

            }

        } else {

            if (args.length < 2) {

                sender.sendMessage(FancyText.getFormattedText("&cWhen using from console, you must specify a player name!"));

            } else {

                String targetString = args[1];
                EntityPlayerMP target = null;
                for (Map.Entry<UUID, EntityPlayerMP> entry : JoinListener.playerMap.entrySet()) {

                    if (entry.getValue().getName().equalsIgnoreCase(targetString)) {

                        target = entry.getValue();
                        break;

                    }

                }
                if (target == null) {

                    sender.sendMessage(FancyText.getFormattedText("&cInvalid player name!"));
                    return;

                }

                String difficulty = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Difficulty");
                if (difficulty.equalsIgnoreCase("none")) {

                    sender.sendMessage(FancyText.getFormattedText("&e" + target.getName() + " does not have a difficulty!"));

                } else {

                    Difficulty diff = GCES.difficultyMap.get(difficulty);
                    CatchingModule catching = diff.getCatchingModule();
                    LevelingModule leveling = diff.getLevelingModule();
                    int catchTierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Catching"));
                    int maxCatchLevel = catching.getTierMap().get("Tier-" + catchTierLevel);
                    int levelTierLevel = Integer.parseInt(ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Leveling"));
                    int maxLevelingLevel = leveling.getTierMap().get("Tier-" + levelTierLevel);
                    sender.sendMessage(FancyText.getFormattedText("&e" + target.getName() + "'s current Catch level is: &a" + catchTierLevel + " &ewith a max catching level of: &a" + maxCatchLevel + "&e, and current Level level is: &a" + levelTierLevel + " &ewith a max leveling level of: &a" + maxLevelingLevel + "&e."));

                }

            }

        }

    }

}
