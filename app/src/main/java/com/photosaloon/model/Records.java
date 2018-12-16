package com.photosaloon.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.photosaloon.content.Services;

import java.util.List;
@Entity
public class Records {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String nameMaster;

    public String date;

    public String time;

    @Embedded
    public List<Services> typeServices;


}
