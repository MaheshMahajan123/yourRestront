package com.example.yoursretront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yoursretront.Drinks.All_Drinks;
import com.example.yoursretront.Veg.All_veg;
import com.google.android.material.textfield.TextInputLayout;

public class Buy extends AppCompatActivity {
TextInputLayout extre;
Button buyy;
TextView editText;
String abc="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        extre=findViewById(R.id.extra);
        buyy=findViewById(R.id.buyy);
buyy.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       Intent i = new Intent(Buy.this,Start_page.class);
        startActivity(i);

    }
});
//   abc=getIntent().getStringExtra("totalprice");
//        editText.setText(abc);
    }
}
