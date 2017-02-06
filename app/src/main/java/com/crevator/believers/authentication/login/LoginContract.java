package com.crevator.believers.authentication.login;

import android.content.Intent;

import com.crevator.believers.BasePresenter;
import com.crevator.believers.BaseView;

/**
 * Created by Slimfit on 1/27/2017.
 */

public interface LoginContract {

    interface Presenter extends BasePresenter<View> {
        void performLogin(String email, String password, String accountType);

        void finalizeLogin(Intent intent);

        void startAuthentication(String authTokentype, String accountName);
    }

    interface View extends BaseView<Presenter> {
        void setTitle(String title);

        void showSignup();

        void showResetForm();

        void showDashboard();

        void finalizeAuthentication(Intent intent);


    }
}
