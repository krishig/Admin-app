package com.krishigadmin.android.ui.home.fragments.manageProduct.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.krishigadmin.android.R;
import com.krishigadmin.android.data.remote.glide.GlideImageLoader;
import com.krishigadmin.android.data.remote.glide.GlideImageLoadingListener;
import com.krishigadmin.android.model.Home;
import com.krishigadmin.android.model.Product;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

public class ManageProductAdapter extends BaseSingleItemAdapter<Product.Result, BaseViewHolder> {

    private Context context;

    public ManageProductAdapter(Context context) {
        this.context = context;
        addChildClickViewIds(R.id.viewDetailLinearLayout);
        addChildClickViewIds(R.id.viewDetailButton);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.item_product_layout;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Product.Result stock, int position) {

        viewHolder.setText(R.id.orderIdDetailTextView, stock.getProductName());
        viewHolder.setText(R.id.productDetailTextView, stock.getSubCategoryName());
        viewHolder.setText(R.id.quantityDetailTextView, stock.getBrandName());
        viewHolder.setText(R.id.totalAmountDetailTextView, stock.getPrice());

        ImageView imageView = viewHolder.findView(R.id.imageProduct);

        if (stock.getProductImage().size()!=0) {
            GlideImageLoader.load(context,
                    stock.getProductImage().get(0).getImageUrl(),
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
                    }
            );
        }else{
            imageView.setImageResource(R.drawable.image_product);
        }

    }
}
