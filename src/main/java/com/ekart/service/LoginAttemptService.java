package com.ekart.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.springframework.stereotype.Service;

import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPT = 5;
    private LoadingCache<String, Integer> attemptsCache;

    LoginAttemptService() {
        super();
        // block the user for 30 mins if no of attempts exceeded
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<String, Integer>() {
                            @Override
                            public Integer load(String s) throws Exception {
                                return 0;
                            }
                        }
                );
    }

    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (final ExecutionException ex) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(final String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (final ExecutionException ex) {
            return false;
        }
    }
}
