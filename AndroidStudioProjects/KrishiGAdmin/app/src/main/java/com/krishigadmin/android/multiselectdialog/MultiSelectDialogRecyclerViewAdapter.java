package com.krishigadmin.android.multiselectdialog;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.remote.glide.GlideImageLoader;
import com.krishigadmin.android.data.remote.glide.GlideImageLoadingListener;
import com.krishigadmin.android.ui.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class MultiSelectDialogRecyclerViewAdapter extends BaseSingleItemAdapter<MultiSelect, BaseViewHolder>  {

    private static final String TAG = MultiSelectDialogRecyclerViewAdapter.class.getSimpleName();

    public Context context;
    private SparseBooleanArray selectedItemPosition;

    public MultiSelectDialogRecyclerViewAdapter(Context context) {
        this.context    = context;
        selectedItemPosition = new SparseBooleanArray();
        addChildClickViewIds(R.id.checkBox);
        addChildClickViewIds(R.id.itemLinearLayout);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.multi_select_dialog_item;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, MultiSelect item, int position) {
        ImageView itemImageView = viewHolder.findView(R.id.itemImageView);
        TextView itemNameTextView = viewHolder.findView(R.id.itemNameTextView);

        GlideImageLoader.load(
                context,
                AppConstants.APIPath.BASE_URL +item.getImage(),
                R.drawable.checkbox_icon,
                R.drawable.checkbox_icon,
                itemImageView,
                new GlideImageLoadingListener() {
                    @Override
                    public void imageLoadSuccess() {
                    }

                    @Override
                    public void imageLoadError() {
                    }
                });

        itemNameTextView.setText(item.getName());

        CheckBox checkBox = viewHolder.findView(R.id.checkBox);
        checkBox.setChecked(isSelected(position));
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
