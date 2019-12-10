package com.tv.guditvapp.dashboard.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tv.guditvapp.dashboard.model.DashBoardModel;
import com.tv.guditvapp.network.AppRepository;

public class DashBoardViewModel extends AndroidViewModel {
    private AppRepository dashBoardRepository;

    public DashBoardViewModel(@NonNull Application application) {
        super(application);
        dashBoardRepository = new AppRepository(application);
    }

    public LiveData<DashBoardModel> getDashBoardData(String screenId) {
        return dashBoardRepository.getMutableLiveDashBordData(screenId);
    }
}
