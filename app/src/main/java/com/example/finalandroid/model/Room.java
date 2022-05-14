package com.example.finalandroid.model;

import java.io.Serializable;

public class Room implements Serializable {
    private Integer idRoom;
    private String name;
    private String floor;
    private String describe;
    private String price;
    private String image;
    private Integer idHotel;

    public Room(Integer idRoom, String name, String floor, String describe, String price, String image, Integer idHotel) {
        this.idRoom = idRoom;
        this.name = name;
        this.floor = floor;
        this.describe = describe;
        this.price = price;
        this.image = image;
        this.idHotel = idHotel;
    }

    public Room() {
    }

    public Integer getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(Integer idHotel) {
        this.idHotel = idHotel;
    }
}
