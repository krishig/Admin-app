package com.krishigadmin.android.ui.home.fragments.addSubCategory.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.krishigadmin.android.data.remote.ApiCallback;
import com.krishigadmin.android.data.remote.ApiResponseArray;
import com.krishigadmin.android.data.remote.ApiResponseObject;
import com.krishigadmin.android.data.remote.exception.NetworkException;
import com.krishigadmin.android.data.repository.LocalRepository;
import com.krishigadmin.android.data.repository.RemoteRepository;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.SubCategory;
import com.krishigadmin.android.model.User;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

@HiltViewModel
public class SubCategoryModel extends ViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;


    @Inject
    public SubCategoryModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }


    //ADD

    private MutableLiveData<User> addCategoryUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> addCategoryUserError = new MutableLiveData<>();

    public MutableLiveData<User> addCategoryUserSuccess() {
        return addCategoryUserSuccess;
    }

    public MutableLiveData<String> addCategoryUserError() {
        return addCategoryUserError;
    }

    public void subCategory(RequestBody body, String accept, String authorisation, String token) {
        remoteRepository.subCategory(body, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<User>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<User>> response) {
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

    //Get
    private MutableLiveData<SubCategory> getSubCategoryUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getSubCategoryUserError = new MutableLiveData<>();

    public MutableLiveData<SubCategory> getSubCategoryUserSuccess() {
        return getSubCategoryUserSuccess;
    }

    public MutableLiveData<String> getSubCategoryUserError() {
        return getSubCategoryUserError;
    }

    public void getSubCategories(String items_per_page,
                                 String page_number,String accept, String authorisation, String token) {
        remoteRepository.getSubCategories(items_per_page,page_number,accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<SubCategory>>() {
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




    //Delete
    private MutableLiveData<ArrayList<SubCategory>> deleteSubCategoryUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> deleteSubCategoryUserError = new MutableLiveData<>();

    public MutableLiveData<ArrayList<SubCategory>> deleteSubCategoryUserSuccess() {
        return deleteSubCategoryUserSuccess;
    }

    public MutableLiveData<String> deleteSubCategoryUserError() {
        return deleteSubCategoryUserError;
    }

    public void deleteSubCategories(String id, String accept, String authorisation, String token) {
        remoteRepository.deleteSubCategories(id, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseArray<SubCategory>>() {
            @Override
            public void onSuccess(Response<ApiResponseArray<SubCategory>> response) {
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
    private MutableLiveData<SubCategory> editSubCategoryUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> editSubCategoryUserError = new MutableLiveData<>();

    public MutableLiveData<SubCategory> editSubCategoryUserSuccess() {
        return editSubCategoryUserSuccess;
    }

    public MutableLiveData<String> editSubCategoryUserError() {
        return editSubCategoryUserError;
    }

    public void editSubCategories(String id, RequestBody body, String accept, String authorisation, String token) {
        remoteRepository.editSubCategories(id, body, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<SubCategory>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<SubCategory>> response) {
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
    private MutableLiveData<SubCategory> getSubCategorySearchUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> getSubCategorySearchUserError = new MutableLiveData<>();

    public MutableLiveData<SubCategory> getSubCategorySearchUserSuccess() {
        return getSubCategorySearchUserSuccess;
    }

    public MutableLiveData<String> getSubCategorySearchUserError() {
        return getSubCategorySearchUserError;
    }

    public void subCategorySearch(String items_per_page,
                                  String page_number,String search_sub_category,String accept, String authorisation, String token) {
        remoteRepository.subCategorySearch(items_per_page,page_number,search_sub_category,accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<SubCategory>>() {
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
