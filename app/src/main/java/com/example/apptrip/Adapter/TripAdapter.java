package com.example.apptrip.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrip.Model.Trip;
import com.example.apptrip.databinding.ItemTripBinding;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private IListTrip iListTrip;

    public TripAdapter(IListTrip iListTrip) {//Khởi tạo hàm adapter với tham số là interface
        this.iListTrip = iListTrip;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//Hiển thị layout
        ItemTripBinding binding = ItemTripBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new TripViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Trip trip = iListTrip.getListTrip(position);//Khởi tạo class trip
        holder.binding.txtIdTrip.setText(String.valueOf(trip.getIdTrip()));//gán dữ liệu vào edittext
        holder.binding.txtNameTrip.setText(trip.getNameTrip());
        holder.binding.txtDateTrip.setText(trip.getDateTrip());
        holder.binding.txtLocateTrip.setText(trip.getLocateTrip());
        holder.binding.txtRiquired.setText(trip.getRequiredTrip());
        holder.itemView.setOnClickListener(new View.OnClickListener() {//bắt sự kiện khi người dùng click vào dữ liệu hiển thị trên recyclerview
            @Override
            public void onClick(View view) {
                iListTrip.onClickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return iListTrip.getCount();
    }//hàm trả về số lượng của list

    public interface IListTrip{//Tạo class interface để tương tác giữa adapter và activity
        int getCount();
        Trip getListTrip(int position);
        void onClickItem(int position);
    }
    public class TripViewHolder extends RecyclerView.ViewHolder{//Khởi tạo viewholder của adapter
        ItemTripBinding binding;
        public TripViewHolder(@NonNull ItemTripBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
