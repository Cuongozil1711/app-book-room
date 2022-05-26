package com.example.finalandroid.dto;

import com.example.finalandroid.model.User;

import java.io.Serializable;

import okhttp3.MultipartBody;

public class Userbean implements Serializable {
    private User user;
    private MultipartBody.Part files;

    public Userbean(User user, MultipartBody.Part files) {
        this.user = user;
        this.files = files;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MultipartBody.Part getFiles() {
        return files;
    }

    public void setFiles(MultipartBody.Part files) {
        this.files = files;
    }
}
