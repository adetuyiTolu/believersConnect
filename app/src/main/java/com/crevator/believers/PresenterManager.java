package com.crevator.believers;

import android.os.Bundle;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * Created by Slimfit on 1/30/2017.
 */

public class PresenterManager {

    private static final String PRESENTER_KEY = "presenter_id";
    private static PresenterManager instance;
    private final AtomicLong currentId;

    private final Cache<Long, BasePresenter<?>> presenters;

    PresenterManager(long maxSize, long expirationValue, TimeUnit expirationUnit) {
        currentId = new AtomicLong();
        presenters = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expirationValue, expirationUnit)
                .build();
    }

    public static PresenterManager getInstance() {
        if (instance == null) {
            instance = new PresenterManager(10, 30, TimeUnit.SECONDS);
        }
        return instance;
    }

    public <P extends BasePresenter<?>> P restorePresnter(Bundle savedInstanceState) {
        Long presenterId = savedInstanceState.getLong(PRESENTER_KEY);
        P presenter = (P) presenters.getIfPresent(presenterId);
        presenters.invalidate(presenterId);
        return presenter;
    }

    public void savePresenter(BasePresenter<?> presenter, Bundle outState) {
        long presenterId = currentId.incrementAndGet();
        presenters.put(presenterId, presenter);
        outState.putLong(PRESENTER_KEY, presenterId);
    }
}
