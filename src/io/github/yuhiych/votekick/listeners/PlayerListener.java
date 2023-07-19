package io.github.yuhiych.votekick.listeners;


import io.github.yuhiych.votekick.VoteKick;
import io.github.yuhiych.votekick.gui.KickGui;
import io.github.yuhiych.votekick.tasks.DelayJoinTask;
import io.github.yuhiych.votekick.tasks.VotedDelayTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitTask;

public class PlayerListener implements Listener {
    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM,1, (short) 3);
        SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
        skullmeta.setDisplayName(ChatColor.GREEN + event.getPlayer().getName());
        skullmeta.setOwningPlayer(event.getPlayer());
        skull.setItemMeta(skullmeta);
        KickGui.setSkull(event.getPlayer(), skull);
        if (VoteKick.getToKickPlayer() != null && VoteKick.getDelayToJoinTime(VoteKick.getToKickPlayer()) > 0) {
            event.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', VoteKick.getInstance().getConfig().getString("Message.delaytojoin").replaceAll("%time%", String.valueOf(VoteKick.getDelayToJoinTime(event.getPlayer())))));
        } else {
            VoteKick.setDelayToJoinTime(event.getPlayer(), 0);
        }
    }
    @EventHandler
    public void onLeave (PlayerQuitEvent event) {
        KickGui.clearSkull(event.getPlayer());
        if (event.getPlayer() == VoteKick.getToKickPlayer()){
            if (VoteKick.getInVote()) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', VoteKick.getInstance().getConfig().getString("Message.stopofplayerleavemessage")));
                VoteKick.stopVote();
                Bukkit.getScheduler().cancelTasks(VoteKick.getInstance());
                BukkitTask voteddelaytask = new VotedDelayTask().runTaskTimer(VoteKick.getInstance(), 0, 20);
                BukkitTask delayjointask = new DelayJoinTask().runTaskTimer(VoteKick.getInstance(), 0, 20);
            }
        }
    }
}
