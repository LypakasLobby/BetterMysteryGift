package com.lypaka.bettermysterygift.MysteryGifts;

import com.google.common.reflect.TypeToken;
import com.lypaka.bettermysterygift.API.GiftClaimEvent;
import com.lypaka.bettermysterygift.BetterMysteryGift;
import com.lypaka.bettermysterygift.Commands.BetterMysteryGiftCommand;
import com.lypaka.bettermysterygift.ConfigGetters;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiftHandler {

    public static Map<String, MysteryGift> serverSourcedGifts;
    public static Map<String, MysteryGift> codeSourcedGifts;
    public static Map<String, MysteryGift> activeMysteryGifts;
    public static Map<String, MysteryGift> allMysteryGifts;

    public static void load() throws ObjectMappingException {

        allMysteryGifts = new HashMap<>();
        serverSourcedGifts = new HashMap<>();
        codeSourcedGifts = new HashMap<>();
        activeMysteryGifts = new HashMap<>();

        for (int i = 0; i < ConfigGetters.mysteryGifts.size(); i++) {

            String name = ConfigGetters.mysteryGifts.get(i);
            boolean claimed = false;
            if (BetterMysteryGift.giftConfigManager.getConfigNode(i, "Claimed").isVirtual()) {

                BetterMysteryGift.giftConfigManager.getConfigNode(i, "Claimed").setValue(false);
                BetterMysteryGift.giftConfigManager.getConfigNode(i, "Claimed").setComment("Marks the gift as claimed and deactivates it. Server handles this, you don't have to. Only used if \"Mode\"=\"FCFS\"");
                BetterMysteryGift.giftConfigManager.save();

            } else {

                claimed = BetterMysteryGift.giftConfigManager.getConfigNode(i, "Claimed").getBoolean();

            }
            boolean configured = BetterMysteryGift.giftConfigManager.getConfigNode(i, "Event-Data", "Configured").getBoolean();
            String[] b = BetterMysteryGift.giftConfigManager.getConfigNode(i, "Event-Data", "Begin-Time").getString().split(", ");
            int[] beginTime = new int[b.length];
            for (int j = 0; j < b.length; j++) {

                beginTime[j] = Integer.parseInt(b[j]);

            }
            String[] e = BetterMysteryGift.giftConfigManager.getConfigNode(i, "Event-Data", "End-Time").getString().split(", ");
            int[] endTime = new int[e.length];
            for (int j = 0; j < e.length; j++) {

                endTime[j] = Integer.parseInt(e[j]);

            }
            List<String> commands = BetterMysteryGift.giftConfigManager.getConfigNode(i, "Executes").getList(TypeToken.of(String.class));
            String mode = BetterMysteryGift.giftConfigManager.getConfigNode(i, "Mode").getString();
            String passcode = BetterMysteryGift.giftConfigManager.getConfigNode(i, "Passcode").getString();
            boolean requireCasing = BetterMysteryGift.giftConfigManager.getConfigNode(i, "Require-Casing").getBoolean();
            String source = BetterMysteryGift.giftConfigManager.getConfigNode(i, "Source").getString();

            MysteryGift gift = new MysteryGift(i, name, claimed, configured, beginTime, endTime, commands, mode, passcode, requireCasing, source);
            allMysteryGifts.put(name, gift);
            if (source.equalsIgnoreCase("server")) {

                serverSourcedGifts.put(name, gift);

            } else {

                codeSourcedGifts.put(name, gift);

            }

        }

    }

    public static void givePlayerCodeGiftsIfAny (ServerPlayerEntity player, String passcode) {

        activeMysteryGifts.entrySet().removeIf(code -> {

            MysteryGift gift = code.getValue();
            if (gift.getSource().equalsIgnoreCase("code")) {

                String required = gift.getPasscode();
                if (gift.doesRequireCasing() && required.equals(passcode) || !gift.doesRequireCasing() && required.equalsIgnoreCase(passcode)) {

                    String name = code.getKey();
                    List<String> claimedGifts = new ArrayList<>();
                    if (ConfigGetters.storageMap.containsKey(player.getUUID().toString())) {

                        claimedGifts = ConfigGetters.storageMap.get(player.getUUID().toString());

                    }
                    if (!claimedGifts.contains(name)) {

                        GiftClaimEvent event = new GiftClaimEvent(player, gift);
                        MinecraftForge.EVENT_BUS.post(event);
                        if (!event.isCanceled()) {

                            claimedGifts.add(name);
                            ConfigGetters.storageMap.put(player.getUUID().toString(), claimedGifts);
                            List<String> commands = gift.getCommands();
                            for (String c : commands) {

                                player.getServer().getCommands().performCommand(player.getServer().createCommandSourceStack(), c.replace("%player%", player.getName().getString()));

                            }

                            if (gift.getMode().equalsIgnoreCase("fcfs")) {

                                gift.setClaimed(true);
                                gift.setActive(false);

                            }

                            return gift.getMode().equalsIgnoreCase("fcfs");

                        }

                    }

                }


            }

            return false;

        });

    }

    public static void givePlayerServerGifts (ServerPlayerEntity player) {

        activeMysteryGifts.entrySet().removeIf(server -> {

            MysteryGift gift = server.getValue();
            if (gift.getSource().equalsIgnoreCase("server")) {

                String name = server.getKey();
                List<String> claimedGifts = new ArrayList<>();
                if (ConfigGetters.storageMap.containsKey(player.getUUID().toString())) {

                    claimedGifts = ConfigGetters.storageMap.get(player.getUUID().toString());

                }
                if (!claimedGifts.contains(name)) {

                    GiftClaimEvent event = new GiftClaimEvent(player, gift);
                    MinecraftForge.EVENT_BUS.post(event);
                    if (!event.isCanceled()) {

                        claimedGifts.add(name);
                        ConfigGetters.storageMap.put(player.getUUID().toString(), claimedGifts);
                        List<String> commands = gift.getCommands();
                        for (String c : commands) {

                            player.getServer().getCommands().performCommand(player.getServer().createCommandSourceStack(), c.replace("%player%", player.getName().getString()));

                        }

                        if (gift.getMode().equalsIgnoreCase("fcfs")) {

                            gift.setClaimed(true);
                            gift.setActive(false);

                        }

                        return gift.getMode().equalsIgnoreCase("fcfs");

                    }

                }

            }

            return false;

        });

    }

}
