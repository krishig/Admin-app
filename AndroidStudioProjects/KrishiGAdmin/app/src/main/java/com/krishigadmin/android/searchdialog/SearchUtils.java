package com.krishigadmin.android.searchdialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.library.adapter.recyclerview.LayoutManagerUtils;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;
import com.library.utilities.custom.DynamicDialog;
import com.krishigadmin.android.R;

import java.util.ArrayList;

;

public class SearchUtils {

    private SearchUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    public static void searchDialog(Activity activity, String headingString, ArrayList<Search> searchArrayList, SelectListener selectListener) {
        DynamicDialog dynamicDialog = new DynamicDialog.Builder(activity)
                .setView(R.layout.search_dialog)
                .setTheme(com.library.utilities.R.style.Dynamic_Dialog_Default_Style)
                .setWindowAnimationStyle(com.library.utilities.R.style.BottomSheetAnimation)
                .setPadding(0, 0, 0, 0)
                .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                .setGravity(Gravity.CENTER)
                .setCanceledOnTouchOutside(false)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        /* Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show(); */
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        /* Toast.makeText(activity, "Dismiss", Toast.LENGTH_SHORT).show(); */
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                            /*
                             * If return true then click on back dialog is not dismiss
                             * If return false then click on back dialog is dismiss
                             */
                            return false;
                        } else {
                            return false;
                        }
                    }
                })
                .applyAttribute(true)
                .build();

        dynamicDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        dynamicDialog.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.colorPrimary));

        ImageView backImageView = dynamicDialog.findView(R.id.backButtonImageView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dynamicDialog.dismiss();
            }
        });

        TextView selectHeadingTextView = dynamicDialog.findView(R.id.selectHeadingTextView);
        selectHeadingTextView.setText(headingString);

        RecyclerView recyclerView = dynamicDialog.findView(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(LayoutManagerUtils.getLinearLayoutManagerVertical(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        SearchDialogRecyclerViewAdapter searchDialogRecyclerViewAdapter = new SearchDialogRecyclerViewAdapter(activity);
        searchDialogRecyclerViewAdapter.addArrayList(searchArrayList);
        recyclerView.setAdapter(searchDialogRecyclerViewAdapter);

        TextInputEditText searchTextInputEditText   = dynamicDialog.findView(R.id.searchTextInputEditText);
        searchTextInputEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchDialogRecyclerViewAdapter.getFilter().filter(s);
            }
        });

        searchDialogRecyclerViewAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<Search>() {
            @Override
            public void OnItemChildClick(View viewChild, Search item, int position) {
                switch (viewChild.getId()) {
                    case R.id.searchDialogRowLinearLayout:
                        selectListener.onSelected(item, position);
                        dynamicDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });

        dynamicDialog.show();
    }
}
