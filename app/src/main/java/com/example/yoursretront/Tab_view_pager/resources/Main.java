package com.example.yoursretront.Tab_view_pager.resources;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.yoursretront.R;
import com.example.yoursretront.Tab_view_pager.MyPagerAdapter;
import com.example.yoursretront.Tab_view_pager.ViewPagerItemFragment;
import com.example.yoursretront.Tab_view_pager.models.Hat;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    private ViewPager mMyViewPager;
    private TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainswapp);
        mTabLayout = findViewById(R.id.tab_layouttt);
        mMyViewPager = findViewById(R.id.view_pagerrr);

        ArrayList<Fragment> fragments = new ArrayList<>();
        Hat[] hats = Hats.getHats();
        for(Hat hat: hats){
            ViewPagerItemFragment fragment = ViewPagerItemFragment.getInstance(hat);
            fragments.add(fragment);
        }
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        mMyViewPager.setAdapter(pagerAdapter);
           mTabLayout.setupWithViewPager(mMyViewPager, true);
}
}