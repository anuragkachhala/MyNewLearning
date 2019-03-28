package se.amerfoort.tafelbaas.modal.response;

import com.google.gson.annotations.SerializedName;

public class ResultResponse {
    //succes
    @SerializedName("result")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}