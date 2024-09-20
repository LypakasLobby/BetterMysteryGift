package com.lypaka.bettermysterygift.MysteryGifts;

import com.lypaka.bettermysterygift.BetterMysteryGift;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.Listeners.JoinListener;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MysteryGiftTimer {

    private static Timer timer;

    public static void startTimer() {

        if (timer != null) {

            timer.cancel();

        }

        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                LocalDateTime now = LocalDateTime.now();

                for (Map.Entry<String, MysteryGift> entry : GiftHandler.allMysteryGifts.entrySet()) {

                    MysteryGift mysteryGift = entry.getValue();
                    int endYear = mysteryGift.getEndYear();
                    int endMonth = mysteryGift.getEndMonth();
                    int endDay = mysteryGift.getEndDay();
                    int endHour = mysteryGift.getEndHour();
                    int endMinute = mysteryGift.getEndMinute();
                    int endSecond = mysteryGift.getEndSecond();
                    int startYear = mysteryGift.getStartYear();
                    int startMonth = mysteryGift.getStartMonth();
                    int startDay = mysteryGift.getStartDay();
                    int startHour = mysteryGift.getStartHour();
                    int startMinute = mysteryGift.getStartMinute();
                    int startSecond = mysteryGift.getStartSecond();

                    LocalDateTime start = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMinute, startSecond);
                    LocalDateTime end = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMinute, endSecond);
                    if (mysteryGift.isConfigured()) {

                        if (!mysteryGift.isActive() && !mysteryGift.isClaimed()) {

                            if (now.isAfter(start) && now.isBefore(end)) {

                                mysteryGift.setActive(true);
                                BetterMysteryGift.logger.info("Activating Mystery Gift: " + entry.getKey());
                                GiftHandler.activeMysteryGifts.put(mysteryGift.getName(), mysteryGift);

                            }

                        } else {

                            if (now.isAfter(end) || mysteryGift.isClaimed()) {

                                mysteryGift.setActive(false);
                                GiftHandler.activeMysteryGifts.entrySet().removeIf(e -> e.getKey().equalsIgnoreCase(entry.getKey()));
                                BetterMysteryGift.logger.info("Deactivating Mystery Gift: " + entry.getKey());

                            }

                        }

                    } else {

                        if (mysteryGift.isActive()) {

                            mysteryGift.setActive(false);
                            GiftHandler.activeMysteryGifts.entrySet().removeIf(e -> e.getKey().equalsIgnoreCase(entry.getKey()));
                            BetterMysteryGift.logger.info("Deactivating Mystery Gift: " + entry.getKey());

                        }

                    }

                }

            }

        }, 0, 1000L);

    }

}
