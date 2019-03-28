package se.amerfoort.tafelbaas.modal.request;

import com.google.gson.annotations.SerializedName;

public class JoinRequest {
    @SerializedName("group")
    private String group;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}