package com.example.finalandroid.model;

import java.io.Serializable;
import java.util.Date;

public class UserRoom implements Serializable {
    private Integer idUserRoom;
    private Integer idUser;
    private Integer idRoom;
    private String dateFrom;
    private String dateTo;
    private String isPayMent;
    private String isDelete;

    public UserRoom(Integer idUserRoom, Integer idUser, Integer idRoom, String dateFrom, String dateTo, String isPayMent, String isDelete) {
        this.idUserRoom = idUserRoom;
        this.idUser = idUser;
        this.idRoom = idRoom;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.isPayMent = isPayMent;
        this.isDelete = isDelete;
    }

    public UserRoom() {
    }

    public Integer getIdUserRoom() {
        return idUserRoom;
    }

    public void setIdUserRoom(Integer idUserRoom) {
        this.idUserRoom = idUserRoom;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getIsPayMent() {
        return isPayMent;
    }

    public void setIsPayMent(String isPayMent) {
        this.isPayMent = isPayMent;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
