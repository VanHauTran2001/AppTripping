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

    public TripAdapter(IListTrip iListTrip) {
        this.iListTrip = iListTrip;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTripBinding binding = ItemTripBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new TripViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Trip trip = iListTrip.getListTrip(position);
        holder.binding.txtIdTrip.setText(String.valueOf(trip.getIdTrip()));
        holder.binding.txtNameTrip.setText(trip.getNameTrip());
        holder.binding.txtDateTrip.setText(trip.getDateTrip());
        holder.binding.txtLocateTrip.setText(trip.getLocateTrip());
        holder.binding.txtRiquired.setText(trip.getRequiredTrip());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListTrip.onClickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return iListTrip.getCount();
    }

    public interface IListTrip{
        int getCount();
        Trip getListTrip(int position);
        void onClickItem(int position);
    }
    public class TripViewHolder extends RecyclerView.ViewHolder{
        ItemTripBinding binding;
        public TripViewHolder(@NonNull ItemTripBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
