package com.vgtu.accounting.service;


import com.vgtu.accounting.request.LoginRequest;
import com.vgtu.accounting.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("users/login")
    Call<UserResponse> loginUser(@Body LoginRequest loginRequest);
    
}
