package com.example.yoursretront.Car_T;

import android.net.Uri;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.yoursretront.R;


public class Cart extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_click_onitem);
        getIncomingIntent();
    }
    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");
//        if (getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")
//                && getIntent().hasExtra("image_discription") && getIntent().hasExtra("image_price")) {
            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");
            String image_discription = getIntent().getStringExtra("image_discription");
            String image_price = getIntent().getStringExtra("image_price");
            setImage(imageUrl, imageName, image_discription, image_price);
    }
    private void setImage(final String imageUrl, String imageName, String image_discription, String image_price) {
        Log.d(TAG, "setImage: setting te image and name to widgets.");
        final TextView name = findViewById(R.id.mEditTextFileName);
        final TextView discription = findViewById(R.id.discription);
        final TextView price = findViewById(R.id.price);

        name.setText(imageName);
        discription.setText(image_discription);
        price.setText(image_price);
        final ImageView image = findViewById(R.id.mImageView);

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }
    }