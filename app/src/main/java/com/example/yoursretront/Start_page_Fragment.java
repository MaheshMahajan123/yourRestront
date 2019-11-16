package com.example.yoursretront;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.yoursretront.Car_T.AddToCart;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * A simple {@link Fragment} subclass.
 */
//https://codinginflow.com/tutorials/android/bottomnavigationview
public class Start_page_Fragment extends Fragment {

    private FrameLayout frameLayout;

    public Start_page_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start_page_, container, false);

        BottomNavigationView bottomNav = view.findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
//            Fragment newFragmen = new Blankfrag();
//            FragmentTransaction a =  getFragmentManager().beginTransaction();;
//            a.replace(R.id.fragment_container, newFragmen);
//            a.commit();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Blankfrag()).commit();
        }
        return view;  }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment =null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new Blankfrag();
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new AddToCart();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new Booking_Fragment();
                            break;
                    }

                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();


                    return true;
                }
            };
}