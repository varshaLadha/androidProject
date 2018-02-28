package com.example.lcom151_two.retrofituser;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/user")
    Call<InsertUserResponseModel> user(@Field("fname") String fname,
                                             @Field("lname") String lname,
                                             @Field("email") String email,
                                             @Field("password") String password,
                                             @Field("mobile") String mobile,
                                             @Field("sampleFile") String sampleFile);

    @GET("api/user")
    Call<GetUsersResponseModel> getUsers();

    @GET("api/user/{username}")
    Call<GetUsersResponseModel> getUser(@Path("username") String username);

    @DELETE("api/user/{name}")
    Call<DeleteUserResponseModel> delUSer(@Path("name") String name);

    @FormUrlEncoded
    @PUT("api/user/{name}")
    Call<DeleteUserResponseModel> updateUser(@Path("name") String name,
                                             @Field("lname") String lname,
                                             @Field("mobile") String mobile,
                                             @Field("fname") String fname,
                                             @Field("password") String password);


}
