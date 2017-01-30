package com.crevator.believers.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.crevator.believers.BuildConfig;

import timber.log.Timber;

/**
 * Created by Slimfit on 1/23/2017.
 */

public class BelieversApplication extends Application {
    private static BelieversApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.i("Creating our Application");
    }

    public static BelieversApplication getInstance() {
        return instance;
    }

    public static boolean hasNetwork() {
        return instance.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
