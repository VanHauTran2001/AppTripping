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

    public ExpenseAdapter(IExpense iExpense) {//Khởi tạo hàm adapter với tham số là interface
        this.iExpense = iExpense;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//Hiển thị layout
        ItemExpenseBinding binding = ItemExpenseBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ExpenseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Expenses expenses = iExpense.getListExpense(position); //Khởi tạo class expense
        holder.binding.txtNameExpense.setText(expenses.getType()); //gán dữ liệu vào edittext
        holder.binding.txtAmount.setText(expenses.getAmount());
        holder.binding.txtTimeOf.setText(expenses.getTimeOf());
    }

    @Override
    public int getItemCount() {
        return iExpense.getCount();
    }//hàm trả về số lượng của list

    public interface IExpense{ //Tạo class interface để tương tác giữa adapter và activity
        int getCount();
        Expenses getListExpense(int position);
    }
    public class ExpenseViewHolder extends RecyclerView.ViewHolder{//Khởi tạo viewholder của adapter
        ItemExpenseBinding binding;
        public ExpenseViewHolder(@NonNull ItemExpenseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
