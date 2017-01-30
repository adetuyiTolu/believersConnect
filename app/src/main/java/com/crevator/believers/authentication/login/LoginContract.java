package com.crevator.believers.authentication.login;

import com.crevator.believers.BasePresenter;
import com.crevator.believers.BaseView;

/**
 * Created by Slimfit on 1/27/2017.
 */

public interface LoginContract {

    interface Presenter extends BasePresenter<View> {
        void performLogin(String email, String password);
        void startCount();
    }

    interface View extends BaseView<Presenter> {
        void showDashboard();


    }
}
