package com.example.lcom151_two.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by lcom151-two on 2/26/2018.
 */

public interface Api {

    String baseUrl ="https://simplifiedcoding.net/demos/";

    @GET("marvel")
    Call<List<Hero>> getHeroes();
}
