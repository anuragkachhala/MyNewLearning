package se.amerfoort.tafelbaas.modal.response;

import com.google.gson.annotations.SerializedName;

public class PushIdResponse {
    //succes
    @SerializedName("push_UUID")
    private String push_UUID;

    public String getPush_UUID() {
        return push_UUID;
    }

    public void setPush_UUID(String push_UUID) {
        this.push_UUID = push_UUID;
    }
}