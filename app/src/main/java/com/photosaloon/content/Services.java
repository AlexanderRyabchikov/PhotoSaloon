package com.photosaloon.content;

public class Services {

    public Services(String typeServices, int price) {
        this.typeServices = typeServices;
        this.price = price;
    }

    public String typeServices;

    public int price;

    public String getTypeServices() {
        return typeServices;
    }

    public int getPrice() {
        return price;
    }
}
