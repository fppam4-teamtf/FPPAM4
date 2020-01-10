package com.teamtf.portalamikom.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teamtf.portalamikom.MainActivity;
import com.teamtf.portalamikom.R;
import com.teamtf.portalamikom.adapter.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private SharedPreferences prefs;
    private MainActivity main;
    private ViewPager vpMain;
    private BottomNavigationView bnvMain;
    private ViewPagerAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        prefs = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        main = (MainActivity) getActivity();

        vpMain = v.findViewById(R.id.vp_main);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        vpMain.setOnPageChangeListener(this);
        setUpViewPager();

        bnvMain = v.findViewById(R.id.bnv_main);
        bnvMain.setOnNavigationItemSelectedListener(this);
        if (getArguments() != null) {
            if (getArguments().getInt("position", 1) == 0) {
                bnvMain.setSelectedItemId(R.id.mi_events);
                main.setUpToolbar(getString(R.string.event));
            } else if (prefs.getBoolean("isLogin", false) && getArguments().getInt("position", 1) == 2) {
                bnvMain.setSelectedItemId(R.id.mi_account);
                main.setUpToolbar(getString(R.string.profile));
            } else {
                bnvMain.setSelectedItemId(R.id.mi_news);
                main.setUpToolbar(getString(R.string.home));
            }
        } else {
            bnvMain.setSelectedItemId(R.id.mi_news);
        }

        return v;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.mi_events:
                vpMain.setCurrentItem(0);
                Log.d("MENU ITEM", "onNavigationItemSelected: 0 " + bnvMain.getMenu());
                return true;
            case R.id.mi_account:
                if (!prefs.getBoolean("isLogin", false)) {
                    Log.d("MENU ITEM", "onNavigationItemSelected: 2 1 " + bnvMain.getMenu());
                    main.replaceFragment(AuthFragment.newInstance(), getString(R.string.tag_auth_fragment), getString(R.string.tag_main_fragment));
                    return false;
                } else {
                    vpMain.setCurrentItem(2);
                    Log.d("MENU ITEM", "onNavigationItemSelected: 2 2 " + bnvMain.getMenu());
                    return true;
                }
            default:
                vpMain.setCurrentItem(1);
                Log.d("MENU ITEM", "onNavigationItemSelected: 1 " + bnvMain.getMenu());
                return true;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("POSITION", "onPageSelected: " + position);
        bnvMain.getMenu().getItem(position).setChecked(false);
        Log.d("ITEM POSITION", "onPageSelected: " + bnvMain.getMenu().getItem(position));

        Fragment fragment = adapter.getFragment(position);
        if (fragment != null) {
            fragment.onResume();
        }

        if (position == 0) {
            main.setUpToolbar(getString(R.string.event));
        } else if (position == 1) {
            main.setUpToolbar(getString(R.string.home));
        } else if (position == 2) {
            main.setUpToolbar(getString(R.string.profile));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setUpViewPager() {

        if (!prefs.getBoolean("isLogin", false)) {
            adapter.addFragment(EventsFragment.newInstance(), getString(R.string.tag_event_fragment));
            adapter.addFragment(NewsFragment.newInstance(), getString(R.string.tag_news_fragment));
        } else {
            adapter.addFragment(EventsFragment.newInstance(), getString(R.string.tag_event_fragment));
            adapter.addFragment(NewsFragment.newInstance(), getString(R.string.tag_news_fragment));
            adapter.addFragment(AccountFragment.newInstance(), getString(R.string.tag_account_fragment));
        }

        vpMain.setOffscreenPageLimit(3);
        vpMain.setAdapter(adapter);
    }
}
