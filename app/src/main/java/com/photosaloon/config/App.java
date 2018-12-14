package com.photosaloon.config;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class App extends Application {
    private static App instance;
    private static AppDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        dataBase = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "database").build();
    }

    public static App getInstance() {
        return instance;
    }

    public static AppDataBase getDataBase() { return dataBase; }

    public static FirebaseAuth getFirebaseAuth() { return FirebaseAuth.getInstance(); }

    public static FirebaseUser getFirebaseUser() { return getFirebaseAuth().getCurrentUser(); }

}
