package com.krishigadmin.android.ui.home.fragments.addProduct.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.data.remote.RequestUtils;
import com.krishigadmin.android.databinding.ActivityAddProductBinding;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.Product;
import com.krishigadmin.android.model.ProductBrands;
import com.krishigadmin.android.model.ProductId;
import com.krishigadmin.android.model.SubCategory;
import com.krishigadmin.android.searchdialog.Search;
import com.krishigadmin.android.searchdialog.SearchUtils;
import com.krishigadmin.android.searchdialog.SelectListener;
import com.krishigadmin.android.ui.AppConstants;
import com.krishigadmin.android.ui.base.BaseActivity;
import com.krishigadmin.android.ui.home.fragments.addProduct.adapter.CarGalleryImagesAdapter;
import com.krishigadmin.android.ui.home.fragments.addProduct.adapter.UploadImagesAdapter;
import com.krishigadmin.android.ui.home.fragments.addProduct.viewmodel.ProductViewModel;
import com.krishigadmin.android.ui.home.fragments.addSubCategory.imageUpload.imageCompressor.Compressor;
import com.library.adapter.recyclerview.LayoutManagerUtils;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;
import com.library.utilities.file.MemoryUtils;
import com.library.utilities.file.RealPathUtils;
import com.library.utilities.permissionutils.ManagePermission;
import com.library.utilities.permissionutils.PermissionName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class AddProductActivity extends BaseActivity<ActivityAddProductBinding> {

    ProductViewModel viewModel;
    String from = "", subCategoryId = "", brandId = "";

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    private static final int MULTIPLE_PERMISSION_REQUEST_CODE = 1001;
    public static final String[] MULTIPLE_PERMISSIONS =
            {
                    PermissionName.READ_EXTERNAL_STORAGE,
                    PermissionName.WRITE_EXTERNAL_STORAGE,
                    PermissionName.CAMERA
            };

    private ManagePermission managePermission;

    private ArrayList<Search> vehicleTypeSearchArrayList = new ArrayList<Search>();
    private ArrayList<SubCategory.Result> vehicleTypeArraylist = new ArrayList<SubCategory.Result>();

    private ArrayList<Search> brandTypeSearchArrayList = new ArrayList<Search>();
    private ArrayList<ProductBrands.Result> brandTypeArraylist = new ArrayList<ProductBrands.Result>();

    CarGalleryImagesAdapter carGalleryImagesAdapter;

    String whichBox = "";

    int PICK_IMAGE_MULTIPLE = 1;
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    ArrayList<Category> stringArrayList = new ArrayList<>();

    ArrayList<String> ImageUploadUrlArrayList = new ArrayList<>();
    ArrayList<String> ImageUploadFileNameArrayList = new ArrayList<>();

    String productId = "";
    ArrayList<ProductId.Result.ProductImage> uploadedImageList = new ArrayList<>();
    UploadImagesAdapter uploadImagesAdapter;

    @Override
    protected ActivityAddProductBinding getViewBinding() {
        return ActivityAddProductBinding.inflate(getLayoutInflater());
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(AddProductActivity.this);
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
        carGalleryImagesAdapter = new CarGalleryImagesAdapter(this);
        uploadImagesAdapter = new UploadImagesAdapter(this);
        setRecyclerView();
        setRecyclerView2();
        managePermission = new ManagePermission(this);
        if (getIntent().getExtras() != null) {
            from = getIntent().getExtras().getString(AppConstants.Extras.FROM);
            productId = getIntent().getExtras().getString(AppConstants.Extras.PRODUCT_BRAND_ID);

            if (productId != null) {
                viewModel.getProductWithId(productId, "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
                showProgressDialog();
            }

        }

    }


    private void setRecyclerView() {
        viewBinding.uploadedImagesRecyclerView.setHasFixedSize(true);
        viewBinding.uploadedImagesRecyclerView.setLayoutManager(LayoutManagerUtils.getGridLayoutManagerVertical(this, 2));
        carGalleryImagesAdapter.addArrayList(stringArrayList);
        viewBinding.uploadedImagesRecyclerView.setAdapter(carGalleryImagesAdapter);
    }

    private void setRecyclerView2() {
        viewBinding.selectedImagesRecyclerView.setHasFixedSize(true);
        viewBinding.selectedImagesRecyclerView.setLayoutManager(LayoutManagerUtils.getGridLayoutManagerVertical(this, 2));
        uploadImagesAdapter.addArrayList(uploadedImageList);
        viewBinding.selectedImagesRecyclerView.setAdapter(uploadImagesAdapter);
    }

    @Override
    protected void addTextChangedListener() {

    }

    @Override
    protected void setOnFocusChangeListener() {

    }

    @Override
    protected void observeViewModel() {
        viewModel.getSubCategories("application/json", "application/json",
                sharedPreferencesHelper.getKeyToken());
        showProgressDialog();

        Observer<String> getCategoryUserError = new Observer<String>() {
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
        viewModel.getSubCategoryUserError().observe(this, getCategoryUserError);

        final Observer<SubCategory> getCategoryUserSuccess = new Observer<SubCategory>() {
            @Override
            public void onChanged(SubCategory employees) {
                hideProgressDialog();
                vehicleTypeArraylist = employees.getResultArrayList();
                vehicleTypeSearchArrayList.clear();
                for (SubCategory.Result employees1 : vehicleTypeArraylist) {
                    vehicleTypeSearchArrayList.add(new Search(employees1.getId(), employees1.getSubCategoryName()));
                }

            }
        };
        viewModel.getSubCategoryUserSuccess().observe(this, getCategoryUserSuccess);


        viewModel.getProductBrands("application/json", "application/json",
                sharedPreferencesHelper.getKeyToken());
        showProgressDialog();

        Observer<String> getProductBrandsUserError = new Observer<String>() {
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
        viewModel.getProductBrandsUserError().observe(this, getProductBrandsUserError);

        final Observer<ProductBrands> getProductBrandsUserSuccess = new Observer<ProductBrands>() {
            @Override
            public void onChanged(ProductBrands categories) {
                hideProgressDialog();
                brandTypeArraylist = categories.getResultArrayList();
                brandTypeSearchArrayList.clear();
                for (ProductBrands.Result employees1 : brandTypeArraylist) {
                    brandTypeSearchArrayList.add(new Search(employees1.getId(), employees1.getBrandName()));
                }
            }
        };
        viewModel.getProductBrandsUserSuccess().observe(this, getProductBrandsUserSuccess);


        Observer<String> uploadImageUserError = new Observer<String>() {
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
        viewModel.uploadImageUserError().observe(this, uploadImageUserError);

        final Observer<ArrayList<Category>> uploadImageUserSuccess = new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> category) {
                hideProgressDialog();
                whichBox = category.get(0).getUrl();
                String imageUrl = "";
                String imageFileName = "";
                imageUrl = category.get(0).getUrl();
                imageFileName = category.get(0).getFile_name();
                ImageUploadUrlArrayList.add(imageUrl);
                ImageUploadFileNameArrayList.add(imageFileName);
            }
        };
        viewModel.uploadImageUserSuccess().observe(this, uploadImageUserSuccess);


        //addProductApi
        Observer<String> addCategoryUserError = new Observer<String>() {
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
        viewModel.addCategoryUserError().observe(this, addCategoryUserError);

        final Observer<Product> addCategoryUserSuccess = new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                hideProgressDialog();
                System.out.println("beforeUploadImage111" + ImageUploadUrlArrayList.toString());
                System.out.println("beforeUploadImage111" + ImageUploadFileNameArrayList.toString());
                JSONObject jsonObject2 = new JSONObject();
                try {
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < ImageUploadUrlArrayList.size(); i++) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("url", ImageUploadUrlArrayList.get(i));
                        jsonObject1.put("file_name", ImageUploadFileNameArrayList.get(i));
                        System.out.println("beforeUploadImage222" + ImageUploadUrlArrayList.get(i));
                        System.out.println("beforeUploadImage222" + ImageUploadFileNameArrayList.get(i));
                        jsonArray.put(jsonObject1);
                    }
                    jsonObject2.put("product_id", product.getId());
                    jsonObject2.put("image_list_url", jsonArray);
                    System.out.println("OuterJsonObject1" + jsonObject2.toString());

                    RequestBody requestBody = RequestUtils.createRequestBodyForString(jsonObject2.toString());

                    viewModel.addProductImage(requestBody, "application/json", "application/json",
                            sharedPreferencesHelper.getKeyToken());
                    showProgressDialog();
                } catch (Exception e) {

                }
            }
        };
        viewModel.addCategoryUserSuccess().observe(this, addCategoryUserSuccess);


        //ProductImageApi
        Observer<String> addProductImageUserError = new Observer<String>() {
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
        viewModel.addProductImageUserError().observe(this, addProductImageUserError);

        final Observer<Product> addProductImageUserSuccess = new Observer<Product>() {
            @Override
            public void onChanged(Product user) {
                hideProgressDialog();
                finish();
            }
        };
        viewModel.addProductImageUserSuccess().observe(this, addProductImageUserSuccess);


        //GetProductDetailWhileEdit
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
                    viewBinding.selectedImagesRecyclerView.setVisibility(View.GONE);
                } else {
                    whichBox = "1";
                    viewBinding.selectedImagesRecyclerView.setVisibility(View.VISIBLE);
                }
                uploadedImageList = product.getResult().getProductImage();
                uploadImagesAdapter.addArrayList(uploadedImageList);
                subCategoryId = product.getResult().getSubCategoryId();
                brandId = product.getResult().getBrandId();
                viewBinding.selectSubCategoryAppCompatAutoCompleteTextView.setText(product.getResult().getSubCategoryName());
                viewBinding.selectBrandAppCompatAutoCompleteTextView.setText(product.getResult().getBrandName());
                viewBinding.productNameTextInputEditText.setText(product.getResult().getProductName());
                viewBinding.priceTextInputEditText.setText(String.valueOf(product.getResult().getPrice()));
                viewBinding.wholePriceTextInputEditText.setText(product.getResult().getWholeSalePrice());
                viewBinding.baseUnitTextInputEditText.setText(product.getResult().getBaseUnit());
                viewBinding.quantityTextInputEditText.setText(product.getResult().getQuantity());
                viewBinding.discountTextInputEditText.setText(String.valueOf(product.getResult().getDiscount()));
                viewBinding.descriptionTextInputEditText.setText(product.getResult().getProductDescription());
            }
        };
        viewModel.getProductIdListUserSuccess().observe(this, getProductIdListUserSuccess);


        //addProductApi
        Observer<String> editSubCategoryUserError = new Observer<String>() {
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
        viewModel.editSubCategoryUserError().observe(this, editSubCategoryUserError);

        final Observer<Product> editSubCategoryUserSuccess = new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                hideProgressDialog();
                System.out.println("beforeUploadImage111" + ImageUploadUrlArrayList.toString());
                System.out.println("beforeUploadImage111" + ImageUploadFileNameArrayList.toString());

                if (ImageUploadUrlArrayList.size() == 0) {
                    finish();
                } else {
                    JSONObject jsonObject2 = new JSONObject();
                    try {
                        JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < ImageUploadUrlArrayList.size(); i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("url", ImageUploadUrlArrayList.get(i));
                            jsonObject1.put("file_name", ImageUploadFileNameArrayList.get(i));
                            System.out.println("beforeUploadImage222" + ImageUploadUrlArrayList.get(i));
                            System.out.println("beforeUploadImage222" + ImageUploadFileNameArrayList.get(i));
                            jsonArray.put(jsonObject1);
                        }
                        jsonObject2.put("product_id", product.getId());
                        jsonObject2.put("image_list_url", jsonArray);
                        System.out.println("OuterJsonObject1" + jsonObject2.toString());

                        RequestBody requestBody = RequestUtils.createRequestBodyForString(jsonObject2.toString());

                        viewModel.addProductImage(requestBody, "application/json", "application/json",
                                sharedPreferencesHelper.getKeyToken());
                        showProgressDialog();
                    } catch (Exception e) {

                    }
                }
            }
        };
        viewModel.editSubCategoryUserSuccess().observe(this, editSubCategoryUserSuccess);


        //deleteProductImageView
        Observer<String> deleteProductImageUserError = new Observer<String>() {
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
        viewModel.deleteProductImageUserError().observe(this, deleteProductImageUserError);

        final Observer<ProductId> deleteProductImageUserSuccess = new Observer<ProductId>() {
            @Override
            public void onChanged(ProductId product) {
                hideProgressDialog();
                if(product.getResult().getProductImage().size()==0){
                    whichBox="";
                    viewBinding.selectedImagesRecyclerView.setVisibility(View.GONE);
                }else{
                    uploadedImageList.clear();
                    uploadedImageList = product.getResult().getProductImage();
                    uploadImagesAdapter.clearAllItem();
                    uploadImagesAdapter.replaceArrayList(uploadedImageList);
                    viewBinding.selectedImagesRecyclerView.setAdapter(uploadImagesAdapter);
                }
            }
        };
        viewModel.deleteProductImageUserSuccess().observe(this, deleteProductImageUserSuccess);


    }

    @Override
    protected void setOnClickListener() {
        viewBinding.icBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewBinding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subCategory = viewBinding.selectSubCategoryAppCompatAutoCompleteTextView.getText().toString().trim();
                String brand = viewBinding.selectBrandAppCompatAutoCompleteTextView.getText().toString().trim();
                String productName = viewBinding.productNameTextInputEditText.getText().toString().trim();
                String price = viewBinding.priceTextInputEditText.getText().toString().trim();
                String wholeSalePrice = viewBinding.wholePriceTextInputEditText.getText().toString().trim();
                String baseUnit = viewBinding.baseUnitTextInputEditText.getText().toString().trim();
                String quantity = viewBinding.quantityTextInputEditText.getText().toString().trim();
                String discount = viewBinding.discountTextInputEditText.getText().toString().trim();
                String description = viewBinding.descriptionTextInputEditText.getText().toString().trim();

                if (validation(subCategory, brand, productName, price, wholeSalePrice, baseUnit,
                        quantity, discount, description)) {
                    if (from.equalsIgnoreCase("0")) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("product_name", productName);
                            jsonObject.put("price", price);
                            jsonObject.put("whole_sale_price", wholeSalePrice);
                            jsonObject.put("base_unit", baseUnit);
                            jsonObject.put("quantity", quantity);
                            jsonObject.put("discount", discount);
                            jsonObject.put("sub_category_id", subCategoryId);
                            jsonObject.put("brand_id", brandId);
                            jsonObject.put("product_description", description);
                            RequestBody requestBody = RequestUtils.createRequestBodyForString(jsonObject.toString());

                            viewModel.addProduct(requestBody, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());

                            System.out.println("AddProductTime" + ImageUploadUrlArrayList.toString());
                            System.out.println("AddProductTime" + ImageUploadFileNameArrayList.toString());

                        } catch (Exception exception) {

                        }
                    } else if (from.equalsIgnoreCase("1")) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("product_name", productName);
                            jsonObject.put("price", price);
                            jsonObject.put("whole_sale_price", wholeSalePrice);
                            jsonObject.put("base_unit", baseUnit);
                            jsonObject.put("quantity", quantity);
                            jsonObject.put("discount", discount);
                            jsonObject.put("sub_category_id", subCategoryId);
                            jsonObject.put("brand_id", brandId);
                            jsonObject.put("product_description", description);
                            RequestBody requestBody = RequestUtils.createRequestBodyForString(jsonObject.toString());

                            viewModel.editProduct(productId, requestBody, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());

                            System.out.println("AddProductTime" + ImageUploadUrlArrayList.toString());
                            System.out.println("AddProductTime" + ImageUploadFileNameArrayList.toString());

                        } catch (Exception exception) {

                        }
                    }


                }


            }
        });

        viewBinding.cameraLinearLayout1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (managePermission.hasPermission(MULTIPLE_PERMISSIONS)) {
                    Log.e("TAG", "permission already granted");
                    //  showPictureDialog(AddProductActivity.this);
                    // initialising intent
                    Intent intent = new Intent();

                    // setting type to select to be image
                    intent.setType("image/*");

                    // allowing multiple image to be selected
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
                } else {
                    Log.e("TAG", "permission is not granted, request for permission");
                    requestPermissions(
                            MULTIPLE_PERMISSIONS,
                            MULTIPLE_PERMISSION_REQUEST_CODE);
                }
            }
        });

        viewBinding.selectSubCategoryAppCompatAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vehicleTypeSearchArrayList.size() != 0) {
                    SearchUtils.searchDialog(AddProductActivity.this, "Select Sub Category", vehicleTypeSearchArrayList, new SelectListener() {
                        @Override
                        public void onSelected(Search search, int position) {
                            viewBinding.selectSubCategoryAppCompatAutoCompleteTextView.setText(search.getItemName());
                            subCategoryId = search.getItemId();

                            System.out.println("=====subCategoryId=====" + subCategoryId);

                        }
                    });
                }
            }
        });
        viewBinding.selectBrandAppCompatAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (brandTypeSearchArrayList.size() != 0) {
                    SearchUtils.searchDialog(AddProductActivity.this, "Select Brand", brandTypeSearchArrayList, new SelectListener() {
                        @Override
                        public void onSelected(Search search, int position) {
                            viewBinding.selectBrandAppCompatAutoCompleteTextView.setText(search.getItemName());
                            brandId = search.getItemId();

                            System.out.println("=====brandId=====" + brandId);

                        }
                    });
                }
            }
        });

        carGalleryImagesAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<Category>() {
            @Override
            public void OnItemChildClick(View viewChild, Category category, int position) {
                switch (viewChild.getId()) {
                    case R.id.crossCardView1:
                        stringArrayList.remove(position);
                        carGalleryImagesAdapter.replaceArrayList(stringArrayList);
                        viewBinding.uploadedImagesRecyclerView.setAdapter(carGalleryImagesAdapter);
                        if (stringArrayList.size() == 0) {
                            whichBox = "";
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        uploadImagesAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<ProductId.Result.ProductImage>() {
            @Override
            public void OnItemChildClick(View viewChild, ProductId.Result.ProductImage category, int position) {
                switch (viewChild.getId()) {
                    case R.id.crossCardView1:
                        viewModel.deleteProductImage(category.getId(), "application/json", "application/json",
                                sharedPreferencesHelper.getKeyToken());
                        showProgressDialog();
                        break;
                    default:
                        break;
                }
            }
        });


    }

    private boolean validation(String subCategory, String brand, String productName, String price, String wholeSalePrice,
                               String baseUnit, String quantity, String discount, String description) {
        boolean isValid = true;

        if (subCategory.equalsIgnoreCase("")) {
            viewBinding.selectSubCategoryTextInputLayout.setError("Please Select SubCategory");
            viewBinding.selectSubCategoryTextInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.selectSubCategoryTextInputLayout.setError("");
            viewBinding.selectSubCategoryTextInputLayout.setErrorEnabled(false);
        }
        if (brand.equalsIgnoreCase("")) {
            viewBinding.selectBrandTextInputLayout.setError("Please Select Brand");
            viewBinding.selectBrandTextInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.selectBrandTextInputLayout.setError("");
            viewBinding.selectBrandTextInputLayout.setErrorEnabled(false);
        }
        if (productName.equalsIgnoreCase("")) {
            viewBinding.productNameInputLayout.setError("Please Enter Product");
            viewBinding.productNameInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.productNameInputLayout.setError("");
            viewBinding.productNameInputLayout.setErrorEnabled(false);
        }
        if (price.equalsIgnoreCase("")) {
            viewBinding.priceInputLayout.setError("Please Enter Price");
            viewBinding.priceInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.priceInputLayout.setError("");
            viewBinding.priceInputLayout.setErrorEnabled(false);
        }
        if (wholeSalePrice.equalsIgnoreCase("")) {
            viewBinding.wholePriceInputLayout.setError("Please Enter Whole Sale Price");
            viewBinding.wholePriceInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.wholePriceInputLayout.setError("");
            viewBinding.wholePriceInputLayout.setErrorEnabled(false);
        }
        if (baseUnit.equalsIgnoreCase("")) {
            viewBinding.baseUnitInputLayout.setError("Please Enter Base Unit");
            viewBinding.baseUnitInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.baseUnitInputLayout.setError("");
            viewBinding.baseUnitInputLayout.setErrorEnabled(false);
        }
        if (quantity.equalsIgnoreCase("")) {
            viewBinding.quantityInputLayout.setError("Please Enter Quantity");
            viewBinding.quantityInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.quantityInputLayout.setError("");
            viewBinding.quantityInputLayout.setErrorEnabled(false);
        }
        if (discount.equalsIgnoreCase("")) {
            viewBinding.discountInputLayout.setError("Please Enter Discount");
            viewBinding.discountInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.discountInputLayout.setError("");
            viewBinding.discountInputLayout.setErrorEnabled(false);
        }
        if (description.equalsIgnoreCase("")) {
            viewBinding.descriptionInputLayout.setError("Please Enter Description");
            viewBinding.descriptionInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.descriptionInputLayout.setError("");
            viewBinding.descriptionInputLayout.setErrorEnabled(false);
        }
        if (whichBox.equalsIgnoreCase("")) {
            Toast.makeText(AddProductActivity.this, "Please Upload At Least one Image of Product", Toast.LENGTH_SHORT).show();
            showSnackBar(viewBinding.AddProductActivity, "Please Upload At Least one Image of Product");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }


    @Override
    public void showProgressDialog() {
        if (viewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.GONE) {
            viewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

    @Override
    public void hideProgressDialog() {
        if (viewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.VISIBLE) {
            viewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                String id = "";
                int countOfId = 0;
                stringArrayList.clear();
                ImageUploadUrlArrayList.clear();
                ImageUploadFileNameArrayList.clear();
                for (int i = 0; i < count; i++) {
                    Uri imageUrl = data.getClipData().getItemAt(i).getUri();
                    mArrayUri.add(imageUrl);

                    String path = RealPathUtils.getRealPath(AddProductActivity.this, imageUrl);
                    File uncompressFile = new File(path);
                    System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                    System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());
                    File compressFile;
                    try {
                        compressFile = Compressor.getDefault(AddProductActivity.this).compressToFile(uncompressFile);
                        System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                        System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                        path = compressFile.getPath();

                        ++countOfId;
                        id = String.valueOf(countOfId);
                        stringArrayList.add(new Category(id, path));

                        MultipartBody.Part profilePic;
                        profilePic = RequestUtils.createMultipartBody("filename", path);
                        viewModel.image(profilePic, sharedPreferencesHelper.getKeyToken());
                        showProgressDialog();

                    } catch (Exception e) {

                    }
                    // viewBinding.productImageView1.setImageURI(mArrayUri.get(0));

                }
                Log.e("mArrayUriIf", "" + mArrayUri);
                Log.e("stringArrayListIf", "" + stringArrayList);
                carGalleryImagesAdapter.clearAllItem();
                carGalleryImagesAdapter.addArrayList(stringArrayList);

            } else {
                Uri imageUrl = data.getData();
                mArrayUri.add(imageUrl);

                String id = "";
                int countOfId = 0;

                String path = RealPathUtils.getRealPath(AddProductActivity.this, imageUrl);
                File uncompressFile = new File(path);
                System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());
                File compressFile;
                try {
                    compressFile = Compressor.getDefault(AddProductActivity.this).compressToFile(uncompressFile);
                    System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                    System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                    path = compressFile.getPath();
                    ++countOfId;
                    id = String.valueOf(countOfId);
                    stringArrayList.clear();
                    ImageUploadUrlArrayList.clear();
                    ImageUploadFileNameArrayList.clear();
                    stringArrayList.add(new Category(id, path));
                    MultipartBody.Part profilePic;
                    profilePic = RequestUtils.createMultipartBody("filename", path);
                    viewModel.image(profilePic, sharedPreferencesHelper.getKeyToken());
                    showProgressDialog();
                } catch (Exception e) {

                }
                Log.e("stringArrayListElse", "" + stringArrayList);
                carGalleryImagesAdapter.clearAllItem();
                carGalleryImagesAdapter.addArrayList(stringArrayList);
            }
        } else {
            // show this if no image is selected
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

}