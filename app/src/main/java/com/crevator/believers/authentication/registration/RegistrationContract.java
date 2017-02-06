package com.crevator.believers.authentication.registration;

import android.content.Intent;

import com.crevator.believers.BasePresenter;
import com.crevator.believers.BaseView;

/**
 * Created by Slimfit on 1/27/2017.
 */

public interface RegistrationContract {

    interface Presenter extends BasePresenter<View> {
        void register(String email, String password);
        void cancelRegistration();
        void initializeAuth(String accountName,String accountType);
    }

    interface View extends BaseView<Presenter> {
        void onCancel();
        void onResultCompleted(Intent intent);

    }
}
