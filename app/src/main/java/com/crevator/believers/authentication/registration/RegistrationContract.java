package com.crevator.believers.authentication.registration;

import com.crevator.believers.BasePresenter;
import com.crevator.believers.BaseView;

/**
 * Created by Slimfit on 1/27/2017.
 */

public interface RegistrationContract {

    interface Presenter extends BasePresenter<BaseView> {
        void register(String email, String password);
    }

    interface View extends BaseView<Presenter> {


    }
}
