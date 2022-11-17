package com.lypaka.gces.Commands;

import com.lypaka.gces.Config.ConfigGetters;
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

public class SetDifficultyCommand extends CommandBase {

    @Override
    public String getName() {

        return "setdiff";

    }

    @Override
    public String getUsage (ICommandSender sender) {

        return "/gces setdiff <player> <difficulty>";

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

        if (args.length < 3) {

            sender.sendMessage(FancyText.getFormattedText(getUsage(sender)));
            return;

        }

        String setDiff = args[0];
        String playerName = args[1];
        String difficulty = args[2];
        EntityPlayerMP target = null;
        for (Map.Entry<UUID, EntityPlayerMP> entry : JoinListener.playerMap.entrySet()) {

            if (entry.getValue().getName().equalsIgnoreCase(playerName)) {

                target = entry.getValue();
                break;

            }

        }
        if (target == null) {

            sender.sendMessage(FancyText.getFormattedText("&eInvalid player name!"));
            return;

        }

        for (String d : ConfigGetters.difficulties) {

            if (d.equalsIgnoreCase(difficulty)) {

                difficulty = d;
                break;

            }

        }

        Map<String, String> map = ConfigGetters.playerAccountsMap.get(target.getUniqueID().toString());
        map.put("Difficulty", difficulty);
        ConfigGetters.playerAccountsMap.put(target.getUniqueID().toString(), map);
        target.sendMessage(FancyText.getFormattedText("&eYour difficulty has been set to: " + difficulty));
        sender.sendMessage(FancyText.getFormattedText("&aSuccessfully set " + target.getName() + "'s difficulty to " + difficulty + "."));

    }

}
