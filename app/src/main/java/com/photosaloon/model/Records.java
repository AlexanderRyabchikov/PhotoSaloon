package com.photosaloon.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Records {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String nameMaster;

    public String date;

    public String time;

    public String typeServices;
}
