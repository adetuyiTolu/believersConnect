package com.crevator.believers.authentication.registration;

import com.crevator.believers.BaseView;
import com.crevator.believers.data.domain.online.UserService;

/**
 * Created by Slimfit on 1/27/2017.
 */

public class RegistrationPresenter implements RegistrationContract.Presenter {
    private RegistrationContract.View mRegisterUserView;
    private UserService userService;

    @Override
    public void bindView(BaseView view) {

    }

    @Override
    public void register(String email, String password) {

    }

    @Override
    public RegistrationContract.View getView() {
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
