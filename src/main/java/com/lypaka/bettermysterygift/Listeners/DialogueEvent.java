package com.lypaka.bettermysterygift.Listeners;

import com.lypaka.bettermysterygift.MysteryGifts.GiftHandler;
import com.pixelmonmod.pixelmon.api.events.dialogue.DialogueInputEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DialogueEvent {

    @SubscribeEvent
    public void onDialogueInput (DialogueInputEvent.Submitted event) {

        ServerPlayerEntity player = event.getPlayer();
        String input = event.getInput();

        GiftHandler.givePlayerCodeGiftsIfAny(player, input);

    }

}
