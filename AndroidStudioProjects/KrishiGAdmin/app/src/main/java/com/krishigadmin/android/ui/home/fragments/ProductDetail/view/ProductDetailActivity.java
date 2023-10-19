package com.krishigadmin.android.ui.home.fragments.ProductDetail.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.databinding.ActivityProductDetailBinding;
import com.krishigadmin.android.model.Product;
import com.krishigadmin.android.model.ProductId;
import com.krishigadmin.android.ui.AppConstants;
import com.krishigadmin.android.ui.base.BaseActivity;
import com.krishigadmin.android.ui.home.fragments.ProductDetail.adapter.ImageRecyclerViewAdapter;
import com.krishigadmin.android.ui.home.fragments.addProduct.view.AddProductActivity;
import com.krishigadmin.android.ui.home.fragments.addProduct.viewmodel.ProductViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductDetailActivity extends BaseActivity<ActivityProductDetailBinding> {

    String productId = "";
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    ProductViewModel viewModel;
    ImageRecyclerViewAdapter imageRecyclerViewAdapter;
    ArrayList<ProductId.Result.ProductImage> galleryImagesArrayList = new ArrayList<>();

    @Override
    protected ActivityProductDetailBinding getViewBinding() {
        return ActivityProductDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(ProductDetailActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void initializeViewModel() {
        viewModel = createViewModel(ProductViewModel.class);

    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void initializeObject() {
        setRecyclerView();
        if (getIntent().getExtras() != null) {
            productId = getIntent().getExtras().getString(AppConstants.Extras.PRODUCT_BRAND_ID);

            viewModel.getProductWithId(productId, "application/json", "application/json",
                    sharedPreferencesHelper.getKeyToken());
            showProgressDialog();
        }
    }

    @Override
    protected void addTextChangedListener() {

    }

    @Override
    protected void setOnFocusChangeListener() {

    }

    private void setRecyclerView() {
        viewBinding.recyclerView.setHasFixedSize(true);
        viewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imageRecyclerViewAdapter = new ImageRecyclerViewAdapter(this);
        imageRecyclerViewAdapter.addArrayList(galleryImagesArrayList);
        viewBinding.recyclerView.setAdapter(imageRecyclerViewAdapter);
    }

    @Override
    protected void observeViewModel() {

        Observer<String> getProductIdListUserError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.getProductIdListUserError().observe(this, getProductIdListUserError);

        final Observer<ProductId> getProductIdListUserSuccess = new Observer<ProductId>() {
            @Override
            public void onChanged(ProductId product) {
                hideProgressDialog();
                if (product.getResult().getProductImage().size() == 0) {
                    viewBinding.productImageView.setVisibility(View.VISIBLE);
                    viewBinding.productImageView.setImageResource(R.drawable.image_product);
                } else {
                    galleryImagesArrayList = product.getResult().getProductImage();
                    imageRecyclerViewAdapter.clearAllItem();
                    imageRecyclerViewAdapter.addArrayList(galleryImagesArrayList);
                }

                viewBinding.titleTextView.setText(product.getResult().getProductName());
                viewBinding.priceTextView.setText("Rs. " +  String.valueOf(product.getResult().getPrice()));
                viewBinding.descriptionTextView.setText(product.getResult().getProductDescription());
                viewBinding.subCategoryTextView.setText(product.getResult().getSubCategoryName());
                viewBinding.baseUnitTextView.setText(product.getResult().getBaseUnit());
                viewBinding.discountPercentTextView.setText(product.getResult().getDiscount()+"%");
                viewBinding.brandATextView.setText(product.getResult().getBrandName());

                int amount = product.getResult().getPrice();
                double percentage = product.getResult().getDiscount();
                double result = (percentage / 100) * amount;
                double disCountPrice=amount-result;
                viewBinding.discountTextView.setText("Rs. " +String.valueOf(disCountPrice));
            }
        };
        viewModel.getProductIdListUserSuccess().observe(this, getProductIdListUserSuccess);

        Observer<String> deleteSubCategoryUserError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.deleteSubCategoryUserError().observe(this, deleteSubCategoryUserError);

        final Observer<ArrayList<Product>> deleteSubCategoryUserSuccess = new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> categories) {
                hideProgressDialog();
                finish();
            }
        };
        viewModel.deleteSubCategoryUserSuccess().observe(this, deleteSubCategoryUserSuccess);

    }

    @Override
    protected void setOnClickListener() {
        viewBinding.icBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewBinding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productId != null) {
                    alertDialogConfirmExit(ProductDetailActivity.this, productId);
                }
            }
        });

        viewBinding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailActivity.this, AddProductActivity.class);
                intent.putExtra(AppConstants.Extras.FROM, "1");
                intent.putExtra(AppConstants.Extras.PRODUCT_BRAND_ID, productId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    private void alertDialogConfirmExit(Activity activity, String id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setIcon(R.drawable.ic_black_question_mark);
        alertDialogBuilder.setTitle("Confirm Delete");
        alertDialogBuilder.setMessage("Are you sure you want to Delete?");


        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                viewModel.deleteProduct(id, "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
                showProgressDialog();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setTextColor(Color.parseColor("#FFFFFF"));
        positiveButton.setBackgroundColor(Color.parseColor("#000000"));

        negativeButton.setTextColor(Color.parseColor("#FFFFFF"));
        negativeButton.setBackgroundColor(Color.parseColor("#000000"));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(20, 0, 0, 0);
        positiveButton.setLayoutParams(params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getProductWithId(productId, "application/json", "application/json",
                sharedPreferencesHelper.getKeyToken());
        showProgressDialog();
    }
}