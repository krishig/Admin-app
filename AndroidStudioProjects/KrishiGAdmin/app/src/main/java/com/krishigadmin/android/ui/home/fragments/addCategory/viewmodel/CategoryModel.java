package com.krishigadmin.android.ui.home.fragments.addCategory.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.krishigadmin.android.data.remote.ApiCallback;
import com.krishigadmin.android.data.remote.ApiResponseArray;
import com.krishigadmin.android.data.remote.ApiResponseObject;
import com.krishigadmin.android.data.remote.exception.NetworkException;
import com.krishigadmin.android.data.repository.LocalRepository;
import com.krishigadmin.android.data.repository.RemoteRepository;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.User;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.RequestBody;
import retrofit2.Response;

@HiltViewModel
public class CategoryModel extends ViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;


    @Inject
    public CategoryModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
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

    public void addCategories(RequestBody body, String accept, String authorisation, String token) {
        remoteRepository.categories(body, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<User>>() {
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



    //Delete
    private MutableLiveData<ArrayList<Category>> deleteCategoryUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> deleteCategoryUserError = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Category>> deleteCategoryUserSuccess() {
        return deleteCategoryUserSuccess;
    }

    public MutableLiveData<String> deleteCategoryUserError() {
        return deleteCategoryUserError;
    }

    public void deleteCategories(String id,String accept, String authorisation, String token) {
        remoteRepository.deleteCategories(id, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseArray<Category>>() {
            @Override
            public void onSuccess(Response<ApiResponseArray<Category>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            deleteCategoryUserError.setValue(response.body().getMessage());
                        } else {
                            deleteCategoryUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        deleteCategoryUserError.setValue(response.body().getMessage());
                    }
                } else
                    deleteCategoryUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                deleteCategoryUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }



    //Edit
    private MutableLiveData<Category> editCategoryUserSuccess = new MutableLiveData<>();
    private MutableLiveData<String> editCategoryUserError = new MutableLiveData<>();

    public MutableLiveData<Category> editCategoryUserSuccess() {
        return editCategoryUserSuccess;
    }

    public MutableLiveData<String> editCategoryUserError() {
        return editCategoryUserError;
    }

    public void editCategories(String id,RequestBody body,String accept, String authorisation, String token) {
        remoteRepository.editCategories(id,body, accept, authorisation, token).enqueue(new ApiCallback<ApiResponseObject<Category>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<Category>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            editCategoryUserError.setValue(response.body().getMessage());
                        } else {
                            editCategoryUserSuccess.setValue(response.body().getData());
                        }
                    } else {
                        editCategoryUserError.setValue(response.body().getMessage());
                    }
                } else
                    editCategoryUserError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                editCategoryUserError.setValue(networkException.getDisplayMessage());
            }
        });

    }

}
