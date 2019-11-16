package com.example.yoursretront;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Booking_Fragment extends Fragment {

    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    //vars

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();


    public Booking_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.fragment_booking, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView =view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter= new RecyclerViewAdapter(getActivity(), mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        getImages();
        return view;
    }

    private void getImages(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/f/fb/Annette's_Diner-_Table_restaurant.jpg");
        mNames.add("ONE SETTER");
        mImageUrls.add("https://www.lifesharephoto.com/wp-content/uploads/2018/03/american-coffee-shop-restaurant-retro-wood-dinette-combination-of-pertaining-to-coffee-shop-tables.jpg");
        mNames.add("TWO SETTER");

        mImageUrls.add("https://images.squarespace-cdn.com/content/5693e13b0ab377d5dda719c9/1455648682192-74VXZKZ07XE8OA81MQP1/2012-07-28+09.08.17.jpg?format=1000w&content-type=image%2Fjpeg");
        mNames.add("THREE SETTER");

        mImageUrls.add("https://static.turbosquid.com/Preview/2014/07/03__21_20_07/squarerestauranttablebanquetdiningsettablewaretableclothplateringrosenapkinfacilitiesservedlayingaccessorys0001.jpgc9db8aa6-5808-4cc6-9d83-538acc0faed5Original.jpg");
        mNames.add("FOUR SETTER");

        mImageUrls.add("https://ebth-com-production.imgix.net/2018/05/20/09/28/47/d16dcf21-b8b3-447c-ab7c-dc20b7f431b0/IMG_8670-Edit.jpg?ixlib=rb-1.1.0&w=400&h=400&fit=crop&crop=&auto=format");
        mNames.add("FIVE SETTER");

        mImageUrls.add("http://photolike.me/wp-content/uploads/2019/07/drexel-dining-chairs-heritage-dining-chairs-room-set-table-and-drexel-dining-tables.jpg");
        mNames.add("SIX SETTER");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/5/50/Italian_dishes_on_a_table%2C_served_at_a_restaurant_in_Dhaka%2C_Bangladesh._2.JPG");
        mNames.add("SEVEN SETTER");

        mImageUrls.add("http://www.quercus-furniture.co.uk/wp-content/uploads/2015/04/Large-Oak-Dining-Table.jpg");
        mNames.add("EIGHT SETTER");

        mImageUrls.add("https://www.acorn-furniture.co.uk/images/fullscreen/spalted-beech-table-top3.jpg");
        mNames.add("TEN SETTER");

        mImageUrls.add("https://img.freepik.com/free-photo/festive-served-table-romantic-dinner-couple-terrace-by-sea_114830-9.jpg?size=626&ext=jpg");
        mNames.add("COUPLE SETTER");
}}
