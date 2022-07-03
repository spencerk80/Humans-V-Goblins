package com.github.spencerk.inventory;

import com.github.spencerk.exceptions.ItemCountExceededException;
import com.github.spencerk.models.InventoryItemRecord;
import com.github.spencerk.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Inventory {
    private List<InventoryItemRecord> inventory;
    private static Inventory instance;

    private Inventory() {
        inventory = new ArrayList<>();
    }

    public void addItem(Item item) throws ItemCountExceededException {
        AtomicReference<InventoryItemRecord> record = null;

        inventory.forEach(entry -> {
            if(entry.getItemName() == item.toString()) {
                record.set(entry);
                return;
            }
        });

        if(record.get() != null)
            record.get().addItem(item);
        else
            inventory.add(new InventoryItemRecord(item));

    }

    public Item getItem(String itemName) {
        AtomicReference<Item> item = null;

        inventory.forEach(entry -> {
            if(entry.getItemName().equals(itemName)) {
                item.set(entry.getItem());
                return;
            }
        });

        return item.get();
    }

    public List<String> getItemList() {
        ArrayList<String> itemList = new ArrayList<>();

        inventory.forEach(entry -> {
            if(entry.getQuantity() > 0) itemList.add(entry.getItemName());
        });

        return itemList;
    }

    public Inventory getInstance() {
        if(instance == null) instance = new Inventory();
        return instance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");

        sb.append('\n');
        inventory.forEach(entry -> {
            if(entry.getQuantity() > 0) sb.append(entry.toString());
        });
        sb.append('\n');

        return sb.toString();
    }
}
