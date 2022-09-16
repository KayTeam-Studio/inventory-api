package org.kayteam.inventoryapi.action;

import org.bukkit.entity.Player;

public interface SlotAction {

    void execute(Player player, int slot);

}