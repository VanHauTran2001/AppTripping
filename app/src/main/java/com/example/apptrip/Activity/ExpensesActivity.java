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
        binding = DataBindingUtil.setContentView(this,R.layout.activity_expenses); //Khởi tạo layout của activity
        Intent intent = getIntent(); //Khởi tạo intent
        idTrip = intent.getIntExtra("idTrip",0); //Nhận dữ liệu id thông qua intent
        sqLiteHelper = new SQLiteHelper(ExpensesActivity.this,"Database.sqlite",null,1); //Khởi tạo hàm SQLite
        expensesArrayList = new ArrayList<>(); //Khở tạo list
        getDatabaseExpense();//Hàm này là hàm hiển thị dữ liệu của Expense từ database theo idTrip
        checkThongBao(); //Hàm này là kiểm tra dữ liệu của list
        intitRecylerExpense(); //Khởi tạo adapter và recyclerview
        onClickAddExpense();//Hàm xử lý sự kiện button add expense
    }

    private void checkThongBao() {
        if (expensesArrayList.size()==0){ //Nếu mà list ko có dữ liệu
            binding.txtThongBao.setVisibility(View.VISIBLE); //Hiển thị cái text view để thông báo cho người dùng
        }else {
            binding.txtThongBao.setVisibility(View.GONE); // Ngược lại nếu list có dữ liệu thì ẩn cái text view đó
        }
    }

    private void getDatabaseExpense() {
        //hiển thị dữ liệu của Expense từ database theo idTrip
        expensesArrayList.clear(); //Mỗi lần hiển thị sẽ clear list trước để khi có thay đổi nó sẽ cập nhật luôn dữ liệu của bảng đó
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM Expense WHERE IdTrip ='" + idTrip + "' ");//Lấy dữ liệu của bảng Expense theo IdTrip
        while (cursor.moveToNext()){
            int idExpense = cursor.getInt(0);//Gán dữ liệu từng cột
            int idTrip = cursor.getInt(1);
            String type = cursor.getString(2);
            String amount = cursor.getString(3);
            String timeof = cursor.getString(4);
            expensesArrayList.add(new Expenses(idExpense,idTrip,type,amount,timeof)); //Thêm dữ liệu vào list
        }
    }

    private void onClickAddExpense() {
        binding.btnAddExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpensesActivity.this,AddExpenseActivity.class)); //Chuyển màn hình từ ExpenseActivity sang màn hình AddExpenseActivity
            }
        });
    }

    private void intitRecylerExpense() {
        adapter = new ExpenseAdapter(this); //Khởi tạo adapter
        binding.recylerExpenses.setLayoutManager(new LinearLayoutManager(this)); //set RecyclerView hiển thị theo chiều dọc
        binding.recylerExpenses.setAdapter(adapter); //set adapter để hiển thị dữ liệu lên RecyclerView
    }

    @Override
    public int getCount() {
        return expensesArrayList.size();
    } //Hàm trả về số lượng trong list

    @Override
    public Expenses getListExpense(int position) { //Hàm lấy giá trị trong list theo vị trí khi người dùng click
        return expensesArrayList.get(position);
    }


    @Override
    protected void onResume() {//Khi người dùng click nút add sẽ chuyển sang màn hình add và người dùng quay lại activity cũ thì sẽ
        super.onResume();       //gọi đến callback onResume() lúc này nhiệm vụ của hàm này sẽ set lại dữ liệu
        getDatabaseExpense();
        adapter.notifyDataSetChanged();
    }
}