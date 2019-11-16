package com.example.yoursretront.Car_T;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yoursretront.Buy;
import com.example.yoursretront.R;
import com.example.yoursretront.Uploadfil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddToCart extends Fragment implements CartAdapter.OnItemClickListener {

//    public void sendInput(String input) {
//        Log.d(TAG, "send nmessage" + input);
//        mInputdisplay.setText(input);
//    }


    private String TAG = "Blankfrag";
    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private DatabaseReference nonmDatabaseRef, frendsref;
    private List<Uploadfil> mUploads;
    private FirebaseStorage nonmStorageRef;
    CardView buyy;
    ImageView imageView7;
    TextView count, total1, mInputdisplay;
    private ValueEventListener mDBListener;
    private int ccount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_to_cart, container, false);
        mRecyclerView = view.findViewById(R.id.rere);
        buyy = view.findViewById(R.id.Buy);
        count = view.findViewById(R.id.count);

        total1 = view.findViewById(R.id.total);
        //   total1.setText(CartAdapter.total+"");


        frendsref = FirebaseDatabase.getInstance().getReference("Add-To-Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d(TAG, "LayoutManager: " + getActivity());

        nonmDatabaseRef = FirebaseDatabase.getInstance().getReference("Add-To-Cart")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mUploads = new ArrayList<Uploadfil>();

        mAdapter = new CartAdapter(getActivity(), mUploads);
        mRecyclerView.setAdapter(mAdapter);

        nonmStorageRef = FirebaseStorage.getInstance();
        nonmDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Uploadfil upload = postSnapshot.getValue(Uploadfil.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }
                mAdapter.setOnItemClickListener(AddToCart.this);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //   mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
        frendsref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ccount = (int) dataSnapshot.getChildrenCount();
                    count.setText("Total Items  "+Integer.toString(ccount));
                    //
                } else {
                    count.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
//////////////////////////////////////////////////////////////////////////////////////////////////
        frendsref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object price = map.get("price");
                    int p = Integer.parseInt(String.valueOf(price));
                    sum += p;
                    total1.setText("Total price  "+String.valueOf(sum));
                }
////////////////////////////////////////////////////////////////////////////////////////////////////
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Buy.class);
                startActivity(i);
            }
        });
        return view;
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(getContext(), "whatever click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        // Toast.makeText(this, "Delete click at position: " + position, Toast.LENGTH_SHORT).show();
        nonmDatabaseRef = FirebaseDatabase.getInstance().getReference("Add-To-Cart")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Uploadfil selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getmKey();
        Log.d(TAG, "delete: " + position);
      //  StorageReference imageRef = nonmStorageRef.getReferenceFromUrl(selectedItem.getImageUrl());
        nonmDatabaseRef.child(selectedKey).removeValue();
//        nonmDatabaseRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void sendInputcount(final int position) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("ARE YOU SURE you  want to Delete This Item").setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                nonmDatabaseRef = FirebaseDatabase.getInstance().getReference("Add-To-Cart")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Uploadfil selectedItem = mUploads.get(position);
                final String selectedKey = selectedItem.getmKey();
                Log.d(TAG, "delete: " + position);
                nonmDatabaseRef.child(selectedKey).removeValue();
//                    System.exit(0);
            }
        }).setNegativeButton("CANCLE",null);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();


    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        nonmDatabaseRef.removeEventListener(mDBListener);
//    }

    }

