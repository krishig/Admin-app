package com.krishigadmin.android.searchdialog;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;
import com.krishigadmin.android.R;

import java.util.ArrayList;

public class SearchDialogRecyclerViewAdapter extends BaseSingleItemAdapter<Search, BaseViewHolder> implements Filterable {

    public Context context;
    public ArrayList<Search> filterList;
    public SearchDialogCustomFilter customFilter;

    public SearchDialogRecyclerViewAdapter(Context context) {
        this.context    = context;
        filterList      = (ArrayList<Search>) data;
        addChildClickViewIds(R.id.searchDialogRowLinearLayout);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.search_dialog_row;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Search item, int position) {
        viewHolder.setText(R.id.optionTextView, item.getItemName());
    }

    @Override
    public Filter getFilter() {
        if(customFilter==null)
        {
            customFilter = new SearchDialogCustomFilter(filterList,this);
        }
        return customFilter;
    }
}
