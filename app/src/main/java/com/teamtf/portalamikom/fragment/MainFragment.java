package com.teamtf.portalamikom.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

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
public class MainFragment extends Fragment {

    SharedPreferences prefs;

    private MainActivity context;
    private ActionBar toolbar;
    private ViewPager vpMain;
    private BottomNavigationView bnvMain;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        prefs = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE);
        context = (MainActivity) getActivity();
        toolbar = context.getSupportActionBar();

        vpMain = v.findViewById(R.id.vp_main);
        vpMain.setOffscreenPageLimit(3);
        setUpViewPager(vpMain);

        bnvMain = v.findViewById(R.id.bnv_main);
        bnvMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.mi_events:
                        vpMain.setCurrentItem(0);
                        return true;
                    case R.id.mi_account:
                        if(!prefs.getBoolean("isLogin", false)){
                            showAuth();
                        } else {
                            vpMain.setCurrentItem(2);
                        }
                        return true;
                    default:
                        vpMain.setCurrentItem(1);
                        return true;
                }
            }
        });

        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                bnvMain.getMenu().getItem(position).setChecked(false);

                if(position == 2){
                    if(!prefs.getBoolean("isLogin", false)){
                        showAuth();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bnvMain.setSelectedItemId(R.id.mi_news);

        return v;
    }


    private void showAuth(){
        context.setCurrentItem(1);

        toolbar.setTitle(R.string.login);
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setDisplayShowHomeEnabled(true);
    }

    private void setUpViewPager(ViewPager vp){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        EventsFragment event = new EventsFragment();
        NewsFragment berita = new NewsFragment();
        AccountFragment akun = new AccountFragment();

        adapter.addFragment(event , "Event");
        adapter.addFragment(berita, "Berita");
        adapter.addFragment(akun, "Akun");

        vp.setAdapter(adapter);
    }

}
