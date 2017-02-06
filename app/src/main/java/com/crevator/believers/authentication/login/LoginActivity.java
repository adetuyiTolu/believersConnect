package com.crevator.believers.authentication.login;

import android.accounts.AccountAuthenticatorActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crevator.believers.PresenterManager;
import com.crevator.believers.R;
import com.crevator.believers.authentication.registration.RegistrationActivity;
import com.crevator.believers.authentication.resetPassword.ResetActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.crevator.believers.authentication.Authenticator.ARG_ACCOUNT_NAME;
import static com.crevator.believers.authentication.Authenticator.ARG_ACCOUNT_TYPE;
import static com.crevator.believers.authentication.Authenticator.ARG_AUTH_TYPE;

public class LoginActivity extends AccountAuthenticatorActivity implements LoginContract.View {
    LoginContract.Presenter mLoginPresenter;

    String mAccountType;
    public static final int REQ_SIGNUP = 0;
    public static final int REQ_FORGOT_PASSWORD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter(savedInstanceState);
        initOthers();
    }

    void initPresenter(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mLoginPresenter = new LoginPresenter();
        } else {
            mLoginPresenter = PresenterManager.getInstance().restorePresnter(savedInstanceState);
        }
    }

    void initOthers() {
        setContentView(R.layout.activity_main_login);
        ButterKnife.bind(this);
        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        String mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        mAccountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
        mLoginPresenter.startAuthentication(mAuthTokenType, accountName);
    }


    @Override
    public void setTitle(String title) {
        setTitle(title);
    }

    public void performLogin(View v) {
        mLoginPresenter.performLogin(null, null, mAccountType);
    }

    public void onSignUpClick(View v) {
        showSignup();
    }
    public void onResetClick(View v){
        Intent i = new Intent(LoginActivity.this, ResetActivity.class);
        i.putExtras(getIntent().getExtras());
        startActivityForResult(i, REQ_FORGOT_PASSWORD);
    }
    @Override
    public void showDashboard() {

    }

    @Override
    public void showSignup() {
        Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
        i.putExtras(getIntent().getExtras());
        startActivityForResult(i, REQ_SIGNUP);
    }

    @Override
    public void showResetForm() {

    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_SIGNUP && resultCode == RESULT_OK) {
            finalizeSignUp(data);
        } else if (requestCode == REQ_FORGOT_PASSWORD && resultCode == RESULT_OK) {
            finalizePasswordRecovery(data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void finalizeAuthentication(Intent intent) {
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    void finalizePasswordRecovery(Intent data) {
        mLoginPresenter.finalizeLogin(data);
    }

    void finalizeSignUp(Intent data) {
        mLoginPresenter.finalizeLogin(data);
    }
}
