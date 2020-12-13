package com.vgtu.accounting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.vgtu.accounting.api.ApiClient;
import com.vgtu.accounting.request.LoginRequest;
import com.vgtu.accounting.response.AccountingSystemResponse;
import com.vgtu.accounting.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    AccountingSystemResponse accountingSystemResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setAccountingSystem();
    }

    private void setAccountingSystem() {
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            accountingSystemResponse = (AccountingSystemResponse) intent.getSerializableExtra("data");

            Log.e("TAG", "===>>>" + accountingSystemResponse.getName());
        }
    }

    public void onBtnClick(View view){

        EditText editTextUsername = findViewById(R.id.editName);
        EditText editTextPassowrd = findViewById(R.id.editPassword);

        if(TextUtils.isEmpty(editTextUsername.getText().toString()) || TextUtils.isEmpty(editTextPassowrd.getText().toString())){
            String message = "All inputs required";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        } else {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setName(editTextUsername.getText().toString());
            loginRequest.setPassword(editTextPassowrd.getText().toString());
            loginUser(loginRequest);
        }
    }

    private void loginUser(LoginRequest loginRequest) {
        Call<UserResponse> loginResponseCall = ApiClient.getUserService().loginUser(loginRequest, String.valueOf(accountingSystemResponse.getId()));
        loginResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    UserResponse userResponse = response.body();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("data", userResponse));
                } else {
                    String message = "Wrong username or password";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

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
