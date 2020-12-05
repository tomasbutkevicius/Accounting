package com.vgtu.accounting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.vgtu.accounting.response.AccountingSystemResponse;

public class AccountingPopUp extends Activity {
    AccountingSystemResponse accountingSystemResponse;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_accounting);
        setAccounting();
        btnClose = (Button) findViewById(R.id.closeBtn);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int)(height*.7));


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }

    private void setAccounting() {
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            setTextFields(intent);
        }
    }

    private void setTextFields(Intent intent) {
        accountingSystemResponse = (AccountingSystemResponse) intent.getSerializableExtra("data");
        TextView systemNameView = findViewById(R.id.systemName);
        systemNameView.append(accountingSystemResponse.getName());
        TextView incomeText = findViewById(R.id.incomeText);
        incomeText.append(String.valueOf(accountingSystemResponse.getIncome()));
        TextView expenseText = findViewById(R.id.expenseText);
        expenseText.append(String.valueOf(accountingSystemResponse.getExpense()));
        TextView version = findViewById(R.id.systemVersion);
        version.append(String.valueOf(accountingSystemResponse.getSystemVersion()));
        TextView date = findViewById(R.id.creationDate);
        date.append(String.valueOf(accountingSystemResponse.getSystemCreationDate()));
    }
}
