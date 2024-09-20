package com.lypaka.bettermysterygift;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static List<String> mysteryGifts;

    public static int guiRows;
    public static String guiTitle;
    public static Map<String, String> guiBorder;
    public static String codeSourcedGiftsDisplayID;
    public static String codeSourcedGiftsDisplayName;
    public static int codeSourcedGiftsSlot;
    public static String serverSourcedGiftsDisplayID;
    public static String serverSourcedGiftsDisplayName;
    public static int serverSourcedGiftsSlot;

    public static Map<String, List<String>> storageMap;

    public static void load() throws ObjectMappingException {

        mysteryGifts = BetterMysteryGift.configManager.getConfigNode(0, "Mystery-Gifts").getList(TypeToken.of(String.class));

        guiRows = BetterMysteryGift.configManager.getConfigNode(1, "General", "Rows").getInt();
        guiTitle = BetterMysteryGift.configManager.getConfigNode(1, "General", "Title").getString();
        guiBorder = BetterMysteryGift.configManager.getConfigNode(1, "Slots", "Border").getValue(new TypeToken<Map<String, String>>() {});
        codeSourcedGiftsDisplayID = BetterMysteryGift.configManager.getConfigNode(1, "Slots", "Other", "Code-Source-Gifts", "Display-ID").getString();
        codeSourcedGiftsDisplayName = BetterMysteryGift.configManager.getConfigNode(1, "Slots", "Other", "Code-Source-Gifts", "Display-Name").getString();
        codeSourcedGiftsSlot = BetterMysteryGift.configManager.getConfigNode(1, "Slots", "Other", "Code-Source-Gifts", "Slot").getInt();
        serverSourcedGiftsDisplayID = BetterMysteryGift.configManager.getConfigNode(1, "Slots", "Other", "Server-Source-Gifts", "Display-ID").getString();
        serverSourcedGiftsDisplayName = BetterMysteryGift.configManager.getConfigNode(1, "Slots", "Other", "Server-Source-Gifts", "Display-Name").getString();
        serverSourcedGiftsSlot = BetterMysteryGift.configManager.getConfigNode(1, "Slots", "Other", "Server-Source-Gifts", "Slot").getInt();

        storageMap = BetterMysteryGift.configManager.getConfigNode(2, "Claimed").getValue(new TypeToken<Map<String, List<String>>>() {});

    }

}
