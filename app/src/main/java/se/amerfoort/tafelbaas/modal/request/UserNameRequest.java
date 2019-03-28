package se.amerfoort.tafelbaas.modal.request;

import com.google.gson.annotations.SerializedName;

public class UserNameRequest {
    @SerializedName("display_name")
    private String display_name;

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}