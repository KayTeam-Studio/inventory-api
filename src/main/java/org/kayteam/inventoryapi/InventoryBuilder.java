package org.kayteam.inventoryapi;

import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kayteam.inventoryapi.action.*;

import java.util.HashMap;
import java.util.List;

public class InventoryBuilder {

    private Inventory inventory;
    private final String title;
    private final int rows;
    private final int updateInterval;
    private final HashMap<Integer, GetItem> items = new HashMap<>();
    private final HashMap<Integer, Boolean> updateItems = new HashMap<>();
    private final HashMap<Integer, SlotAction> leftActions =  new HashMap<>();
    private final HashMap<Integer, SlotAction> rightActions =  new HashMap<>();
    private final HashMap<Integer, SlotAction> middleActions =  new HashMap<>();
    private final HashMap<Integer, SlotAction> leftShiftActions =  new HashMap<>();
    private final HashMap<Integer, SlotAction> rightShiftActions =  new HashMap<>();
    private CloseAction closeAction;

    public InventoryBuilder() {
        title = "Default Title";
        rows = 3;
        this.updateInterval = 0;

    }
    public InventoryBuilder(String title) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        rows = 3;
        this.updateInterval = 0;
    }
    public InventoryBuilder(String title, int rows) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.rows = rows;
        this.updateInterval = 0;
    }
    public InventoryBuilder(String title, int rows, int updateInterval) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.rows = rows;
        this.updateInterval = updateInterval;
    }

    public Inventory getInventory() {
        return inventory;
    }
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getTitle() {
        return title;
    }
    public int getRows() {
        return rows;
    }
    public int getUpdateInterval() {
        return updateInterval;
    }

    public void fillItem(GetItem getItem) {
        for (int i = 0; i < (rows * 9); i++) {
            if (!items.containsKey(i)) {
                items.put(i, getItem);
            }
        }
    }
    public void fillItem(GetItem getItem, int[] fillRows) {
        for (int row:fillRows) {
            for (int i = ((row * 9) - 9); i < (row * 9); i++) {
                addItem(i, getItem);
            }
        }
    }
    public void fillItem(GetItem getItem, int start, int end) {
        for (int i = start; i <= end; i++) {
            addItem(i, getItem);
        }
    }


    public boolean containItem(int slot) {
        return items.containsKey(slot);
    }
    public void addItem(int slot, GetItem getItem) {
        items.put(slot, getItem);
    }
    public GetItem getItem(int slot) {
        return items.get(slot);
    }
    public void removeItem(int slot) {
        items.remove(slot);
    }


    public HashMap<Integer, Boolean> getUpdateItems() {
        return updateItems;
    }
    public boolean isUpdateItem(int slot) {
        if (updateItems.containsKey(slot)) {
            return updateItems.get(slot);
        }
        return false;
    }
    public void setUpdateItem(int slot, boolean value) {
        updateItems.put(slot, value);
    }
    public void removeUpdateItem(int slot) {
        updateItems.remove(slot);
    }

    public boolean containLeftAction(int slot) {
        return leftActions.containsKey(slot);
    }
    public void addLeftAction(int slot, SlotAction action) {
        leftActions.put(slot, action);
    }
    public SlotAction getLeftAction(int slot) {
        return leftActions.get(slot);
    }
    public void removeLeftAction(int slot) {
        leftActions.remove(slot);
    }

    public boolean containRightAction(int slot) {
        return rightActions.containsKey(slot);
    }
    public void addRightAction(int slot, SlotAction action) {
        rightActions.put(slot, action);
    }
    public SlotAction getRightAction(int slot) {
        return rightActions.get(slot);
    }
    public void removeRightAction(int slot) {
        rightActions.remove(slot);
    }

    public boolean containMiddleAction(int slot) {
        return middleActions.containsKey(slot);
    }
    public void addMiddleAction(int slot, SlotAction action) {
        middleActions.put(slot, action);
    }
    public SlotAction getMiddleAction(int slot) {
        return middleActions.get(slot);
    }
    public void removeMiddleAction(int slot) {
        middleActions.remove(slot);
    }

    public boolean containLeftShiftAction(int slot) {
        return leftShiftActions.containsKey(slot);
    }
    public void addLeftShiftAction(int slot, SlotAction action) {
        leftShiftActions.put(slot, action);
    }
    public SlotAction getLeftShiftAction(int slot) {
        return leftShiftActions.get(slot);
    }
    public void removeLeftShiftAction(int slot) {
        leftShiftActions.remove(slot);
    }

    public boolean containRightShiftAction(int slot) {
        return rightShiftActions.containsKey(slot);
    }
    public void addRightShiftAction(int slot, SlotAction action) {
        rightShiftActions.put(slot, action);
    }
    public SlotAction getRightShiftAction(int slot) {
        return rightShiftActions.get(slot);
    }
    public void removeRightShiftAction(int slot) {
        rightShiftActions.remove(slot);
    }


    public void setCloseAction(CloseAction closeAction) {
        this.closeAction = closeAction;
    }
    public CloseAction getCloseAction() {
        if (closeAction != null) {
            return closeAction;
        } else {
            return player -> {};
        }
    }

    public void onReload() {

        for ( int slot = 0 ; slot < (rows * 9) ; slot++ ) {

            GetItem getItem = getItem(slot);

            ItemStack itemStack = getItem.getItem();

            inventory.setItem(slot, itemStack);

        }

    }

}