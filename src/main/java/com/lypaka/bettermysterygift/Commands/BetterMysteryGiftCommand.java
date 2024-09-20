package com.lypaka.bettermysterygift.Commands;

import com.lypaka.bettermysterygift.BetterMysteryGift;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = BetterMysteryGift.MOD_ID)
public class BetterMysteryGiftCommand {

    public static final List<String> ALIASES = Arrays.asList("bettermysterygift", "mysterygift", "mg", "gifts", "mgifts");

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new DeactivateCommand(event.getDispatcher());
        new MenuCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}
