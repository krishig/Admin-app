package com.krishigadmin.android.searchdialog;

import android.widget.Filter;

import java.util.ArrayList;

public class SearchDialogCustomFilter extends Filter{

    public SearchDialogRecyclerViewAdapter adapter;
    public ArrayList<Search> filterList;

    public SearchDialogCustomFilter(ArrayList<Search> filterList, SearchDialogRecyclerViewAdapter adapter)
    {
        this.adapter    = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        if(constraint != null && constraint.length() > 0)
        {
            constraint=constraint.toString().toUpperCase();
            ArrayList<Search> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                if(filterList.get(i).getItemName().toUpperCase().contains(constraint))
                {
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }
        else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results)
    {
        adapter.data = (ArrayList<Search>) results.values;
        adapter.notifyDataSetChanged();
    }
}
