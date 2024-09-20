package com.lypaka.bettermysterygift.Commands;

import com.lypaka.bettermysterygift.GUI.GiftMenu;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class MenuCommand {

    public MenuCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterMysteryGiftCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("menu")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    GiftMenu.open(player);

                                                }

                                                return 1;

                                            })
                            )
                            .executes(c -> {

                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                    GiftMenu.open(player);

                                }

                                return 1;

                            })
            );

        }

    }

}
