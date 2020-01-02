package com.teamtf.portalamikom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.teamtf.portalamikom.fragment.MainFragment;
import com.teamtf.portalamikom.handler.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler dbHelper;

    private SharedPreferences prefs;
    private Toolbar toolbar;

    private FragmentManager manager;
    private Fragment currentFragment;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHandler(this);
        prefs = getPreferences(Context.MODE_PRIVATE);

        setUpAdmin();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpToolbar();
                onBackPressed();
            }
        });

        manager = getSupportFragmentManager();
        setUpFragment();

    }

    private void setUpFragment(){
        manager.beginTransaction().add(R.id.replaced,MainFragment.newInstance(),getString(R.string.tag_main_fragment)).commit();
        currentFragment = MainFragment.newInstance();
    }

    public void reloadFragmnet(){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(currentFragment);
        transaction.attach(currentFragment);
        transaction.commit();
    }

    public void  replaceFragment(Fragment fragment,String tag){
        manager.beginTransaction().replace(R.id.replaced, fragment,tag).commit();
        currentFragment = fragment;
    }


//    public int getStatusBarHeight() {
//        int result = 0;
//        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }

    @Override
    public void onBackPressed() {
        if(currentFragment.equals(MainFragment.newInstance())){
            super.onBackPressed();
        } else {
            replaceFragment(MainFragment.newInstance(),getString(R.string.tag_main_fragment));
            setUpToolbar();
        }
    }

    public void setUpToolbar(){
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
