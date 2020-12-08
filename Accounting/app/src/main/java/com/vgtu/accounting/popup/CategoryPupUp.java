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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vgtu.accounting.api.ApiClient;
import com.vgtu.accounting.R;
import com.vgtu.accounting.response.CategoryResponse;
import com.vgtu.accounting.response.ExpenseResponse;
import com.vgtu.accounting.response.IncomeResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPupUp extends Activity {
    CategoryResponse categoryResponse;
    ListView listView;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_pup_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int)(height*.89));


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
        setCategoryResponse();
        displayCategoryText();

        btnClose = (Button) findViewById(R.id.closeBtn);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

                    TextView title = findViewById(R.id.title);
                    title.setText(categoryResponseSelected.getTitle());
                    TextView description = findViewById(R.id.description);
                    description.setText(categoryResponseSelected.getDescription());
                    TextView parent = findViewById(R.id.parent);
                    parent.setText("Parent category:" + categoryResponse[0].getTitle());
                } else {
                    //____________
                    //If parent category name not found then it has no parent
                    //____________
                    TextView title = findViewById(R.id.title);
                    title.setText(categoryResponseSelected.getTitle());
                    TextView description = findViewById(R.id.description);
                    description.setText(categoryResponseSelected.getDescription());
                    TextView parent = findViewById(R.id.parent);
                    parent.setText("");
                }

            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
        List<String> incomeTitles = new ArrayList<>();
        List<String> incomeAmounts = new ArrayList<>();
        List<String> incomeDates = new ArrayList<>();
        for(IncomeResponse income: categoryResponseSelected.getIncomes()){
            incomeTitles.add(income.getName());
            incomeAmounts.add(String.valueOf(income.getAmount()));
            incomeDates.add(income.getCreationDate());
        }

        listView = findViewById(R.id.incomes);
        IncomeExpenseListAdapter incomeAdapter = new IncomeExpenseListAdapter(getApplicationContext(), incomeTitles, incomeAmounts, incomeDates);
        listView.setAdapter(incomeAdapter);

        List<String> expenseTitles = new ArrayList<>();
        List<String> expenseAmounts = new ArrayList<>();
        List<String> expenseDates = new ArrayList<>();

        for(ExpenseResponse expense: categoryResponseSelected.getExpenses()){
            expenseTitles.add(expense.getName());
            expenseAmounts.add(String.valueOf(expense.getAmount()));
            expenseDates.add(expense.getCreationDate());
        }

        listView = findViewById(R.id.expenses);
        IncomeExpenseListAdapter expenseAdapter = new IncomeExpenseListAdapter(getApplicationContext(), expenseTitles, expenseAmounts, expenseDates);
        listView.setAdapter(expenseAdapter);
    }

    class IncomeExpenseListAdapter extends ArrayAdapter<String>{
        Context context;
        List<String> rTitle;
        List<String> amounts;
        List<String> creationDates;

        IncomeExpenseListAdapter(Context c, List<String> titles, List<String> amounts, List<String> creationDates){
            super( c,  R.layout.category_row,  R.id.name,  titles);
            this.context = c;
            this.rTitle = titles;
            this.amounts = amounts;
            this.creationDates = creationDates;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.income_expense_view, parent, false);

            TextView titleView = row.findViewById(R.id.name);
            titleView.setText(rTitle.get(position));
            TextView amountView = row.findViewById(R.id.amount);
            amountView.setText(amounts.get(position) + " eur");
            TextView creationDateView = row.findViewById(R.id.creationDate);
            creationDateView.setText(creationDates.get(position));
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