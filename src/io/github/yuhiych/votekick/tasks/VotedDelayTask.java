package io.github.yuhiych.votekick.tasks;

import io.github.yuhiych.votekick.VoteKick;
import org.bukkit.scheduler.BukkitRunnable;

public class VotedDelayTask extends BukkitRunnable {
    @Override
    public void run() {
        if (VoteKick.getVotedDelayTime(VoteKick.getToVotePlayer()) == 0) {
            cancel();
        }
        if (VoteKick.getVotedDelayTime(VoteKick.getToVotePlayer()) <= 0) {
            VoteKick.setVotedDelayTime(VoteKick.getToVotePlayer(), 0);
        }
        VoteKick.setVotedDelayTime(VoteKick.getToVotePlayer(), VoteKick.getVotedDelayTime(VoteKick.getToVotePlayer())-1);
    }
}
