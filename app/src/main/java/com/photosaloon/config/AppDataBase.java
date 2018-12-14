package com.photosaloon.config;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.photosaloon.daoModel.DaoClient;
import com.photosaloon.daoModel.DaoRecords;
import com.photosaloon.model.Client;
import com.photosaloon.model.Records;

@Database(entities = {Records.class, Client.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract DaoRecords daoRecords();
    public abstract DaoClient daoClient();
}
