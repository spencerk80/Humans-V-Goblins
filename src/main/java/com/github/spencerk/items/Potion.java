package com.github.spencerk.items;

import com.github.spencerk.Prompt.PromptFactory;
import com.github.spencerk.exceptions.ItemCountExceededException;
import com.github.spencerk.inventory.Inventory;
import com.github.spencerk.models.Player;

import java.util.Objects;
import java.util.Random;

public class Potion implements Item {

    private static final Random random      = new Random();
    public static final float   MIN_HEAL    = 0.2f,
                                MAX_HEAL    = 0.4f;
    private final float         healPercent;

    public Potion() {
        healPercent = MIN_HEAL + random.nextFloat() * (MAX_HEAL - MIN_HEAL);
    }

    //Returns a % of health to restore
    public String use() {
        //Don't let player waste a potion if at full health already
        if(Player.getInstance().getHealth() == Player.getInstance().getMaxHealth()) {
            //Put the health potion back
            try { Inventory.getInstance().addItem(this); }
            catch(ItemCountExceededException icee) {/*This item was just removed. Won't throw*/}

            return String.format("%s is at full health already!\n", Player.getInstance().getName());
        }

        Player.getInstance().heal(healPercent);

        return String.format(
                "%s is at %d/%d HP\n",
                Player.getInstance().getName(),
                Player.getInstance().getHealth(),
                Player.getInstance().getMaxHealth()
        );
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
        return"potion";
    }

}
