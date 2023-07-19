package io.github.yuhiych.votekick.papi;

import io.github.yuhiych.votekick.VoteKick;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends PlaceholderExpansion {
    /*
    The identifier, shouldn't contain any _ or %
     */
    public String getIdentifier() {
        return "votekick";
    }

    /*
     The author of the Placeholder
     This cannot be null
     */
    public String getAuthor() {
        return "Yuhiy";
    }

    /*
     Same with #getAuthor() but for versioon
     This cannot be null
     */

    public String getVersion() {
        return "1.0";
    }
    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }
    /*
    Use this method to setup placeholders
    This is somewhat similar to EZPlaceholderhook
     */
    public String onPlaceholderRequest(Player player, String identifier) {
        if(identifier.equalsIgnoreCase("yes")){
            return String.valueOf(VoteKick.getYesVote());
        }
        if(identifier.equalsIgnoreCase("no")){
            return String.valueOf(VoteKick.getNoVote());
        }
        if(identifier.equalsIgnoreCase("votetime")){
            return String.valueOf(VoteKick.getVoteTime());
        }
        return null;
    }
}