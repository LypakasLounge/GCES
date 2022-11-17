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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LevelUpCommand extends CommandBase {

    @Override
    public String getName() {

        return "levelup";

    }

    @Override
    public List<String> getAliases() {

        List<String> a = new ArrayList<>();
        a.add("lvlup");
        return a;

    }

    @Override
    public String getUsage (ICommandSender sender) {

        return "/gces lvlup <player> [<module>]";

    }

    @Override
    public void execute (MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (sender instanceof EntityPlayerMP) {

            EntityPlayerMP player = (EntityPlayerMP) sender;
            if (!PermissionHandler.hasPermission(player, "gces.command.admin")) {

                player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"));
                return;

            }

        }

        String playerArg = args[1];
        EntityPlayerMP target = null;

        if (args.length > 2) {

            String module = args[2];
            for (Map.Entry<UUID, EntityPlayerMP> entry : JoinListener.playerMap.entrySet()) {

                if (entry.getValue().getName().equalsIgnoreCase(playerArg)) {

                    target = entry.getValue();
                    break;

                }

            }

            if (target == null) {

                sender.sendMessage(FancyText.getFormattedText("&cInvalid player name!"));
                return;

            }

            String diff = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Difficulty");
            if (diff.equalsIgnoreCase("none")) {

                sender.sendMessage(FancyText.getFormattedText("&eTarget player does not have a difficulty!"));
                return;

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

                    sender.sendMessage(FancyText.getFormattedText("&eLevel cannot be higher than max level!"));
                    return;

                }

                map.put("Catching", String.valueOf(nextLevel));
                ConfigGetters.playerAccountsMap.put(target.getUniqueID().toString(), map);
                target.sendMessage(FancyText.getFormattedText("&aYour tier in catching has increased to " + nextLevel + "!"));
                sender.sendMessage(FancyText.getFormattedText("&eSuccessfully set " + target.getName() + "'s catching tier to " + nextLevel + "."));

            } else if (module.equalsIgnoreCase("leveling") || module.equalsIgnoreCase("level")) {

                LevelingModule levelingModule = difficulty.getLevelingModule();
                Map<String, String> map = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString());
                level = Integer.parseInt(map.get("Leveling"));
                nextLevel = level + 1;
                int maxLevel = levelingModule.getTierMap().size();
                if (nextLevel > maxLevel) {

                    sender.sendMessage(FancyText.getFormattedText("&eLevel cannot be higher than max level!"));
                    return;

                }

                map.put("Leveling", String.valueOf(nextLevel));
                ConfigGetters.playerAccountsMap.put(target.getUniqueID().toString(), map);
                target.sendMessage(FancyText.getFormattedText("&aYour tier in leveling has increased to " + nextLevel + "!"));
                sender.sendMessage(FancyText.getFormattedText("&eSuccessfully set " + target.getName() + "'s leveling tier to " + nextLevel + "."));

            }

        } else {

            for (Map.Entry<UUID, EntityPlayerMP> entry : JoinListener.playerMap.entrySet()) {

                if (entry.getValue().getName().equalsIgnoreCase(playerArg)) {

                    target = entry.getValue();
                    break;

                }

            }

            if (target == null) {

                sender.sendMessage(FancyText.getFormattedText("&cInvalid player name!"));
                return;

            }

            String diff = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Difficulty");
            if (diff.equalsIgnoreCase("none")) {

                sender.sendMessage(FancyText.getFormattedText("&eTarget player does not have a difficulty!"));
                return;

            }

            Difficulty difficulty = GCES.difficultyMap.get(diff);
            int level;
            int nextLevel;
            CatchingModule catchingModule = difficulty.getCatchingModule();
            Map<String, String> map = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString());
            level = Integer.parseInt(map.get("Catching"));
            nextLevel = level + 1;
            int maxLevel = catchingModule.getTierMap().size();
            if (nextLevel > maxLevel) {

                sender.sendMessage(FancyText.getFormattedText("&eLevel cannot be higher than max level!"));
                return;

            }

            map.put("Catching", String.valueOf(nextLevel));
            ConfigGetters.playerAccountsMap.put(target.getUniqueID().toString(), map);
            target.sendMessage(FancyText.getFormattedText("&aYour tier in catching has increased to " + nextLevel + "!"));
            sender.sendMessage(FancyText.getFormattedText("&eSuccessfully set " + target.getName() + "'s catching tier to " + nextLevel + "."));

            LevelingModule levelingModule = difficulty.getLevelingModule();
            map = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString());
            level = Integer.parseInt(map.get("Leveling"));
            nextLevel = level + 1;
            maxLevel = levelingModule.getTierMap().size();
            if (nextLevel > maxLevel) {

                sender.sendMessage(FancyText.getFormattedText("&eLevel cannot be higher than max level!"));
                return;

            }

            map.put("Leveling", String.valueOf(nextLevel));
            ConfigGetters.playerAccountsMap.put(target.getUniqueID().toString(), map);
            target.sendMessage(FancyText.getFormattedText("&aYour tier in leveling has increased to " + nextLevel + "!"));
            sender.sendMessage(FancyText.getFormattedText("&eSuccessfully set " + target.getName() + "'s leveling tier to " + nextLevel + "."));

        }

    }

}
