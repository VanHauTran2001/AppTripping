package com.example.apptrip.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.example.apptrip.Adapter.ExpenseAdapter;
import com.example.apptrip.Adapter.TripAdapter;
import com.example.apptrip.Model.Expenses;
import com.example.apptrip.Model.Trip;
import com.example.apptrip.R;
import com.example.apptrip.SQLite.SQLiteHelper;
import com.example.apptrip.databinding.ActivityExpensesBinding;

import java.util.ArrayList;

public class ExpensesActivity extends AppCompatActivity implements ExpenseAdapter.IExpense {
    private ActivityExpensesBinding binding;
    private ArrayList<Expenses> expensesArrayList;
    private ExpenseAdapter adapter;
    private SQLiteHelper sqLiteHelper;
    private int idTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_expenses);
        Intent intent = getIntent();
        idTrip = intent.getIntExtra("idTrip",0);
        sqLiteHelper = new SQLiteHelper(ExpensesActivity.this,"Database.sqlite",null,1);
        expensesArrayList = new ArrayList<>();
        getDatabaseExpense();
        checkThongBao();
        intitRecylerExpense();
        onClickAddExpense();
    }

    private void checkThongBao() {
        if (expensesArrayList.size()==0){
            binding.txtThongBao.setVisibility(View.VISIBLE);
        }else {
            binding.txtThongBao.setVisibility(View.GONE);
        }
    }

    private void getDatabaseExpense() {

        expensesArrayList.clear();
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM Expense WHERE IdTrip ='" + idTrip + "' ");
        while (cursor.moveToNext()){
            int idExpense = cursor.getInt(0);
            int idTrip = cursor.getInt(1);
            String type = cursor.getString(2);
            String amount = cursor.getString(3);
            String timeof = cursor.getString(4);
            expensesArrayList.add(new Expenses(idExpense,idTrip,type,amount,timeof));
        }
    }

    private void onClickAddExpense() {
        binding.btnAddExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ExpensesActivity.this,AddExpenseActivity.class);
//                intent.putExtra("TripId",idTrip);
                startActivity(new Intent(ExpensesActivity.this,AddExpenseActivity.class));
            }
        });
    }

    private void intitRecylerExpense() {
        adapter = new ExpenseAdapter(this);
        binding.recylerExpenses.setLayoutManager(new LinearLayoutManager(this));
        binding.recylerExpenses.setAdapter(adapter);
    }

    @Override
    public int getCount() {
        return expensesArrayList.size();
    }

    @Override
    public Expenses getListExpense(int position) {
        return expensesArrayList.get(position);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getDatabaseExpense();
        adapter.notifyDataSetChanged();
    }
}