package com.krishigadmin.android.ui.home.fragments.manageProduct.view;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.databinding.FragmentManageProductBinding;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.Product;
import com.krishigadmin.android.model.ProductBrands;
import com.krishigadmin.android.model.SubCategory;
import com.krishigadmin.android.searchdialog.Search;
import com.krishigadmin.android.searchdialog.SearchUtils;
import com.krishigadmin.android.searchdialog.SelectListener;
import com.krishigadmin.android.ui.AppConstants;
import com.krishigadmin.android.ui.base.BaseFragment;
import com.krishigadmin.android.ui.home.fragments.ProductDetail.view.ProductDetailActivity;
import com.krishigadmin.android.ui.home.fragments.addProduct.view.AddProductActivity;
import com.krishigadmin.android.ui.home.fragments.addProduct.viewmodel.ProductViewModel;
import com.krishigadmin.android.ui.home.fragments.addSubCategory.viewmodel.SubCategoryModel;
import com.krishigadmin.android.ui.home.fragments.manageProduct.adapter.ManageProductAdapter;
import com.krishigadmin.android.ui.home.fragments.manageProduct.adapter.SelectBrandAdapter;
import com.krishigadmin.android.ui.home.fragments.manageProduct.adapter.SelectSubCategoryAdapter;
import com.library.adapter.recyclerview.LayoutManagerUtils;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ManageProductFragment extends BaseFragment<FragmentManageProductBinding> {
    ProductViewModel viewModel;

    ManageProductAdapter manageProductAdapter;
    ArrayList<Product.Result> homeArrayList = new ArrayList<Product.Result>();
    ArrayList<Product.Result> dataStoreHomeArrayList = new ArrayList<Product.Result>();

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    //BottomSheet
    SelectBrandAdapter categoryRecyclerViewAdapter;
    SelectSubCategoryAdapter selectSubCategoryAdapter;
    ArrayList<ProductBrands.Result> brandArrayList = new ArrayList<>();
    ArrayList<SubCategory.Result> subCategoryArrayList = new ArrayList<>();


    private ArrayList<Search> vehicleTypeSearchArrayList = new ArrayList<Search>();
    private ArrayList<SubCategory.Result> vehicleTypeArraylist = new ArrayList<SubCategory.Result>();

    private ArrayList<Search> brandTypeSearchArrayList = new ArrayList<Search>();
    private ArrayList<ProductBrands.Result> brandTypeArraylist = new ArrayList<ProductBrands.Result>();

    private ArrayList<Search> categorySearchArrayList = new ArrayList<Search>();
    private ArrayList<Category> categoryArraylist = new ArrayList<Category>();

    String from = "", subCategoryId = "", categoryId = "", brandId = "", subCategoryName = "", brandName = "";

    boolean visible = true;

    int totalPages = 0, count = 1;
    String searchData = "";

    String itemPerPageInProduct = "5";

    BottomSheetDialog bottomSheetDialog;
    TextView subCategoryTextView;
    EditText searchEditText;
    RecyclerView seedsRecyclerView;
    ImageView errorImageViewSeeds;
    ImageView cancelImageView;
    TextView errorTextViewSeeds;

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(getActivity());
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected FragmentManageProductBinding getViewBinding() {
        return FragmentManageProductBinding.inflate(getLayoutInflater());
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
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.dialog_select_brand_sub_category);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        subCategoryTextView = bottomSheetDialog.findViewById(R.id.subCategoryTextView);
        searchEditText = bottomSheetDialog.findViewById(R.id.searchEditText);
        seedsRecyclerView = bottomSheetDialog.findViewById(R.id.seedsRecyclerView);
        errorImageViewSeeds = bottomSheetDialog.findViewById(R.id.errorImageViewSeeds);
        cancelImageView = bottomSheetDialog.findViewById(R.id.cancelImageView);
        errorTextViewSeeds = bottomSheetDialog.findViewById(R.id.errorTextViewSeeds);
        setRecyclerView(viewBinding.recyclerView);
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        manageProductAdapter = new ManageProductAdapter(getContext());
        manageProductAdapter.addArrayList(homeArrayList);
        recyclerView.setAdapter(manageProductAdapter);
    }

    @Override
    protected void addTextChangedListener() {
        viewBinding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchData = editable.toString();
                if (searchData.equalsIgnoreCase("")) {
                } else {
                    dataStoreHomeArrayList.clear();
                    count = 1;
                    viewModel.productSearch(itemPerPageInProduct, String.valueOf(count), editable.toString(), "application/json", "application/json",
                            sharedPreferencesHelper.getKeyToken());
                }


            }
        });

    }

    @Override
    protected void setOnFocusChangeListener() {

    }

    @Override
    protected void observeViewModel() {
        Log.e("", "observer--------------------------------");
        viewModel.getProduct(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                sharedPreferencesHelper.getKeyToken());
        showProgressDialog();

        Observer<String> getProductListUserError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
                viewBinding.idPBLoading.setVisibility(View.GONE);
            }
        };
        viewModel.getProductListUserError().observe(this, getProductListUserError);

        final Observer<Product> getProductListUserSuccess = new Observer<Product>() {
            @Override
            public void onChanged(Product categories) {
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                if (categories.getResult() == null) {
                    viewBinding.recyclerView.setVisibility(View.GONE);
                    viewBinding.errorImageView.setVisibility(View.VISIBLE);
                    viewBinding.errorTextView.setVisibility(View.VISIBLE);
                } else {
                    totalPages = categories.getTotalPages();
                    viewBinding.recyclerView.setVisibility(View.VISIBLE);
                    viewBinding.errorImageView.setVisibility(View.GONE);
                    viewBinding.errorTextView.setVisibility(View.GONE);
                    homeArrayList = categories.getResult();
                    dataStoreHomeArrayList.addAll(homeArrayList);
                    Log.e("", "dataStoreHomeArrayList==" + dataStoreHomeArrayList.size());
                    Log.e("", "homeArrayList==" + homeArrayList.size());
                    manageProductAdapter.clearAllItem();
                    manageProductAdapter.replaceArrayList(dataStoreHomeArrayList);
                }

            }
        };
        viewModel.getProductListUserSuccess().observe(this, getProductListUserSuccess);


        Observer<String> getProductSearchUserError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.getProductSearchUserError().observe(this, getProductSearchUserError);

        final Observer<Product> getProductSearchUserSuccess = new Observer<Product>() {
            @Override
            public void onChanged(Product categories) {
                hideProgressDialog();

                viewBinding.idPBLoading.setVisibility(View.GONE);

                if (categories.getResult().size() == 0) {
                    viewBinding.recyclerView.setVisibility(View.GONE);
                    viewBinding.errorImageView.setVisibility(View.VISIBLE);
                    viewBinding.errorTextView.setVisibility(View.VISIBLE);
                } else {
                    totalPages = categories.getTotalPages();
                    viewBinding.recyclerView.setVisibility(View.VISIBLE);
                    viewBinding.errorImageView.setVisibility(View.GONE);
                    viewBinding.errorTextView.setVisibility(View.GONE);
                    homeArrayList = categories.getResult();
                    dataStoreHomeArrayList.addAll(homeArrayList);
                    Log.e("", "dataStoreHomeArrayList==" + dataStoreHomeArrayList.size());
                    Log.e("", "homeArrayList==" + homeArrayList.size());
                    manageProductAdapter.clearAllItem();
                    manageProductAdapter.replaceArrayList(dataStoreHomeArrayList);
                }

            }
        };
        viewModel.getProductSearchUserSuccess().observe(this, getProductSearchUserSuccess);


       /* viewModel.getSubCategories("application/json", "application/json",
                sharedPreferencesHelper.getKeyToken());
        showProgressDialog();

        Observer<String> getSubCategoryUserError = new Observer<String>() {
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
        viewModel.getSubCategoryUserError().observe(this, getSubCategoryUserError);

        final Observer<SubCategory> getSubCategoryUserSuccess = new Observer<SubCategory>() {
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
        viewModel.getSubCategoryUserSuccess().observe(this, getSubCategoryUserSuccess);
*/

        viewModel.getCategories("application/json", "application/json",
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
        viewModel.getCategoryUserError().observe(this, getCategoryUserError);

        final Observer<ArrayList<Category>> getCategoryUserSuccess = new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> categories) {
                hideProgressDialog();
                categoryArraylist = categories;
                categorySearchArrayList.clear();
                for (Category employees1 : categoryArraylist) {
                    categorySearchArrayList.add(new Search(employees1.getId(), employees1.getCategory_name()));
                }

            }
        };
        viewModel.getCategoryUserSuccess().observe(this, getCategoryUserSuccess);

   /*     viewModel.getProductBrands("application/json", "application/json",
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
        viewModel.getProductBrandsUserSuccess().observe(this, getProductBrandsUserSuccess);*/


        //filter
        Observer<String> getFilterSearchUserError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.getFilterSearchUserError().observe(this, getFilterSearchUserError);

        final Observer<Product> getFilterSearchUserSuccess = new Observer<Product>() {
            @Override
            public void onChanged(Product categories) {
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                if (categories.getResult().size() == 0) {
                    viewBinding.recyclerView.setVisibility(View.GONE);
                    viewBinding.errorImageView.setVisibility(View.VISIBLE);
                    viewBinding.errorTextView.setVisibility(View.VISIBLE);
                } else {
                    totalPages = categories.getTotalPages();
                    viewBinding.recyclerView.setVisibility(View.VISIBLE);
                    viewBinding.errorImageView.setVisibility(View.GONE);
                    viewBinding.errorTextView.setVisibility(View.GONE);
                    homeArrayList = categories.getResult();
                    dataStoreHomeArrayList.addAll(homeArrayList);
                    Log.e("", "dataStoreHomeArrayList==" + dataStoreHomeArrayList.size());
                    Log.e("", "homeArrayList==" + homeArrayList.size());
                    manageProductAdapter.clearAllItem();
                    manageProductAdapter.replaceArrayList(dataStoreHomeArrayList);
                }

            }
        };
        viewModel.getFilterSearchUserSuccess().observe(this, getFilterSearchUserSuccess);


        //BrandSearch
        Observer<String> getBrandSearchUserError = new Observer<String>() {
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
        viewModel.getBrandSearchUserError().observe(this, getBrandSearchUserError);

        final Observer<ProductBrands> getBrandSearchUserSuccess = new Observer<ProductBrands>() {
            @Override
            public void onChanged(ProductBrands categories) {
                hideProgressDialog();
                if (categories != null) {
                    int size = categories.getResultArrayList().size();
                    if (size == 0) {
                        seedsRecyclerView.setVisibility(View.GONE);
                        errorImageViewSeeds.setVisibility(View.VISIBLE);
                        errorTextViewSeeds.setVisibility(View.VISIBLE);
                    } else {
                        seedsRecyclerView.setVisibility(View.VISIBLE);
                        errorImageViewSeeds.setVisibility(View.GONE);
                        errorTextViewSeeds.setVisibility(View.GONE);
                        brandArrayList = categories.getResultArrayList();
                        categoryRecyclerViewAdapter.clearAllItem();
                        categoryRecyclerViewAdapter.replaceArrayList(brandArrayList);
                    }
                }

            }
        };
        viewModel.getBrandSearchUserSuccess().observe(this, getBrandSearchUserSuccess);


        //SubCategorySearch
        Observer<String> getSubCategorySearchUserError = new Observer<String>() {
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
        viewModel.getSubCategorySearchUserError().observe(this, getSubCategorySearchUserError);

        final Observer<SubCategory> getSubCategorySearchUserSuccess = new Observer<SubCategory>() {
            @Override
            public void onChanged(SubCategory categories) {
                hideProgressDialog();
                if (categories != null) {
                    int size = categories.getResultArrayList().size();
                    if (size == 0) {
                        seedsRecyclerView.setVisibility(View.GONE);
                        errorImageViewSeeds.setVisibility(View.VISIBLE);
                        errorTextViewSeeds.setVisibility(View.VISIBLE);
                    } else {
                        seedsRecyclerView.setVisibility(View.VISIBLE);
                        errorImageViewSeeds.setVisibility(View.GONE);
                        errorTextViewSeeds.setVisibility(View.GONE);
                        subCategoryArrayList = categories.getResultArrayList();
                        selectSubCategoryAdapter.clearAllItem();
                        selectSubCategoryAdapter.replaceArrayList(subCategoryArrayList);
                    }
                }


            }
        };
        viewModel.getSubCategorySearchUserSuccess().observe(this, getSubCategorySearchUserSuccess);


    }

    @Override
    protected void setOnClickListener() {

        viewBinding.addMaterialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                intent.putExtra(AppConstants.Extras.FROM, "0");
                startActivity(intent);
            }
        });
        manageProductAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<Product.Result>() {
            @Override
            public void OnItemChildClick(View viewChild, Product.Result product, int position) {
                switch (viewChild.getId()) {
                    case R.id.viewDetailButton:
                        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                        intent.putExtra(AppConstants.Extras.PRODUCT_BRAND_ID, product.getId());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

        viewBinding.selectCategoryAppCompatAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categorySearchArrayList.size() != 0) {
                    SearchUtils.searchDialog(getActivity(), "Select Category", categorySearchArrayList, new SelectListener() {
                        @Override
                        public void onSelected(Search search, int position) {
                            viewBinding.selectCategoryAppCompatAutoCompleteTextView.setText(search.getItemName());
                            categoryId = search.getItemId();
                            System.out.println("=====categoryId=====" + categoryId);
                            dataStoreHomeArrayList.clear();
                            count = 1;
                            searchData = "";
                            viewBinding.searchEditText.setText("");
                            viewModel.productFilter(categoryId, subCategoryId, brandId, itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                            showProgressDialog();

                        }
                    });
                }
            }
        });

        viewBinding.selectSubCategoryAppCompatAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog("SubCategory");
            }
        });

        viewBinding.selectBrandAppCompatAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog("Brand");
            }
        });


        viewBinding.filterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visible) {
                    visible = false;
                    viewBinding.linear.setVisibility(View.VISIBLE);
                } else {
                    visible = true;
                    viewBinding.linear.setVisibility(View.GONE);
                }
            }
        });

        viewBinding.clearTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBinding.selectBrandAppCompatAutoCompleteTextView.setText("");
                viewBinding.selectSubCategoryAppCompatAutoCompleteTextView.setText("");
                viewBinding.selectCategoryAppCompatAutoCompleteTextView.setText("");
                subCategoryId = "";
                brandId = "";
                categoryId = "";
                count = 1;
                searchData = "";
                viewBinding.searchEditText.setText("");
                dataStoreHomeArrayList.clear();
                Log.e("", "clearAll--------------------------------");
                viewModel.getProduct(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
                showProgressDialog();
            }
        });

        viewBinding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    ++count;
                    if (count <= totalPages) {
                        viewBinding.idPBLoading.setVisibility(View.VISIBLE);
                        if (!subCategoryId.equalsIgnoreCase("")) {
                            viewModel.productFilter(categoryId, subCategoryId, brandId, itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                            showProgressDialog();
                        } else if (!brandId.equalsIgnoreCase("")) {
                            viewModel.productFilter(categoryId, subCategoryId, brandId, itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                            showProgressDialog();
                        } else if (!categoryId.equalsIgnoreCase("")) {
                            viewModel.productFilter(categoryId, subCategoryId, brandId, itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                            showProgressDialog();
                        } else if (!searchData.equalsIgnoreCase("")) {
                            viewModel.productSearch(itemPerPageInProduct, String.valueOf(count), searchData, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else {
                            Log.e("", "scroller--------------------------------");
                            viewModel.getProduct(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        }

                    }

                }
            }
        });




        viewBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewBinding.refreshLayout.setRefreshing(false);
                viewBinding.selectBrandAppCompatAutoCompleteTextView.setText("");
                viewBinding.selectSubCategoryAppCompatAutoCompleteTextView.setText("");
                viewBinding.selectCategoryAppCompatAutoCompleteTextView.setText("");
                subCategoryId = "";
                brandId = "";
                categoryId = "";
                count = 1;
                searchData = "";
                viewBinding.searchEditText.setText("");
                dataStoreHomeArrayList.clear();
                Log.e("", "clearAll--------------------------------");
                viewModel.getProduct(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
                showProgressDialog();
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
        if (viewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.GONE) {
            viewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.VISIBLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

    @Override
    public void hideProgressDialog() {
        if (viewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.VISIBLE) {
            viewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.GONE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private void showBottomSheetDialog(String from) {

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("")) {
                    seedsRecyclerView.setVisibility(View.GONE);
                    errorImageViewSeeds.setVisibility(View.VISIBLE);
                    errorTextViewSeeds.setVisibility(View.VISIBLE);
                } else {
                    seedsRecyclerView.setVisibility(View.VISIBLE);
                    errorImageViewSeeds.setVisibility(View.GONE);
                    errorTextViewSeeds.setVisibility(View.GONE);

                    if (from.equalsIgnoreCase("Brand")) {
                        viewModel.subProductBrandsSearch(editable.toString(), "application/json", "application/json",
                                sharedPreferencesHelper.getKeyToken());
                    } else if (from.equalsIgnoreCase("SubCategory")) {
                        viewModel.subCategorySearch(editable.toString(), "application/json", "application/json",
                                sharedPreferencesHelper.getKeyToken());
                    }
                }
            }
        });


        if (from.equalsIgnoreCase("Brand")) {
            setBrandRecyclerView(seedsRecyclerView);
            subCategoryTextView.setText("Select Brand");
            searchEditText.setHint("Search Brand");
        } else if (from.equalsIgnoreCase("SubCategory")) {
            setSubCategoryRecyclerView(seedsRecyclerView);
            subCategoryTextView.setText("Select SubCategory");
            searchEditText.setHint("Search SubCategory");
        }
        searchEditText.setText("");
        seedsRecyclerView.setVisibility(View.GONE);
        errorImageViewSeeds.setVisibility(View.VISIBLE);
        errorTextViewSeeds.setVisibility(View.VISIBLE);


        if (from.equalsIgnoreCase("Brand")) {
            categoryRecyclerViewAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<ProductBrands.Result>() {
                @Override
                public void OnItemChildClick(View viewChild, ProductBrands.Result market, int position) {
                    switch (viewChild.getId()) {
                        case R.id.searchDialogRowLinearLayout:
                            brandId = market.getId();
                            brandName = market.getBrandName();
                            bottomSheetDialog.dismiss();
                            System.out.println("=====brandId=====" + brandId);

                            dataStoreHomeArrayList.clear();
                            count = 1;
                            searchData = "";
                            viewBinding.searchEditText.setText("");
                            viewModel.productFilter(categoryId, subCategoryId, brandId, itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                            showProgressDialog();
                            viewBinding.selectBrandAppCompatAutoCompleteTextView.setText(brandName);
                            break;
                        default:
                            break;

                    }
                }
            });
        } else if (from.equalsIgnoreCase("SubCategory")) {
            selectSubCategoryAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<SubCategory.Result>() {
                @Override
                public void OnItemChildClick(View viewChild, SubCategory.Result market, int position) {
                    switch (viewChild.getId()) {
                        case R.id.searchDialogRowLinearLayout:
                            subCategoryId = market.getId();
                            subCategoryName = market.getSubCategoryName();
                            bottomSheetDialog.dismiss();
                            viewBinding.selectSubCategoryAppCompatAutoCompleteTextView.setText(subCategoryName);
                            System.out.println("=====subCategoryId=====" + subCategoryId);

                            dataStoreHomeArrayList.clear();
                            count = 1;
                            searchData = "";
                            viewBinding.searchEditText.setText("");
                            viewModel.productFilter(categoryId, subCategoryId, brandId, itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                            showProgressDialog();
                            break;
                        default:
                            break;

                    }
                }
            });

        }

        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();


    }

    private void setBrandRecyclerView(RecyclerView seedsRecyclerView) {
        seedsRecyclerView.setHasFixedSize(true);
        seedsRecyclerView.setLayoutManager(LayoutManagerUtils.getLinearLayoutManagerVertical(getActivity()));
        categoryRecyclerViewAdapter = new SelectBrandAdapter(getActivity());
        categoryRecyclerViewAdapter.addArrayList(brandArrayList);
        seedsRecyclerView.setAdapter(categoryRecyclerViewAdapter);
    }

    private void setSubCategoryRecyclerView(RecyclerView seedsRecyclerView) {
        seedsRecyclerView.setHasFixedSize(true);
        seedsRecyclerView.setLayoutManager(LayoutManagerUtils.getLinearLayoutManagerVertical(getActivity()));
        selectSubCategoryAdapter = new SelectSubCategoryAdapter(getActivity());
        selectSubCategoryAdapter.addArrayList(subCategoryArrayList);
        seedsRecyclerView.setAdapter(selectSubCategoryAdapter);
    }

}