package com.krishigadmin.android.ui.home.fragments.addProduct.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.krishigadmin.android.R;
import com.krishigadmin.android.data.remote.glide.GlideImageLoader;
import com.krishigadmin.android.data.remote.glide.GlideImageLoadingListener;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.Product;
import com.krishigadmin.android.model.ProductId;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

public class UploadImagesAdapter extends BaseSingleItemAdapter<ProductId.Result.ProductImage, BaseViewHolder> {

    private Context context;

    public UploadImagesAdapter(Context context) {
        this.context = context;
        addChildClickViewIds(R.id.crossCardView1);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.car_gallery_images;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, ProductId.Result.ProductImage images, int position) {
        ImageView subCategoryImageView = viewHolder.findView(R.id.productImageView1);

        GlideImageLoader.load(
                context,
                 images.getImageUrl(),
                R.drawable.image_product,
                R.drawable.image_product,
                subCategoryImageView,
                new GlideImageLoadingListener() {
                    @Override
                    public void imageLoadSuccess() {

                    }

                    @Override
                    public void imageLoadError() {

                    }
                });

        //subCategoryImageView.setImageResource(images.getImages());

    }
}
