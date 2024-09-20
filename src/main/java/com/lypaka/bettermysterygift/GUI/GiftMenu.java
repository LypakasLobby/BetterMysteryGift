package com.lypaka.bettermysterygift.GUI;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.lypaka.bettermysterygift.ConfigGetters;
import com.lypaka.bettermysterygift.MysteryGifts.GiftHandler;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import com.pixelmonmod.pixelmon.api.dialogue.DialogueInputScreen;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class GiftMenu {

    public static void open (ServerPlayerEntity player) {

        ChestTemplate chestTemplate = ChestTemplate.builder(ConfigGetters.guiRows).build();
        GooeyPage page = GooeyPage.builder().template(chestTemplate).title(FancyText.getFormattedText(ConfigGetters.guiTitle)).build();

        for (Map.Entry<String, String> entry : ConfigGetters.guiBorder.entrySet()) {

            String id = entry.getKey();
            String[] slots = entry.getValue().split(", ");
            for (String s : slots) {

                page.getTemplate().getSlot(Integer.parseInt(s)).setButton(
                        GooeyButton.builder().display(ItemStackBuilder.buildFromStringID(id).setHoverName(FancyText.getFormattedText(""))).build()
                );

            }

        }

        ItemStack code = ItemStackBuilder.buildFromStringID(ConfigGetters.codeSourcedGiftsDisplayID).setHoverName(FancyText.getFormattedText(ConfigGetters.codeSourcedGiftsDisplayName));
        page.getTemplate().getSlot(ConfigGetters.codeSourcedGiftsSlot).setButton(
                GooeyButton.builder()
                        .display(code)
                        .onClick(() -> {

                            DialogueInputScreen.openDialogueInput(player, FancyText.getFormattedText("&eMystery Gift"), FancyText.getFormattedText("&aEnter code..."));

                        })
                        .build()
        );

        ItemStack server = ItemStackBuilder.buildFromStringID(ConfigGetters.serverSourcedGiftsDisplayID).setHoverName(FancyText.getFormattedText(ConfigGetters.serverSourcedGiftsDisplayName));
        page.getTemplate().getSlot(ConfigGetters.serverSourcedGiftsSlot).setButton(
                GooeyButton.builder()
                        .display(server)
                        .onClick(() -> {

                            GiftHandler.givePlayerServerGifts(player);

                        })
                        .build()
        );

        UIManager.openUIForcefully(player, page);

    }

}
