package com.krishigadmin.android.ui.home.fragments.manageSubCategory.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.krishigadmin.android.R;
import com.krishigadmin.android.data.remote.ApiConfiguration;
import com.krishigadmin.android.data.remote.glide.GlideImageLoader;
import com.krishigadmin.android.data.remote.glide.GlideImageLoadingListener;
import com.krishigadmin.android.model.History;
import com.krishigadmin.android.model.Home;
import com.krishigadmin.android.model.SubCategory;
import com.krishigadmin.android.ui.AppConstants;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

public class SubCategoryAdapter extends BaseSingleItemAdapter<SubCategory.Result, BaseViewHolder> {

    private Context context;

    public SubCategoryAdapter(Context context) {
        this.context = context;
        addChildClickViewIds(R.id.deleteImageView);
        addChildClickViewIds(R.id.editImageView);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.item_sub_category_layout;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, SubCategory.Result stock, int position) {
        viewHolder.setText(R.id.sno, stock.getId());
        viewHolder.setText(R.id.subCategory, stock.getSubCategoryName());
        viewHolder.setText(R.id.category, stock.getCategoryName());
        ImageView imageView = viewHolder.findView(R.id.imageView);

        GlideImageLoader.load(
                context,
                stock.getSubCategoryImage(),
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
