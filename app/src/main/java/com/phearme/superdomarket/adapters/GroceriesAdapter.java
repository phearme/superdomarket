package com.phearme.superdomarket.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.phearme.superdoclient.SuperdoBag;
import com.phearme.superdomarket.BR;
import com.phearme.superdomarket.BagItemActivity;
import com.phearme.superdomarket.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class GroceriesAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private ArrayList<SuperdoBag> groceries;

    public GroceriesAdapter(ArrayList<SuperdoBag> groceries) {
        this.groceries = groceries;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.bag_item, parent, false);
        return new BindingViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        final SuperdoBag item = groceries == null ? null : groceries.get(position);
        holder.getViewDataBinding().setVariable(BR.bag, item);
        holder.getViewDataBinding().executePendingBindings();

        final View circle = holder.itemView.findViewById(R.id.imgCircle);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BagItemActivity.class);
                intent.putExtra("bagColor", item.bagColor);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext(), circle, "bagCircle");
                v.getContext().startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return groceries == null ? 0 : groceries.size();
    }

    public void setGroceries(ArrayList<SuperdoBag> groceries) {
        this.groceries = groceries;
    }
}
