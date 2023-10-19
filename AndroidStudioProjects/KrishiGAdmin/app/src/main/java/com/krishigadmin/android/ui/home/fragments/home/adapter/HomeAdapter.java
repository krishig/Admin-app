package com.krishigadmin.android.ui.home.fragments.home.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.krishigadmin.android.R;
import com.krishigadmin.android.model.Home;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeAdapter extends BaseSingleItemAdapter<Home.OrderResponseDto.Content, BaseViewHolder> {

    private Context context;

    public HomeAdapter(Context context) {
        this.context = context;
        addChildClickViewIds(R.id.viewDetailLinearLayout);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.item_home_layout;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Home.OrderResponseDto.Content stock, int position) {
        viewHolder.setText(R.id.orderIdDetailTextView, stock.getOrderId());
        viewHolder.setText(R.id.customerNameDetailTextView, stock.getCustomerId().getFullName());
        viewHolder.setText(R.id.productDetailTextView, stock.getStatus());

        String inputDateString = stock.getCreatedDate();
        String formattedDate = convertDateFormat(inputDateString);
        viewHolder.setText(R.id.quantityDetailTextView, formattedDate);
        viewHolder.setText(R.id.totalAmountDetailTextView, stock.getTotalPrice());
    }

    public static String convertDateFormat(String inputDateString) {
        try {
            SimpleDateFormat inputFormat = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US);
            }
            Date date = inputFormat.parse(inputDateString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
