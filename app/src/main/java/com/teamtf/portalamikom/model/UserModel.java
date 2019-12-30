package com.teamtf.portalamikom.model;

public class UserModel {

    private String userid, privilages, name, gender, address;

    public UserModel() {

    }

    public UserModel(String userid, String privilages, String name, String gender, String address) {
        this.userid = userid;
        this.privilages = privilages;
        this.name = name;
        this.gender = gender;
        this.address = address;
    }

    public String getUserid() {
        return userid;
    }

    public String getPrivilages() {
        return privilages;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }
}
