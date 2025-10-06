package net.tmkspace.musicplayer.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

import static net.tmkspace.musicplayer.MusicPlayer.config;
import static net.tmkspace.musicplayer.MusicPlayer.getInstance;

public class MusicPlayerCommand {

    public static LiteralCommandNode<CommandSourceStack> createCommand(final String commandName) {

        return Commands.literal(commandName)
                .then(Commands.literal("list")
                    .executes(ctx -> {
                        ctx.getSource().getSender().sendRichMessage("list");
                        ctx.getSource().getSender().sendRichMessage(config().getString("test"));
                        return Command.SINGLE_SUCCESS;
                    })
                )
                .then(Commands.literal("download")
                        .executes(ctx -> {
                            ctx.getSource().getSender().sendRichMessage("download");
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .then(Commands.literal("create")
                        .executes(ctx -> {
                            ctx.getSource().getSender().sendRichMessage("create");
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .then(Commands.literal("help")
                        .executes(ctx -> {
                            ctx.getSource().getSender().sendRichMessage("help");
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .then(Commands.literal("reload")
                        .executes(ctx -> {
                            ctx.getSource().getSender().sendRichMessage("reload");
                            getInstance().reloadConfig();
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .build();
    }
}
