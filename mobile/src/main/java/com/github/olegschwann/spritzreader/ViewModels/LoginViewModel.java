package com.github.olegschwann.spritzreader.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.github.olegschwann.spritzreader.repo.AuthRepo;

import ru.mail.auth.sdk.AuthResult;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<LoginState> mLoginState = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mLoginState.setValue(LoginState.NONE);
    }

    public enum LoginState {
        NONE,
        LOGGED
    }

    public void startLoginFlow(Fragment fragment) {
        AuthRepo.getInstance(getApplication()).startLoginFlow(fragment);
    }

    public void saveResult(AuthResult result) {
        mLoginState.postValue(LoginState.LOGGED);
        AuthRepo.getInstance(getApplication()).saveResult(result);
    }

    public void logout() {
        mLoginState.postValue(LoginState.NONE);
        AuthRepo.getInstance(getApplication()).logout();
    }

    public boolean isLoggedIn() {
        return AuthRepo.getInstance(getApplication()).isLoggedIn();
    }

    public LiveData<LoginState> getLoginState() {
        return mLoginState;
    }
}