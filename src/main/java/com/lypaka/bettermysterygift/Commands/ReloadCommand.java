package com.lypaka.bettermysterygift.Commands;

import com.lypaka.bettermysterygift.BetterMysteryGift;
import com.lypaka.bettermysterygift.ConfigGetters;
import com.lypaka.bettermysterygift.MysteryGifts.GiftHandler;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterMysteryGiftCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("reload")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "bettermysterygifts.command.admin")) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUUID());
                                                        return 0;

                                                    }

                                                    try {

                                                        Map<String, List<String>> map = ConfigGetters.storageMap;
                                                        BetterMysteryGift.configManager.load();
                                                        ConfigGetters.load();
                                                        ConfigGetters.storageMap = map;
                                                        BetterMysteryGift.giftConfigManager.setFileNames(ConfigGetters.mysteryGifts);
                                                        BetterMysteryGift.giftConfigManager.load();
                                                        GiftHandler.load();
                                                        c.getSource().sendSuccess(FancyText.getFormattedText("&aSuccessfully reloaded BetterMysteryGifts!"), true);

                                                    } catch (ObjectMappingException e) {

                                                        throw new RuntimeException(e);

                                                    }

                                                }

                                                return 0;

                                            })
                            )
            );

        }

    }

}
