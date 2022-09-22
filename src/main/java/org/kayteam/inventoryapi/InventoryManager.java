package org.kayteam.inventoryapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.kayteam.inventoryapi.listeners.InventoryClickListener;
import org.kayteam.inventoryapi.listeners.InventoryCloseListener;
import org.kayteam.inventoryapi.listeners.PlayerQuitListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class InventoryManager {

    private final JavaPlugin javaPlugin;
    private final HashMap<UUID, InventoryBuilder> openedInventories = new HashMap<>();

    public InventoryManager(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }

    public void registerManager() {

        Server server = javaPlugin.getServer();

        PluginManager pluginManager = server.getPluginManager();

        pluginManager.registerEvents(new InventoryClickListener(this), javaPlugin);
        pluginManager.registerEvents(new InventoryCloseListener(this), javaPlugin);
        pluginManager.registerEvents(new PlayerQuitListener(this), javaPlugin);

        ConsoleCommandSender console = server.getConsoleSender();

        List<String> strings = new ArrayList<>();

        strings.add("&f");
        strings.add("&f &7" + javaPlugin.getName() + " &6use &7InventoryAPI developed by KayTeam");
        strings.add("&f");
        strings.add("&f &6Website: &7https://kayteam.org");
        strings.add("&f &6Github: &7https://github.com/KayTeam-Studio/inventory-api");
        strings.add("&f");

        for ( String string : strings ) {
            console.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
        }

    }

    public HashMap<UUID, InventoryBuilder> getOpenedInventories() {
        return openedInventories;
    }
    public boolean containInventoryBuilder(InventoryBuilder inventoryBuilder) {
        return openedInventories.containsValue(inventoryBuilder);
    }
    public boolean containInventoryBuilder(Player player) {
        return openedInventories.containsKey(player.getUniqueId());
    }
    public InventoryBuilder getInventoryBuilder(Player player) {
        return openedInventories.get(player.getUniqueId());
    }


    public void openInventory(Player player, InventoryBuilder inventoryBuilder) {

        UUID uuid = player.getUniqueId();

        try {

            player.closeInventory();

        } catch ( IllegalStateException ignored ) {

        } finally {

            openedInventories.put( uuid , inventoryBuilder );

            Inventory inventory = Bukkit.createInventory( null , inventoryBuilder.getRows() * 9 , inventoryBuilder.getTitle() );

            for ( int slot = 0 ; slot < (inventoryBuilder.getRows() * 9) ; slot++ ) {

                GetItem getItem = inventoryBuilder.getItem(slot);

                if ( getItem != null ) {

                    ItemStack itemStack = getItem.getItem();

                    inventory.setItem(slot, itemStack);

                }

            }

            inventoryBuilder.setInventory(inventory);

            Server server = javaPlugin.getServer();

            BukkitScheduler bukkitScheduler = server.getScheduler();

            bukkitScheduler.runTaskLater(javaPlugin, () -> player.openInventory(inventory) , 1L);

            if ( inventoryBuilder.getUpdateInterval() > 0 ) {

                InventoryUpdater inventoryUpdater = new InventoryUpdater( this , inventoryBuilder );

                long updateInterval = inventoryBuilder.getUpdateInterval() * 20L;

                inventoryUpdater.runTaskTimer(javaPlugin, 0, updateInterval);

            }

        }

    }

}