package com.teamtf.portalamikom.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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

    private SharedPreferences prefs;
    private ActionBar toolbar;
    private ViewPager vpMain;
    private BottomNavigationView bnvMain;
    private ViewPagerAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        prefs = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);

        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        vpMain = v.findViewById(R.id.vp_main);

        adapter = new ViewPagerAdapter(getChildFragmentManager());

        setUpViewPager();

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

                Fragment fragment = adapter.getFragment(position);
                if (fragment != null) {
                    fragment.onResume();
                }

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
        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        AuthFragment auth = new AuthFragment();
        mainActivity.replaceFragment(auth,"Auth View Fragment");

        toolbar.setTitle(R.string.login);
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setDisplayShowHomeEnabled(true);
    }

    public void setUpViewPager(){

        adapter.addFragment(EventsFragment.newInstance(), "Event");
        adapter.addFragment(NewsFragment.newInstance(), "Berita");
        adapter.addFragment(AccountFragment.newInstance(), "Akun");

        vpMain.setOffscreenPageLimit(1);
        vpMain.setAdapter(adapter);
    }

}
