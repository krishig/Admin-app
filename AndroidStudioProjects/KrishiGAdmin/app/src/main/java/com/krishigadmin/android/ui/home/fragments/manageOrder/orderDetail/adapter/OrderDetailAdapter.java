package com.krishigadmin.android.ui.home.fragments.manageOrder.orderDetail.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.krishigadmin.android.R;
import com.krishigadmin.android.data.remote.glide.GlideImageLoader;
import com.krishigadmin.android.data.remote.glide.GlideImageLoadingListener;
import com.krishigadmin.android.model.Order;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

public class OrderDetailAdapter extends BaseSingleItemAdapter<Order.ProductResponseDtos, BaseViewHolder> {

    private Context context;

    public OrderDetailAdapter(Context context) {
        this.context = context;
        addChildClickViewIds(R.id.addMaterialCardView);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.item_orders_detail;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Order.ProductResponseDtos stock, int position) {

        viewHolder.setText(R.id.productNameTextView, stock.getProductName());
        viewHolder.setText(R.id.quantityTextView, "Quantity - " + stock.getQuantity());
        TextView actualPriceTextView = viewHolder.findView(R.id.actualPriceTextView);
        actualPriceTextView.setText("Rs " + stock.getPrice());
        actualPriceTextView.setPaintFlags(actualPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.setText(R.id.discountTextView, "Discount - " + stock.getDiscount() + "%");
        viewHolder.setText(R.id.discountPriceTextView, "Rs " + stock.getDiscountPrice());

        ImageView imageView = viewHolder.findView(R.id.categoryImageView);

        if (stock.getProductImageResponse() != null) {
            GlideImageLoader.load(context,
                    stock.getProductImageResponse().getImageUrl(),
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
        } else {
            imageView.setImageResource(R.drawable.image_product);
        }

    }
}
