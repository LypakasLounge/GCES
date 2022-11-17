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

public class SetLevelCommand extends CommandBase {

    @Override
    public String getName() {

        return "setlevel";

    }

    @Override
    public List<String> getAliases() {

        List<String> a = new ArrayList<>();
        a.add("setlvl");
        return a;

    }

    @Override
    public String getUsage (ICommandSender sender) {

        return "/gces setlevel <player> <module> <level>";

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

        if (args.length < 4) {

            sender.sendMessage(FancyText.getFormattedText(getUsage(sender)));
            return;

        }

        String setLevel = args[0];
        String playerName = args[1];
        String module = args[2];
        int level = Integer.parseInt(args[3]);
        EntityPlayerMP target = null;
        for (Map.Entry<UUID, EntityPlayerMP> entry : JoinListener.playerMap.entrySet()) {

            if (entry.getValue().getName().equals(playerName)) {

                target = entry.getValue();
                break;

            }

        }

        if (target == null) {

            sender.sendMessage(FancyText.getFormattedText("&eInvalid player name!"));
            return;

        }

        if (!ConfigGetters.playerAccountsMap.containsKey(target.getUniqueID().toString())) {

            sender.sendMessage(FancyText.getFormattedText("&eTarget player does not have an account, which is not a good thing!"));
            return;

        }

        String diff = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString()).get("Difficulty");
        if (diff.equalsIgnoreCase("none")) {

            sender.sendMessage(FancyText.getFormattedText("&eTarget player does not have a difficulty!"));
            return;

        }

        Difficulty difficulty = GCES.difficultyMap.get(diff);
        if (module.equalsIgnoreCase("catching") || module.equalsIgnoreCase("catch")) {

            CatchingModule catchingModule = difficulty.getCatchingModule();
            int maxLevel = catchingModule.getTierMap().size();
            if (level > maxLevel) {

                sender.sendMessage(FancyText.getFormattedText("&eLevel cannot be higher than max level!"));
                return;

            }

            Map<String, String> map = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString());
            map.put("Catching", String.valueOf(level));
            ConfigGetters.playerAccountsMap.put(target.getUniqueID().toString(), map);
            target.sendMessage(FancyText.getFormattedText("&aYour tier in catching has increased to " + level + "!"));
            sender.sendMessage(FancyText.getFormattedText("&eSuccessfully set " + target.getName() + "'s catching tier to " + level + "."));

        } else if (module.equalsIgnoreCase("leveling") || module.equalsIgnoreCase("level")) {

            LevelingModule levelingModule = difficulty.getLevelingModule();
            int maxLevel = levelingModule.getTierMap().size();
            if (level > maxLevel) {

                sender.sendMessage(FancyText.getFormattedText("&eLevel cannot be higher than max level!"));
                return;

            }

            Map<String, String> map = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString());
            map.put("Leveling", String.valueOf(level));
            ConfigGetters.playerAccountsMap.put(target.getUniqueID().toString(), map);
            target.sendMessage(FancyText.getFormattedText("&aYour tier in leveling has increased to " + level + "!"));
            sender.sendMessage(FancyText.getFormattedText("&eSuccessfully set " + target.getName() + "'s leveling tier to " + level + "."));

        }

    }

}
