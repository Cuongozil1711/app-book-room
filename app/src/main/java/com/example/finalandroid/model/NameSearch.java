package com.example.finalandroid.model;

import java.io.Serializable;

public class NameSearch implements Serializable {
    private int id;
    private String nameSearch;

    public NameSearch(){

    }

    public NameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public NameSearch(int id, String nameSearch) {
        this.id = id;
        this.nameSearch = nameSearch;
    }

    public int getId() {
        return id;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }
}
