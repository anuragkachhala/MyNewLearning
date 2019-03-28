package se.amerfoort.tafelbaas.modal.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by skushwa1 on 11/1/2017.
 */
public class UserDetail {
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("name")
    private String name;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
