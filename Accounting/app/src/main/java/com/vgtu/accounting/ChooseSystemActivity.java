package com.vgtu.accounting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.vgtu.accounting.api.ApiClient;
import com.vgtu.accounting.response.AccountingSystemResponse;
import com.vgtu.accounting.response.UserResponse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseSystemActivity extends AppCompatActivity {
    UserResponse userResponse;
    List<AccountingSystemResponse> systems;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_system);
        getSupportActionBar().setDisplayShowCustomEnabled(true);


        setSystemList();
    }

    private void setSystemList() {
        Call<List<AccountingSystemResponse>> call = ApiClient.getAccountingService().getSystems();

        call.enqueue(new Callback<List<AccountingSystemResponse>>() {
            @Override
            public void onResponse(Call<List<AccountingSystemResponse>> call, Response<List<AccountingSystemResponse>> response) {
                if (response.isSuccessful()) {
                    systems = response.body();
                    listView = findViewById(R.id.systems);
                    ArrayList<String> titles = new ArrayList<>();
                    ArrayList<String> dates = new ArrayList<>();

                    for (AccountingSystemResponse accountingSystemResponse : systems) {
                        titles.add(accountingSystemResponse.getName());
                        dates.add(accountingSystemResponse.getSystemCreationDate());
                    }

                    ChooseSystemActivity.MyAdapter adapter = new ChooseSystemActivity.MyAdapter(getApplicationContext(), titles, dates);

                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            startActivity(new Intent(ChooseSystemActivity.this, LoginActivity.class).putExtra("data", systems.get(position)));
                        }
                    });

                } else {
                    String message = "Error occurred";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AccountingSystemResponse>> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

            }
        });
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> titles;
        ArrayList<String> dates;

        MyAdapter(Context c, ArrayList<String> titles, ArrayList<String> dates) {
            super(c, R.layout.category_row, R.id.title, titles);
            this.context = c;
            this.titles = titles;
            this.dates = dates;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.category_row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.title);

            images.setImageResource(0);
            myTitle.setText(titles.get(position));

            return row;
        }
    }
}