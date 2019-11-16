package com.example.yoursretront.Veg;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yoursretront.Nonveg.All_nonveg;
import com.example.yoursretront.R;
import com.example.yoursretront.Uploadfil;
import com.example.yoursretront.zClickL;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>  {
    private Context mContext;
    private List<Uploadfil> mUploads;
    private List<Uploadfil> examplelistfull;

    private String TAG = "adapterTAG";
    private zClickL mListener;

    public ImageAdapter(Context context, List<Uploadfil> uploads) {
        mContext = context;
        mUploads = uploads;
        examplelistfull =new ArrayList<>(uploads);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.imagei_tem, parent, false);
        ImageViewHolder va=new ImageViewHolder(v);
        return  va;
    }

@Override
public void onBindViewHolder(ImageViewHolder holder, final int position) {
    final Uploadfil uploadCurrent = mUploads.get(position);
    holder.textViewName.setText(uploadCurrent.getName());

    holder.price.setText(uploadCurrent.getPrice());
    holder.discription.setText(uploadCurrent.getDiscription());
    Glide.with(mContext).load(mUploads.get(position).getImageUrl()).into(holder.imageView);

    Log.d(TAG, "onBindViewHolder: " + mUploads.get(position).getImageUrl());

    holder.parent_layout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, All_veg.class);
            Log.d(TAG, "onClick: clicked on: " + new Intent(mContext, All_nonveg.class));
            intent.putExtra("image_url", uploadCurrent.getImageUrl());
            intent.putExtra("image_name",uploadCurrent .getName());
            intent.putExtra("image_discription",uploadCurrent .getDiscription());
            intent.putExtra("image_price",uploadCurrent .getPrice());
            intent.putExtra("image_key",uploadCurrent .getmKey());
            mContext.startActivity(intent);
        }
    });
}

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

//    @Override
//    public Filter getFilter() {
//       return  examplefilter;
//    }
//    private Filter examplefilter=new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            List<Uploadfil>filterlist=new ArrayList<>();
//    if(charSequence==null || charSequence.length()==0)
//    {
//        filterlist.addAll(examplelistfull);
//    }
//    else
//    {
//        String filterpattern=charSequence.toString().toLowerCase().trim();
//        for (Uploadfil item : examplelistfull)
//        {
//            if(item.getPrice().toLowerCase().contains(filterpattern))
//            {
//                filterlist.add(item);
//            }
//        }
//    }FilterResults filterresult=new FilterResults();
//    filterresult.values=filterlist;
//    return filterresult;
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//mUploads.clear();
//mUploads.addAll((List)filterResults.values);
//notifyDataSetChanged();
//        }
//    };

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName, price, discription;
        public ImageView imageView;
        public LinearLayout parent_layout;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            discription = itemView.findViewById(R.id.discription);
            imageView = itemView.findViewById(R.id.circle1);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }}}
