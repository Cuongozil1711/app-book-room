package com.example.finalandroid.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.finalandroid.fragment.FragmentBook;
import com.example.finalandroid.fragment.FragmentHistory;
import com.example.finalandroid.fragment.FragmentHome;
import com.example.finalandroid.fragment.FragmentProfile;

public class ViewPaperAdapter extends FragmentPagerAdapter {

    public ViewPaperAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentHome();
            case 1: return new FragmentBook();
            case 2: return new FragmentHistory();
            case 3: return new FragmentProfile();
            default: return new FragmentHome();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
