package io.github.yuhiych.votekick.tasks;

import io.github.yuhiych.votekick.VoteKick;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


public class InVoteTask extends BukkitRunnable {
    @Override
        public void run() {
            if (VoteKick.getVoteTime() == 0) {
                Bukkit.broadcastMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                VoteKick.getInstance().getConfig().getString("Message.stopmessage")
                                        .replaceAll("%yes%", String.valueOf(VoteKick.getYesVote()))
                        .replaceAll("%no%", String.valueOf(VoteKick.getNoVote()))));
                if (VoteKick.getYesVote() >  VoteKick.getNoVote()) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.kickmessagebroadcast")));
                    VoteKick.stopVote();
                    VoteKick.getToKickPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.kickmessage")));
                    VoteKick.setDelayToJoinTime(VoteKick.getToKickPlayer(), VoteKick.getInstance().getConfig().getInt("Config.delaytojointime"));
                    BukkitTask delayjointask = new DelayJoinTask().runTaskTimer(VoteKick.getInstance(), 0, 20);
                } else {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.noenoughvotetokick")));
                    VoteKick.stopVote();
                }
                cancel();
            } else {
                if (VoteKick.getVoteTime() == 60) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.voting")
                            .replaceAll("%yes%", String.valueOf(VoteKick.getYesVote()))
                            .replaceAll("%no%", String.valueOf(VoteKick.getNoVote()))
                                    .replaceAll("%time%", String.valueOf(VoteKick.getVoteTime()))));
                }
                if (VoteKick.getVoteTime() == 30) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.voting")
                                    .replaceAll("%yes%", String.valueOf(VoteKick.getYesVote()))
                                    .replaceAll("%no%", String.valueOf(VoteKick.getNoVote()))
                                    .replaceAll("%time%", String.valueOf(VoteKick.getVoteTime()))));
                }
                if (VoteKick.getVoteTime() == 10) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.voting")
                                    .replaceAll("%yes%", String.valueOf(VoteKick.getYesVote()))
                                    .replaceAll("%no%", String.valueOf(VoteKick.getNoVote()))
                                    .replaceAll("%time%", String.valueOf(VoteKick.getVoteTime()))));
                }
                if (VoteKick.getVoteTime() == 5) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.voting")
                                    .replaceAll("%yes%", String.valueOf(VoteKick.getYesVote()))
                                    .replaceAll("%no%", String.valueOf(VoteKick.getNoVote()))
                                    .replaceAll("%time%", String.valueOf(VoteKick.getVoteTime()))));
                }
                if (VoteKick.getVoteTime() == 0) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.voting")
                                    .replaceAll("%yes%", String.valueOf(VoteKick.getYesVote()))
                                    .replaceAll("%no%", String.valueOf(VoteKick.getNoVote()))
                                    .replaceAll("%time%", String.valueOf(VoteKick.getVoteTime()))));
                }
                VoteKick.setVoteTime(VoteKick.getVoteTime()-1);
            }
    }
}
