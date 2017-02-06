package com.crevator.believers.authentication.login;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;

import com.crevator.believers.app.BelieversApplication;
import com.crevator.believers.authentication.Authenticator;
import com.crevator.believers.data.domain.online.Online;
import com.crevator.believers.data.domain.online.UserService;
import com.crevator.believers.data.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.crevator.believers.authentication.Authenticator.ARG_IS_ADDING_NEW_ACCOUNT;
import static com.crevator.believers.authentication.Authenticator.AUTHTOKEN_TYPE_FULL_ACCESS;
import static com.crevator.believers.authentication.Authenticator.KEY_ERROR_MESSAGE;
import static com.crevator.believers.authentication.Authenticator.PARAM_USER_DATA;
import static com.crevator.believers.authentication.Authenticator.PARAM_USER_PASS;

/**
 * Created by Slimfit on 1/27/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {

    //view to be updated
    private LoginContract.View mLoginUserView;

    private UserService mUserService;

    //mUser model for Login
    User mUser;

    //for authenticating mUser
    private AccountManager mAccountManager;

    //authetication token
    private String mAuthTokenType;

    @Override
    public void bindView(LoginContract.View view) {
        this.mLoginUserView = view;
        mLoginUserView.setPresenter(this);
        updateView();
    }

    public LoginPresenter() {
        mUserService = Online.provideUserService();
    }

    void updateView() {
        loadDefaultUser();
        mLoginUserView.showMessage(mUser.getFirstName());
    }

    void loadDefaultUser() {
        mUser = new User();
        mUser.setFirstName("");
    }

    @Override
    public void startAuthentication(String authTokentype, String accountName) {
        mAccountManager = AccountManager.get(BelieversApplication.getInstance());
        if (mAuthTokenType == null)
            mAuthTokenType = AUTHTOKEN_TYPE_FULL_ACCESS;

        if (accountName != null) {
            mLoginUserView.setTitle(accountName);
        }

    }

    @Override
    public void performLogin(String userName, String password, String accountType) {
        User user = new User();
        user.setUserName(userName);
        final Bundle data = new Bundle();
        final String newAccountType = accountType;
        mUserService.loginUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mUser = response.body();
                data.putString(AccountManager.KEY_ACCOUNT_NAME, mUser.getUserName());
                data.putString(AccountManager.KEY_ACCOUNT_TYPE, newAccountType);
                data.putString(AccountManager.KEY_AUTHTOKEN, mUser.getAuthToken());
                data.putString(PARAM_USER_PASS, mUser.getUserName());
                //save user data it for subsequent personal request
                Bundle userData = new Bundle();
                userData.putString(PARAM_USER_DATA, String.valueOf(mUser.getUserId()));
                data.putBundle(AccountManager.KEY_USERDATA, userData);
                final Intent res = new Intent();
                res.putExtras(data);
                finalizeLogin(res);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mLoginUserView.showMessage("Error signing in to Believers account");
            }

        });
    }

    @Override
    public void finalizeLogin(Intent intent) {

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if (intent.getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            // Creating the account on the device and setting the auth token we got
            mAccountManager.addAccountExplicitly(account, accountPassword, intent.getBundleExtra(AccountManager.KEY_USERDATA));
            mAccountManager.setAuthToken(account, mAuthTokenType, authtoken);
        } else {
            mAccountManager.setPassword(account, accountPassword);
        }
        mLoginUserView.finalizeAuthentication(intent);
        mLoginUserView.showDashboard();
    }

    @Override
    public LoginContract.View getView() {
        return mLoginUserView;
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
