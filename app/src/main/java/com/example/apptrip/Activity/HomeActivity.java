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
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        onClickAddtoDatabase();
    }

    private void onClickAddtoDatabase() {
        binding.btnAddtoDatabase.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String name = binding.edtNameTrip.getText().toString().trim();
                String destination = binding.edtDestination.getText().toString().trim();
                String date = binding.edtDate.getText().toString().trim();
                String required = "";
                if (binding.radioYes.isChecked()){
                    required = "Yes";
                }else if (binding.radioNo.isChecked()){
                    required =  "No";
                }
                String description = binding.edtDescription.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)||TextUtils.isEmpty(date)
                ||TextUtils.isEmpty(destination)||TextUtils.isEmpty(required)){
                    Dialog dialog = new Dialog(HomeActivity.this);
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
                    sqLiteHelper = new SQLiteHelper(HomeActivity.this,"Database.sqlite",null,1);
                    sqLiteHelper.QueryData("INSERT INTO Trips VALUES(null,'" + name + "','" +
                            date + "','" + destination + "','" + required + "','" + description + "')");

                    Dialog dialog = new Dialog(HomeActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_confirm);
                    TextView txtContent = (TextView) dialog.findViewById(R.id.tv_content);
                    txtContent.setText(
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
                            startActivity(new Intent(HomeActivity.this,MainActivity.class));
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
}