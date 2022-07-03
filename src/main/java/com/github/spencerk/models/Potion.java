package com.github.spencerk.models;

import java.util.Objects;
import java.util.Random;

public class Potion extends Item {

    private static Random       random      = new Random();
    public static final float   MIN_HEAL    = 0.1f,
                                MAX_HEAL    = 0.2f;
    private float           healPercent;

    public Potion() {
        this.itemName = "potion";
        healPercent = MIN_HEAL + random.nextFloat() * (MAX_HEAL - MIN_HEAL);
    }

    //Returns a % of health to restore
    public float use() {
        return this.healPercent;
    }

    public float getHealPercent() {
        return this.healPercent;
    }

    @Override
    public boolean equals(Object o) {
        Potion p = (Potion) o;

        if(
                p.getHealPercent() == this.healPercent &&
                p.getItemName().equals(this.itemName)
        ) return true;

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(healPercent);
    }

    @Override
    public String toString() {
        return this.itemName;
    }
}
