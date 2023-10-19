package com.krishigadmin.android.ui.home.fragments.manageSalesPerson.viewmodel;

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
public class ManageSalesPersonViewModel extends ViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;


    @Inject
    public ManageSalesPersonViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }


    //ADD

    private MutableLiveData<User> editUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> editUserError = new MutableLiveData<>();

    public MutableLiveData<User> editUserSuccess() {
        return editUserSuccess;
    }

    public MutableLiveData<String> editUserError() {
        return editUserError;
    }

    public void editUser(String id, RequestBody body, String accept, String authorisation, String token) {
        remoteRepository.editUser(id, body, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<User>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<User>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            editUserError.setValue(response.body().getMessage());
                        } else {
                            editUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        editUserError.setValue(response.body().getMessage());
                    }
                } else
                    editUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                editUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }


    //ProductBrands
    private MutableLiveData<User> getUserWithIdSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getUserWithIdError = new MutableLiveData<>();

    public MutableLiveData<User> getUserWithIdSuccess() {
        return getUserWithIdSuccess;
    }

    public MutableLiveData<String> getUserWithIdError() {
        return getUserWithIdError;
    }

    public void getUserWithId(String id,
                              String accept,
                              String authorisation,
                              String token) {
        remoteRepository.getUserWithId(id, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<User>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<User>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            getUserWithIdError.setValue(response.body().getMessage());
                        } else {
                            getUserWithIdSuccess.setValue(response.body().getData());
                        }
                    } else {
                        getUserWithIdError.setValue(response.body().getMessage());
                    }
                } else
                    getUserWithIdError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                getUserWithIdError.setValue(networkException.getDisplayMessage());
            }
        });

    }


    private MutableLiveData<User> getUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getUserError = new MutableLiveData<>();

    public MutableLiveData<User> getUserSuccess() {
        return getUserSuccess;
    }

    public MutableLiveData<String> getUserError() {
        return getUserError;
    }

    public void getUser(
            String items_per_page,
            String page_number,
            String accept,
            String authorisation,
            String token) {
        remoteRepository.getUser(items_per_page, page_number, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<User>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<User>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            getUserError.setValue(response.body().getMessage());
                        } else {
                            getUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        getUserError.setValue(response.body().getMessage());
                    }
                } else
                    getUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                getUserError.setValue(networkException.getDisplayMessage());
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

    public void deleteProductBrands(String id, String accept, String authorisation, String token) {
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

    public void editProductBrands(String id, RequestBody body, String accept, String authorisation, String token) {
        remoteRepository.editProductBrands(id, body, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<ProductBrands>>() {
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

    //GetSearch
    //GetSearch
    private MutableLiveData<User> getSearchUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getSearchUserError = new MutableLiveData<>();

    public MutableLiveData<User> getSearchUserSuccess() {
        return getSearchUserSuccess;
    }

    public MutableLiveData<String> getSearchUserError() {
        return getSearchUserError;
    }

    public void getUserSearch(String items_per_page,
                              String page_number, String search_user,
                              String accept,
                              String authorisation,
                              String token) {
        remoteRepository.getUserSearch(items_per_page,page_number,search_user, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<User>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<User>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            getSearchUserError.setValue(response.body().getMessage());
                        } else {
                            getSearchUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        getSearchUserError.setValue(response.body().getMessage());
                    }
                } else
                    getSearchUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                getSearchUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }

}
