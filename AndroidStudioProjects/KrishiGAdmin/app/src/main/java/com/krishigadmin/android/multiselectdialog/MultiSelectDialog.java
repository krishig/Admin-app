package com.krishigadmin.android.multiselectdialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.library.adapter.recyclerview.LayoutManagerUtils;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;
import com.library.utilities.custom.DynamicDialog;
import com.krishigadmin.android.R;

import java.util.ArrayList;

public class MultiSelectDialog {

    private MultiSelectDialog() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    public static void dialog(Activity activity, String headingString, ArrayList<MultiSelect> multiSelectArrayList, MultiSelectListener multiSelectListener) {
        DynamicDialog dynamicDialog = new DynamicDialog.Builder(activity)
                .setView(R.layout.multi_select_dialog)
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

        MultiSelectDialogRecyclerViewAdapter multiSelectDialogRecyclerViewAdapter = new MultiSelectDialogRecyclerViewAdapter(activity);
        multiSelectDialogRecyclerViewAdapter.replaceArrayList(multiSelectArrayList);
        recyclerView.setAdapter(multiSelectDialogRecyclerViewAdapter);

        multiSelectDialogRecyclerViewAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<MultiSelect>() {
            @Override
            public void OnItemChildClick(View viewChild, MultiSelect item, int position) {
                if (viewChild.getId() == R.id.checkBox || viewChild.getId() == R.id.itemLinearLayout)
                {
                    multiSelectDialogRecyclerViewAdapter.checkCheckBox(position, !(multiSelectDialogRecyclerViewAdapter.isSelected(position)));

                    String ids = null;
                    String names = null;

                    SparseBooleanArray selectedRows = multiSelectDialogRecyclerViewAdapter.getSelectedIds();
                    if (selectedRows.size() > 0) {
                        StringBuilder idsStringBuilder = new StringBuilder();
                        StringBuilder namesStringBuilder = new StringBuilder();
                        int lastIdx = selectedRows.size() - 1;

                        for (int i = 0; i < selectedRows.size(); i++) {
                            int key = selectedRows.keyAt(i);
                            String id = multiSelectArrayList.get(key).getId();
                            String name = multiSelectArrayList.get(key).getName();
                            if (i == lastIdx)
                            {
                                idsStringBuilder.append(id);
                                namesStringBuilder.append(name);
                            }
                            else
                            {
                                idsStringBuilder.append(id+",");
                                namesStringBuilder.append(name+",");
                            }
                        }
                        ids = idsStringBuilder.toString();
                        names = namesStringBuilder.toString();
                    }

                    multiSelectListener.onMultiSelected(ids, names, position);
                }
            }
        });

        MaterialButton doneMaterialButton = dynamicDialog.findView(R.id.doneMaterialButton);
        doneMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicDialog.dismiss();
            }
        });

        dynamicDialog.show();
    }
}
