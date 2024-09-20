package com.lypaka.bettermysterygift.Commands;

import com.lypaka.bettermysterygift.MysteryGifts.GiftHandler;
import com.lypaka.bettermysterygift.MysteryGifts.MysteryGift;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;

public class DeactivateCommand {

    public DeactivateCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterMysteryGiftCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("deactivate")
                                            .then(
                                                    Commands.argument("mysterygift", StringArgumentType.word())
                                                            .suggests(
                                                                    (context, builder) -> ISuggestionProvider.suggest(GiftHandler.activeMysteryGifts.keySet(), builder)
                                                            )
                                                            .executes(c -> {

                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                    if (!PermissionHandler.hasPermission(player, "bettermysterygift.command.admin")) {

                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUUID());
                                                                        return 0;

                                                                    }

                                                                }

                                                                String target = StringArgumentType.getString(c, "mysterygift");
                                                                MysteryGift mysteryGift = GiftHandler.activeMysteryGifts.get(target);
                                                                mysteryGift.setConfigured(false);
                                                                c.getSource().sendSuccess(FancyText.getFormattedText("&aSuccessfully queued Mystery Gift: " + target + " for deactivation on the next iteration!"), true);
                                                                return 1;

                                                            })
                                            )
                            )
            );

        }

    }

}
