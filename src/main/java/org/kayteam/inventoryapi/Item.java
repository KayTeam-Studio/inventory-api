package org.kayteam.inventoryapi;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private final ItemStack itemStack;
    private final boolean update;
    private final String displayName;
    private final List<String> lore;

    public Item(ItemStack itemStack, boolean update, String displayName, List<String> lore) {
        this.itemStack = itemStack;
        this.update = update;
        this.displayName = displayName;
        this.lore = lore;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isUpdate() {
        return update;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public void updateItem(Player player) {

        applyLore(player);

        applyDisplayName(player);

    }

    private void applyLore(Player player) {
        try {

            List<String> newLore = new ArrayList<>();

            for (String line:lore) {

                line = applyPlaceholders(player, line);

                line = ChatColor.translateAlternateColorCodes('&', line);

                newLore.add(line);

            }

            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setLore(newLore);

            itemStack.setItemMeta(itemMeta);

        } catch (Exception e) {

            Bukkit.getLogger().info("Error applying lore to item: " + e.getMessage());

        }
    }

    private void applyDisplayName(Player player) {

        try {

            String newDisplayName = displayName;

            newDisplayName = applyPlaceholders(player, newDisplayName);

            newDisplayName = ChatColor.translateAlternateColorCodes('&', newDisplayName);

            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(newDisplayName);

            itemStack.setItemMeta(itemMeta);

        } catch (Exception e) {

            Bukkit.getLogger().info("Error applying display name to item: " + e.getMessage());

        }

    }

    private String applyPlaceholders(Player player, String text) {

        if (player != null && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {

            return PlaceholderAPI.setPlaceholders(player, text);

        }

        return text;

    }

}