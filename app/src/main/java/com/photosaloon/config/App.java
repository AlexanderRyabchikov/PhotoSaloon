package com.photosaloon.config;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    public static FirebaseAuth getFirebaseAuth() { return FirebaseAuth.getInstance(); }

    public static FirebaseUser getFirebaseUser() { return getFirebaseAuth().getCurrentUser(); }

}
