package com.krishigadmin.android.ui.login.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.krishigadmin.android.data.remote.ApiCallback;
import com.krishigadmin.android.data.remote.ApiResponseObject;
import com.krishigadmin.android.data.remote.exception.NetworkException;
import com.krishigadmin.android.data.repository.LocalRepository;
import com.krishigadmin.android.data.repository.RemoteRepository;
import com.krishigadmin.android.model.User;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.RequestBody;
import retrofit2.Response;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    private MutableLiveData<User> userSuccess = new MutableLiveData<>();
    private MutableLiveData<String> userError = new MutableLiveData<>();
    private MutableLiveData<User> userOtpSuccess = new MutableLiveData<>();
    private MutableLiveData<String> userOtpError = new MutableLiveData<>();

    @Inject
    public LoginViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public MutableLiveData<User> userSuccess() {
        return userSuccess;
    }

    public MutableLiveData<String> userError() {
        return userError;
    }

    public MutableLiveData<User> userOtpSuccess() {
        return userOtpSuccess;
    }

    public MutableLiveData<String> userOtpError() {
        return userOtpError;
    }


    public void login(RequestBody body, String role, String accept, String authorisation) {
        remoteRepository.login(body, role,accept, authorisation).enqueue(new ApiCallback<ApiResponseObject<User>>() {
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

    public void otpVerify(String mobileNumber, String otp) {
        remoteRepository.otpVerify(mobileNumber, otp).enqueue(new ApiCallback<ApiResponseObject<User>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<User>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            userOtpError.setValue(response.body().getMessage());
                        } else {
                            userOtpSuccess.setValue(response.body().getData());
                        }
                    } else {
                        userOtpError.setValue(response.body().getMessage());
                    }
                } else
                    userOtpError.setValue(null);
            }

            @Override
            public void onFailure(NetworkException networkException) {
                userOtpError.setValue(networkException.getDisplayMessage());
            }
        });

    }

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
