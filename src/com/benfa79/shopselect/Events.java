/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benfa79.shopselect;

import static com.benfa79.shopselect.ShopSelect.shopsInv;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author benjaminfarias
 */
public class Events implements Listener{
        @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.sendMessage("Yup");
        Inventory inventory = event.getInventory();
        if (inventory.getTitle().equals(shopsInv.getTitle())) {
            int selected = event.getSlot();
            if (selected < 45) {
                int x = ShopSelect.shops.get(selected).x;
                int y = ShopSelect.shops.get(selected).y;
                int z = ShopSelect.shops.get(selected).z;
                Location loc = new Location(player.getWorld(), x, y, z);
                event.setCancelled(true);
                player.closeInventory();
                player.teleport(loc);
            }
            if (selected==49) {
                //Create new shop code here
            }
        }
    }
}
