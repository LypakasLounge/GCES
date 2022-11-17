package com.lypaka.gces.Commands;

import com.lypaka.gces.Config.ConfigGetters;
import com.lypaka.gces.GCES;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.util.Map;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(
                Commands.literal("gces")
                        .then(
                                Commands.literal("reload")
                                        .executes(c -> {

                                            if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                if (!PermissionHandler.hasPermission(player, "gces.command.admin")) {

                                                    player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                    return 0;

                                                }

                                            }

                                            try {

                                                Map<String, Map<String, String>> playerAccountsMap = ConfigGetters.playerAccountsMap; // a very stupid fix for a very stupid problem
                                                GCES.configManager.load();
                                                ConfigGetters.load();
                                                ConfigGetters.playerAccountsMap = playerAccountsMap;
                                                GCES.loadDifficulties();
                                                c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully reloaded GCES configuration!"), true);

                                            } catch (ObjectMappingException | IOException e) {

                                                e.printStackTrace();

                                            }
                                            return 1;

                                        })
                        )
        );

    }

}
