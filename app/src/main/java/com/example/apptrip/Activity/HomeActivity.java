package com.example.apptrip.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import com.example.apptrip.Model.Trip;
import com.example.apptrip.R;
import com.example.apptrip.SQLite.SQLiteHelper;
import com.example.apptrip.databinding.ActivityHomeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);//Khởi tạo layout của activity
        onClickAddtoDatabase();//Hàm xử lý chức năng của button add to database
    }

    private void onClickAddtoDatabase() {
        binding.btnAddtoDatabase.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                //Khởi tạo và gán dữ liệu vào biến String khi người dùng nhập dữ liệu
                String name = binding.edtNameTrip.getText().toString().trim();
                String destination = binding.edtDestination.getText().toString().trim();
                String date = binding.edtDate.getText().toString().trim();
                String required = "";
                if (binding.radioYes.isChecked()){//Nếu người dùng click vào radioYes
                    required = "Yes";//Gán required = "Yes"
                }else if (binding.radioNo.isChecked()){//Ngược lại nếu người dùng click vào radioNo
                    required =  "No";//Gán required = "No"
                }
                String description = binding.edtDescription.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)||TextUtils.isEmpty(date) //Kiểm tra người dùng nhập đủ dữ liệu chưa
                ||TextUtils.isEmpty(destination)||TextUtils.isEmpty(required)){
                    Dialog dialog = new Dialog(HomeActivity.this);//Khởi tạo dialog (giải thích tương tự như class khác)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_confirm);
                    TextView txtContent = (TextView) dialog.findViewById(R.id.tv_content);
                    txtContent.setText("You need to fill all required fields");
                    Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }else {
                    //add to database
                    sqLiteHelper = new SQLiteHelper(HomeActivity.this,"Database.sqlite",null,1);//Khởi tạo hàm sqlite
                    sqLiteHelper.QueryData("INSERT INTO Trips VALUES(null,'" + name + "','" + //Thêm dữ liệu vào bảng Trips trong database
                            date + "','" + destination + "','" + required + "','" + description + "')");

                    Dialog dialog = new Dialog(HomeActivity.this);//Khởi tạo dialog
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_confirm);
                    TextView txtContent = (TextView) dialog.findViewById(R.id.tv_content);
                    txtContent.setText( //gán dữ liệu vào text view content để hiện thị lên dialog
                                    "Name : " +name +"\n"+
                                    "Destination :" + destination+"\n"+
                                    "Data of the Trip :"+"\n"
                                    + date+"\n"+
                                    "Risk Assessment :"+ required+"\n"+
                                    "Description :" + description
                    );
                    Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            startActivity(new Intent(HomeActivity.this,MainActivity.class)); //bắt đầu và khởi tạo intent
                        }
                    });
                    dialog.show();//hiển thị dialog
                }
            }
        });
    }
}