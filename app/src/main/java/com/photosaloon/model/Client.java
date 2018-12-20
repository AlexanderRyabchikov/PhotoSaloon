package com.photosaloon.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Client {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    public String phone;

    public String photo;

    public String email;

}
