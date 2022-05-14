package com.example.finalandroid.model;

import java.io.Serializable;

public class BookRoomOfUser implements Serializable {
    private String timeNhan;
    private String timeTra;

    public BookRoomOfUser(String timeNhan, String timeTra) {
        this.timeNhan = timeNhan;
        this.timeTra = timeTra;
    }

    public BookRoomOfUser() {
    }

    public String getTimeNhan() {
        return timeNhan;
    }

    public void setTimeNhan(String timeNhan) {
        this.timeNhan = timeNhan;
    }

    public String getTimeTra() {
        return timeTra;
    }

    public void setTimeTra(String timeTra) {
        this.timeTra = timeTra;
    }
}
