package com.lypaka.bettermysterygift.API;

import com.lypaka.bettermysterygift.MysteryGifts.MysteryGift;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class GiftClaimEvent extends Event {

    private final ServerPlayerEntity player;
    private final MysteryGift gift;

    public GiftClaimEvent (ServerPlayerEntity player, MysteryGift gift) {

        this.player = player;
        this.gift = gift;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public MysteryGift getGift() {

        return this.gift;

    }

}
