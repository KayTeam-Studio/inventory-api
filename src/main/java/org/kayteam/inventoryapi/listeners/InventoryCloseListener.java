package org.kayteam.inventoryapi.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.kayteam.inventoryapi.InventoryBuilder;
import org.kayteam.inventoryapi.InventoryManager;

import java.util.HashMap;
import java.util.UUID;

public class InventoryCloseListener implements Listener {

    private final InventoryManager inventoryManager;

    public InventoryCloseListener(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @EventHandler
    public void onInventoryClose( InventoryCloseEvent event ) {

        Player player = (Player) event.getPlayer();

        UUID uuid = player.getUniqueId();

        HashMap<UUID, InventoryBuilder> openedInventories = inventoryManager.getOpenedInventories();

        if ( ! openedInventories.containsKey(uuid) )   return;

        InventoryBuilder inventoryBuilder = openedInventories.get(uuid);

        InventoryView inventoryView = event.getView();

        String title = inventoryView.getTitle();

        if ( ! title.equals(inventoryBuilder.getTitle() ) )   return;

        inventoryBuilder.getCloseAction().execute(player);

        openedInventories.remove(uuid);

    }

}