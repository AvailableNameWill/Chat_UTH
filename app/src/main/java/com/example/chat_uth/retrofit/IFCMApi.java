package com.example.chat_uth.retrofit;

import com.example.chat_uth.models.FCMBody;
import com.example.chat_uth.models.FCMResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAqdBYpTQ:APA91bGcPkoNnAlc9CQv9UOA8SdkPhIEj07HWV0whJX0t4Ch1lYa615ZZnhL-E3uSDcmBTlAvl5MnspckGzxP16g96gGhjklPXTtz27m1T7T1Kqyp719NDvwTiIiogQUV4-SkjrsdkZQ"
    })
    @POST("fcm/send")

    Call<FCMResponse> send(@Body FCMBody body);
}
