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
    static File shopsFile;
    static FileConfiguration shopsData;
    public static Inventory shopsInv = Bukkit.createInventory(null, 54, "Shops");
    public static Inventory items1 = Bukkit.createInventory(null, 54, "Select An Item");
    static ArrayList<Player> newPlayers = new ArrayList<>();
    static ArrayList<Shop> newShops = new ArrayList<>();
    
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
        
        getServer().getPluginManager().registerEvents(new Events(), this);
        
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
    
    public static void saveYamls() {
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
            tempName = shopsData.getString("shops."+Integer.toString(i)+".name");
            tempOwner = shopsData.getString("shops."+Integer.toString(i)+".owner");
            tempItem = shopsData.getItemStack("shops."+Integer.toString(i)+".item");
            tempX = shopsData.getInt("shops."+Integer.toString(i)+".X");
            tempY = shopsData.getInt("shops."+Integer.toString(i)+".Y");
            tempZ = shopsData.getInt("shops."+Integer.toString(i)+".Z");
            
            ItemMeta tempMeta = tempItem.getItemMeta();
            tempMeta.setDisplayName(tempName);
            ArrayList<String> Lore = new ArrayList<>();
            Lore.add("Owned By: "+tempOwner);
            tempMeta.setLore(Lore);
            tempItem.setItemMeta(tempMeta);
            
            newShop2(tempName, tempOwner, tempItem, tempX, tempY, tempZ);
            shopsInv.setItem(i, tempItem);
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
    
    public static void newShop(String name, String owner, ItemStack item, int x, int y, int z) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("Owned By: "+owner);
        meta.setLore(Lore);
        item.setItemMeta(meta);
        
        shops.add(new Shop(name, owner, item, x, y, z));
        
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
    
    static {
        items1.setItem(0, new ItemStack(Material.DIAMOND_SWORD, 1));
        items1.setItem(1, new ItemStack(Material.IRON_SWORD, 1));
        items1.setItem(2, new ItemStack(Material.GOLD_SWORD, 1));
        items1.setItem(3, new ItemStack(Material.STONE_SWORD, 1));
        items1.setItem(4, new ItemStack(Material.BOW, 1));
        items1.setItem(5, new ItemStack(Material.ARROW, 1));
        items1.setItem(6, new ItemStack(Material.GOLD_SPADE, 1));
        items1.setItem(7, new ItemStack(Material.GOLD_PICKAXE, 1));
        items1.setItem(8, new ItemStack(Material.GOLD_AXE, 1));
        items1.setItem(9, new ItemStack(Material.IRON_SPADE, 1));
        items1.setItem(10, new ItemStack(Material.IRON_PICKAXE, 1));
        items1.setItem(11, new ItemStack(Material.IRON_AXE, 1));
        items1.setItem(12, new ItemStack(Material.DIAMOND_SPADE, 1));
        items1.setItem(13, new ItemStack(Material.DIAMOND_PICKAXE, 1));
        items1.setItem(14, new ItemStack(Material.DIAMOND_AXE, 1));
        items1.setItem(15, new ItemStack(Material.DIAMOND_HOE, 1));
        items1.setItem(16, new ItemStack(Material.NAME_TAG, 1));
        items1.setItem(17, new ItemStack(Material.LEASH, 1));
        items1.setItem(18, new ItemStack(Material.SHEARS, 1));
        items1.setItem(19, new ItemStack(Material.APPLE, 1));
        items1.setItem(20, new ItemStack(Material.BREAD, 1));
        items1.setItem(21, new ItemStack(Material.PORK, 1));
        items1.setItem(22, new ItemStack(Material.CAKE, 1));
        items1.setItem(23, new ItemStack(Material.COOKIE, 1));
        items1.setItem(24, new ItemStack(Material.CARROT, 1));
        items1.setItem(25, new ItemStack(Material.DIAMOND_SWORD, 1));
        items1.setItem(26, new ItemStack(Material.WHEAT, 1));
        items1.setItem(27, new ItemStack(Material.SEEDS, 1));
        items1.setItem(28, new ItemStack(Material.COAL, 1));
        items1.setItem(29, new ItemStack(Material.IRON_INGOT, 1));
        items1.setItem(30, new ItemStack(Material.GOLD_INGOT, 1));
        items1.setItem(31, new ItemStack(Material.DIAMOND, 1));
        items1.setItem(32, new ItemStack(Material.EMERALD, 1));
        items1.setItem(33, new ItemStack(Material.QUARTZ, 1));
        items1.setItem(34, new ItemStack(Material.SAPLING, 1));
        items1.setItem(35, new ItemStack(Material.RED_ROSE, 1));
        items1.setItem(36, new ItemStack(Material.SKULL, 1));
        items1.setItem(37, new ItemStack(Material.ANVIL, 1));
        items1.setItem(38, new ItemStack(Material.POTION, 1));
        items1.setItem(39, new ItemStack(Material.BREWING_STAND_ITEM, 1));
        items1.setItem(40, new ItemStack(Material.GLASS_BOTTLE, 1));
        items1.setItem(41, new ItemStack(Material.SADDLE, 1));
        items1.setItem(42, new ItemStack(Material.STICK, 1));
        items1.setItem(43, new ItemStack(Material.STRING, 1));
        items1.setItem(44, new ItemStack(Material.FEATHER, 1));
        items1.setItem(45, new ItemStack(Material.SULPHUR, 1));
        items1.setItem(46, new ItemStack(Material.LEATHER, 1));
        items1.setItem(47, new ItemStack(Material.CLAY_BALL, 1));
        items1.setItem(48, new ItemStack(Material.EGG, 1));
        items1.setItem(49, new ItemStack(Material.FLINT, 1));
        items1.setItem(50, new ItemStack(Material.INK_SACK, 1));
        items1.setItem(51, new ItemStack(Material.SLIME_BALL, 1));
        items1.setItem(52, new ItemStack(Material.BOOK, 1));
        items1.setItem(53, new ItemStack(Material.SNOW_BALL, 1));
    }
}
