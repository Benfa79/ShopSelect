/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benfa79.shopselect;

import static com.benfa79.shopselect.ShopSelect.shopsInv;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class Events implements Listener{
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.sendMessage("Yup");
        Inventory inventory = event.getInventory();
        if (inventory.getTitle().equals(shopsInv.getTitle())) {
            int selected = event.getSlot();
            int x = 0;
            int y = 0;
            int z = 0;
            if (selected<45) {
                x = ShopSelect.shops.get(selected).x;
                y = ShopSelect.shops.get(selected).y;
                z = ShopSelect.shops.get(selected).z;
                
                event.setCancelled(true);
                player.closeInventory();
                player.teleport(new Location(player.getWorld(), x, y, z));
                
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Add Shop")) {
                event.setCancelled(true);
                player.closeInventory();
                ShopSelect.newPlayers.add(player);
                ShopSelect.newShops.add(new Shop());
                ShopSelect.newShops.get(ShopSelect.newPlayers.indexOf(player)).x = player.getLocation().getBlockX();
                ShopSelect.newShops.get(ShopSelect.newPlayers.indexOf(player)).y = player.getLocation().getBlockY();
                ShopSelect.newShops.get(ShopSelect.newPlayers.indexOf(player)).z = player.getLocation().getBlockZ();
                ShopSelect.newShops.get(ShopSelect.newPlayers.indexOf(player)).owner = player.getName();
                player.openInventory(ShopSelect.items1);
            }    
        }
        if (inventory.getTitle().equals(ShopSelect.items1.getTitle())) {
            ShopSelect.newShops.get(ShopSelect.newPlayers.indexOf(player)).item = chooseItem(event);
            player.sendMessage("Type the name of your shop.");
            
        }
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (ShopSelect.newPlayers.contains(event.getPlayer())) {
            Player player = event.getPlayer();
            ShopSelect.newShops.get(ShopSelect.newPlayers.indexOf(event.getPlayer())).name = setName(event);
            ShopSelect.newShop(ShopSelect.newShops.get(ShopSelect.newPlayers.indexOf(player)).name, ShopSelect.newShops.get(ShopSelect.newPlayers.indexOf(player)).owner, ShopSelect.newShops.get(ShopSelect.newPlayers.indexOf(player)).item, ShopSelect.newShops.get(ShopSelect.newPlayers.indexOf(player)).x, ShopSelect.newShops.get(ShopSelect.newPlayers.indexOf(player)).y, ShopSelect.newShops.get(ShopSelect.newPlayers.indexOf(player)).z);
            ShopSelect.newShops.remove(ShopSelect.newPlayers.indexOf(player));
            ShopSelect.newPlayers.remove(player);
        }
    }
    
    public ItemStack chooseItem(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        return event.getCurrentItem();
    }
    public String setName(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        return event.getMessage();
    }
}
