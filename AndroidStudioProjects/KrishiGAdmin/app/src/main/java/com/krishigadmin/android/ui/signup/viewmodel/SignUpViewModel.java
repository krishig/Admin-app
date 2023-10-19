package com.krishigadmin.android.ui.signup.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.krishigadmin.android.data.remote.ApiCallback;
import com.krishigadmin.android.data.remote.ApiResponseObject;
import com.krishigadmin.android.data.remote.exception.NetworkException;
import com.krishigadmin.android.data.repository.LocalRepository;
import com.krishigadmin.android.data.repository.RemoteRepository;
import com.krishigadmin.android.model.User;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.RequestBody;
import retrofit2.Response;

@HiltViewModel
public class SignUpViewModel extends ViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    private MutableLiveData<User> userSuccess = new MutableLiveData<>();
    private MutableLiveData<String> userError = new MutableLiveData<>();


    @Inject
    public SignUpViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public MutableLiveData<User> userSuccess() {
        return userSuccess;
    }

    public MutableLiveData<String> userError() {
        return userError;
    }


    public void signUp(RequestBody jsonObject, String accept, String authorisation) {
        remoteRepository.signUp(jsonObject, accept, authorisation).enqueue(new ApiCallback<ApiResponseObject<User>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<User>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            userError.setValue(response.body().getMessage());
                        } else {
                            userSuccess.setValue(response.body().getData());
                        }
                    } else {
                        userError.setValue(response.body().getMessage());
                    }
                } else
                    userError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                userError.setValue(networkException.getDisplayMessage());
            }
        });

    }




/*    private MutableLiveData<User> userOtpSuccess = new MutableLiveData<>();
    private MutableLiveData<String> userOtpError = new MutableLiveData<>();

    public MutableLiveData<User> userOtpSuccess() {
        return userOtpSuccess;
    }

    public MutableLiveData<String> userOtpError() {
        return userOtpError;
    }


    public void pinCode(String pinCode) {
        remoteRepository.pinCode(pinCode).enqueue(new ApiCallback<ArrayList<User>>() {
            @Override
            public void onSuccess(Response<ArrayList<User>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        userOtpSuccess.setValue(response.body().get(0));
                    } else {
                        userOtpError.setValue(response.message());
                    }
                } else {
                    userOtpError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                userOtpError.setValue(networkException.getDisplayMessage());
            }
        });

    }*/

    private MutableLiveData<User> userResendOtpSuccess = new MutableLiveData<>();
    private MutableLiveData<String> userResendOtpError = new MutableLiveData<>();

    public MutableLiveData<User> userResendOtpSuccess() {
        return userResendOtpSuccess;
    }

    public MutableLiveData<String> userResendOtpError() {
        return userResendOtpError;
    }

    public void resendOtp(String mobileNumber) {
        remoteRepository.resendOtp(mobileNumber).enqueue(new ApiCallback<ApiResponseObject<User>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<User>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            userResendOtpError.setValue(response.body().getMessage());
                        } else {
                            userResendOtpSuccess.setValue(response.body().getData());
                        }
                    } else {
                        userResendOtpError.setValue(response.body().getMessage());
                    }
                } else
                    userResendOtpError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                userResendOtpError.setValue(networkException.getDisplayMessage());
            }
        });

    }
}
