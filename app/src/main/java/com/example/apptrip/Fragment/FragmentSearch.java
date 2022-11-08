package com.example.apptrip.Fragment;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.apptrip.Activity.EditTripActivity;
import com.example.apptrip.Adapter.TripAdapter;
import com.example.apptrip.Model.Trip;
import com.example.apptrip.R;
import com.example.apptrip.SQLite.SQLiteHelper;
import com.example.apptrip.databinding.FragmentSearchBinding;

import java.util.ArrayList;

public class FragmentSearch extends Fragment implements TripAdapter.IListTrip{
    private FragmentSearchBinding binding;
    private SQLiteHelper sqLiteHelper;
    private ArrayList<Trip> tripArrayList;
    private TripAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater,container,false); //khởi tạo layout của fragment
        sqLiteHelper = new SQLiteHelper(getContext(),"Database.sqlite",null,1);//khởi tạo hàm gọi đến qlite
        tripArrayList = new ArrayList<>();//khởi tạo list
        CustumSearch();//xử lý sự kiện button search
        return binding.getRoot();
    }

    private void intitDataRecylerView() {
        adapter = new TripAdapter(this);//khởi tạo adapter
        binding.recyLerSearch.setLayoutManager(new LinearLayoutManager(getContext()));//set recyclerview hiển thị theo chiều dọc
        binding.recyLerSearch.setAdapter(adapter); //set adapter để hiển thị dữ liệu lên recyclerview
    }

    private void CustumSearch() {
        binding.btnSearchTrip.setOnClickListener(new View.OnClickListener() {//bắt sự kiện khi người dùng click vào button search
            @Override
            public void onClick(View view) {
                String nameSearch = binding.edtSearch.getText().toString().trim();//Gán dữ liệu vào String khi người dùng nhập vào edittext
                if (TextUtils.isEmpty(nameSearch)){ //Kiểm tra người dùng nhập dữ liệu chưa
                    Toast.makeText(getContext(),"Fill all required fields",Toast.LENGTH_SHORT).show();//Nếu dữ liệu trông sẽ thông báo
                }else {//ngược lại
                    //add to database
                    tripArrayList.clear();//xóa dữ liệu trong list
                    Cursor cursor = sqLiteHelper.getData("SELECT * FROM Trips WHERE NameTrip = '"+nameSearch+ "' ");//hiển thị dữ liệu bảng Trip theo Id
                    while (cursor.moveToNext()){
                        int idTrip = cursor.getInt(0);//lấy dữ liệu theo từng cột
                        String name = cursor.getString(1);
                        String date = cursor.getString(2);
                        String locate = cursor.getString(3);
                        String required = cursor.getString(4);
                        String description = cursor.getString(5);
                        tripArrayList.add(new Trip(idTrip,name,date,locate,required,description));//gán dữ liệu vào list
                    }
                    intitDataRecylerView();//gọi hàm để hiển thị dữ liệu lên recyclerview
                }
            }
        });
    }

    @Override
    public int getCount() {
        return tripArrayList.size();
    }//hàm trả về số lượng phần tử trong list

    @Override
    public Trip getListTrip(int position) {//hàm lấy giá trị trong list tại vị trí
        return tripArrayList.get(position);
    }

    @Override
    public void onClickItem(int position) {

    }
}
