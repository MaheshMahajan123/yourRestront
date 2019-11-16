package com.example.yoursretront;

import android.net.Uri;

public class Upload {
    private String email, phone, Password, name ;
    private Uri mImageUrl;

    public Upload(String email, String phone, String password, String name,Uri mImageUrl) {
        this.email = email;
        this.phone = phone;
        Password = password;
        this.name = name;
        this.mImageUrl = mImageUrl;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(Uri mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
