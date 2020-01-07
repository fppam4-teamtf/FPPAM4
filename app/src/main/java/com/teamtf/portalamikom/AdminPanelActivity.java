package com.teamtf.portalamikom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.teamtf.portalamikom.fragment.AddNewsFragment;
import com.teamtf.portalamikom.fragment.AdminMenuFragment;
import com.teamtf.portalamikom.handler.DatabaseHandler;

public class AdminPanelActivity extends AppCompatActivity{

    private DatabaseHandler dbHelper;
    private SharedPreferences prefs;
    private Toolbar toolbar;
    private FragmentManager manager;

    public AdminPanelActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        dbHelper = new DatabaseHandler(this);
        prefs = getPreferences(Context.MODE_PRIVATE);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.replaced, AdminMenuFragment.newInstance(), getString(R.string.tag_admin_menu_fragment))
                .addToBackStack("ADMIN_MENU")
                .commit();

        manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "onOptionItemSelected home Button", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        if (manager.getBackStackEntryCount() > 1) {
            manager.popBackStack();
            Log.d("POP", "onBackPressed: popreturn");

        } else {
            Log.d("INTENT", "onBackPressed: INTENT");

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            super.onBackPressed();
//            replaceFragment(AdminMenuFragment.newInstance(), "ADMIN_MENU");
        }

        if (manager.getBackStackEntryCount() == 1){
            setUpToolbar(getString(R.string.admin_panel));
        }
    }

    public void setUpToolbar(String title) {
        toolbar.setTitle(title);
    }

    public void replaceFragment(Fragment fragment, String tag) {
        manager.beginTransaction().replace(R.id.replaced, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    public void sendToAddNews(Bundle bundle){
        AddNewsFragment addNews = AddNewsFragment.newInstance();
        addNews.setArguments(bundle);
    }
}
