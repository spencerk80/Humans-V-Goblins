package com.github.spencerk.models;

import com.github.spencerk.exceptions.ItemCountExceededException;

import java.util.ArrayList;
import java.util.List;

public class InventoryItemRecord {

    public final static byte MAX_ITEM_QUANTITY  = 20;

    private List<Item> items;
    private String  itemName;

    public InventoryItemRecord(Item item) {
        items = new ArrayList<>();
        this.itemName = item.toString();
        items.add(item);
    }

    public String getItemName() {
        return itemName;
    }

    public byte getQuantity() {return (byte) items.size();}

    public void addItem(Item item) throws ItemCountExceededException {
        if(items.size() < MAX_ITEM_QUANTITY) items.add(item);
        else throw new ItemCountExceededException(String.format(
                "Player cannot have more than %d %ss.",
                MAX_ITEM_QUANTITY,
                item.toString()
        ));
    }

    public Item getItem() {
        try {
            return items.remove(0);
        } catch(IndexOutOfBoundsException ioobe) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %02d\n", itemName, items.size());
    }
}
