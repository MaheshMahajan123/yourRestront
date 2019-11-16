package com.example.yoursretront.Drinks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yoursretront.Car_T.AddToCart;
import com.example.yoursretront.R;
import com.example.yoursretront.Start_page;
import com.example.yoursretront.Start_page_Fragment;
import com.example.yoursretront.Uploadfil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class All_Drinks extends AppCompatActivity {

    TextView eye;
    TextView addtocart, price, mEditTextFileName,discription;
    ConstraintLayout tr;
    ImageView imageView3;
    long maxid=0;
    private Uri imageUrl;
    private StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    Uploadfil uploadfil;
    private static final String TAG = "GalleryActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__drinks);
        eye = findViewById(R.id.eye);
        imageView3 = findViewById(R.id.imageView3);
        addtocart = findViewById(R.id.addtocart);
        discription = findViewById(R.id.discription);
        tr = findViewById(R.id.trr);
        getIncomingIntent();
        eye.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        uploadfil=new  Uploadfil();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Add-To-Cart");

        addtocart.setOnClickListener(new View.OnClickListener() {
            boolean visible;

            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(tr);
                visible = !visible;
                eye.setVisibility(visible ? View.VISIBLE : View.GONE);
                imageView3.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });
    }
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")
                && getIntent().hasExtra("image_discription")&& getIntent().hasExtra("image_price")){

            Log.d(TAG, "getIncomingIntent: found intent extras.");
            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");
            String image_discription = getIntent().getStringExtra("image_discription");
            String image_price = getIntent().getStringExtra("image_price");
            setImage(imageUrl, imageName,image_discription,image_price);
        }
    }
    private void setImage(final String imageUrl, String imageName, String image_discription, String image_price){
        Log.d(TAG, "setImage: setting te image and name to widgets.");
        final TextView name = findViewById(R.id.mEditTextFileName);
        final TextView  discription= findViewById(R.id.discription);
        final TextView price = findViewById(R.id.price);

        name.setText(imageName);
        discription.setText(image_discription);
        price.setText(image_price);

        ImageView image = findViewById(R.id.mImageView);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namee=name.getText().toString();
                String ddiscription=discription.getText().toString().trim();
                String pprice=price.getText().toString().trim();
                String img=imageUrl.trim();

                String id=mDatabaseRef.push().getKey();
                Uploadfil uploadfil=new Uploadfil(namee,img,pprice,ddiscription);
                mDatabaseRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(id).setValue(uploadfil);
                uploadfil.setName(namee);
                uploadfil.setDiscription(ddiscription);
                uploadfil.setPrice(pprice);
                uploadfil.setImageUrl(img);
//                Fragment fragment = new Start_page_Fragment();
//                if (fragment != null) {
//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.trr, fragment);
//                    ft.addToBackStack("datil");
//                    ft.commit();
//                }
                Intent i=new Intent(getApplicationContext(), Start_page.class);
                startActivity(i);
                Toast.makeText(All_Drinks.this, "upload sucessfull", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
