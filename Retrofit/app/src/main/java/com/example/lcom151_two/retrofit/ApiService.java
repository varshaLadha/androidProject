package com.example.lcom151_two.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("/insertUser")
    Call<InsertUserResponseModel> insertUser(@Field("username") String username, @Field("password") String password);
}
