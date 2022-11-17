package com.lypaka.gces.Commands;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.util.Map;

public class ReloadCommand extends CommandBase {

    @Override
    public String getName() {

        return "reload";

    }

    @Override
    public String getUsage (ICommandSender sender) {

        return "/gces reload";

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

        try {

            Map<String, Map<String, String>> playerAccountsMap = ConfigGetters.playerAccountsMap; // a very stupid fix for a very stupid problem
            GCES.configManager.load();
            ConfigGetters.load();
            ConfigGetters.playerAccountsMap = playerAccountsMap;
            GCES.loadDifficulties();
            sender.sendMessage(FancyText.getFormattedText("&aSuccessfully reloaded GCES configuration!"));

        } catch (ObjectMappingException | IOException e) {

            e.printStackTrace();

        }

    }

}
