package com.crevator.believers;

/**
 * Created by Slimfit on 1/27/2017.
 */

public interface BasePresenter<T> {
    T getView();
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void bindView(T view);
}
