package com.krishigadmin.android.ui.home.fragments.manageProduct.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.krishigadmin.android.R;
import com.krishigadmin.android.model.ProductBrands;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

public class SelectBrandAdapter extends BaseSingleItemAdapter<ProductBrands.Result, BaseViewHolder> {

    private Context context;

    public SelectBrandAdapter(Context context) {
        addChildClickViewIds(R.id.searchDialogRowLinearLayout);
        this.context = context;
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.item_select_brand;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, ProductBrands.Result home, int position) {
        viewHolder.setText(R.id.brandNameTextView, home.getBrandName());
    }
}
