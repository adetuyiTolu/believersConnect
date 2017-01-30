package com.crevator.believers;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.crevator.believers.data.model.User;
import com.crevator.believers.data.domain.online.Online;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.crevator.believers", appContext.getPackageName());
    }
    public void checkOnline() {
        try {
            Online.provideUserService().getUsers().enqueue(new Callback<List<User>>() {
               @Override
               public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    for(User user:response.body()){
                        Log.e(user.getFirstName(), user.getUserName());
                    }
               }

               @Override
               public void onFailure(Call<List<User>> call, Throwable t) {

               }
           });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
