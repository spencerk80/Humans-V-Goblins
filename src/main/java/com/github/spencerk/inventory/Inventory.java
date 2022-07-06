package com.github.spencerk.inventory;

import com.github.spencerk.exceptions.ItemCountExceededException;
import com.github.spencerk.items.Potion;
import com.github.spencerk.models.InventoryItemRecord;
import com.github.spencerk.items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Inventory {
    private final List<InventoryItemRecord> inventory;
    private static Inventory instance;

    private Inventory() {
        inventory = new ArrayList<>();

        for(byte i = 0; i < 3; i++) try {
            addItem(new Potion());
        } catch(ItemCountExceededException icee) {/*Not gonna happen with just 3 potions*/}
    }

    public void addItem(Item item) throws ItemCountExceededException {
        AtomicReference<InventoryItemRecord> record = new AtomicReference<>();

        inventory.forEach(entry -> {
            if(entry.getItemName().equals(item.toString())) {
                record.set(entry);
            }
        });

        if(record.get() != null)
            record.get().addItem(item);
        else
            inventory.add(new InventoryItemRecord(item));

    }

    public Item getItem(String itemName) {
        AtomicReference<Item> item = new AtomicReference<>();

        inventory.forEach(entry -> {
            if(entry.getItemName().equals(itemName)) {
                item.set(entry.getItem());
            }
        });

        return item.get();
    }

    public byte getItemQuantity(String itemName) {
        AtomicReference<Byte> quantity = new AtomicReference<>((byte) 0);

        inventory.forEach(entry -> {
            if(entry.getItemName().equals(itemName)) quantity.set(entry.getQuantity());
        });

        return quantity.get();
    }

    public List<String> getItemList() {
        ArrayList<String> itemList = new ArrayList<>();

        inventory.forEach(entry -> {
            if(entry.getQuantity() > 0) itemList.add(entry.getItemName());
        });

        return itemList;
    }

    public void reset() {
        instance = new Inventory();
    }

    public static Inventory getInstance() {
        if(instance == null) instance = new Inventory();
        return instance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        inventory.forEach(entry -> {
            if(entry.getQuantity() > 0) sb.append(entry);
        });

        return sb.toString();
    }
}
