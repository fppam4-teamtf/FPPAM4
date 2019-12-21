package com.teamtf.portalamikom.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

    ViewPager vpMain;
    BottomNavigationView bnvMain;
    private boolean isLogin = false;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        vpMain = view.findViewById(R.id.vp_main);
        vpMain.setOffscreenPageLimit(3);
        setUpViewPager(vpMain);

        bnvMain = view.findViewById(R.id.bnv_main);
        bnvMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.mi_events:
                        vpMain.setCurrentItem(0);
                        return true;
                    case R.id.mi_account:
                        vpMain.setCurrentItem(2);
                        return true;
                    default:
                        vpMain.setCurrentItem(1);
                        return true;
                }
            }
        });

        return view;
    }
    private void setUpViewPager(ViewPager vp){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        EventsFragment event = new EventsFragment();
        NewsFragment berita = new NewsFragment();
        AccountFragment akun = new AccountFragment();
//        AuthFragment auth = new AuthFragment();

        adapter.addFragment(event , "Event");
        adapter.addFragment(berita, "Berita");
        adapter.addFragment(akun, "Akun");

        vp.setAdapter(adapter);
    }

}
