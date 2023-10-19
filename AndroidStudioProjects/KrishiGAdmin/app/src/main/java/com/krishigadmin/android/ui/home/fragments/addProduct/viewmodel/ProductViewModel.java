package com.krishigadmin.android.ui.home.fragments.addProduct.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.krishigadmin.android.data.remote.ApiCallback;
import com.krishigadmin.android.data.remote.ApiResponseArray;
import com.krishigadmin.android.data.remote.ApiResponseObject;
import com.krishigadmin.android.data.remote.exception.NetworkException;
import com.krishigadmin.android.data.repository.LocalRepository;
import com.krishigadmin.android.data.repository.RemoteRepository;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.Product;
import com.krishigadmin.android.model.ProductBrands;
import com.krishigadmin.android.model.ProductId;
import com.krishigadmin.android.model.SubCategory;
import com.krishigadmin.android.model.User;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

@HiltViewModel
public class ProductViewModel extends ViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;


    @Inject
    public ProductViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }


    //ADD
    private MutableLiveData<Product> addCategoryUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> addCategoryUserError = new MutableLiveData<>();

    public MutableLiveData<Product> addCategoryUserSuccess() {
        return addCategoryUserSuccess;
    }

    public MutableLiveData<String> addCategoryUserError() {
        return addCategoryUserError;
    }

    public void addProduct(RequestBody body, String accept, String authorisation, String token) {
        remoteRepository.addProduct(body, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<Product>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<Product>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            addCategoryUserError.setValue(response.body().getMessage());
                        } else {
                            addCategoryUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        addCategoryUserError.setValue(response.body().getMessage());
                    }
                } else
                    addCategoryUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                addCategoryUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }


    ////GetProductBrands
    private MutableLiveData<ProductBrands> getProductBrandsUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getProductBrandsUserError = new MutableLiveData<>();

    public MutableLiveData<ProductBrands> getProductBrandsUserSuccess() {
        return getProductBrandsUserSuccess;
    }

    public MutableLiveData<String> getProductBrandsUserError() {
        return getProductBrandsUserError;
    }

    public void getProductBrands(String accept, String authorisation, String token) {
        remoteRepository.getProductBrands(accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<ProductBrands>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<ProductBrands>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            getProductBrandsUserError.setValue(response.body().getMessage());
                        } else {
                            getProductBrandsUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        getProductBrandsUserError.setValue(response.body().getMessage());
                    }
                } else
                    getProductBrandsUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                getProductBrandsUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }

    //Get
    private MutableLiveData<SubCategory> getSubCategoryUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getSubCategoryUserError = new MutableLiveData<>();

    public MutableLiveData<SubCategory> getSubCategoryUserSuccess() {
        return getSubCategoryUserSuccess;
    }

    public MutableLiveData<String> getSubCategoryUserError() {
        return getSubCategoryUserError;
    }

    public void getSubCategories(String accept, String authorisation, String token) {
        remoteRepository.getSubCategories(accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<SubCategory>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<SubCategory>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            getSubCategoryUserError.setValue(response.body().getMessage());
                        } else {
                            getSubCategoryUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        getSubCategoryUserError.setValue(response.body().getMessage());
                    }
                } else
                    getSubCategoryUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                getSubCategoryUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }


    //GetProduct
    private MutableLiveData<Product> getProductListUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getProductListUserError = new MutableLiveData<>();

    public MutableLiveData<Product> getProductListUserSuccess() {
        return getProductListUserSuccess;
    }

    public MutableLiveData<String> getProductListUserError() {
        return getProductListUserError;
    }

    public void getProduct(
            String items_per_page,
            String page_number, String accept, String authorisation, String token) {
        remoteRepository.getProduct(items_per_page, page_number, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<Product>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<Product>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            getProductListUserError.setValue(response.body().getMessage());
                        } else {
                            getProductListUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        getProductListUserError.setValue(response.body().getMessage());
                    }
                } else
                    getProductListUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                getProductListUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }


    //GetProductWithID
    private MutableLiveData<ProductId> getProductIdListUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getProductIdListUserError = new MutableLiveData<>();

    public MutableLiveData<ProductId> getProductIdListUserSuccess() {
        return getProductIdListUserSuccess;
    }

    public MutableLiveData<String> getProductIdListUserError() {
        return getProductIdListUserError;
    }

    public void getProductWithId(
            String id, String accept, String authorisation, String token) {
        remoteRepository.getProductWithId(id, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<ProductId>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<ProductId>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            getProductIdListUserError.setValue(response.body().getMessage());
                        } else {
                            getProductIdListUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        getProductIdListUserError.setValue(response.body().getMessage());
                    }
                } else
                    getProductIdListUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                getProductIdListUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }


    //Delete
    private MutableLiveData<ArrayList<Product>> deleteSubCategoryUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> deleteSubCategoryUserError = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Product>> deleteSubCategoryUserSuccess() {
        return deleteSubCategoryUserSuccess;
    }

    public MutableLiveData<String> deleteSubCategoryUserError() {
        return deleteSubCategoryUserError;
    }

    public void deleteProduct(String id, String accept, String authorisation, String token) {
        remoteRepository.deleteProduct(id, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseArray<Product>>() {
            @Override
            public void onSuccess(Response<ApiResponseArray<Product>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            deleteSubCategoryUserError.setValue(response.body().getMessage());
                        } else {
                            deleteSubCategoryUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        deleteSubCategoryUserError.setValue(response.body().getMessage());
                    }
                } else
                    deleteSubCategoryUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                deleteSubCategoryUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }


    //Edit
    private MutableLiveData<Product> editSubCategoryUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> editSubCategoryUserError = new MutableLiveData<>();

    public MutableLiveData<Product> editSubCategoryUserSuccess() {
        return editSubCategoryUserSuccess;
    }

    public MutableLiveData<String> editSubCategoryUserError() {
        return editSubCategoryUserError;
    }

    public void editProduct(String id, RequestBody body, String accept, String authorisation, String token) {
        remoteRepository.editProduct(id, body, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<Product>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<Product>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            editSubCategoryUserError.setValue(response.body().getMessage());
                        } else {
                            editSubCategoryUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        editSubCategoryUserError.setValue(response.body().getMessage());
                    }
                } else
                    editSubCategoryUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                editSubCategoryUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }


    //Image
    private MutableLiveData<ArrayList<Category>> uploadImageUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> uploadImageUserError = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Category>> uploadImageUserSuccess() {
        return uploadImageUserSuccess;
    }

    public MutableLiveData<String> uploadImageUserError() {
        return uploadImageUserError;
    }

    public void image(MultipartBody.Part image, String token) {
        remoteRepository.image(image, token).enqueue(new ApiCallback<ApiResponseArray<Category>>() {
            @Override
            public void onSuccess(Response<ApiResponseArray<Category>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            uploadImageUserError.setValue(response.body().getMessage());
                        } else {
                            uploadImageUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        uploadImageUserError.setValue(response.body().getMessage());
                    }
                } else
                    uploadImageUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                uploadImageUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }

    //ADDImage
    private MutableLiveData<Product> addProductImageUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> addProductImageUserError = new MutableLiveData<>();

    public MutableLiveData<Product> addProductImageUserSuccess() {
        return addProductImageUserSuccess;
    }

    public MutableLiveData<String> addProductImageUserError() {
        return addProductImageUserError;
    }

    public void addProductImage(RequestBody body, String accept, String authorisation, String token) {
        remoteRepository.addProductImage(body, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<Product>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<Product>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            addProductImageUserError.setValue(response.body().getMessage());
                        } else {
                            addProductImageUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        addProductImageUserError.setValue(response.body().getMessage());
                    }
                } else
                    addProductImageUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                addProductImageUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }


    //DeleteProductImage
    private MutableLiveData<ProductId> deleteProductImageUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> deleteProductImageUserError = new MutableLiveData<>();

    public MutableLiveData<ProductId> deleteProductImageUserSuccess() {
        return deleteProductImageUserSuccess;
    }

    public MutableLiveData<String> deleteProductImageUserError() {
        return deleteProductImageUserError;
    }

    public void deleteProductImage(String id, String accept, String authorisation, String token) {
        remoteRepository.deleteProductImage(id, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<ProductId>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<ProductId>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            deleteProductImageUserError.setValue(response.body().getMessage());
                        } else {
                            deleteProductImageUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        deleteProductImageUserError.setValue(response.body().getMessage());
                    }
                } else
                    deleteProductImageUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                deleteProductImageUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }


    //GetSearch
    private MutableLiveData<Product> getProductSearchUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getProductSearchUserError = new MutableLiveData<>();

    public MutableLiveData<Product> getProductSearchUserSuccess() {
        return getProductSearchUserSuccess;
    }

    public MutableLiveData<String> getProductSearchUserError() {
        return getProductSearchUserError;
    }

    public void productSearch(String items_per_page,
                              String page_number, String search_sub_category, String accept, String authorisation, String token) {
        remoteRepository.productSearch(items_per_page, page_number, search_sub_category, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<Product>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<Product>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            getProductSearchUserError.setValue(response.body().getMessage());
                        } else {
                            getProductSearchUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        getProductSearchUserError.setValue(response.body().getMessage());
                    }
                } else
                    getProductSearchUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                getProductSearchUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }


    //GetFilter
    private MutableLiveData<Product> getFilterSearchUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getFilterSearchUserError = new MutableLiveData<>();

    public MutableLiveData<Product> getFilterSearchUserSuccess() {
        return getFilterSearchUserSuccess;
    }

    public MutableLiveData<String> getFilterSearchUserError() {
        return getFilterSearchUserError;
    }

    public void productFilter(String category_id, String sub_category_id,
                              String brand_id,
                              String items_per_page,
                              String page_number,
                              String accept, String authorisation, String token) {
        remoteRepository.productFilter(category_id, sub_category_id, brand_id, items_per_page, page_number, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<Product>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<Product>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            getFilterSearchUserError.setValue(response.body().getMessage());
                        } else {
                            getFilterSearchUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        getFilterSearchUserError.setValue(response.body().getMessage());
                    }
                } else
                    getFilterSearchUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                getFilterSearchUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }

    //Get
    private MutableLiveData<ArrayList<Category>> getCategoryUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getCategoryUserError = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Category>> getCategoryUserSuccess() {
        return getCategoryUserSuccess;
    }

    public MutableLiveData<String> getCategoryUserError() {
        return getCategoryUserError;
    }

    public void getCategories(String accept, String authorisation, String token) {
        remoteRepository.getCategories(accept, authorisation, token).enqueue(new ApiCallback<ApiResponseArray<Category>>() {
            @Override
            public void onSuccess(Response<ApiResponseArray<Category>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            getCategoryUserError.setValue(response.body().getMessage());
                        } else {
                            getCategoryUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        getCategoryUserError.setValue(response.body().getMessage());
                    }
                } else
                    getCategoryUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                getCategoryUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }


    //GetSearch
    private MutableLiveData<ProductBrands> getBrandSearchUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getBrandSearchUserError = new MutableLiveData<>();

    public MutableLiveData<ProductBrands> getBrandSearchUserSuccess() {
        return getBrandSearchUserSuccess;
    }

    public MutableLiveData<String> getBrandSearchUserError() {
        return getBrandSearchUserError;
    }

    public void subProductBrandsSearch(String search_sub_category, String accept, String authorisation, String token) {
        remoteRepository.subProductBrandsSearch(search_sub_category, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<ProductBrands>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<ProductBrands>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            getBrandSearchUserError.setValue(response.body().getMessage());
                        } else {
                            getBrandSearchUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        getBrandSearchUserError.setValue(response.body().getMessage());
                    }
                } else
                    getBrandSearchUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                getBrandSearchUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }


    //GetSearch
    private MutableLiveData<SubCategory> getSubCategorySearchUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getSubCategorySearchUserError = new MutableLiveData<>();

    public MutableLiveData<SubCategory> getSubCategorySearchUserSuccess() {
        return getSubCategorySearchUserSuccess;
    }

    public MutableLiveData<String> getSubCategorySearchUserError() {
        return getSubCategorySearchUserError;
    }

    public void subCategorySearch(String search_sub_category, String accept, String authorisation, String token) {
        remoteRepository.subCategorySearch(search_sub_category, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<SubCategory>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<SubCategory>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            getSubCategorySearchUserError.setValue(response.body().getMessage());
                        } else {
                            getSubCategorySearchUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        getSubCategorySearchUserError.setValue(response.body().getMessage());
                    }
                } else
                    getSubCategorySearchUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                getSubCategorySearchUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }


}
