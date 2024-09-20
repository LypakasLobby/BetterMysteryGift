package com.lypaka.bettermysterygift.Listeners;

import com.lypaka.bettermysterygift.BetterMysteryGift;
import com.lypaka.bettermysterygift.MysteryGifts.GiftHandler;
import com.lypaka.bettermysterygift.MysteryGifts.MysteryGiftTimer;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

@Mod.EventBusSubscriber(modid = BetterMysteryGift.MOD_ID)
public class ServerStartedListener {

    @SubscribeEvent
    public static void onServerStarted (FMLServerStartedEvent event) throws ObjectMappingException {

        Pixelmon.EVENT_BUS.register(new DialogueEvent());
        GiftHandler.load();
        MysteryGiftTimer.startTimer();

    }

}
