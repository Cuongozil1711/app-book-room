package com.example.finalandroid.model;

import java.io.Serializable;

public class Hotel implements Serializable {
    private Integer id;
    private String name;
    private String address;
    private String describe;
    private String star;
    private String image;
    private String phone;

    public Hotel(Integer id, String name, String address, String describe, String star, String image, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.describe = describe;
        this.star = star;
        this.image = image;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
