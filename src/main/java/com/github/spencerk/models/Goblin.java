package com.github.spencerk.models;

import java.util.Random;

public class Goblin {
    private static final Random random              = new Random();
    private short               health;
    private final short         MAX_HEALTH;
    private final byte          STRENGTH,
                                LEVEL;

    public Goblin(Player player) {
        byte gobStr         = (byte) (player.getStrength() - 2);

        health              = (byte) (player.getMaxHealth() - 2 - random.nextInt(4));
        MAX_HEALTH          = health;
        STRENGTH            = gobStr > 0 ? gobStr : 1;
        LEVEL               = player.getLevel();
    }

    public short getHealth() {
        return health;
    }
    public short getMaxHealth() { return MAX_HEALTH; }

    public byte getStrength() {
        return STRENGTH;
    }

    public void takeDamage(short dmg) {
        this.health = this.health - dmg < 0 ? 0 : (short) (this.health - dmg);
    }

    public int getExpWorth() {
        return (int) (2 + Math.floor(LEVEL * 2.2));
    }

    @Override
    public String toString() {
        return String.format(
                "Goblin wielding a spear and looking around with wild eyes. Current health: %d/%d",
                health,
                MAX_HEALTH
        );
    }
}
