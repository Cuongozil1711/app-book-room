package com.example.finalandroid.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private Integer id;
    private String uuId;
    private String email;
    private String name;
    private String password;
    private String phone;
    private String age;
    private String address;
    private String typeLogin;
    private String gener;
    private String image;
    private String isPending;
    private String accessToken;

    public User(Integer id, String uuId, String email, String name, String password, String phone, String age, String address, String typeLogin, String gener, String image, String isPending, String accessToken) {
        this.id = id;
        this.uuId = uuId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.age = age;
        this.address = address;
        this.typeLogin = typeLogin;
        this.gener = gener;
        this.image = image;
        this.isPending = isPending;
        this.accessToken = accessToken;
    }


    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypeLogin() {
        return typeLogin;
    }

    public void setTypeLogin(String typeLogin) {
        this.typeLogin = typeLogin;
    }

    public String getGener() {
        return gener;
    }

    public void setGener(String gener) {
        this.gener = gener;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsPending() {
        return isPending;
    }

    public void setIsPending(String isPending) {
        this.isPending = isPending;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
