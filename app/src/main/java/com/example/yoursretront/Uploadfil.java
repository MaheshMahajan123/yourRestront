package com.example.yoursretront;

import com.google.firebase.database.Exclude;

public class Uploadfil {
    private String mName;
    private String price;
    private String discription;
    private String mImageUrl;
    private String mKey;

    public Uploadfil() {
        //empty constructor needed
    }

    public Uploadfil(String name, String imageUrl,String price,String discription) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        this.price=price;
        this.discription=discription;
         mName = name;
         mImageUrl = imageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    @Exclude
    public String getmKey() {
        return mKey;

    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }

}