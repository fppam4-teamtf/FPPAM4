package com.teamtf.portalamikom.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Map<Integer, String> fragmentTags;
    private FragmentManager fragmentManager;

    private final List<Fragment> fmtList = new ArrayList<Fragment>();
    private final List<String> titleList = new ArrayList<String>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
        fragmentTags = new HashMap<Integer, String>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fmtList.get(position);
    }

    @Override
    public int getCount() {
        return fmtList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        if (object instanceof Fragment) {
            Fragment fmt = (Fragment) object;
            String tag = fmt.getTag();
            fragmentTags.put(position, tag);
        }
        return object;
    }

    public void addFragment(Fragment fragment, String title) {
        fmtList.add(fragment);
        titleList.add(title);
    }

    public Fragment getFragment(int position){
        Fragment fmt = null;
        String tag = fragmentTags.get(position);
        if (tag != null){
            fmt = fragmentManager.findFragmentByTag(tag);
        }
        return fmt;
    }
}
