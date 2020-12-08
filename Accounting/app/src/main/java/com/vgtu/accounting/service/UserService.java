package com.vgtu.accounting.service;


import com.vgtu.accounting.request.LoginRequest;
import com.vgtu.accounting.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @POST("users/login/{id}")
    Call<UserResponse> loginUser(@Body LoginRequest loginRequest, @Path("id") String id);
    
}
