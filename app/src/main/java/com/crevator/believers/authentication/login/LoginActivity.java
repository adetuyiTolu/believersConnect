package com.crevator.believers.authentication.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crevator.believers.PresenterManager;
import com.crevator.believers.R;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    LoginContract.Presenter mLoginPresenter;
    @Bind(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            mLoginPresenter = new LoginPresenter();
        } else {
            mLoginPresenter = PresenterManager.getInstance().restorePresnter(savedInstanceState);
        }

        setContentView(R.layout.activity_main_login);
        ButterKnife.bind(this);


    }

    public void startCount(View v) {
        mLoginPresenter.startCount();
    }

    @Override
    public void showDashboard() {

    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    public void showMessage(String message) {
        //Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        btn.setText(message);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mLoginPresenter = presenter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mLoginPresenter, outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLoginPresenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginPresenter.bindView(this);
    }
}
