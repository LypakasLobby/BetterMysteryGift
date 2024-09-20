package com.lypaka.bettermysterygift.MysteryGifts;

import com.lypaka.bettermysterygift.BetterMysteryGift;

import java.util.List;

public class MysteryGift {

    private final int index;
    private final String name;
    private boolean claimed;
    private boolean configured;
    private final int[] beginTime;
    private final int[] endTime;
    private final List<String> commands;
    private final String mode;
    private final String passcode;
    private final boolean requireCasing;
    private final String source;

    private boolean active;

    public MysteryGift (int index, String name, boolean claimed, boolean configured, int[] beginTime, int[] endTime, List<String> commands, String mode, String passcode, boolean requireCasing, String source) {

        this.index = index;
        this.name = name;
        this.claimed = claimed;
        this.configured = configured;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.commands = commands;
        this.mode = mode;
        this.passcode = passcode;
        this.requireCasing = requireCasing;
        this.source = source;
        this.active = false;

    }

    public String getName() {

        return this.name;

    }

    public boolean isClaimed() {

        return this.claimed;

    }

    public void setClaimed (boolean claimed) {

        this.claimed = claimed;
        updateClaimed();

    }

    private void updateClaimed() {

        BetterMysteryGift.giftConfigManager.getConfigNode(this.index, "Claimed").setValue(this.claimed);
        BetterMysteryGift.giftConfigManager.save();

    }

    public boolean isConfigured() {

        return this.configured;

    }

    public void setConfigured (boolean configured) {

        this.configured = configured;
        updatedConfigured();

    }

    private void updatedConfigured() {

        BetterMysteryGift.giftConfigManager.getConfigNode(this.index, "Event-Data", "Configured").setValue(this.configured);
        BetterMysteryGift.giftConfigManager.save();

    }

    public int getStartYear() {

        return this.beginTime[0];

    }

    public int getStartMonth() {

        return this.beginTime[1];

    }

    public int getStartDay() {

        return this.beginTime[2];

    }

    public int getStartHour() {

        return this.beginTime[3];

    }

    public int getStartMinute() {

        return this.beginTime[4];

    }

    public int getStartSecond() {

        return this.beginTime[5];

    }

    public int getEndYear() {

        return this.endTime[0];

    }

    public int getEndMonth() {

        return this.endTime[1];

    }

    public int getEndDay() {

        return this.endTime[2];

    }

    public int getEndHour() {

        return this.endTime[3];

    }

    public int getEndMinute() {

        return this.endTime[4];

    }

    public int getEndSecond() {

        return this.endTime[5];

    }

    public List<String> getCommands() {

        return this.commands;

    }

    public String getMode() {

        return this.mode;

    }

    public String getPasscode() {

        return this.passcode;

    }

    public boolean doesRequireCasing() {

        return this.requireCasing;

    }

    public String getSource() {

        return this.source;

    }

    public boolean isActive() {

        return this.active;

    }

    public void setActive (boolean active) {

        this.active = active;

    }

}
