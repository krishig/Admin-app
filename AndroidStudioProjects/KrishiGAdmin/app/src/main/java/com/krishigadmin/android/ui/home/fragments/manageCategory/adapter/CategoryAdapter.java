package com.krishigadmin.android.ui.home.fragments.manageCategory.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.krishigadmin.android.R;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.History;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

public class CategoryAdapter extends BaseSingleItemAdapter<Category, BaseViewHolder> {

    private Context context;

    public CategoryAdapter(Context context) {
        this.context = context;
        addChildClickViewIds(R.id.deleteImageView);
        addChildClickViewIds(R.id.editImageView);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.item_category_layout;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Category category, int position) {
        viewHolder.setText(R.id.sno, category.getId());
        viewHolder.setText(R.id.category, category.getCategory_name());
    }
}
