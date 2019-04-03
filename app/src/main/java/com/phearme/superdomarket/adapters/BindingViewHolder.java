package com.phearme.superdomarket.adapters;


import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BindingViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding viewDataBinding;

    public BindingViewHolder(ViewDataBinding itemView) {
        super(itemView.getRoot());
        viewDataBinding = itemView;
    }

    public ViewDataBinding getViewDataBinding() {
        return viewDataBinding;
    }
}