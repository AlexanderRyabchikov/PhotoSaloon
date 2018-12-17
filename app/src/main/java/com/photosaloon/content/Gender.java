package com.photosaloon.content;

public enum Gender {

    MALE(0),
    FEMALE(1);

    private final int type;

    Gender(int i) {
        this.type = i;
    }

    public int getValue() {
        return type;
    }
}
