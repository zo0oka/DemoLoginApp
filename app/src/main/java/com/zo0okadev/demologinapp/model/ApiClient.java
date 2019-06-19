package com.zo0okadev.demologinapp.model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String TAG = ApiClient.class.getSimpleName();
    private static final String API_BASE_URL = "https://www.yalladealz.com";
    private static ApiService sInstance = null;

    public static ApiService getInstance() {

        if (sInstance == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

            Retrofit.Builder builder =
                    new Retrofit
                            .Builder()
                            .baseUrl(API_BASE_URL)
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create());

            sInstance = builder.build().create(ApiService.class);
        }

        return sInstance;
    }

    public static void userLogin(ApiService service, String email, String password, final LoginStatusCallback loginStatusCallback) {
        service.getResponse(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                Log.d(TAG, String.format("Got a response: %s", response));
                if (response.isSuccessful()) {
                    loginStatusCallback.onSuccess(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : null;
                        loginStatusCallback.onError(errorBody != null ? errorBody : "Unknown error!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                loginStatusCallback.onError(t.getMessage() != null ? t.getMessage() : "Unknown error");
            }
        });
    }

    public interface LoginStatusCallback {
        void onSuccess(LoginResponse loginResponse);

        void onError(String error);
    }
}
