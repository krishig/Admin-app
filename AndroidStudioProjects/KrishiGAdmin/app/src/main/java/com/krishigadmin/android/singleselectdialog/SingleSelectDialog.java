package com.krishigadmin.android.singleselectdialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.library.adapter.recyclerview.LayoutManagerUtils;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;
import com.library.utilities.custom.DynamicDialog;
import com.krishigadmin.android.R;

import java.util.ArrayList;

public class SingleSelectDialog {

    private SingleSelectDialog() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    public static void dialog(Activity activity, String headingString, ArrayList<SingleSelect> singleSelectArrayList, SingleSelectListener singleSelectListener) {
        DynamicDialog dynamicDialog = new DynamicDialog.Builder(activity)
                .setView(R.layout.single_select_dialog)
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
                        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
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

        SingleSelectDialogRecyclerViewAdapter singleSelectDialogRecyclerViewAdapter = new SingleSelectDialogRecyclerViewAdapter(activity);
        singleSelectDialogRecyclerViewAdapter.replaceArrayList(singleSelectArrayList);
        recyclerView.setAdapter(singleSelectDialogRecyclerViewAdapter);

        singleSelectDialogRecyclerViewAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<SingleSelect>() {
            @Override
            public void OnItemChildClick(View viewChild, SingleSelect item, int position) {
                switch (viewChild.getId()) {
                    case R.id.itemLinearLayout:
                        singleSelectListener.onSingleSelected(item, position);
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
