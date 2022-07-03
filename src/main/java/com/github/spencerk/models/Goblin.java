package com.github.spencerk.models;

import java.util.Random;

public class Goblin {
    private static final Random random              = new Random();
    private short               health,
                                maxHealth;
    private byte                strength,
                                level;

    private Goblin(Player player) {
        byte gobStr         = (byte) (player.getStrength() - 2);

        health              = (byte) (player.getHealth() - 5 - random.nextInt(4));
        maxHealth           = health;
        strength            = gobStr > 0 ? gobStr : 1;
        level               = player.getLevel();
    }

    public short getHealth() {
        return health;
    }

    public byte getStrength() {
        return strength;
    }

    public byte getLevel() {
        return level;
    }

    public void takeDamage(short dmg) {
        this.health -= dmg;
    }

    @Override
    public String toString() {
        return String.format(
                "Goblin wielding a spear and looking around with wild eyes. Current health: %d/%d",
                health,
                maxHealth
        );
    }
}
