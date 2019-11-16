package com.example.yoursretront.Tab_view_pager.resources;
import com.example.yoursretront.R;
import com.example.yoursretront.Tab_view_pager.models.Hat;

import java.math.BigDecimal;

public class Hats {
    public static Hat[] getHats(){
        return SNAPBACKS;
    }

    public static final Hat SNAPBACK_BLACK = new Hat("Black Snapback", R.mipmap.offers,
            new BigDecimal(20.99), 9377376);

    public static final Hat SNAPBACK_CAMO = new Hat("Camo Snapback", R.mipmap.fastfood,
            new BigDecimal(20.99), 9377377);

    public static final Hat SNAPBACK_GREY = new Hat("Grey Snapback", R.mipmap.tea,
            new BigDecimal(20.99), 9377378);

    public static final Hat SNAPBACK_NAVY = new Hat("Navy Snapback",R.mipmap.spoffer,
            new BigDecimal(20.99), 9377379);

    public static final Hat SNAPBACK_RED = new Hat("Red Snapback", R.mipmap.drink,
            new BigDecimal(20.99), 9377380);

    public static final Hat SNAPBACK_TEAL = new Hat("Teal Snapback",R.mipmap.cofee,
            new BigDecimal(20.99), 9377381);

    public static final Hat[] SNAPBACKS = {SNAPBACK_NAVY, SNAPBACK_BLACK, SNAPBACK_CAMO, SNAPBACK_GREY, SNAPBACK_RED, SNAPBACK_TEAL};







}