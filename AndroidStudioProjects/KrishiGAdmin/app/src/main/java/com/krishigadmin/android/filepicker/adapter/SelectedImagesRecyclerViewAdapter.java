package com.krishigadmin.android.filepicker.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;
import com.krishigadmin.android.R;

public class SelectedImagesRecyclerViewAdapter extends BaseSingleItemAdapter<String, BaseViewHolder> {

    private Context context;

    public SelectedImagesRecyclerViewAdapter(Context context) {
        this.context = context;
        addChildClickViewIds(R.id.imageView);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.item_selected_picture;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, String path, int position) {
        ImageView imageView = viewHolder.findView(R.id.imageView);
        Glide.with(context)
                .load(path)
                .placeholder(R.color.gray)
                .centerCrop()
                .into(imageView);
    }
}
