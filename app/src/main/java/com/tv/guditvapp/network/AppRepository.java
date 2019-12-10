package com.tv.guditvapp.network;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.tv.guditvapp.dashboard.model.DashBoardModel;
import com.tv.guditvapp.login.model.LoginUserCreateModel;
import com.tv.guditvapp.login.model.LoginUserModel;
import com.tv.guditvapp.utils.GudiLogs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppRepository {
    private static final String TAG = "AppRepository";
    private MutableLiveData<DashBoardModel> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoginUserModel> loginLiveData = new MutableLiveData<>();
    private Application application;

    public AppRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<DashBoardModel> getMutableLiveDashBordData(String screenId) {

        RestApiService apiService = RetrofitInstance.getApiService();
        Call<DashBoardModel> call = apiService.getAllScreenData("1948e4c6-1592-11ea-8d71-362b9e155667");
        GudiLogs.logDebug(TAG,"Service dashBoardModel call -> "+call.request());
        call.enqueue(new Callback<DashBoardModel>() {
            @Override
            public void onResponse(Call<DashBoardModel> call, Response<DashBoardModel> response) {
                DashBoardModel dashBoardModel = response.body();
                if (dashBoardModel != null)
                {
                    mutableLiveData.setValue(dashBoardModel);
                }else{
                    mutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<DashBoardModel> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<LoginUserModel> createUserProfile(LoginUserCreateModel createModel) {

        RestApiService apiService = RetrofitInstance.getApiService();
        Call<LoginUserModel> call = apiService.postRegistration(createModel);
        GudiLogs.logDebug(TAG,"Service loginLiveData call -> "+call.request());
        call.enqueue(new Callback<LoginUserModel>() {
            @Override
            public void onResponse(Call<LoginUserModel> call, Response<LoginUserModel> response) {
                LoginUserModel loginUserModel = response.body();
                GudiLogs.logDebug(TAG,"Service loginLiveData call ONRESPONSE-> "+call.request());
                if (loginUserModel != null) {
                    loginLiveData.setValue(loginUserModel);
                }else{
                    loginLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginUserModel> call, Throwable t) {
                GudiLogs.logDebug(TAG,"Service loginLiveData call ERROR-> "+call.request());
                loginLiveData.setValue(null);
            }
        });
        return loginLiveData;
    }
}
