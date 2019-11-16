package com.example.yoursretront.Car_T;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.yoursretront.Buy;
import com.example.yoursretront.Nonveg.All_nonveg;
import com.example.yoursretront.R;
import com.example.yoursretront.Uploadfil;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ImageViewHolder> {


//
//    public OnInputSelected mOnInputSelected;
    private Context mContext;
    private List<Uploadfil> mUploads;
    private String TAG = "adapterTAG";
    public int total = 0;
    private OnItemClickListener mListener;
    ElegantNumberButton buttoncount;
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName, price, discription, result;
        public ImageView imageView,deleteitem;
        public LinearLayout parent_layout;

        public ImageViewHolder(View itemView) {
            super(itemView);
             buttoncount = itemView. findViewById(R.id.countbutton);
            textViewName = itemView.findViewById(R.id.name);
            deleteitem = itemView.findViewById(R.id.deleteitem);
            result = itemView.findViewById(R.id.result);
            price = itemView.findViewById(R.id.price);
            discription = itemView.findViewById(R.id.discription);
            imageView = itemView.findViewById(R.id.circle1);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            itemView.setOnClickListener(this);
            deleteitem.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

          deleteitem.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if(mListener!=null)
                  {
                      int position=getAdapterPosition();
                      if(position!=RecyclerView.NO_POSITION)
                      {
                          mListener.sendInputcount(position);
                      }
                  }

                }
            });
        }
        @Override
        public void onClick(View view) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("select Action");
            MenuItem doWhatever = contextMenu.add(Menu.NONE, 1, 1, "Do whatever");
            MenuItem delete = contextMenu.add(Menu.NONE, 2, 2, "Delete");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (menuItem.getItemId())
                    {
                        case 1:
                            mListener.onWhatEverClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);
        void sendInputcount(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CartAdapter(Context context, List<Uploadfil> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.faltu, parent, false);
        ImageViewHolder va = new ImageViewHolder(v);
        return va;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        final Uploadfil uploadCurrent = mUploads.get(position);

        holder.textViewName.setText(uploadCurrent.getName());
        holder.price.setText(uploadCurrent.getPrice());
        holder.discription.setText(uploadCurrent.getDiscription());
        Glide.with(mContext).load(mUploads.get(position).getImageUrl()).into(holder.imageView);

//data send  static bna do  uperrr
//    int onetprice=(Integer.valueOf(uploadCurrent.getPrice()));
//    total=total+onetprice;
        //Android Shopping Cart System-Calculate Total Price-shopApplication Android Studio Tutorials
        //Coding cafe
        Log.d(TAG, "onBindViewHolder: " + mUploads.get(position).getImageUrl());
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Cart.class);
                Log.d(TAG, "onClick: clicked on: " + new Intent(mContext, All_nonveg.class));
                intent.putExtra("image_url", uploadCurrent.getImageUrl());
                intent.putExtra("image_name", uploadCurrent.getName());
                intent.putExtra("image_discription", uploadCurrent.getDiscription());
                intent.putExtra("image_price", uploadCurrent.getPrice());
                intent.putExtra("image_key", uploadCurrent.getmKey());
                mContext.startActivity(intent);

            }
        });
//        buttoncount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
//            @Override
//            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
//                Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
//                String num = buttoncount.getNumber();
//                holder.result.setText(uploadCurrent.getPrice());
//                       }
//        });
    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }








}