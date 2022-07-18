package com.github.spencerk.gui;

import com.github.spencerk.items.Item;
import com.github.spencerk.inventory.Inventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryController {
    @FXML
    protected ListView<Label> inventoryList;
    @FXML
    protected   Button        useBtn;
    private     String      selectedItem;

    public void initialize() {
        inventoryList.getSelectionModel().selectedItemProperty().addListener(e -> {
            javafx.collections.ObservableList<Label> observableList = inventoryList.getSelectionModel()
            /*----------------------Part of the line above----------------------*/ .getSelectedItems();

            if(observableList.size() == 0)
                useBtn.setDisable(true);
            else {
                useBtn.setDisable(false);
                selectedItem = observableList.get(0).getText().split(" ")[1];
            }
        });

        updateInventoryList();
    }

    private void updateInventoryList() {
        List<String> itemList = Inventory.getInstance().getItemList();
        List<Byte>   itemQuantities = new ArrayList<>();

        //Reset the list instead of appending the modified inventory
        inventoryList.getItems().clear();

        //Get quantities of each item for easy of display
        itemList.forEach(item -> itemQuantities.add(Inventory.getInstance().getItemQuantity(item)));

        for(byte i = 0; i < itemList.size(); i++)
            inventoryList.getItems().add(new Label(String.format(
                    "%d %s",
                    itemQuantities.get(i),
                    itemList.get(i))
            ));
    }

    public void useItem(ActionEvent event) {
        Item item = Inventory.getInstance().getItem(selectedItem);
        String msg = item.use();

        //If a message is returned from the item, show it to the player
        if( ! "".equals(msg)) {
            Alert   alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Use Item Effect");
            alert.setHeaderText(item.toString());
            alert.setContentText(msg);
            alert.show();
        }

        updateInventoryList();
    }
}
