package me.darkolythe.deepstorageplus.dsu.listeners;

import me.darkolythe.deepstorageplus.DeepStoragePlus;
import me.darkolythe.deepstorageplus.utils.ItemList;
import me.darkolythe.deepstorageplus.utils.LanguageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WrenchListener implements Listener {

    private DeepStoragePlus main;
    public WrenchListener(DeepStoragePlus plugin) {
        main = plugin;
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    private void onWrenchUse(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack storageWrench = ItemList.createStorageWrench();
            ItemStack sorterWrench = ItemList.createSorterWrench();
            if (player.getInventory().getItemInMainHand().equals(storageWrench)) {
                Block block = event.getClickedBlock();
                if (block != null && block.getType() == Material.CHEST) {
                    if (player.hasPermission("deepstorageplus.create")) {
                        if (!event.isCancelled()) {
                            event.setCancelled(true);
                            if (isInventoryEmpty(block)) {
                                if (sizeOfInventory(block) == 54) {
                                    createDSU(block);
                                    player.getInventory().getItemInMainHand().setAmount(0);
                                    player.sendMessage(DeepStoragePlus.prefix + ChatColor.GREEN + LanguageManager.getValue("dsucreate"));
                                } else {
                                    player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("chest must be double"));
                                }
                            } else {
                                player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("chestmustbeempty"));
                            }
                        }
                    } else {
                        player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("nopermission"));
                    }
                } else if (block != null && block.getType() == Material.GRASS_BLOCK) { // Handle using the "shovel" to make dirt paths
                    event.setCancelled(true);
                }
            }

            if (player.getInventory().getItemInMainHand().equals(sorterWrench)) {
                Block block = event.getClickedBlock();
                if (block != null && block.getType() == Material.CHEST) {
                    if (player.hasPermission("deepstorageplus.create")) {
                        if (!event.isCancelled()) {
                            event.setCancelled(true);
                            if (isInventoryEmpty(block)) {
                                if (sizeOfInventory(block) == 54) {
                                    createDSU(block);
                                    player.getInventory().getItemInMainHand().setAmount(0);
                                    player.sendMessage(DeepStoragePlus.prefix + ChatColor.GREEN + LanguageManager.getValue("dsucreate"));
                                } else {
                                    player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("chest must be double"));
                                }
                            } else {
                                player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("chestmustbeempty"));
                            }
                        }
                    } else {
                        player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + LanguageManager.getValue("nopermission"));
                    }
                } else if (block != null && block.getType() == Material.GRASS_BLOCK) { // Handle using the "shovel" to make dirt paths
                    event.setCancelled(true);
                }
            }
        }
    }

    private static boolean isInventoryEmpty(Block block) {
        Inventory inv = getInventoryFromBlock(block);
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) != null) {
                return false;
            }
        }
        return true;
    }

    private static int sizeOfInventory(Block block) {
        Inventory inv = getInventoryFromBlock(block);
        return inv.getSize();
    }

    private void createDSU(Block block) {
        Chest chest = (Chest) block.getState();
        chest.setCustomName(DeepStoragePlus.DSUname);
        chest.update();
    }

    private void createSorter(Block block) {
        Chest chest = (Chest) block.getState();
        chest.setCustomName(DeepStoragePlus.sortername);
        chest.update();
    }

    private static Inventory getInventoryFromBlock(Block block) {
        BlockState bs = block.getState();
        Chest chest = (Chest) bs;
        return chest.getInventory();
    }
}
