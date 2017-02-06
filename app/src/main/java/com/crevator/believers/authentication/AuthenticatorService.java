package com.crevator.believers.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Slimfit on 2/4/2017.
 */

public class AuthenticatorService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Authenticator authenticator = new Authenticator(getApplicationContext());
        return authenticator.getIBinder();
    }
}
