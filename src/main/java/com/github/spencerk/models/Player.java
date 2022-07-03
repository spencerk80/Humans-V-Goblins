package com.github.spencerk.models;

import java.util.Random;

public class Player {
    private static Player       player;
    private static final Random random              = new Random();
    private short               health,
                                maxHealth;
    private byte                strength,
                                level;
    private String              name;
    private int                 currentExp,
                                expNeededToLevel;

    private Player() {
        name                = "Tivian";
        health              = 10;
        maxHealth           = 10;
        strength            = 2;
        level               = 1;
        currentExp          = 0;
        expNeededToLevel    = 20;
    }

    public Player getInstnace() {
        if(player == null) player = new Player();
        return player;
    }

    public String getName() {
        return name;
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

    public int getCurrentExp() {
        return currentExp;
    }

    public int getExpNeededToLevel() {
        return expNeededToLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void takeDamage(short dmg) {
        this.health -= dmg;
    }

    public void addExp(int exp) {
        currentExp += exp;

        if(currentExp < expNeededToLevel) {
            currentExp -= expNeededToLevel;
            expNeededToLevel *= .25;
            levelUp();
        }
    }

    private void levelUp() {
        this.maxHealth += 2;
        //Player heals on level up
        this.health = maxHealth;
        this.strength += random.nextInt(2) + 1;
    }

    @Override
    public String toString() {
        return String.format("%s is a level %d warrior. Current health: %d/%d");
    }
}
