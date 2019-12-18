package com.teamtf.portalamikom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teamtf.portalamikom.adapter.ViewPagerAdapter;
import com.teamtf.portalamikom.fragment.AccountFragment;
import com.teamtf.portalamikom.fragment.EventsFragment;
import com.teamtf.portalamikom.fragment.NewsFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager vpMain;
    private BottomNavigationView bnvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vpMain = findViewById(R.id.vp_main);
        vpMain.setOffscreenPageLimit(3);
        setUpViewPager(vpMain);

        bnvMain = findViewById(R.id.bnv_main);
        bnvMain.setSelectedItemId(R.id.mi_news);
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
    }

    private void setUpViewPager(ViewPager vp){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        EventsFragment event = new EventsFragment();
        NewsFragment berita = new NewsFragment();
        AccountFragment akun = new AccountFragment();

        adapter.addFragment(event , "Event");
        adapter.addFragment(berita, "Berita");
        adapter.addFragment(akun, "Akun");

        vp.setAdapter(adapter);
    }
}
