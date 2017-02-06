package com.crevator.believers.authentication.resetPassword;

import android.content.Intent;

import com.crevator.believers.BasePresenter;
import com.crevator.believers.BaseView;

/**
 * Created by Slimfit on 1/27/2017.
 */

public interface ResetContract {

    interface Presenter extends BasePresenter<View> {
        void resetPassword(String email);
        void cancelReset();
        void initializeAuth(String accountName, String accountType);
    }

    interface View extends BaseView<Presenter> {
        void onCancel();
        void onResultCompleted(Intent intent);

    }
}
