package com.crevator.believers.authentication;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.crevator.believers.app.BelieversApplication;
import com.crevator.believers.authentication.login.LoginActivity;

import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;

/**
 * Created by Slimfit on 2/4/2017.
 */

public class Authenticator extends AbstractAccountAuthenticator {
    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String ACCOUNT_TYPE = "com.crevator.authenticator";
    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";
    public final static String PARAM_USER_PASS = "USER_PASS";
    public final static String PARAM_USER_DATA = "USER_DATA";
    private final Context mContext;
    AccountManager mAccountManager;

    Authenticator(Context context) {
        super(context);
        mContext = context;
        mAccountManager = AccountManager.get(mContext);
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response,
                             String accountType,
                             String authTokenType,
                             String[] requiredFeatures,
                             Bundle options) throws NetworkErrorException {
        //add bundle details and show LoginActivity for users
        final Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return authTokenType;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(KEY_BOOLEAN_RESULT, false);
        return result;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String authTokenType, Bundle options) throws NetworkErrorException {

        //request for the authToken stored
        String authToken = mAccountManager.peekAuthToken(account, authTokenType);
        //check if thr authToken is valid and return bundle details
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }
        //Couldn't fetch authToken, allow user to login again
        final Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);
        intent.putExtra(ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(ARG_AUTH_TYPE, authTokenType);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }


    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }


    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    public static boolean finishLogin(Intent intent, AccountAuthenticatorActivity activity, String authtokenType) {
        AccountManager mAccountManager = AccountManager.get(BelieversApplication.getInstance());
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if (intent.getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            // Creating the account on the device and setting the auth token we got
            mAccountManager.addAccountExplicitly(account, accountPassword, intent.getBundleExtra(AccountManager.KEY_USERDATA));
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
        } else {
            mAccountManager.setPassword(account, accountPassword);
        }

        activity.setAccountAuthenticatorResult(intent.getExtras());
        activity.setResult(activity.RESULT_OK, intent);
        activity.finish();
        return true;
    }

}
