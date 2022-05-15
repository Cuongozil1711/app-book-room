package com.example.finalandroid.model;

import java.io.Serializable;

public class ReviewHotel implements Serializable {
    private String nameReview;
    private String dayReview;
    private String reviewStar;
    private String idReview;

    public ReviewHotel() {
    }

    public ReviewHotel(String nameReview, String dayReview, String reviewStar, String idReview) {
        this.nameReview = nameReview;
        this.dayReview = dayReview;
        this.reviewStar = reviewStar;
        this.idReview = idReview;
    }

    public String getNameReview() {
        return nameReview;
    }

    public void setNameReview(String nameReview) {
        this.nameReview = nameReview;
    }

    public String getDayReview() {
        return dayReview;
    }

    public void setDayReview(String dayReview) {
        this.dayReview = dayReview;
    }

    public String getReviewStar() {
        return reviewStar;
    }

    public void setReviewStar(String reviewStar) {
        this.reviewStar = reviewStar;
    }

    public String getIdReview() {
        return idReview;
    }

    public void setIdReview(String idReview) {
        this.idReview = idReview;
    }
}
