package com.lypaka.gces.Commands;

import com.lypaka.gces.Config.ConfigGetters;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.server.command.CommandTreeBase;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GCESCommand extends CommandTreeBase {

    public GCESCommand() {

        addSubcommand(new ReloadCommand());
        addSubcommand(new SetLevelCommand());
        addSubcommand(new SetDifficultyCommand());
        addSubcommand(new LevelUpCommand());
        addSubcommand(new CheckCommand());

    }

    @Override
    public String getName() {

        return "gces";

    }

    @Override
    public String getUsage (ICommandSender sender) {

        return "/gces <args>";

    }

    @Override
    public List<String> getTabCompletions (MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {

        List<String> cmds = new ArrayList<>();
        if (args.length <= 1) {

            cmds.add("setlvl");
            cmds.add("reload");
            cmds.add("setdiff");
            cmds.add("lvlup");
            cmds.add("check");

        } else {

            String arg = args[0];
            if (arg.equalsIgnoreCase("setlvl") || arg.equalsIgnoreCase("setlevel") || arg.equalsIgnoreCase("levelup") || arg.equalsIgnoreCase("lvlup")) {

                if (args.length <= 2) {

                    return GCESCommand.getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());

                } else {

                    List<String> modules = new ArrayList<>();
                    modules.add("catching");
                    modules.add("leveling");
                    return GCESCommand.getListOfStringsMatchingLastWord(args, modules);

                }

            } else if (arg.equalsIgnoreCase("setdiff")) {

                if (args.length <= 2) {

                    return GCESCommand.getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());

                } else {

                    return GCESCommand.getListOfStringsMatchingLastWord(args, ConfigGetters.difficulties);

                }

            } else if (arg.equalsIgnoreCase("check")) {

                if (args.length <= 2) {

                    return GCESCommand.getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());

                }

            }

        }

        return cmds;

    }

    @Override
    public void execute (MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length > 0) {

            String arg = args[0];
            if (arg.equalsIgnoreCase("reload")) {

                ReloadCommand reloadCommand = new ReloadCommand();
                reloadCommand.execute(server, sender, args);

            } else if (arg.equalsIgnoreCase("setlvl") || arg.equalsIgnoreCase("setlevel")) {

                SetLevelCommand setLevelCommand = new SetLevelCommand();
                setLevelCommand.execute(server, sender, args);

            } else if (arg.equalsIgnoreCase("setdiff")) {

                SetDifficultyCommand setDifficultyCommand = new SetDifficultyCommand();
                setDifficultyCommand.execute(server, sender, args);

            } else if (arg.equalsIgnoreCase("levelup") || arg.equalsIgnoreCase("lvlup")) {

                LevelUpCommand levelUpCommand = new LevelUpCommand();
                levelUpCommand.execute(server, sender, args);

            } else if (arg.equalsIgnoreCase("check")) {

                CheckCommand checkCommand = new CheckCommand();
                checkCommand.execute(server, sender, args);

            }

        }

    }

}
