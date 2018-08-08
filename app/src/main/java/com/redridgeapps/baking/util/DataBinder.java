package com.redridgeapps.baking.util;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public final class DataBinder {

    private DataBinder() {
    }

    @BindingAdapter({"imageUrl", "placeholder", "error"})
    public static void setImageUrl(
            ImageView imageView, String url,
            Drawable placeholder,
            Drawable error
    ) {

        if (!TextUtils.isEmpty(url)) {
            Picasso.get()
                    .load(url)
                    .placeholder(placeholder)
                    .error(error)
                    .into(imageView);
        } else {
            imageView.setImageDrawable(error);
        }
    }
}
