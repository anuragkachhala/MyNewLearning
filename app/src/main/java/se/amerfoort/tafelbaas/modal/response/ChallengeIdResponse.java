package se.amerfoort.tafelbaas.modal.response;

import com.google.gson.annotations.SerializedName;

public class ChallengeIdResponse {
    @SerializedName("challenge_id")
    private String challenge_id;

    public String getChallenge_id() {
        return challenge_id;
    }

    public void setChallenge_id(String challenge_id) {
        this.challenge_id = challenge_id;
    }
}