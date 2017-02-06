package com.crevator.believers.authentication.resetPassword;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;

import com.crevator.believers.data.domain.online.Online;
import com.crevator.believers.data.domain.online.UserService;
import com.crevator.believers.data.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.crevator.believers.authentication.Authenticator.PARAM_USER_DATA;
import static com.crevator.believers.authentication.Authenticator.PARAM_USER_PASS;

/**
 * Created by Slimfit on 1/27/2017.
 */

public class ResetPresenter implements ResetContract.Presenter {
    private ResetContract.View mRegisterUserView;
    private UserService mUserService;
    private User mUser;
    String mAccountName;
    String mAccountType;

    public ResetPresenter() {
        mUserService = Online.provideUserService();
    }

    @Override
    public void bindView(ResetContract.View view) {
        this.mRegisterUserView = view;
        mRegisterUserView.setPresenter(this);
    }

    @Override
    public void initializeAuth(String accountName, String accountType) {
        mAccountName = accountName;
        mAccountType = accountType;
    }

    @Override
    public void resetPassword(String email) {

        mUserService.getUser(email).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mUser = response.body();
                Bundle data = new Bundle();
                String authtoken = null;
                if (mUser != null)
                    authtoken = mUser.getAuthToken();
                Bundle userData = new Bundle();
                userData.putString(PARAM_USER_DATA, String.valueOf(mUser.getUserId()));
                data.putBundle(AccountManager.KEY_USERDATA, userData);
                data.putString(AccountManager.KEY_ACCOUNT_NAME, mUser.getUserName());
                data.putString(AccountManager.KEY_ACCOUNT_TYPE, mAccountType);
                data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                data.putString(PARAM_USER_PASS, mUser.getFirstName());
                final Intent intent = new Intent();
                intent.putExtras(data);
                mRegisterUserView.onResultCompleted(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public void cancelReset() {
        mRegisterUserView.onCancel();
    }

    @Override
    public ResetContract.View getView() {
        return mRegisterUserView;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }
}
