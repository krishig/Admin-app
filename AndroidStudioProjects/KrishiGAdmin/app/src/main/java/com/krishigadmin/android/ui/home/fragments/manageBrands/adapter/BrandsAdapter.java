package com.krishigadmin.android.ui.home.fragments.manageBrands.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.krishigadmin.android.R;
import com.krishigadmin.android.data.remote.glide.GlideImageLoader;
import com.krishigadmin.android.data.remote.glide.GlideImageLoadingListener;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.ProductBrands;
import com.krishigadmin.android.ui.AppConstants;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

public class BrandsAdapter extends BaseSingleItemAdapter<ProductBrands.Result, BaseViewHolder> {

    private Context context;

    public BrandsAdapter(Context context) {
        this.context = context;
        addChildClickViewIds(R.id.deleteImageView);
        addChildClickViewIds(R.id.editImageView);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.item_brands_layout;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, ProductBrands.Result category, int position) {
        viewHolder.setText(R.id.sno, category.getId());
        viewHolder.setText(R.id.brands, category.getBrandName());
        ImageView imageView = viewHolder.findView(R.id.imageView);

        GlideImageLoader.load(
                context,
                 category.getBrandImageUrl(),
                R.drawable.image_product,
                R.drawable.image_product,
                imageView,
                new GlideImageLoadingListener() {
                    @Override
                    public void imageLoadSuccess() {
                    }

                    @Override
                    public void imageLoadError() {
                    }
                });
    }
}
