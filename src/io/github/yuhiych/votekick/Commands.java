package io.github.yuhiych.votekick;

import io.github.yuhiych.votekick.tasks.InVoteTask;
import io.github.yuhiych.votekick.gui.KickGui;
import io.github.yuhiych.votekick.tasks.VotedDelayTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0 || args[0].equals("help")) {
            List<String> helplist = new ArrayList<>();
            helplist.addAll(VoteKick.getInstance().getConfig().getStringList("Message.help"));
            for (int i = 0; i < helplist.size(); i++) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', helplist.get(i)));
            }
            return true;
        }
        if (args[0].equals("kick")) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    KickGui.openKickGui(p,1);
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.kick")));
                }
            } else if (sender.hasPermission("votekick.vote")) {
                Player tokickplayer = Bukkit.getPlayer(args[1]);
                if (args.length != 0 && tokickplayer != null) {
                    if (Bukkit.getServer().getOnlinePlayers().size() >= VoteKick.getInstance().getConfig().getInt("Config.enoughplayer")) {
                        if (!VoteKick.getInVote()) {
                            if (sender.getName() != tokickplayer.getName()) {
                                if (!tokickplayer.hasPermission("votekick.bypass")) {
                                    if (VoteKick.getVotedDelayTime((Player) sender) == 0) {
                                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                                                VoteKick.getInstance().getConfig().getString("Message.kickplayer")
                                                        .replaceAll("%voter%", sender.getName())
                                                        .replaceAll("%player%", tokickplayer.getName())));
                                        VoteKick.setInVote(true);
                                        List<Player> playerlist = new ArrayList<>();
                                        playerlist.addAll(Bukkit.getOnlinePlayers());
                                        for (int i = 0; i < playerlist.size(); i++) {
                                            VoteKick.setPlayerVote(playerlist.get(i), false);
                                        }
                                        VoteKick.setPlayerVote((Player)sender, true);
                                        VoteKick.setToKickPlayer(tokickplayer);
                                        VoteKick.setToVotePlayer((Player) sender);
                                        VoteKick.setYesVote(1);
                                        VoteKick.setNoVote(0);
                                        VoteKick.setVoteTime(VoteKick.getInstance().getConfig().getInt("Config.votetime"));
                                        VoteKick.setVotedDelayTime((Player) sender, VoteKick.getInstance().getConfig().getInt("Config.voteddelaytime"));
                                        BukkitTask invotetask = new InVoteTask().runTaskTimer(VoteKick.getInstance(), 0, 20);
                                        BukkitTask voteddelaytask = new VotedDelayTask().runTaskTimer(VoteKick.getInstance(), 0, 20);
                                        } else {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                VoteKick.getInstance().getConfig().getString("Message.invoteddelay"))
                                                .replaceAll("%time%", String.valueOf(VoteKick.getVotedDelayTime((Player)sender))));
                                        }
                                    } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            VoteKick.getInstance().getConfig().getString("Message.canottokickplayer")));
                                }
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        VoteKick.getInstance().getConfig().getString("Message.nottovoteself")));
                            }
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        VoteKick.getInstance().getConfig().getString("Message.invote")));
                            }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                VoteKick.getInstance().getConfig().getString("Message.noenoughplayer")));
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.noplayer")));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        VoteKick.getInstance().getConfig().getString("Message.nopermission")));
            }
            return true;
        }
        if (args[0].equals("yes") && args.length < 2) {
            if (VoteKick.getInVote()) {
                if (VoteKick.getToKickPlayer()!=sender) {
                if (!VoteKick.getPlayerVote((Player) sender)) {
                    VoteKick.setPlayerVote((Player) sender, true);
                    VoteKick.setYesVote(VoteKick.getYesVote() + 1);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.voteyes")));
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.voting")
                            .replaceAll("%yes%", String.valueOf(VoteKick.getYesVote()))
                            .replaceAll("%no%", String.valueOf(VoteKick.getNoVote()))
                            .replaceAll("%time%", String.valueOf(VoteKick.getVoteTime()))));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.voted")));
                 }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.isvoteplayer")));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        VoteKick.getInstance().getConfig().getString("Message.noinvote")));
            }
            return true;
        }
        if (args[0].equals("no")) {
            if (VoteKick.getInVote()) {
                if (VoteKick.getToKickPlayer()!=sender) {
                    if (!VoteKick.getPlayerVote((Player)sender)) {
                        VoteKick.setPlayerVote((Player)sender, true);
                        VoteKick.setNoVote(VoteKick.getNoVote() + 1);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                VoteKick.getInstance().getConfig().getString("Message.voteno")));
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                                VoteKick.getInstance().getConfig().getString("Message.voting")
                                .replaceAll("%yes%", String.valueOf(VoteKick.getYesVote()))
                                .replaceAll("%no%", String.valueOf(VoteKick.getNoVote()))
                                .replaceAll("%time%", String.valueOf(VoteKick.getVoteTime()))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                VoteKick.getInstance().getConfig().getString("Message.voted")));
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.isvoteplayer")));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        VoteKick.getInstance().getConfig().getString("Message.noinvote")));
            }
            return true;
        }
        if (args[0].equals("reload") && args.length < 2){
            if (sender.hasPermission("votekick.admin")) {
                VoteKick.getInstance().reloadConfig();
                VoteKick.stopVote();
                if (VoteKick.getInVote()) {
                    Bukkit.getScheduler().cancelTasks(VoteKick.getInstance());
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        VoteKick.getInstance().getConfig().getString("Message.reload")));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        VoteKick.getInstance().getConfig().getString("Message.nopermission")));
            }
            return true;
        }
        if (args[0].equals("stop") && args.length < 2) {
            if (sender.hasPermission("votekick.admin")) {
                if (!VoteKick.getInVote()) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            VoteKick.getInstance().getConfig().getString("Message.noinvote")));
                }
                VoteKick.stopVote();
                Bukkit.getScheduler().cancelTasks(VoteKick.getInstance());
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                        VoteKick.getInstance().getConfig().getString("Message.adminstopmessage")));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        VoteKick.getInstance().getConfig().getString("Message.nopermission")));
            }
            return true;
        }
        return true;
    }
}