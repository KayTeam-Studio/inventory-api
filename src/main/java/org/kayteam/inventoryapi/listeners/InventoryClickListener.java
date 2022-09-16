package org.kayteam.inventoryapi.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.kayteam.inventoryapi.InventoryBuilder;
import org.kayteam.inventoryapi.InventoryManager;
import org.kayteam.inventoryapi.action.SlotAction;

import java.util.HashMap;
import java.util.UUID;

public class InventoryClickListener implements Listener {

    private final InventoryManager inventoryManager;

    public InventoryClickListener(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        UUID uuid = player.getUniqueId();

        HashMap<UUID, InventoryBuilder> openedInventories = inventoryManager.getOpenedInventories();

        if ( ! openedInventories.containsKey(uuid) )   return;

        InventoryBuilder inventoryBuilder = openedInventories.get(uuid);

        InventoryView inventoryView = event.getView();

        String title = inventoryView.getTitle();

        if ( ! title.equals( inventoryBuilder.getTitle() ) )   return;

        event.setCancelled(true);

        int slot = event.getRawSlot();

        switch (event.getClick()) {

            case LEFT: {

                if ( ! inventoryBuilder.containLeftAction(slot) )  return;

                SlotAction slotAction = inventoryBuilder.getLeftAction(slot);

                slotAction.execute(player, slot);

                break;

            }

            case SHIFT_LEFT: {

                if ( ! inventoryBuilder.containLeftShiftAction(slot) )  return;

                SlotAction slotAction = inventoryBuilder.getLeftShiftAction(slot);

                slotAction.execute(player, slot);

                break;

            }

            case MIDDLE: {

                if ( ! inventoryBuilder.containMiddleAction(slot) )  return;

                SlotAction slotAction = inventoryBuilder.getMiddleAction(slot);

                slotAction.execute(player, slot);

                break;

            }

            case RIGHT: {

                if ( ! inventoryBuilder.containRightAction(slot) )  return;

                SlotAction slotAction = inventoryBuilder.getRightAction(slot);

                slotAction.execute(player, slot);

                break;

            }

            case SHIFT_RIGHT: {

                if ( ! inventoryBuilder.containRightShiftAction(slot) )  return;

                SlotAction slotAction = inventoryBuilder.getRightShiftAction(slot);

                slotAction.execute(player, slot);

                break;

            }

        }

    }

}