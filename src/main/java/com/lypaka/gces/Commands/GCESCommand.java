package com.lypaka.gces.Commands;

import com.lypaka.gces.GCES;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

/**
 * FUCK Brigadier
 */
@Mod.EventBusSubscriber(modid = GCES.MOD_ID)
public class GCESCommand {

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new CheckCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());
        new SetDifficultyCommand(event.getDispatcher());
        new SetLevelCommand(event.getDispatcher());
        new LevelUpCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}
