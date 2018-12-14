package com.photosaloon.content;

public enum Gender {

    MALE(1),
    FEMALE(2);


    Gender(int i) {
        this.type = i;
    }

    private int type;
}
