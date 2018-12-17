package com.photosaloon.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Client {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String nameFirst;

    public String nameLast;

    public String phone;

    public String email;

    public String bithday;

}
