package com.github.spencerk.items;

import com.github.spencerk.Prompt.PromptFactory;
import com.github.spencerk.models.Player;

import java.util.Objects;
import java.util.Random;

public class Potion implements Item {

    private static final Random random      = new Random();
    public static final float   MIN_HEAL    = 0.2f,
                                MAX_HEAL    = 0.4f;
    private final float         healPercent;
    private final String    ITEM_NAME       = "potion";

    public Potion() {
        healPercent = MIN_HEAL + random.nextFloat() * (MAX_HEAL - MIN_HEAL);
    }

    //Returns a % of health to restore
    public void use() {
        Player.getInstance().heal(healPercent);
        System.out.printf(
                "%s is at %d/%d HP\n",
                Player.getInstance().getName(),
                Player.getInstance().getHealth(),
                Player.getInstance().getMaxHealth()
        );
        System.out.println("Press enter to continue");
        PromptFactory.getScanner().nextLine();
    }

    public float getHealPercent() {
        return this.healPercent;
    }

    @Override
    public boolean equals(Object o) {
        Potion p;

        if(o instanceof Potion) p = (Potion) o;
        else return false;

        return p.getHealPercent() == this.healPercent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(healPercent);
    }

    @Override
    public String toString() {
        return ITEM_NAME;
    }

    public String getItemName() {
        return ITEM_NAME;
    }
}
