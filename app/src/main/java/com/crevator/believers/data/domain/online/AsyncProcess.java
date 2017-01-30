package com.crevator.believers.data.domain.online;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;


public class AsyncProcess extends AsyncTask<Integer, String, Boolean> {
    String result;
    callback callback;

    public AsyncProcess(callback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(Integer... arg0) {
        try {
            for (int i = 0; i < arg0[0]; i++) {
                Thread.sleep(1000);
                publishProgress(String.valueOf(i+1));
            }

        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        callback.onUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean results) {
        super.onPostExecute(results);
        try {
            Log.e("result", result);
            callback.onFinish(result);
        } catch (Exception e) {

        }

    }

   public interface callback {
        void onFinish(String result);

        void onUpdate(String value);
    }

}
