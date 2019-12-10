package com.tv.guditvapp.login.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tv.guditvapp.login.model.LoginUserCreateModel;
import com.tv.guditvapp.login.model.LoginUserModel;
import com.tv.guditvapp.network.AppRepository;

public class LoginViewModel extends AndroidViewModel {
    private AppRepository dashBoardRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        dashBoardRepository = new AppRepository(application);
    }

    public LiveData<LoginUserModel> postUserCreate(LoginUserCreateModel loginUserModel) {
        return dashBoardRepository.createUserProfile(loginUserModel);
    }
}
