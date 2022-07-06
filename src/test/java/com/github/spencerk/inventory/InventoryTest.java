package com.github.spencerk.inventory;

import com.github.spencerk.exceptions.ItemCountExceededException;
import com.github.spencerk.models.InventoryItemRecord;
import com.github.spencerk.items.Potion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InventoryTest {

    private Potion      testPotion;
    private Inventory   testInventory = Inventory.getInstance();

    @BeforeEach
    public void setUpData() {
        Field invInstance;

        try {
            invInstance = Inventory.class.getDeclaredField("instance");
            invInstance.setAccessible(true);
            invInstance.set(testInventory, null);
        } catch(IllegalAccessException | NoSuchFieldException e) {
            System.err.println("Error: Unable to reset inventory instance for testing!");
        }

        //Creates a new inventory after the singleton instance has been set null
        testInventory = Inventory.getInstance();
        testPotion = new Potion();

        try {
            testInventory.addItem(testPotion);
        } catch (ItemCountExceededException e) {/*Not able to happen*/}
    }

    @Test
    public void addItem() {
        assertEquals("potion: 01\n", testInventory.toString());
        assertEquals("[potion]", testInventory.getItemList().toString());
    }

    @Test
    public void retrieveItem() {
        Field inventoryStoreField;
        List<InventoryItemRecord> inventoryStore = null;

        try{
            inventoryStoreField = Inventory.class.getDeclaredField("inventory");
            inventoryStoreField.setAccessible(true);
            inventoryStore = (List) inventoryStoreField.get(testInventory);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Error: Could not access underlying list of Inventory for testing!");
        }

        assertEquals(testPotion, testInventory.getItem(testPotion.toString()));
        assertEquals(0, testInventory.getItemList().size());
        //The inventory keeps the record object. The record object says there are 0 potions
        assertEquals(1, inventoryStore.size());
    }

    @Test
    public void tryRetrieveOnceTooMany() {
        testInventory.getItem(testPotion.toString());

        assertEquals(null, testInventory.getItem(testPotion.toString()));
    }

    @Test
    public void exceedItemLimit() {
        //Inventory already has one potion
        try {
            for(byte i = 0; i < InventoryItemRecord.MAX_ITEM_QUANTITY; i++)
                testInventory.addItem(new Potion());
        } catch(ItemCountExceededException icee) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }
}
