package com.vgtu.accounting.popup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vgtu.accounting.api.ApiClient;
import com.vgtu.accounting.R;
import com.vgtu.accounting.response.CategoryResponse;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPupUp extends Activity {
    CategoryResponse categoryResponse;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_pup_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.9), (int)(height*.27));


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
        setCategoryResponse();
        displayCategoryText();
    }

    private void displayCategoryText(){
        final CategoryResponse[] categoryResponse = new CategoryResponse[1];
        CategoryResponse categoryResponseSelected = this.categoryResponse;
        Call<CategoryResponse> call = ApiClient.getCategoryService().getCategory(String.valueOf(this.categoryResponse.getParentCategoryID()));

        call.enqueue(new Callback<CategoryResponse>(){
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful()){
                    categoryResponse[0] = (CategoryResponse) response.body();

                    listView = findViewById(R.id.listview);
                    MyAdapter adapter = new MyAdapter(getApplicationContext(), categoryResponseSelected.getTitle(), categoryResponseSelected.getDescription(), categoryResponse[0].getTitle());
                    listView.setAdapter(adapter);
                } else {
                    listView = findViewById(R.id.listview);
                    MyAdapter adapter = new MyAdapter(getApplicationContext(), categoryResponseSelected.getTitle(), categoryResponseSelected.getDescription(), "");
                    listView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle;
        String rDescription;
        String parentName;

        MyAdapter(Context c, String title, String description, String parentName){
            super( c,  R.layout.category_row,  R.id.title, Collections.singletonList(title));
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.parentName = parentName;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.category_view, parent, false);
            TextView myTitle = row.findViewById(R.id.title);
            TextView myDescription = row.findViewById(R.id.description);
            TextView parentName = row.findViewById(R.id.parent);
            if(!this.parentName.equals(""))
                parentName.setText("Parent: " + this.parentName);
            else
                parentName.setText("Parent category");

            myTitle.setText(rTitle);
            myDescription.setText(rDescription);

            return row;
        }
    }

    private void setCategoryResponse() {
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            categoryResponse = (CategoryResponse) intent.getSerializableExtra("data");
        }
    }
}