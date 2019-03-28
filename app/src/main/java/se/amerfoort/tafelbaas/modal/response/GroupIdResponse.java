package se.amerfoort.tafelbaas.modal.response;

import com.google.gson.annotations.SerializedName;

public class GroupIdResponse {
    @SerializedName("group_id")
    private String group_id;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}