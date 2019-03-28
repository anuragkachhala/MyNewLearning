package se.amerfoort.tafelbaas.modal.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by skushwa1 on 10/30/2017.
 */
public class Group {

    @SerializedName("hash")
    private String hash;

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
