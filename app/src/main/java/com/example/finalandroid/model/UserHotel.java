package com.example.finalandroid.model;

import java.io.Serializable;

public class UserHotel implements Serializable {
    private Integer idUserHotel;
    private String review;
    private String star;
    private String dateReview;
    private Integer idUser;
    private Integer idHotel;

    public UserHotel(Integer idUserHotel, String review, String star, String dateReview, Integer idUser, Integer idHotel) {
        this.idUserHotel = idUserHotel;
        this.review = review;
        this.star = star;
        this.dateReview = dateReview;
        this.idUser = idUser;
        this.idHotel = idHotel;
    }

    public UserHotel() {
    }

    public Integer getIdUserHotel() {
        return idUserHotel;
    }

    public void setIdUserHotel(Integer idUserHotel) {
        this.idUserHotel = idUserHotel;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getDateReview() {
        return dateReview;
    }

    public void setDateReview(String dateReview) {
        this.dateReview = dateReview;
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
