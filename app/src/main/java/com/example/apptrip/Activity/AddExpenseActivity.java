package com.example.apptrip.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptrip.R;
import com.example.apptrip.SQLite.SQLiteHelper;
import com.example.apptrip.Utils.Utils;
import com.example.apptrip.databinding.ActivityAddExpenseBinding;

public class AddExpenseActivity extends AppCompatActivity {
    private ActivityAddExpenseBinding binding;
    private SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_expense);
        sqLiteHelper = new SQLiteHelper(AddExpenseActivity.this,"Database.sqlite",null,1);
        customSpinnerType();
        customBtnAddExpenseToDatabase();
    }

    private void customBtnAddExpenseToDatabase() {
        binding.btnAddExpenseDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = binding.spinnerType.getSelectedItem().toString();
                String amount = binding.edtAmount.getText().toString();
                String timeOf = binding.edtTimeof.getText().toString();
                if (TextUtils.isEmpty(type)||TextUtils.isEmpty(amount)||TextUtils.isEmpty(timeOf)){
                    Toast.makeText(AddExpenseActivity.this,"Fill all required fields !!!",Toast.LENGTH_SHORT).show();
                }else {
                    //Add Expense to Database
                    sqLiteHelper.QueryData("INSERT INTO Expense VALUES(null,'" + Utils.ID_TRIP + "','" + type + "','" +
                            amount + "','" + timeOf + "')");
                    Toast.makeText(AddExpenseActivity.this,"Expense Added Successfully !!!",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void customSpinnerType() {
        String[] typeData = {"Travel" , "Food" , "Transport","Entertainment"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,typeData);
        binding.spinnerType.setAdapter(adapter);
    }
}