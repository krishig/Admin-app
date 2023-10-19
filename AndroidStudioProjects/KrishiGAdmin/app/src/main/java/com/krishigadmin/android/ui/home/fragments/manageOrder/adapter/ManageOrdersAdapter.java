package com.krishigadmin.android.ui.home.fragments.manageOrder.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.krishigadmin.android.R;
import com.krishigadmin.android.model.Order;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

public class ManageOrdersAdapter extends BaseSingleItemAdapter<Order.Content, BaseViewHolder> {

    private Context context;

    public ManageOrdersAdapter(Context context) {
        this.context = context;
        addChildClickViewIds(R.id.editImageView);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.item_manage_order;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Order.Content stock, int position) {
        viewHolder.setText(R.id.orderIdDetailTextView, stock.getOrderId());
        viewHolder.setText(R.id.statusTextView, stock.getStatus());
        viewHolder.setText(R.id.contactNumberTextView, stock.getContactNumber());

    }
}
