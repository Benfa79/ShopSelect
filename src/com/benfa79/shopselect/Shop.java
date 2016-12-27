/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benfa79.shopselect;

import org.bukkit.inventory.ItemStack;

/**
 *
 * @author benjaminfarias
 */
class Shop {
    String name;
    String owner;
    ItemStack item;
    int x;
    int y;
    int z;

    public Shop(String name, String owner, ItemStack item, int x, int y, int z) {
        this.name = name;
        this.owner = owner;
        this.item = item;
        this.x = x;
        this.y = y;
        this.z = z;
        
        
    }
 
    
    
}
