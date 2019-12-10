package com.tv.guditvapp.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.locationlib.LocationUtils;
import com.tv.guditvapp.R;
import com.tv.guditvapp.base.BaseActivity;
import com.tv.guditvapp.dashboard.model.DashBoardModel;
import com.tv.guditvapp.dashboard.view.DashBoardActivity;
import com.tv.guditvapp.dashboard.viewmodel.DashBoardViewModel;
import com.tv.guditvapp.login.model.LoginUserCreateModel;
import com.tv.guditvapp.login.model.LoginUserModel;
import com.tv.guditvapp.login.viewmodel.LoginViewModel;
import com.tv.guditvapp.utils.AppSharedPreUtils;
import com.tv.guditvapp.utils.AppSharedPrefConst;
import com.tv.guditvapp.utils.GudiLogs;
import com.tv.guditvapp.utils.Utilities;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private EditText mEdtName;
    private EditText mEdtUserName;
    private EditText mEdtLocation;
    private EditText mEdtPassword;
    private DashBoardModel dashBoardModel;
    private DashBoardViewModel mDashBoardViewModel;
    private LoginViewModel mLoginViewModel;
    private LocationUtils locationUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mDashBoardViewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
        locationUtils = new LocationUtils(this);
        locationUtils.startLocation();
        setCurrentAddress();
        loggedInUser();
        onLogin();
    }

    private void loggedInUser() {
        if (Utilities.isStringNotNull(AppSharedPreUtils.getInstance(LoginActivity.this).getStringValues(AppSharedPrefConst.APP_SCREEN_USER_NAME))) {
            startDashBoardActivity();
        }
    }

    private void initialize() {
        mEdtName = findViewById(R.id.edit_name);
        mEdtUserName = findViewById(R.id.edit_username);
        mEdtLocation = findViewById(R.id.edit_location);
        mEdtPassword = findViewById(R.id.edit_password);
    }

    private void setCurrentAddress(){
        mEdtLocation.setText(locationUtils.getCurrentAddress());
    }

    private void onLogin() {
        Button mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    showProgressDialog();
                    postLoginUser(loginPostData());
                }
            }
        });
    }

    private LoginUserCreateModel loginPostData(){
        LoginUserCreateModel createModel = new LoginUserCreateModel();
        createModel.setScreenName(mEdtName.getText().toString());
        createModel.setScreenUsername(mEdtUserName.getText().toString());
        createModel.setScreenAddress(mEdtLocation.getText().toString());
        createModel.setScreenPassword(mEdtPassword.getText().toString());
        return createModel;
    }

    private void startDashBoardActivity() {
        Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validate() {
        if (!Utilities.isStringNotNull(mEdtName.getText().toString())) {
            Utilities.showToastMessage(this, getString(R.string.error_msg_name));
            return false;
        }
        if (!Utilities.isStringNotNull(mEdtUserName.getText().toString())) {
            Utilities.showToastMessage(this, getString(R.string.error_msg_username));
            return false;
        }
        if (!Utilities.isStringNotNull(mEdtLocation.getText().toString())) {
            Utilities.showToastMessage(this, getString(R.string.error_msg_location));
            return false;
        }
        if (!Utilities.isStringNotNull(mEdtPassword.getText().toString())) {
            Utilities.showToastMessage(this, getString(R.string.error_msg_password));
            return false;
        }
        return true;
    }

    private void postLoginUser(LoginUserCreateModel createModel) {

        mLoginViewModel.postUserCreate(createModel).observe(this, new Observer<LoginUserModel>() {
            @Override
            public void onChanged(LoginUserModel loginUserModel) {
                if (loginUserModel != null) {
                    getDashBoardData(loginUserModel.getScreen().getScreenId());
                    AppSharedPreUtils.getInstance(LoginActivity.this).saveStringValues(AppSharedPrefConst.APP_SCREEN_ID,
                            loginUserModel.getScreen().getScreenId());
                    AppSharedPreUtils.getInstance(LoginActivity.this).saveStringValues(AppSharedPrefConst.APP_SCREEN_USER_NAME,
                            loginUserModel.getScreen().getScreenUsername());
                    AppSharedPreUtils.getInstance(LoginActivity.this).saveStringValues(AppSharedPrefConst.APP_SCREEN_NAME,
                            loginUserModel.getScreen().getScreenName());
                    AppSharedPreUtils.getInstance(LoginActivity.this).saveStringValues(AppSharedPrefConst.APP_SCREEN_LOCATION,
                            loginUserModel.getScreen().getScreenAddress());
                    AppSharedPreUtils.getInstance(LoginActivity.this).saveIntValues(AppSharedPrefConst.APP_SCREEN_VERSION,
                            loginUserModel.getScreen().getVersion());

                } else {
                    hideProgressDialog();
                    GudiLogs.logDebug(TAG, "DashBoardActivity onChanged else -> ");
                }
            }
        });
    }

    private void getDashBoardData(String screenId) {
        mDashBoardViewModel.getDashBoardData(screenId).observe(this, new Observer<DashBoardModel>() {
            @Override
            public void onChanged(DashBoardModel dashBoardModel) {
                if (dashBoardModel != null) {
                    AppSharedPreUtils.getInstance(LoginActivity.this).saveDashBoardSectionData(dashBoardModel.getSectionData());
                    AppSharedPreUtils.getInstance(LoginActivity.this).saveIntValues(AppSharedPrefConst.APP_SCREEN_TYPE,
                            dashBoardModel.getSectionData().getScreenType());
                    hideProgressDialog();
                    startDashBoardActivity();
                    GudiLogs.logDebug(TAG, "DashBoardActivity onChanged upload -> " + dashBoardModel.getUploadId());
                } else {
                    hideProgressDialog();
                    GudiLogs.logDebug(TAG, "DashBoardActivity onChanged else -> ");
                }

            }
        });
    }

    /*private void readJsonData() {
        String jsonString = Utilities.getAssetJsonData(this, "gudi.json");
        Gson gson = new Gson();
        dashBoardModel = gson.fromJson(jsonString, DashBoardModel.class);
        if (dashBoardModel != null) {
            DashBoardUtils.INSTANCE.setScreenType(dashBoardModel.getGudi().getScreenType());
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        locationUtils.registrationReceiver();
        setCurrentAddress();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationUtils.unRegistrationReceiver();
    }
}
