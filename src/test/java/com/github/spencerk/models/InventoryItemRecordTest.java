package com.github.spencerk.models;

import com.github.spencerk.exceptions.ItemCountExceededException;
import com.github.spencerk.items.Potion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InventoryItemRecordTest {

    private InventoryItemRecord testRecord;
    private Potion testPotion;

    @BeforeEach
    public void setUpData() {
        testPotion = new Potion();
        testRecord = new InventoryItemRecord(testPotion);
    }

    @Test
    public void createNewRecord() {
        assertEquals("potion: 01\n", testRecord.toString());
        assertEquals(1, testRecord.getQuantity());
        assertEquals("potion", testRecord.getItemName());
    }

    @Test
    public void retrieveItem() {
        assertEquals(testPotion, testRecord.getItem());
        assertEquals(0, testRecord.getQuantity());
    }

    @Test
    public void retrieveItemOnceTooMany() {
        testRecord.getItem();

        assertEquals(null, testRecord.getItem());
    }

    @Test
    public void exceedItemLimit() {
        //When the test starts, the item count is already 1 potion
        try {
            for(byte i = 0; i < InventoryItemRecord.MAX_ITEM_QUANTITY; i++)
                testRecord.addItem(new Potion());

        } catch(ItemCountExceededException icee) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }

}
