package com.example.capture.model;

public class ProductCategory {
    private int id;
    private String name, icon, about, color;

    public ProductCategory(int id, String name, String icon, String about, String color) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.about = about;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
