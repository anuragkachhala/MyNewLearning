package se.amerfoort.tafelbaas.modal.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by skushwa1 on 10/30/2017.
 */
public class GroupScore {
    @SerializedName("score_id")
    private String score_id;
    @SerializedName("score")
    private String score;
    @SerializedName("group")
    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore_id() {
        return score_id;
    }

    public void setScore_id(String score_id) {
        this.score_id = score_id;
    }

}
