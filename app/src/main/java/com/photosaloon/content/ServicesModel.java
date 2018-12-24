package com.photosaloon.content;

public class ServicesModel {

    public ServicesModel(String typeServices, int price) {
        this.typeServices = typeServices;
        this.price = price;
    }

    public String getTypeServices() {
        return typeServices;
    }

    public int getPrice() {
        return price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private String typeServices;

    private int price;

    private boolean isSelected = false;

}
