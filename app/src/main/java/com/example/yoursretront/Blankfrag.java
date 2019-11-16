package com.example.yoursretront;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.yoursretront.Drinks.DrinkAdapter;
import com.example.yoursretront.Drinks.Hr_layout_drink;
import com.example.yoursretront.Nonveg.Hr_layout_nonveg;
import com.example.yoursretront.Tab_view_pager.MyPagerAdapter;
import com.example.yoursretront.Tab_view_pager.ViewPagerItemFragment;
import com.example.yoursretront.Tab_view_pager.models.Hat;
import com.example.yoursretront.Tab_view_pager.resources.Hats;
import com.example.yoursretront.Tab_view_pager.resources.Main;
import com.example.yoursretront.Veg.ImageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Blankfrag extends Fragment  {
    private String TAG = "Blankfrag";
    private RecyclerView mRecyclerView,Drinksry;
    private ImageAdapter mAdapter;
    private DrinkAdapter mDrink;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDrinkdatabase;
    private List<Uploadfil> mUploads;
    private FirebaseStorage mStorage,mDrinkstorage;
    private ValueEventListener mDBListener;
    private Object FragmentManager;
    private FrameLayout hrlayout,drinklayout,swapping;
    private ViewPager mMyViewPager;
    TabLayout mTabLayout;
    SearchView search;
ImageView offerimage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup content_frame,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blankfrag, content_frame, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        hrlayout = view.findViewById(R.id.hrlayout);
        swapping = view.findViewById(R.id.swapping);
  //      search = view.findViewById(R.id.search);
        offerimage = view.findViewById(R.id.offerimage);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        drinklayout= view.findViewById(R.id.drinklayout);
//        LinearLayoutManager linearLayout=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
//        Drinksry.setLayoutManager(linearLayout);

        mUploads = new ArrayList<Uploadfil>();
        mAdapter = new ImageAdapter(getActivity(),mUploads);
        mRecyclerView.setAdapter(mAdapter);

        Fragment newFragment = new Hr_layout_nonveg();
        FragmentTransaction transaction =  getFragmentManager().beginTransaction();;
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
        transaction.replace(R.id.hrlayout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
       ///////////////////////////////////////////////////////////////////////////
        Fragment newFragmen = new Hr_layout_drink();
        FragmentTransaction a =  getFragmentManager().beginTransaction();;
        a.replace(R.id.drinklayout, newFragmen);
        a.addToBackStack(null);
        a.commit();
///////////////////////////////////////////////////////////////////////////////////////////
        Fragment newFragme= new Swapping();
        FragmentTransaction ab =  getFragmentManager().beginTransaction();;
        ab.replace(R.id.swapping, newFragme);
        ab.addToBackStack(null);
        ab.commit();

offerimage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(getContext(), Main.class);
        startActivity(i);
    }
});
        mDBListener=mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Uploadfil upload = postSnapshot.getValue(Uploadfil.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //   mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                mAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
        return  view;
    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }


}


