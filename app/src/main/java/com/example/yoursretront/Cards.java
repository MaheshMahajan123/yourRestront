package com.example.yoursretront;

public class Cards {

    private String description;

    private String imagePath;

    public Cards(String imagePath, String description) {
        this.imagePath = imagePath;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

}