package com.vgtu.accounting;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("users/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);


}
