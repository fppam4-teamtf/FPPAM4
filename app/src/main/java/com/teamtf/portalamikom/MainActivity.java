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
import android.widget.Toast;

import com.teamtf.portalamikom.fragment.AuthFragment;
import com.teamtf.portalamikom.fragment.MainFragment;
import com.teamtf.portalamikom.handler.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler dbHelper;
    private SharedPreferences prefs;
    private FragmentManager manager;
    private MainFragment mainFragment;
    private AuthFragment authFragment;
    private Fragment currentFragment;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHandler(this);
        prefs = getSharedPreferences("login", Context.MODE_PRIVATE);

        setUpAdmin();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mainFragment = MainFragment.newInstance();
        authFragment = AuthFragment.newInstance();

        manager = getSupportFragmentManager();
        Bundle bundle = new Bundle();

        if (getIntent() != null){
//            manager.popBackStack();
            bundle.putBoolean("isLogin", prefs.getBoolean("isLogin",false));
            bundle.putInt("position",getIntent().getIntExtra("position", 1));
            mainFragment.setArguments(bundle);
        } else {
            bundle.putInt("position",1);
            mainFragment.setArguments(bundle);
        }

        manager.beginTransaction().add(R.id.replaced, mainFragment, getString(R.string.tag_main_fragment)).commit();
        currentFragment = mainFragment;

    }

    @Override
    public void onBackPressed() {
        if (manager.getBackStackEntryCount()>0) {
            Log.d("BACKSTACK", "onBackPressed: "+manager.getBackStackEntryCount());
            manager.popBackStack(getString(R.string.tag_main_fragment),1);
            Log.d("BACKSTACK", "onBackPressed: "+manager.getBackStackEntryCount());
        } else {
            Toast.makeText(this, "Super", Toast.LENGTH_SHORT).show();
            Log.d("BACKSTACK", "onBackPressed: "+manager.getBackStackEntryCount());
        }
    }

    public void reloadFragmnet() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(currentFragment);
        transaction.attach(currentFragment);
        transaction.commit();
    }

    public void replaceFragment(Fragment fragment, String tag) {
        manager.beginTransaction().replace(R.id.replaced, fragment, tag).commit();
    }

    public void replaceFragment(Fragment fragment, String tag, String backStackTag) {
        manager.beginTransaction().replace(R.id.replaced, fragment, tag)
                .addToBackStack(backStackTag)
                .commit();
    }

    public void setUpToolbar(String title) {
        getSupportActionBar().show();
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void setUpAdmin() {
        Boolean cekId = dbHelper.cekId("admin");
        if (cekId.equals(true)) {
            Boolean addAdmin = dbHelper.addUser("admin", "admin", "admin", "Administrator", "None", "Office");
            if (addAdmin.equals(true)) {
                Log.d("SETUP_ADMIN", "onCreate: Admin Successfully Registeres");
            } else {
                Log.d("SETUP_ADMIN", "onCreate: Admin Registration Failed");
            }
        } else {
            Log.d("CHECK_ADMIN", "onCreate: Admin Already Registered");
        }
    }
}
