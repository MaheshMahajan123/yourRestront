package com.example.yoursretront.Nonveg;

import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yoursretront.R;
import com.example.yoursretront.Uploadfil;

import java.util.List;

public class ImageAdapter2 extends RecyclerView.Adapter<ImageAdapter2.ImageViewHolder> {
    private Context mContextt;
    private List<Uploadfil> mUploadss;
    private String TAG = "adapterTAG";
    private OnItemClickListener mListener;

    public ImageAdapter2(Context contextt, List<Uploadfil> uploads) {
        mContextt = contextt;
        mUploadss = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContextt).inflate(R.layout.nonvegrecycler, parent, false);
        ImageViewHolder va=new ImageViewHolder(v);
        return  va;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder,final int position) {
        final Uploadfil uploadCurrent = mUploadss.get(position);
        holder.atextViewName.setText(uploadCurrent.getName());

        holder.aprice.setText(uploadCurrent.getPrice());
        holder.adiscription.setText(uploadCurrent.getDiscription());
        Glide.with(mContextt).load(mUploadss.get(position).getImageUrl()).into(holder.aimageView);

        Log.d(TAG, "onBindViewHolder: " + mUploadss.get(position).getImageUrl());

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContextt, All_nonveg.class);
                Log.d(TAG, "onClick: clicked on: " + new Intent(mContextt, All_nonveg.class));
                intent.putExtra("image_url", uploadCurrent.getImageUrl());
                intent.putExtra("image_name",uploadCurrent .getName());
                intent.putExtra("image_discription",uploadCurrent .getDiscription());
                intent.putExtra("image_price",uploadCurrent .getPrice());
                intent.putExtra("image_key",uploadCurrent .getmKey());
                mContextt.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploadss.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener
    {
        public TextView atextViewName,aprice,adiscription;
        public ImageView aimageView;
        public LinearLayout parent_layout;
        public ImageViewHolder(View itemView) {
            super(itemView);

            atextViewName = itemView.findViewById(R.id.name2);
            aprice = itemView.findViewById(R.id.price2);
            adiscription = itemView.findViewById(R.id.discription2);
            aimageView = itemView.findViewById(R.id.image2);
            parent_layout = itemView.findViewById(R.id.parent_layout);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
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
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}