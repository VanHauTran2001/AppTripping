package com.example.apptrip.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptrip.Adapter.TripAdapter;
import com.example.apptrip.Fragment.FragmentSearch;
import com.example.apptrip.Fragment.FragmentTrip;
import com.example.apptrip.Fragment.FragmentUpload;
import com.example.apptrip.Model.Trip;
import com.example.apptrip.R;
import com.example.apptrip.SQLite.SQLiteHelper;
import com.example.apptrip.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;
    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initSQLite();
        replaceFragment(new FragmentTrip());
        onClickDeleteTrip();
        onClickButtonMenu();
    }

    private void onClickButtonMenu() {
        binding.btnTrip.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentTrip());
                binding.btnTrip.setBackgroundColor(Color.parseColor("#00BCD4"));
                binding.btnUpload.setBackgroundColor(Color.parseColor("#E1DFDF"));
                binding.btnSearch.setBackgroundColor(Color.parseColor("#E1DFDF"));
                binding.txtTrip.setTextColor(Color.parseColor("#FFFFFFFF"));
                binding.txtSearch.setTextColor(Color.parseColor("#FF000000"));
                binding.txtUpload.setTextColor(Color.parseColor("#FF000000"));
                binding.txtProfileTitle.setText("All Trips");
                binding.imgDelete.setVisibility(View.VISIBLE);
            }
        });
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentSearch());
                binding.btnSearch.setBackgroundColor(Color.parseColor("#00BCD4"));
                binding.btnTrip.setBackgroundColor(Color.parseColor("#E1DFDF"));
                binding.btnUpload.setBackgroundColor(Color.parseColor("#E1DFDF"));
                binding.txtSearch.setTextColor(Color.parseColor("#FFFFFFFF"));
                binding.txtTrip.setTextColor(Color.parseColor("#FF000000"));
                binding.txtUpload.setTextColor(Color.parseColor("#FF000000"));
                binding.txtProfileTitle.setText("Search");
                binding.imgDelete.setVisibility(View.GONE);
            }
        });
        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentUpload());
                binding.btnUpload.setBackgroundColor(Color.parseColor("#00BCD4"));
                binding.btnTrip.setBackgroundColor(Color.parseColor("#E1DFDF"));
                binding.btnSearch.setBackgroundColor(Color.parseColor("#E1DFDF"));
                binding.txtUpload.setTextColor(Color.parseColor("#FFFFFFFF"));
                binding.txtTrip.setTextColor(Color.parseColor("#FF000000"));
                binding.txtSearch.setTextColor(Color.parseColor("#FF000000"));
                binding.txtProfileTitle.setText("Upload to Cloud Service");
                binding.imgDelete.setVisibility(View.GONE);
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame,fragment,Fragment.class.getName())
                .commit();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void onClickDeleteTrip() {
        binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_delete);
                TextView txtContent = (TextView) dialog.findViewById(R.id.tv_content);
                txtContent.setText("Do you want to delete all Trips ?");
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sqLiteHelper.QueryData("DELETE FROM Trips");
                        replaceFragment(new FragmentTrip());
                        Toast.makeText(MainActivity.this,"Delete Success !!!",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }





    private void initSQLite() {
        sqLiteHelper = new SQLiteHelper(MainActivity.this,"Database.sqlite",null,1);
        sqLiteHelper.QueryData("CREATE TABLE IF NOT EXISTS Trips(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NameTrip NVARCHAR(100)," +
                "DateTrip NVARCHAR(100)," +
                "LocateTrip NVARCHAR(100)," +
                "RequiredTrip NVARCHAR(50)," +
                "DescriptionTrip VARCHAR(100))");
        sqLiteHelper.QueryData("CREATE TABLE IF NOT EXISTS Expense(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "IdTrip INTEGER," +
                "Type NVARCHAR(100)," +
                "Amount NVARCHAR(100)," +
                "TimeOf VARCHAR(100))");
    }
}