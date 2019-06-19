package com.zo0okadev.demologinapp.model;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/api/login")
    Call<LoginResponse> getResponse(@Query("email") String email, @Query("password") String password);
}
