package com.example.apptrip.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.apptrip.Activity.EditTripActivity;
import com.example.apptrip.Activity.HomeActivity;
import com.example.apptrip.Adapter.TripAdapter;
import com.example.apptrip.Model.Trip;
import com.example.apptrip.SQLite.SQLiteHelper;
import com.example.apptrip.databinding.FragmentTripBinding;

import java.util.ArrayList;

public class FragmentTrip extends Fragment implements TripAdapter.IListTrip{
    public static final String TAG_Trip = FragmentTrip.class.getName();
    private ArrayList<Trip> tripArrayList;
    private TripAdapter adapter;
    private SQLiteHelper sqLiteHelper;
    private int idTrip;
    private FragmentTripBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTripBinding.inflate(inflater,container,false);//khởi tạo layout
        tripArrayList = new ArrayList<>(); //khởi tạo list
        sqLiteHelper = new SQLiteHelper(getContext(),"Database.sqlite",null,1); //khởi tạo dữ liệu sqlite
        getDataTrip(); //hàm hiển thị dữ liệu từ sqlite
        onClickAdd(); //hàm xử lý sự kiện khi người dùng click vào button add
        initRecyler(); //hàm hiển thị dữ liệu lên recyclerview
        checkThongBao(); //hàm kiểm tra và hiển thị thông báo
        return binding.getRoot();
    }

    private void getDataTrip() {
        tripArrayList.clear(); //xóa dữ liệu của list
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM Trips"); //hiển thị dữ liệu bảng Trips trong database
        while (cursor.moveToNext()){
            idTrip = cursor.getInt(0); //lấy dữ liệu từng cột
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            String locate = cursor.getString(3);
            String required = cursor.getString(4);
            String description = cursor.getString(5);
            tripArrayList.add(new Trip(idTrip,name,date,locate,required,description));//gán dữ liệu vào list
        }
    }
    private void onClickAdd() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {//xử lý sự kiện khi người dùng click vào button add
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HomeActivity.class));//chuyển màn hình từ Fragment này sang HomeActivity
            }
        });
    }

    private void checkThongBao() {//hàm kiểm tra
        if (tripArrayList.size()==0){ //Nếu list không có dữ liệu
            binding.txtThongBao.setVisibility(View.VISIBLE); //hiển thị text view thông báo
        }else { //ngược lại list có dữ liệu
            binding.txtThongBao.setVisibility(View.GONE); //ẩn text view thông báo
        }
    }

    private void initRecyler() {
        adapter = new TripAdapter(this);//khởi tạo adapter
        binding.recylerTrip.setLayoutManager(new LinearLayoutManager(getContext()));//set recyclerview hiển thị theo chiều dọc
        binding.recylerTrip.setAdapter(adapter); //set adapter để hiển thị lên recyclerview
    }

    @Override
    public int getCount() {
        return tripArrayList.size();
    } //trả về số lượng của list

    @Override
    public Trip getListTrip(int position) {//lấy dữ liệu của list theo vị trí
        return tripArrayList.get(position);
    }

    @Override
    public void onClickItem(int position) {//xử lý khi người dùng click vào dữ liệu trên recyclerview
        Trip trip = tripArrayList.get(position); //khởi tạo class trip tại vị trí khi người dùng click
        Intent intent = new Intent(getContext(), EditTripActivity.class);//khởi tạo intent chuyển từ FragmentTrip sang EditTripActivity
        intent.putExtra("trip",trip); //truyền dữ liệu vào intent
        startActivity(intent); //intent đc bắt đầu chạy
    }
}
