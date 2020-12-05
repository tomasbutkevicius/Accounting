package com.vgtu.accounting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vgtu.accounting.response.CategoryResponse;
import com.vgtu.accounting.response.UserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {
    UserResponse userResponse;
    List<CategoryResponse> categories;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_activity);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setTitle("My categories");
        setUser();
        setCategoryList();
    }

    private void setUser() {
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            userResponse = (UserResponse) intent.getSerializableExtra("data");

            Log.e("TAG", "===>>>" + userResponse.getName());
        }
    }

    private void setCategoryList(){
        Call<List<CategoryResponse>> call = ApiClient.getCategoryService().getCategories(String.valueOf(userResponse.getId()));

        call.enqueue(new Callback<List<CategoryResponse>>(){
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                if(response.isSuccessful()){
                    categories = response.body();
                    listView = findViewById(R.id.listview);
                    ArrayList<String> arrayList = new ArrayList<>();

                    for(CategoryResponse category: categories)
                        arrayList.add(category.getTitle());

                    ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.row, arrayList);

                    listView.setAdapter(arrayAdapter);

                } else {
                    String message = "Error occurred";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

