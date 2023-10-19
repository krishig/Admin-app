package com.krishigadmin.android.ui.home.fragments.manageCustomer.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.krishigadmin.android.R;
import com.krishigadmin.android.model.Customer;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

public class ManageCustomerAdapter extends BaseSingleItemAdapter<Customer.Content, BaseViewHolder> {

    private Context context;

    public ManageCustomerAdapter(Context context) {
        this.context = context;
        addChildClickViewIds(R.id.editImageView);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.item_customer;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Customer.Content customer, int position) {
        viewHolder.setText(R.id.nameDetailTextView, customer.getFullName());
        viewHolder.setText(R.id.contactNumberTextView, customer.getMobileNumber());

    }
}
