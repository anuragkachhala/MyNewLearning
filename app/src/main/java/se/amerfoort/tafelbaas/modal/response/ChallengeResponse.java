package se.amerfoort.tafelbaas.modal.response;

import com.google.gson.annotations.SerializedName;

public class ChallengeResponse {
    @SerializedName("challenge_id")
    private String challenge_id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("opponent_id")
    private String opponent_id;
    @SerializedName("group_id")
    private String group_id;
    @SerializedName("auto_match")
    private String auto_match;
    @SerializedName("timestamp")
    private int timestamp;
    @SerializedName("accepted")
    private String accepted;
    @SerializedName("canceled")
    private String canceled;
    @SerializedName("done")
    private String done;
    @SerializedName("group")
    private Group group;
    @SerializedName("user")
    private UserDetail user;
    @SerializedName("opponent")
    private UserDetail opponent;

    public String getChallenge_id() {
        return challenge_id;
    }

    public void setChallenge_id(String challenge_id) {
        this.challenge_id = challenge_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOpponent_id() {
        return opponent_id;
    }

    public void setOpponent_id(String opponent_id) {
        this.opponent_id = opponent_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getAuto_match() {
        return auto_match;
    }

    public void setAuto_match(String auto_match) {
        this.auto_match = auto_match;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public String getCanceled() {
        return canceled;
    }

    public void setCanceled(String canceled) {
        this.canceled = canceled;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UserDetail getUser() {
        return user;
    }

    public void setUser(UserDetail user) {
        this.user = user;
    }

    public UserDetail getOpponent() {
        return opponent;
    }

    public void setOpponent(UserDetail opponent) {
        this.opponent = opponent;
    }
}