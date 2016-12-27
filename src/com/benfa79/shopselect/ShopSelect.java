/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benfa79.shopselect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author benjaminfarias
 */
public class ShopSelect extends JavaPlugin {

    /**
     * @param args the command line arguments
     */
    static ArrayList<Shop> shops = new ArrayList();
    File shopsFile;
    FileConfiguration shopsData;
    public static Inventory shopsInv = Bukkit.createInventory(null, 54, "Shops");
    
    public static void main(String[] args) {
    }
    
    @Override
    public void onEnable() {
        shopsFile = new File(getDataFolder(), "shops.yml");
        if (!shopsFile.exists()) {
            shopsFile.getParentFile().mkdirs();
            try {
                shopsFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ShopSelect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        shopsData = new YamlConfiguration();
        loadYamls();
        loadShops();
//        newShop("name", "owner", new ItemStack(Material.BEACON), 0, 80, 0);
//        newShop("name2", "owner2", new ItemStack(Material.BEDROCK), 10, 80, 10);
        
        ItemStack star = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = star.getItemMeta();
        meta.setDisplayName("Add Shop");
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("Add a shop to the menu.");
        meta.setLore(Lore);
        star.setItemMeta(meta);
        shopsInv.setItem(49, star);
        
        getLogger().info("ShopSelect Enabled!");
    }
    
    
    @Override
    public void onDisable() {
        
    }
    
    public void loadYamls() {
        try {
            shopsData.load(shopsFile);
        } catch (Exception e) {
        }
    }
    
    public void saveYamls() {
        try {
            shopsData.save(shopsFile);
        } catch (Exception e) {
        }
    }
    
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("shops")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.openInventory(shopsInv);
                
                
            }
            else {
                getLogger().info("You must be a player to run this command.");
            }
        }
        return true;
    }
    
    public void loadShops() {
        int number = shopsData.getInt("amount");
        String tempName;
        String tempOwner;
        ItemStack tempItem;
        int tempX;
        int tempY;
        int tempZ;
        
        for (int i = 0; i < number; i++) {
            tempName = Integer.toString(shopsData.getInt("shops."+Integer.toString(i)+".name"));
            tempOwner = Integer.toString(shopsData.getInt("shops."+Integer.toString(i)+".owner"));
            tempItem = shopsData.getItemStack("shops."+Integer.toString(i)+".item");
            tempX = shopsData.getInt("shops."+Integer.toString(i)+".X");
            tempY = shopsData.getInt("shops."+Integer.toString(i)+".Y");
            tempZ = shopsData.getInt("shops."+Integer.toString(i)+".Z");
            
            newShop2(tempName, tempOwner, tempItem, tempX, tempY, tempZ);
            shopsInv.setItem(i, tempItem);
            getLogger().info(tempItem.toString());
        }
    }
    
    public void saveShops() {
        int number = shops.size();
        shopsData.set("amount", number);
        
        for (int i = 0; i < number; i++) {
            shopsData.set("shops."+Integer.toString(i)+".name", shops.get(i).name);
            shopsData.set("shops."+Integer.toString(i)+".owner", shops.get(i).owner);
            shopsData.set("shops."+Integer.toString(i)+".item", shops.get(i).item);
            shopsData.set("shops."+Integer.toString(i)+".X", shops.get(i).x);
            shopsData.set("shops."+Integer.toString(i)+".Y", shops.get(i).y);
            shopsData.set("shops."+Integer.toString(i)+".Z", shops.get(i).z);
            
        }
    }
    
    public void newShop(String name, String owner, ItemStack item, int x, int y, int z) {
        shops.add(new Shop(name, owner, item, x, y, z));
        
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("Owned By: "+owner);
        meta.setLore(Lore);
        item.setItemMeta(meta);
        
        shopsData.set("amount", shops.size());
        shopsData.set("shops."+Integer.toString(shops.size()-1)+".name", name);
        shopsData.set("shops."+Integer.toString(shops.size()-1)+".owner", owner);
        shopsData.set("shops."+Integer.toString(shops.size()-1)+".item", item);
        shopsData.set("shops."+Integer.toString(shops.size()-1)+".X", x);
        shopsData.set("shops."+Integer.toString(shops.size()-1)+".Y", y);
        shopsData.set("shops."+Integer.toString(shops.size()-1)+".Z", z);
        saveYamls();
        
        shopsInv.setItem(shops.size()-1, item);
    }
    
    public void newShop2(String name, String owner, ItemStack item, int x, int y, int z) {
        shops.add(new Shop(name, owner, item, x, y, z));
        
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("Owned By: "+owner);
        meta.setLore(Lore);
        item.setItemMeta(meta);
        
        shopsInv.setItem(shops.size()-1, item);
    }
    
}
