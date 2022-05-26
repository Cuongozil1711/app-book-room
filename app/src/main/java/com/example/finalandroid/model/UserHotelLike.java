package com.example.finalandroid.model;

import java.io.Serializable;

public class UserHotelLike implements Serializable {
    private Integer id;
    private String userLike;
    private Integer idUser;
    private Integer idHotel;

    public UserHotelLike() {
    }

    public UserHotelLike(Integer id, String userLike, Integer idUser, Integer idHotel) {
        this.id = id;
        this.userLike = userLike;
        this.idUser = idUser;
        this.idHotel = idHotel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserLike() {
        return userLike;
    }

    public void setUserLike(String userLike) {
        this.userLike = userLike;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(Integer idHotel) {
        this.idHotel = idHotel;
    }
}
