package com.example.appbanhang.model;

import java.util.ArrayList;

public class List_bg {
    private String id;
    private String images_bg;

    private String status;
    private ArrayList<List_bg> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<List_bg> getResult() {
        return result;
    }

    public void setResult(ArrayList<List_bg> result) {
        this.result = result;
    }

    public List_bg(String id) {
        this.id = id;
    }

    public List_bg() {
    }

    public List_bg(String id, String images) {
        this.id = id;
        this.images_bg = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImages_bg() {
        return images_bg;
    }

    public void setImages_bg(String images_bg) {
        this.images_bg = images_bg;
    }
}
