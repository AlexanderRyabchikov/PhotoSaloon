package com.photosaloon.daoModel;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.photosaloon.model.Records;

import java.util.List;
import io.reactivex.Observable;

@Dao
public interface DaoRecords {

    @Query("SELECT * FROM Records")
    Observable<List<Records>> getAllRecords();

    @Query("SELECT * FROM Records WHERE id = :id")
    Observable<Records> getById(long id);

    @Insert
    void insert(Records records);

    @Update
    void update (Records records);

    @Delete
    void delete (Records records);

}
