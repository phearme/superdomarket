package com.phearme.superdomarket.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter("backcolor")
    public static void setBackgroundColor(ImageView view, String hexColor) {
        if (view != null && hexColor != null) {
            GradientDrawable backgroundGradient = (GradientDrawable)view.getBackground();
            backgroundGradient.setColor(Color.parseColor(hexColor));
        }
    }
}
