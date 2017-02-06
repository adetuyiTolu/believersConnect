package com.crevator.believers.Main;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.crevator.believers.R;
import com.crevator.believers.authentication.Authenticator;

public class Dashboard extends AppCompatActivity {
    private String authToken = null;
    private Account mConnectedAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                authenticate(Authenticator.ACCOUNT_TYPE,Authenticator.AUTHTOKEN_TYPE_FULL_ACCESS);
            }
        });
    }

    void authenticate(String accountType, String authTokenType) {
        final AccountManagerFuture<Bundle> future = AccountManager.get(this).getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        Bundle bnd = null;
                        try {
                            bnd = future.getResult();
                            authToken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                            if (authToken != null) {
                                String accountName = bnd.getString(AccountManager.KEY_ACCOUNT_NAME);
                                mConnectedAccount = new Account(accountName, Authenticator.ACCOUNT_TYPE);

                            }
                            Toast.makeText(Dashboard.this, "done", Toast.LENGTH_SHORT).show();


                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(Dashboard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , null);
    }


}
