package com.transpos.market.view;

import android.graphics.drawable.GradientDrawable;

public class CustomGradient extends GradientDrawable {
    private float radius = 4f;
    private Orientation orientation = Orientation.TOP_BOTTOM;
    private int[] colors;

    public CustomGradient(Orientation orientation, int[] colors, float radius) {
        super(orientation, colors);
        this.radius = radius;
        this.orientation = orientation;
        this.colors = colors;
        setCornerRadius(radius);
    }
}
