package com.photosaloon.config;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.hawk.Hawk;
import com.photosaloon.repository.RepositoryProvider;

public class App extends Application {
    private static App instance;

    private static FirebaseAuth firebaseAuth;
    private static FirebaseUser firebaseUser;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        initHawk();

        RepositoryProvider.initialize();

    }

    public static App getInstance() {
        return instance;
    }

    public static FirebaseAuth getFirebaseAuth() { return firebaseAuth; }

    public static FirebaseUser getFirebaseUser() { return firebaseUser; }

    private void initHawk() {
        Hawk.init(this).build();
    }

}
