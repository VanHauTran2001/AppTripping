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
        binding = FragmentTripBinding.inflate(inflater,container,false);
        tripArrayList = new ArrayList<>();
        sqLiteHelper = new SQLiteHelper(getContext(),"Database.sqlite",null,1);
        getDataTrip();
        onClickAdd();
        initRecyler();
        checkThongBao();
        return binding.getRoot();
    }

    private void getDataTrip() {
        tripArrayList.clear();
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM Trips");
        while (cursor.moveToNext()){
            idTrip = cursor.getInt(0);
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            String locate = cursor.getString(3);
            String required = cursor.getString(4);
            String description = cursor.getString(5);
            tripArrayList.add(new Trip(idTrip,name,date,locate,required,description));
        }
    }
    private void onClickAdd() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HomeActivity.class));
            }
        });
    }

    private void checkThongBao() {
        if (tripArrayList.size()==0){
            binding.txtThongBao.setVisibility(View.VISIBLE);
        }else {
            binding.txtThongBao.setVisibility(View.GONE);
        }
    }

    private void initRecyler() {
        adapter = new TripAdapter(this);
        binding.recylerTrip.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recylerTrip.setAdapter(adapter);
    }

    @Override
    public int getCount() {
        return tripArrayList.size();
    }

    @Override
    public Trip getListTrip(int position) {
        return tripArrayList.get(position);
    }

    @Override
    public void onClickItem(int position) {
        Trip trip = tripArrayList.get(position);
        Intent intent = new Intent(getContext(), EditTripActivity.class);
        intent.putExtra("trip",trip);
        startActivity(intent);
    }
}
