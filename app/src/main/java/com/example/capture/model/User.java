package com.example.capture.model;

import java.util.ArrayList;

public class User {

    String name, phoneNumber, uid, imageUrl;
    public static ArrayList<Integer> cartList;

    public User() {
    }

    public User(String name, String phoneNumber, String uid, String imageUrl) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.uid = uid;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
