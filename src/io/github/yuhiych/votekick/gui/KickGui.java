package io.github.yuhiych.votekick.gui;

import io.github.yuhiych.votekick.VoteKick;
import io.github.yuhiych.votekick.util.PageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KickGui {
    private static final Map<Player,ItemStack> skull = new HashMap<>();
    public static void setSkull(Player player, ItemStack itemStack){
        skull.put(player,itemStack);
    }
    public static void clearSkull(Player player){
        skull.remove(player);
    }

    public static void openKickGui(Player p, int page){
        Inventory inv = Bukkit.createInventory(null, 54,
                ChatColor.translateAlternateColorCodes('&',
                        VoteKick.getInstance().getConfig().getString("Config.kickgui.title"))
                        .replaceAll("%page%", String.valueOf(page)));
        ItemStack barror = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta barrormeta = barror.getItemMeta();
        barrormeta.setDisplayName("§0-");
        barror.setItemMeta(barrormeta);
        inv.setItem(0, barror);
        inv.setItem(1, barror);
        inv.setItem(2, barror);
        inv.setItem(3, barror);
        inv.setItem(4, barror);
        inv.setItem(5, barror);
        inv.setItem(6, barror);
        inv.setItem(7, barror);
        inv.setItem(8, barror);
        inv.setItem(45, barror);
        inv.setItem(46, barror);
        inv.setItem(48, barror);
        inv.setItem(49, barror);
        inv.setItem(50, barror);
        inv.setItem(52, barror);
        inv.setItem(53, barror);
        List<ItemStack> allItems = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        for (int i = 0; i < players.size() ;i++) {
            allItems.add(skull.get(players.get(i)));
        }
        ItemStack lastPage = new ItemStack(Material.ARROW);
        ItemMeta lastPageMeta = lastPage.getItemMeta();
        if (PageUtil.isPageValid(allItems, page - 1, 27)) {
            lastPageMeta.setDisplayName("§a上一页");
            lastPageMeta.setLocalizedName(String.valueOf(page));
            lastPage.setItemMeta(lastPageMeta);
            inv.setItem(47, lastPage);
        } else {
            barrormeta.setLocalizedName(String.valueOf(page));
            barror.setItemMeta(barrormeta);
            inv.setItem(47, barror);
        }
        ItemStack nextPage = new ItemStack(Material.ARROW);
        ItemMeta nextPageMeta = nextPage.getItemMeta();
        if (PageUtil.isPageValid(allItems, page + 1, 27) && players.size() > 27) {
            nextPageMeta.setDisplayName("§a下一页");
            nextPage.setItemMeta(nextPageMeta);
            inv.setItem(51, nextPage);
        } else {
            inv.setItem(51, barror);
        }
        for (ItemStack item : PageUtil.getPageItems(allItems, page, 27)) {
            inv.setItem(inv.firstEmpty(), item);
        }
        p.openInventory(inv);
    }
}
