package com.zacharyvds.quillmc.domain.debug;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DebugPanel {
    public static void openDebugWindow(Player player){
        Inventory inv = Bukkit.createInventory(null, 27, "§6Debug Panel");

        for (int i = 0; i < 27; i++) {
            if (i % 9 == 0 || i % 9 == 8 || i < 9) {
                inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            } else {
                inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
            }
        }

        inv.setItem(13, createItem("Version: " + Bukkit.getVersion()));
        player.openInventory(inv);
    }

    private static ItemStack createItem(String name) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

}
