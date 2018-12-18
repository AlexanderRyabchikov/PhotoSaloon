package com.photosaloon.daoModel;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.photosaloon.model.Client;
import io.reactivex.Single;

@Dao
public interface DaoClient {

    @Query("SELECT * FROM Client")
    Single<Client> getClient();

    @Insert
    void insert (Client client);

    @Update
    void update (Client client);

}
