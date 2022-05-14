package com.example.finalandroid.model;

import java.io.Serializable;

public class HistoryBookRoom implements Serializable {
    private Hotel hotel;
    private UserRoom userRoom;
    private Room room;

    public HistoryBookRoom(Hotel hotel, UserRoom userRoom, Room room) {
        this.hotel = hotel;
        this.userRoom = userRoom;
        this.room = room;
    }

    public HistoryBookRoom() {
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public UserRoom getUserRoom() {
        return userRoom;
    }

    public void setUserRoom(UserRoom userRoom) {
        this.userRoom = userRoom;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
