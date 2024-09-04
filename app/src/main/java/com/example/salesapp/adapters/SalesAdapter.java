package com.example.salesapp.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesapp.callbacks.OnClickListener;
import com.example.salesapp.database.remote.model.SalesEntities;
import com.example.salesapp.databinding.ItemSaleBinding;

import java.util.ArrayList;
import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SalesViewHolder> {

    private List<SalesEntities> salesList = new ArrayList<>();
    private final OnClickListener onClickListener;

    public SalesAdapter(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSalesList(List<SalesEntities> salesList) {
        this.salesList = salesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSaleBinding binding = ItemSaleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SalesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesViewHolder holder, int position) {
        SalesEntities sale = salesList.get(position);
        holder.bind(sale);

        holder.binding.imageViewEdit.setOnClickListener(v -> onClickListener.onClickEdit(sale));

        holder.binding.imageViewDelete.setOnClickListener(v -> onClickListener.onClickDelete(sale.getID()));
    }


    @Override
    public int getItemCount() {
        return salesList.size();
    }

    public static class SalesViewHolder extends RecyclerView.ViewHolder {
        private final ItemSaleBinding binding;

        public SalesViewHolder(ItemSaleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(SalesEntities sale) {
            binding.textViewQuantity.setText("Quantity: " + sale.getProductQuantity());
            binding.textViewAmount.setText("Amount: Rs." + sale.getProductAmount());
            binding.textViewDate.setText("Date: " + sale.getDateString());
        }
    }
}
