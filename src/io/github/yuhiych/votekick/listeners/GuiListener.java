package io.github.yuhiych.votekick.listeners;

import io.github.yuhiych.votekick.VoteKick;
import io.github.yuhiych.votekick.gui.KickGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {
    @EventHandler
    public void onKickGuiClike(InventoryClickEvent event) {
        ItemStack clickitem = event.getCurrentItem();
        Player player = (Player) event.getView().getPlayer();
        Inventory inv = event.getClickedInventory();
            if (clickitem != null && clickitem.getType() != null && inv.getTitle().equals(ChatColor.translateAlternateColorCodes('&',
                    VoteKick.getInstance().getConfig().getString("Config.kickgui.title")))) {
                if (clickitem.getType()==Material.SKULL_ITEM) {
                    Player tokickplayer = Bukkit.getPlayer(clickitem.getItemMeta().getDisplayName().replace("§a", ""));
                    player.chat("/vk kick " + tokickplayer.getName());
                    player.closeInventory();
                }
                int page = Integer.valueOf(clickitem.getItemMeta().getLocalizedName());
                if (event.getRawSlot() == 47 && clickitem.getType() == Material.ARROW && clickitem.getItemMeta().getDisplayName().contains("§a上一页")) {
                    player.closeInventory();
                    KickGui.openKickGui(player, page - 1);
                }
                if (event.getRawSlot() == 51 && clickitem.getType() == Material.ARROW && clickitem.getItemMeta().getDisplayName().contains("§a上一页")) {
                    player.closeInventory();
                    KickGui.openKickGui(player, page + 1);
                }
            }
            event.setCancelled(true);
        }
}
