package com.crevator.believers.authentication.login;

import com.crevator.believers.data.domain.online.AsyncProcess;
import com.crevator.believers.data.domain.online.Online;
import com.crevator.believers.data.domain.online.UserService;
import com.crevator.believers.data.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Slimfit on 1/27/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {
    //view to be updated
    private LoginContract.View mLoginUserView;
    private UserService mUserService;
    //model to work with
    User user;
    //value for testing
    String mValue = "start count";

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
        mLoginUserView.showMessage(mValue);
    }

    @Override
    public void performLogin(String email, String password) {
        mUserService.getUser(null).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public LoginContract.View getView() {
        return mLoginUserView;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void startCount() {
        new AsyncProcess(new AsyncProcess.callback() {
            @Override
            public void onFinish(String result) {

            }

            @Override
            public void onUpdate(String value) {
                mValue = value;
                mLoginUserView.showMessage(value);
            }
        }).execute(20);
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
