package com.example.e_petrol;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SliderFragmentCollectionAdapter extends FragmentStatePagerAdapter {
    private FrameLayout frameLayout;
    public int[] slide_images = {
            R.drawable.googlemap,
            R.drawable.notebook,
            R.drawable.location
    };

    public SliderFragmentCollectionAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        SliderFragment sliderFragment = new SliderFragment();
        Bundle bundle = new Bundle();

        position = position +1;
        if (position == 1){
            bundle.putString("mes","PETROL PRICE");
            bundle.putInt("image", R.drawable.googlemap);
            bundle.putInt("button", position);
        }
        else if (position == 2){
            bundle.putString("mes","HISTORY");
            bundle.putInt("button", position);
            bundle.putInt("image",R.drawable.notebook);
        }
        else if(position ==3){
            bundle.putString("mes","FIND A ROUTE");
            bundle.putInt("button", position);
            bundle.putInt("image",R.drawable.location);
        }



        sliderFragment.setArguments(bundle);


        return sliderFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
