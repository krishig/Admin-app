package com.krishigadmin.android.ui.home.fragments.manageSalesPerson.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.krishigadmin.android.R;
import com.krishigadmin.android.data.remote.glide.GlideImageLoader;
import com.krishigadmin.android.data.remote.glide.GlideImageLoadingListener;
import com.krishigadmin.android.model.SubCategory;
import com.krishigadmin.android.model.User;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

public class ManageSalesPersonAdapter extends BaseSingleItemAdapter<User.result, BaseViewHolder> {

    private Context context;

    public ManageSalesPersonAdapter(Context context) {
        this.context = context;
        addChildClickViewIds(R.id.editImageView);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.item_manage_sales_person;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, User.result user, int position) {
        viewHolder.setText(R.id.sno, user.getId());
        viewHolder.setText(R.id.subCategory, user.getUsername());

        TextView textView = viewHolder.findView(R.id.category);

        if (user.getRole().equalsIgnoreCase("1")) {
            textView.setText("A");
        } else if (user.getRole().equalsIgnoreCase("2")) {
            textView.setText("S");
        }
    }
}
