package com.teamtf.portalamikom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.appbar.AppBarLayout;
import com.teamtf.portalamikom.adapter.ViewPagerAdapter;
import com.teamtf.portalamikom.custom.CustomViewPager;
import com.teamtf.portalamikom.fragment.AuthFragment;
import com.teamtf.portalamikom.fragment.MainFragment;
import com.teamtf.portalamikom.handler.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler dbHelper;
    private AppBarLayout appBar;
    private Toolbar toolbar;
    private CustomViewPager vpBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHandler(this);

        setUpAdmin();

        appBar = findViewById(R.id.appbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            appBar.setPadding(0,getStatusBarHeight(),0,0);
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backTo();
            }
        });

        vpBase = findViewById(R.id.vp_base);
        setUpViewPager(vpBase);

        vpBase.setPagingEnabled(false);

    }

    public void setCurrentItem(int i){
        vpBase.setCurrentItem(i);
    }

    private void setUpViewPager(ViewPager vp){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        MainFragment main = new MainFragment();
        AuthFragment auth = new AuthFragment();

        adapter.addFragment(main , "Main");
        adapter.addFragment(auth, "Auth");

        vp.setAdapter(adapter);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        if (vpBase.getCurrentItem()==1){
            backTo();
        } else {

        }
    }

    private void backTo(){
        vpBase.setCurrentItem(0);
        toolbar.setTitle(R.string.app_name);


        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void setUpAdmin(){
        Boolean cekId = dbHelper.cekId("admin");
        if (cekId.equals(true)){
            Boolean addAdmin = dbHelper.addUser("admin","admin","admin","Administrator","None","Office");
            if (addAdmin.equals(true)){
                Log.d("SETUP_ADMIN","onCreate: Admin Successfully Registeres");
            } else {
                Log.d("SETUP_ADMIN","onCreate: Admin Registration Failed");
            }
        } else {
            Log.d("CHECK_ADMIN","onCreate: Admin Already Registered");
        }
    }

}
