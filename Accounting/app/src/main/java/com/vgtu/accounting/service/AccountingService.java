package com.vgtu.accounting.service;
import com.vgtu.accounting.response.AccountingSystemResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountingService {

    @GET("systems/{id}")
    Call<AccountingSystemResponse> getSystem(@Path("id") String id);


}
