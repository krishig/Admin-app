package com.krishigadmin.android.ui.home.fragments.manageBrands.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.krishigadmin.android.data.remote.ApiCallback;
import com.krishigadmin.android.data.remote.ApiResponseArray;
import com.krishigadmin.android.data.remote.ApiResponseObject;
import com.krishigadmin.android.data.remote.exception.NetworkException;
import com.krishigadmin.android.data.repository.LocalRepository;
import com.krishigadmin.android.data.repository.RemoteRepository;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.ProductBrands;
import com.krishigadmin.android.model.SubCategory;
import com.krishigadmin.android.model.User;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

@HiltViewModel
public class BrandModel extends ViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;


    @Inject
    public BrandModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }


    //ADD

    private MutableLiveData<ProductBrands> addProductBrandsUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> addProductBrandsUserError = new MutableLiveData<>();

    public MutableLiveData<ProductBrands> addProductBrandsUserSuccess() {
        return addProductBrandsUserSuccess;
    }

    public MutableLiveData<String> addProductBrandsUserError() {
        return addProductBrandsUserError;
    }

    public void addProductBrands(RequestBody body, String accept, String authorisation, String token) {
        remoteRepository.addProductBrands(body, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<ProductBrands>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<ProductBrands>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            addProductBrandsUserError.setValue(response.body().getMessage());
                        } else {
                            addProductBrandsUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        addProductBrandsUserError.setValue(response.body().getMessage());
                    }
                } else
                    addProductBrandsUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                addProductBrandsUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }



    //ProductBrands
    private MutableLiveData<ProductBrands> getProductBrandsUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getProductBrandsUserError = new MutableLiveData<>();

    public MutableLiveData<ProductBrands> getProductBrandsUserSuccess() {
        return getProductBrandsUserSuccess;
    }

    public MutableLiveData<String> getProductBrandsUserError() {
        return getProductBrandsUserError;
    }

    public void getProductBrands(String items_per_page,
                                 String page_number,String accept, String authorisation, String token) {
        remoteRepository.getProductBrands(items_per_page,page_number,accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<ProductBrands>>() {
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



    //Delete
    private MutableLiveData<ArrayList<ProductBrands>> deleteProductBrandsUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> deleteProductBrandsUserError = new MutableLiveData<>();

    public MutableLiveData<ArrayList<ProductBrands>> deleteProductBrandsUserSuccess() {
        return deleteProductBrandsUserSuccess;
    }

    public MutableLiveData<String> deleteProductBrandsUserError() {
        return deleteProductBrandsUserError;
    }

    public void deleteProductBrands(String id,String accept, String authorisation, String token) {
        remoteRepository.deleteProductBrands(id, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseArray<ProductBrands>>() {
            @Override
            public void onSuccess(Response<ApiResponseArray<ProductBrands>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            deleteProductBrandsUserError.setValue(response.body().getMessage());
                        } else {
                            deleteProductBrandsUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        deleteProductBrandsUserError.setValue(response.body().getMessage());
                    }
                } else
                    deleteProductBrandsUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                deleteProductBrandsUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }



    //Edit
    private MutableLiveData<ProductBrands> editProductBrandUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> editProductBrandUserError = new MutableLiveData<>();

    public MutableLiveData<ProductBrands> editProductBrandUserSuccess() {
        return editProductBrandUserSuccess;
    }

    public MutableLiveData<String> editProductBrandUserError() {
        return editProductBrandUserError;
    }

    public void editProductBrands(String id,RequestBody body,String accept, String authorisation, String token) {
        remoteRepository.editProductBrands(id,body, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<ProductBrands>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<ProductBrands>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            editProductBrandUserError.setValue(response.body().getMessage());
                        } else {
                            editProductBrandUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        editProductBrandUserError.setValue(response.body().getMessage());
                    }
                } else
                    editProductBrandUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                editProductBrandUserError.setValue(networkException.getDisplayMessage());
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

    public void image(MultipartBody.Part image,  String token) {
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

    //GetSearch
    private MutableLiveData<ProductBrands> getSubCategorySearchUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getSubCategorySearchUserError = new MutableLiveData<>();

    public MutableLiveData<ProductBrands> getSubCategorySearchUserSuccess() {
        return getSubCategorySearchUserSuccess;
    }

    public MutableLiveData<String> getSubCategorySearchUserError() {
        return getSubCategorySearchUserError;
    }

    public void subProductBrandsSearch(String items_per_page,
                                       String page_number,String search_sub_category,String accept, String authorisation, String token) {
        remoteRepository.subProductBrandsSearch(items_per_page,page_number,search_sub_category,accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<ProductBrands>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<ProductBrands>> response) {
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
