package com.example.yoursretront;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Swapping extends Fragment {
    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Cards> array;
    private SwipeFlingAdapterView flingContainer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_swapping, container, false);
        flingContainer = (SwipeFlingAdapterView)view. findViewById(R.id.frame);

        array = new ArrayList<>();
        array.add(new Cards("https://www.citi.io/wp-content/uploads/2015/06/fast-food.jpg","Fast-Food Items"));
        array.add(new Cards("https://s0.yellowpages.com.au/87df4a53-ca4c-4a46-981d-eed309cfeaec/delhi-rocks-image.jpg", " Pav Bhaji."));
        array.add(new Cards("http://www.shirescookeryschool.com/wp-content/uploads/2009/08/vegetarianchristmas1.jpg","veg-Rool"));
        array.add(new Cards("https://foodandtravelling.files.wordpress.com/2015/01/shutterstock_91566017.jpg","South-Indian Dosa"));
        array.add(new Cards("http://corporatemonks.com/wp-content/uploads/2015/06/5851279-fastfood.jpg","Combow Dhamal"));
array.add(new Cards("https://images.mapsofindia.com/my-india/Chinese-Hakka-Noodles-665x640.jpg","Noodels"));
array.add(new Cards("http://s1.1zoom.net/big3/193/335607-svetik.jpg","Pizza Tomatoes Mushrooms"));
array.add(new Cards("https://i.pinimg.com/originals/6f/24/fe/6f24fe4c1a2986c0957ac69411db1793.jpg","Bar-B-Que"));
array.add(new Cards("http://wallpapercrafter.com/uploads/posts/11054-___restaurant-fast-food-potato-and-seasoning-hd.jpg","potato-and-seasoning-"));
array.add(new Cards("http://www.gharanarestaurant.com/wp-content/uploads/2016/09/Indian-Golgappa.jpg","Indian-Golgappa"));
array.add(new Cards("http://thesamosahouse.com/wp-content/uploads/2015/08/butter_samosa.jpg","Samosa"));
array.add(new Cards("http://media2.sailusfood.com/wp-content/uploads/2016/03/recipe-of-momos.jpg","Momos"));
array.add(new Cards("http://1.bp.blogspot.com/-wPknFiiI_hs/VdyWah6u4JI/AAAAAAAAQxY/H1OGYS6teYg/s1600/bon-appetite-smiley.png","Thank  You"));

        myAppAdapter = new MyAppAdapter(array, getContext());
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                array.remove(0);
                myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
            }

            @Override
            public void onRightCardExit(Object dataObject) {

                array.remove(0);
                myAppAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                myAppAdapter.notifyDataSetChanged();
            }
        });
  return view;  }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;


    }

    public class MyAppAdapter extends BaseAdapter {


        public List<Cards> parkingList;
        public Context context;

        private MyAppAdapter(List<Cards> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.swappingitem, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImagett);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");

            Glide.with(getActivity()).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return rowView;
        }
    }
}