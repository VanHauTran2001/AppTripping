package com.example.apptrip.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apptrip.Model.Expenses;
import com.example.apptrip.databinding.ItemExpenseBinding;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>{
    private IExpense iExpense;

    public ExpenseAdapter(IExpense iExpense) {
        this.iExpense = iExpense;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemExpenseBinding binding = ItemExpenseBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ExpenseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Expenses expenses = iExpense.getListExpense(position);
        holder.binding.txtNameExpense.setText(expenses.getType());
        holder.binding.txtAmount.setText(expenses.getAmount());
        holder.binding.txtTimeOf.setText(expenses.getTimeOf());
    }

    @Override
    public int getItemCount() {
        return iExpense.getCount();
    }

    public interface IExpense{
        int getCount();
        Expenses getListExpense(int position);
    }
    public class ExpenseViewHolder extends RecyclerView.ViewHolder{
        ItemExpenseBinding binding;
        public ExpenseViewHolder(@NonNull ItemExpenseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
