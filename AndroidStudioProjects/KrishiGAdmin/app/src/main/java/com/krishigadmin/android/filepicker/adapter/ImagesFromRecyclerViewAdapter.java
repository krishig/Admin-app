package com.krishigadmin.android.filepicker.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.library.adapter.recyclerview.adapter.BaseMultipleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;
import com.library.utilities.mediastore.images.ImagesMediaFile;
import com.krishigadmin.android.R;
import com.krishigadmin.android.filepicker.ImagePickerDialogFragment;
import com.krishigadmin.android.filepicker.model.CameraAndFolder;
import com.krishigadmin.android.filepicker.model.PickerMultiItem;

import java.util.ArrayList;
import java.util.List;

public class ImagesFromRecyclerViewAdapter extends BaseMultipleItemAdapter<PickerMultiItem, BaseViewHolder> {

    private static final String TAG = ImagesFromRecyclerViewAdapter.class.getSimpleName();

    private Context context;
    private ImagePickerDialogFragment imagePickerDialogFragment;
    private SparseBooleanArray selectedItemPosition;

    public ImagesFromRecyclerViewAdapter(Context context, ImagePickerDialogFragment imagePickerDialogFragment) {
        addItemType(1, R.layout.item_camera_or_folder);
        addItemType(2, R.layout.item_select_picture);

        addChildClickViewIds(R.id.checkBox);

        this.context = context;
        this.imagePickerDialogFragment = imagePickerDialogFragment;
        selectedItemPosition = new SparseBooleanArray();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, PickerMultiItem pickerMultiItem, int position) {
        int itemViewType = viewHolder.getItemViewType();
        switch (itemViewType) {
            case 1:

                CameraAndFolder cameraAndFolder = pickerMultiItem.getCameraAndFolder();

                viewHolder.setImageResource(R.id.cameraOrFolderImageView, cameraAndFolder.getResourceImage());
                viewHolder.setText(R.id.cameraOrFolderTextView, cameraAndFolder.getItemTitle());

                break;
            case 2:

                ImagesMediaFile imagesMediaFile = pickerMultiItem.getImagesMediaFile();

                ImageView selectImageView = viewHolder.findView(R.id.selectImageView);
                CheckBox checkBox = viewHolder.findView(R.id.checkBox);
                View foregroundFrame = viewHolder.findView(R.id.foregroundFrame);

                Glide.with(context)
                        .load(imagesMediaFile.getData())
                        .placeholder(R.color.gray)
                        .centerCrop()
                        .into(selectImageView);

                if (isSelected(position)) {
                    checkBox.setChecked(true);
                    foregroundFrame.setVisibility(View.VISIBLE);
                } else {
                    checkBox.setChecked(false);
                    foregroundFrame.setVisibility(View.GONE);
                }

                break;
            default:
                break;
        }
    }

    /**
     * Select all checkbox
     **/
    public void selectAll() {
        Log.d(TAG, "selectAll() : " + "arrayList");
    }

    /**
     * Remove all checkbox
     **/
    public void deselectAll() {
        Log.d(TAG, "deselectAll() : " + "arrayList");

        List<Integer> selection = getSelectedItems();
        selectedItemPosition.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    /**
     * Check the Checkbox if not checked, if already check then unchecked
     **/
    public void checkCheckBox(int position, boolean value) {
        if (value) {
            selectedItemPosition.put(position, true);
        } else {
            selectedItemPosition.delete(position);
        }
        Log.e(TAG, "selectAll() : " + selectedItemPosition);
        //notifyDataSetChanged();
        notifyItemChanged(position);
    }

    /**
     * Return the selected Checkbox position
     **/
    public SparseBooleanArray getSelectedIds() {
        return selectedItemPosition;
    }

    /**
     * Count the selected items
     *
     * @return Selected items count
     */
    public int getSelectedItemCount() {
        return selectedItemPosition.size();
    }

    /**
     * Indicates the list of selected items
     *
     * @return List of selected items ids
     */
    public List<Integer> getSelectedItems() {
        System.out.println("=============" + selectedItemPosition.size());
        List<Integer> items = new ArrayList<>(selectedItemPosition.size());

        for (int i = 0; i < selectedItemPosition.size(); ++i) {
            items.add(selectedItemPosition.keyAt(i));
        }
        return items;
    }

    /**
     * Indicates if the item at position position is selected
     *
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }
}