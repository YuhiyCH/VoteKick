package io.github.yuhiych.votekick.tasks;

import io.github.yuhiych.votekick.VoteKick;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayJoinTask extends BukkitRunnable {
    @Override
    public void run() {
        if (VoteKick.getDelayToJoinTime(VoteKick.getToKickPlayer()) <= 0) {
            VoteKick.setDelayToJoinTime(VoteKick.getToKickPlayer(), 0);
        }
        if (VoteKick.getDelayToJoinTime(VoteKick.getToKickPlayer()) == 0) {
            cancel();
        }
        VoteKick.setDelayToJoinTime(VoteKick.getToKickPlayer(), VoteKick.getDelayToJoinTime(VoteKick.getToKickPlayer())-1);
    }
}
