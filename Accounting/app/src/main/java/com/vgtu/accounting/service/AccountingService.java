package com.vgtu.accounting.service;
import com.vgtu.accounting.response.AccountingSystemResponse;
import com.vgtu.accounting.response.CategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountingService {

    @GET("systems/{id}")
    Call<AccountingSystemResponse> getSystem(@Path("id") String id);

    @GET("systems/{id}/categories")
    Call<List<CategoryResponse>> getSystemCategories(@Path("id") String id);

}
