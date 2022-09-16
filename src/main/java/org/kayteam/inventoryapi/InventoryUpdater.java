package org.kayteam.inventoryapi;

import org.bukkit.scheduler.BukkitRunnable;

public class InventoryUpdater extends BukkitRunnable {

    private final InventoryManager inventoryManager;
    private final InventoryBuilder inventoryBuilder;


    public InventoryUpdater(InventoryManager inventoryManager, InventoryBuilder inventoryBuilder) {
        this.inventoryManager = inventoryManager;
        this.inventoryBuilder = inventoryBuilder;
    }

    @Override
    public void run() {

        if ( ! inventoryManager.containInventoryBuilder(inventoryBuilder) ) {

            cancel();

            return;

        }

        for ( int slot : inventoryBuilder.getUpdateItems().keySet() ) {

            inventoryBuilder.getInventory().setItem( slot , inventoryBuilder.getItem(slot).getItem() );

        }

    }

}