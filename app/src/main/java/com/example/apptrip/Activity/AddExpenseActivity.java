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
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_expense); //Khởi tạo layout của activity
        sqLiteHelper = new SQLiteHelper(AddExpenseActivity.this,"Database.sqlite",null,1); //Khởi tạo hàm SQLite
        customSpinnerType(); //Thiết kế và gán dữ liệu của Spinner
        customBtnAddExpenseToDatabase(); //Hàm xử lý chức năng add dữ liệu từ button AddExpense vào database
    }

    private void customBtnAddExpenseToDatabase() {
        binding.btnAddExpenseDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Khởi tạo dữ liệu khi người dùng nhập và tương tác
                String type = binding.spinnerType.getSelectedItem().toString();
                String amount = binding.edtAmount.getText().toString();
                String timeOf = binding.edtTimeof.getText().toString();
                if (TextUtils.isEmpty(type)||TextUtils.isEmpty(amount)||TextUtils.isEmpty(timeOf)){ //Kiểm tra dữ liệu người dùng đã nhập đủ dữ liệu chưa
                    Toast.makeText(AddExpenseActivity.this,"Fill all required fields !!!",Toast.LENGTH_SHORT).show(); //Nếu chưa nhập đủ sẽ hiển thị toast lên thông báo
                }else {
                    int amounts = Integer.parseInt(amount); //Convert amount từ String sang Int
                    if (amounts < 0){ //Kiểm tra nếu amount < 0
                        Toast.makeText(AddExpenseActivity.this,"Giá tiền không được < 0",Toast.LENGTH_SHORT).show();//Hiển thị toast thông báo
                    }else { //Ngược lại nếu amount > 0
                        //Add Expense to Database
                        sqLiteHelper.QueryData("INSERT INTO Expense VALUES(null,'" + Utils.ID_TRIP + "','" + type + "','" +
                                amount + "','" + timeOf + "')");//Thêm dữ liệu người dùng nhập vào trong bảng Expense trong database
                        Toast.makeText(AddExpenseActivity.this,"Expense Added Successfully !!!",Toast.LENGTH_SHORT).show();//Hiển thị toast thêm thành công
                        finish(); //Kết thúc activity
                    }

                }
            }
        });
    }

    private void customSpinnerType() {
        String[] typeData = {"Travel" , "Food" , "Transport","Entertainment"}; //Khởi tạo dữ liệu cho mảng
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,typeData);//Khởi tạo adapter để hiển thị dữ liệu
        binding.spinnerType.setAdapter(adapter); //gán dữ liệu vào spinner
    }
}