package com.krishigadmin.android.ui.home.fragments.manageProduct.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.krishigadmin.android.R;
import com.krishigadmin.android.model.SubCategory;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

public class SelectSubCategoryAdapter extends BaseSingleItemAdapter<SubCategory.Result, BaseViewHolder> {

    private Context context;

    public SelectSubCategoryAdapter(Context context) {
        addChildClickViewIds(R.id.searchDialogRowLinearLayout);
        this.context = context;
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.item_select_brand;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, SubCategory.Result home, int position) {
        viewHolder.setText(R.id.brandNameTextView, home.getSubCategoryName());
    }
}
