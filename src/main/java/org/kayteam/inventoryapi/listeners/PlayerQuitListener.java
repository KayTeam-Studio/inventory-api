package org.kayteam.inventoryapi.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kayteam.inventoryapi.InventoryBuilder;
import org.kayteam.inventoryapi.InventoryManager;

import java.util.HashMap;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

    private final InventoryManager inventoryManager;

    public PlayerQuitListener(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        UUID uuid = player.getUniqueId();

        HashMap<UUID, InventoryBuilder> openedInventories = inventoryManager.getOpenedInventories();

        openedInventories.remove(uuid);

    }

}