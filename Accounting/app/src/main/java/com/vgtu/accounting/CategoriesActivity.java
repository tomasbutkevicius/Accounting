package com.vgtu.accounting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
                    ArrayList<String> titles = new ArrayList<>();
                    ArrayList<String> descriptions = new ArrayList<>();
                    ArrayList<Boolean> isParent = new ArrayList<>();

                    for(CategoryResponse category: categories){
                        titles.add(category.getTitle());
                        descriptions.add(category.getDescription());
                        if(category.getParentCategoryID() != 0){
                            isParent.add(false);
                        } else
                            isParent.add(true);
                    }

                    MyAdapter adapter = new MyAdapter(getApplicationContext(), titles, descriptions, isParent);

                    listView.setAdapter(adapter);

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

    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        ArrayList<String> rTitle;
        ArrayList<String> rDescription;
        ArrayList<Boolean> isParent;

        MyAdapter(Context c, ArrayList<String> titles, ArrayList<String> descriptions, ArrayList<Boolean> isParent){
            super( c,  R.layout.category_row,  R.id.title,  titles);
            this.context = c;
            this.rTitle = titles;
            this.rDescription = descriptions;
            this.isParent = isParent;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.category_row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.title);
            TextView myDescription = row.findViewById(R.id.subtitle);

            if(!isParent.get(position))
                images.setImageResource(0);
            myTitle.setText(rTitle.get(position));
            myDescription.setText(rDescription.get(position));

            return row;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

