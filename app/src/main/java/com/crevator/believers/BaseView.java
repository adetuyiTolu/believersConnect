package com.crevator.believers;


/**
 * Created by Slimfit on 1/27/2017.
 */

public interface BaseView<T> {

    void showLoading(boolean show);

    void showMessage(String message);

    void setPresenter(T presenter);
}
