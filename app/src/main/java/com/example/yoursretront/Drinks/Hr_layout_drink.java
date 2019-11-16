package com.example.yoursretront.Drinks;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yoursretront.R;
import com.example.yoursretront.Uploadfil;
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
public class Hr_layout_drink extends Fragment  {

    private String TAG = "Blankfrag";
    private RecyclerView Horizontal;
    private DrinkAdapter mAdapter;
    private DatabaseReference nonmDatabaseRef;
    private List<Uploadfil> mUploads;
    private FirebaseStorage nonmStorageRef;
    private ValueEventListener mDBListener;
    private Object FragmentManager;
    public Hr_layout_drink() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_hr_drink, container, false);
        Horizontal=view.findViewById(R.id.hr);
  LinearLayoutManager linearLayout=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
   Horizontal.setLayoutManager(linearLayout);

       // nonmDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        // nonmStorageRef = FirebaseStorage.getInstance().getReference("Nonveg");
        mUploads = new ArrayList<Uploadfil>();
        mAdapter = new DrinkAdapter(getActivity(),mUploads);
        Horizontal.setAdapter(mAdapter);
//        Horizontal.setAdapter(mAdapter);
       // mAdapter.setOnItemClickListener(Hr_layout_nonveg.this);
        nonmStorageRef=FirebaseStorage.getInstance();
        nonmDatabaseRef = FirebaseDatabase.getInstance().getReference("Drinks");

        nonmDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Uploadfil upload = postSnapshot.getValue(Uploadfil.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }
                mAdapter.notifyDataSetChanged();
//
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //   mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

       return  view;
    }

//    @Override
//    public void onItemClick(int position) {
//
//    }
//
//    @Override
//    public void onWhatEverClick(int position) {
//
//    }
//
//    @Override
//    public void onDeleteClick(int position) {
//        Toast.makeText(getContext(), "hahahh", Toast.LENGTH_SHORT).show();
//    }
}
