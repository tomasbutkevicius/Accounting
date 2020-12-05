package com.vgtu.accounting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vgtu.accounting.response.AccountingSystemResponse;
import com.vgtu.accounting.response.UserResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    UserResponse userResponse;
    AccountingSystemResponse accountingSystemResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUser();
        setAccountingSystem();
    }

    public void myCategoriesBtnClick(View view){
        startActivity(new Intent(MainActivity.this, CategoriesActivity.class).putExtra("data", userResponse));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setUser() {
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            userResponse = (UserResponse) intent.getSerializableExtra("data");

            Log.e("TAG", "===>>>" + userResponse.getName());
        }
    }

    private void setAccountingSystem(){
        Call<AccountingSystemResponse> call = ApiClient.getAccountingService().getSystem(String.valueOf(userResponse.getAccountingSystemID()));

        call.enqueue(new Callback<AccountingSystemResponse>(){
            @Override
            public void onResponse(Call<AccountingSystemResponse> call, Response<AccountingSystemResponse> response) {
                if(response.isSuccessful()){
                    accountingSystemResponse = response.body();
                    TextView systemNameView = findViewById(R.id.systemName);
                    systemNameView.append(accountingSystemResponse.getName());
                    TextView incomeText = findViewById(R.id.incomeText);
                    incomeText.append(String.valueOf(accountingSystemResponse.getIncome()));
                    TextView expenseText = findViewById(R.id.expenseText);
                    expenseText.append(String.valueOf(accountingSystemResponse.getExpense()));
                } else {
                    String message = "Error occurred";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AccountingSystemResponse> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

            }
        });
    }
}
