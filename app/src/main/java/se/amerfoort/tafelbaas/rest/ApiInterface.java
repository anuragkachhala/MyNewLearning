package se.amerfoort.tafelbaas.rest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import se.amerfoort.tafelbaas.modal.request.JoinRequest;
import se.amerfoort.tafelbaas.modal.request.UserNameRequest;
import se.amerfoort.tafelbaas.modal.response.ChallengeIdResponse;
import se.amerfoort.tafelbaas.modal.response.ChallengeResponse;
import se.amerfoort.tafelbaas.modal.response.GroupIdResponse;
import se.amerfoort.tafelbaas.modal.response.GroupResponse;
import se.amerfoort.tafelbaas.modal.response.MyGameResponse;
import se.amerfoort.tafelbaas.modal.response.PushIdResponse;
import se.amerfoort.tafelbaas.modal.response.UserIdResponse;

public interface ApiInterface {

    //POST URI
    @POST("users/")
    Call<UserIdResponse> registerUser(@Body UserNameRequest displayName);

    @FormUrlEncoded
    @POST("groups")
    Call<GroupIdResponse> creatGroups(@HeaderMap Map<String, String> headers, @Field("name") String name, @Field("description") String description);

    @FormUrlEncoded
    @POST("myGames")
    Call<String> postScore(@HeaderMap Map<String, String> headers, @Field("score_1") String score_1, @Field("user_hash_2") String user_hash_2, @Field("score_2") String score_2, @Field("group") String group);

    @FormUrlEncoded
    @POST("regPushNotification/")
    Call<PushIdResponse> postScoress(@HeaderMap Map<String, String> headers, @Field("push_UUID") String push_UUID);

    @FormUrlEncoded
    @POST("joinGroup")
    Call<String> joinGroup(@HeaderMap Map<String, String> headers, @Field("group") String group);

    @FormUrlEncoded
    @POST("challengeChangeState")
    Call<String> changeChallengeState(@HeaderMap Map<String, String> headers, @Field("state") String state, @Field("challenge") String challenge);

    @FormUrlEncoded
    @POST("challenges")
    Call<ChallengeIdResponse> challenge(@HeaderMap Map<String, String> headers, @Field("opponent") String opponent, @Field("group") String group, @Field("auto_match") String auto_match);

    //GET URI
    @GET("groups")
    Call<List<GroupResponse>> getGroups(@HeaderMap Map<String, String> headers);

    @GET("challenges")
    Call<List<ChallengeResponse>> getChallenges(@HeaderMap Map<String, String> headers);

    @GET("myGames")
    Call<List<MyGameResponse>> getMyGames(@HeaderMap Map<String, String> headers);
}