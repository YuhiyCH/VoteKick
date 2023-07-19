package io.github.yuhiych.votekick;

import io.github.yuhiych.votekick.gui.KickGui;
import io.github.yuhiych.votekick.listeners.GuiListener;
import io.github.yuhiych.votekick.listeners.PlayerListener;
import io.github.yuhiych.votekick.papi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteKick extends JavaPlugin {
    private static Player tokickplayer;
    private static Player tovoteplayer;
    private static int votetime;
    private static VoteKick instance;
    private static boolean invote = false;
    private static final Map<Player, Boolean> playervoted = new HashMap<>();
    private static final Map<Player, Integer> voteddelay = new HashMap<>();
    private static final Map<Player, Integer> delaytojoin = new HashMap<>();
    private static int yesvote;
    private static int novote;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        reloadConfig();
        System.out.println("VoteKick-Config 加载成功");
        getCommand("vk").setExecutor(new Commands());
        System.out.println("VoteKick-Commands 注册成功");
        yesvote = 0;
        novote = 0;
        loadSkullData();
        System.out.println("VoteKick-Datas 设置成功");
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
        Bukkit.getPluginManager().registerEvents(new GuiListener(),this);
        System.out.println("VoteKick-Listeners 注册成功");
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPI().register();
            System.out.println("VoteKick-PlaceholderAPI 注册成功");
        }
        System.out.println("VoteKick 加载成功");
    }

    @Override
    public void onDisable() {
        playervoted.clear();
        voteddelay.clear();
        System.out.println("VoteKick-Datas 清除成功");
        Bukkit.getScheduler().cancelTasks(VoteKick.getInstance());
        System.out.println("VoteKick-Tasks 关闭成功");
        System.out.println("VoteKick 卸载成功");
    }
    public static void loadSkullData(){
        List<Player> players = new ArrayList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        ItemStack skull = new ItemStack(Material.SKULL_ITEM,1, (short) 3);
        SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
        for (int i =0; i< players.size();i++) {
            skullmeta.setDisplayName(ChatColor.GREEN + players.get(i).getName());
            skullmeta.setOwningPlayer(players.get(i));
            skull.setItemMeta(skullmeta);
            KickGui.setSkull(players.get(i), skull);
        }
    }

    public static VoteKick getInstance() {
        return VoteKick.instance;
    }

    public static void stopVote() {
        playervoted.clear();
        yesvote = 0;
        novote = 0;
    }
    public static int getVoteTime() {
        return votetime;
    }
    public static void setVoteTime(Integer time) {
        votetime = time;
    }
    public static int getDelayToJoinTime(Player player) {
        if (delaytojoin.containsValue(null)) {
            delaytojoin.put(player, 0);
        }
        return delaytojoin.get(player);
    }
    public static void setDelayToJoinTime(Player player, Integer time) {
        delaytojoin.put(player, time);
    }
    public static int getVotedDelayTime(Player player) {
        if (voteddelay.containsValue(null)) {
            voteddelay.put(player, 0);
        }
        return voteddelay.get(player);
    }
    public static void setVotedDelayTime(Player player, Integer time) {
        voteddelay.put(player, time);
    }
    public static Player getToKickPlayer() {
        return tokickplayer;
    }
    public static void setToKickPlayer(Player player) {
        tokickplayer = player;
    }
    public static Player getToVotePlayer() {
        return tovoteplayer;
    }
    public static void setToVotePlayer(Player player) {
        tovoteplayer = player;
    }
    public static int getYesVote() {
        return yesvote;
    }
    public static void setYesVote(Integer yes) {
        yesvote = yes;
    }

    public static int getNoVote() {
        return novote;
    }
    public static void setNoVote(Integer no) {
        novote = no;
    }

    public static boolean getPlayerVote(Player player) {
        if (playervoted.containsValue(null)) {
            playervoted.put(player, false);
        }
        return playervoted.get(player);
    }

    public static void setPlayerVote(Player player, Boolean voted) {
        playervoted.put(player, voted);
    }

    public static void setInVote(Boolean b) {
        invote = b;
    }

    public static boolean getInVote() {
        return invote;
    }

}
