package se.amerfoort.tafelbaas.modal.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by skushwa1 on 10/30/2017.
 */
public class User {
    @SerializedName("display_name")
    private String display_name;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("matches_won")
    private int matches_won;
    @SerializedName("matches_lost")
    private int matches_lost;
    @SerializedName("timestamp")
    private int timestamp;
    @SerializedName("groupScores")
    private GroupScore groupScores;

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getMatches_won() {
        return matches_won;
    }

    public void setMatches_won(int matches_won) {
        this.matches_won = matches_won;
    }

    public int getMatches_lost() {
        return matches_lost;
    }

    public void setMatches_lost(int matches_lost) {
        this.matches_lost = matches_lost;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public GroupScore getGroupScores() {
        return groupScores;
    }

    public void setGroupScores(GroupScore groupScores) {
        this.groupScores = groupScores;
    }
}
