package se.amerfoort.tafelbaas.modal.response;

import com.google.gson.annotations.SerializedName;

public class MyGameResponse {
    @SerializedName("user_1_display_name")
    private String user_1_display_name;
    @SerializedName("user_1_score")
    private String user_1_score;
    @SerializedName("user_2_display_name")
    private String user_2_display_name;
    @SerializedName("user_2_score")
    private String user_2_score;
    @SerializedName("group_name")
    private String group_name;
    @SerializedName("timestamp")
    private String timestamp;
    @SerializedName("identifier")
    private String identifier;

    public String getUser_1_display_name() {
        return user_1_display_name;
    }

    public void setUser_1_display_name(String user_1_display_name) {
        this.user_1_display_name = user_1_display_name;
    }

    public String getUser_1_score() {
        return user_1_score;
    }

    public void setUser_1_score(String user_1_score) {
        this.user_1_score = user_1_score;
    }

    public String getUser_2_display_name() {
        return user_2_display_name;
    }

    public void setUser_2_display_name(String user_2_display_name) {
        this.user_2_display_name = user_2_display_name;
    }

    public String getUser_2_score() {
        return user_2_score;
    }

    public void setUser_2_score(String user_2_score) {
        this.user_2_score = user_2_score;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}