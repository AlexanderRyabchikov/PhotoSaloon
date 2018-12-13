package com.photosaloon.repository;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

public class RepositoryProvider {

    private static UserRepository userRepository;

    private RepositoryProvider(){}

    @MainThread
    public static void initialize() {
        userRepository = HawkUserRepository.getInstance();
    }

    @NonNull
    public static UserRepository provideUserRepository() {
        if (userRepository == null) {
            userRepository = HawkUserRepository.getInstance();
        }

        return userRepository;
    }

}
