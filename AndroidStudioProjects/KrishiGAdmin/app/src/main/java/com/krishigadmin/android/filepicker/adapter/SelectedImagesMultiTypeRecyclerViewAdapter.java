package com.krishigadmin.android.filepicker.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.library.adapter.recyclerview.adapter.BaseMultipleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;
import com.krishigadmin.android.R;
import com.krishigadmin.android.filepicker.model.AddAndAddedMultiItem;
import com.krishigadmin.android.filepicker.model.AddImages;
import com.krishigadmin.android.filepicker.model.AddedImages;

public class SelectedImagesMultiTypeRecyclerViewAdapter extends BaseMultipleItemAdapter<AddAndAddedMultiItem, BaseViewHolder> {

    private Context context;

    public SelectedImagesMultiTypeRecyclerViewAdapter(Context context) {
        addItemType(1, R.layout.item_added_picture);
        addItemType(2, R.layout.item_add_picture);
        addChildClickViewIds(R.id.addImageView);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, AddAndAddedMultiItem addAndAddedMultiItem, int position) {
        int itemViewType = viewHolder.getItemViewType();
        switch (itemViewType) {
            case 1:
                AddedImages addedImages = addAndAddedMultiItem.getAddedImages();
                ImageView addedImageView = viewHolder.findView(R.id.addedImageView);
                Glide.with(context)
                        .load(addedImages.getData())
                        .placeholder(R.color.gray)
                        .centerCrop()
                        .into(addedImageView);
                break;
            case 2:
                AddImages addImages = addAndAddedMultiItem.getAddImages();
                viewHolder.setImageResource(R.id.addImageView, addImages.getResourceImage());
                break;
            default:
                break;
        }
    }
}
