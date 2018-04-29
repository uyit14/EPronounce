package com.example.uytai.epronounce;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by uytai on 4/28/2018.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ProAFragment proAFragment = new ProAFragment();
                return proAFragment;
            case 1:
                ProBFragment proBFragment = new ProBFragment();
                return proBFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Pronounce A";
            case 1:
                return "Pronounce B";
            default:
                return null;
        }
    }
}
