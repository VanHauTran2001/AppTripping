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
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        sqLiteHelper = new SQLiteHelper(getContext(),"Database.sqlite",null,1);
        tripArrayList = new ArrayList<>();
        CustumSearch();
        return binding.getRoot();
    }

    private void intitDataRecylerView() {
        adapter = new TripAdapter(this);
        binding.recyLerSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyLerSearch.setAdapter(adapter);
    }

    private void CustumSearch() {
        binding.btnSearchTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameSearch = binding.edtSearch.getText().toString().trim();
                if (TextUtils.isEmpty(nameSearch)){
                    Toast.makeText(getContext(),"Fill all required fields",Toast.LENGTH_SHORT).show();
                }else {
                    //add to database
                    tripArrayList.clear();
                    Cursor cursor = sqLiteHelper.getData("SELECT * FROM Trips WHERE NameTrip = '"+nameSearch+ "' ");
                    while (cursor.moveToNext()){
                        int idTrip = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String date = cursor.getString(2);
                        String locate = cursor.getString(3);
                        String required = cursor.getString(4);
                        String description = cursor.getString(5);
                        tripArrayList.add(new Trip(idTrip,name,date,locate,required,description));
                    }
                    intitDataRecylerView();
                }
            }
        });
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

    }
}
