package com.zo0okadev.demologinapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zo0okadev.demologinapp.model.ApiClient;
import com.zo0okadev.demologinapp.model.ApiService;
import com.zo0okadev.demologinapp.model.LoginResponse;

public class AppViewModel extends ViewModel {

    private final MutableLiveData<LoginResponse> loginResponse;
    private final MutableLiveData<String> errorMessage;
    private ApiService service;

    public AppViewModel() {
        loginResponse = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        service = ApiClient.getInstance();
    }

    public void loginUser(String email, String password) {
        ApiClient.userLogin(service, email, password, new ApiClient.LoginStatusCallback() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                AppViewModel.this.loginResponse.postValue(loginResponse);
            }

            @Override
            public void onError(String error) {
                errorMessage.postValue(error);
            }
        });
    }

    public LiveData<LoginResponse> getLoginResponse() {
        return loginResponse;
    }

    public LiveData<String> getError() {
        return errorMessage;
    }
}
