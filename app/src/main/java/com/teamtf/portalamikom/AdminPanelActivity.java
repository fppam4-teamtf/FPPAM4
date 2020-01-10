package com.teamtf.portalamikom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceControl;
import android.widget.Toast;

import com.teamtf.portalamikom.fragment.AddNewsFragment;
import com.teamtf.portalamikom.fragment.AdminMenuFragment;
import com.teamtf.portalamikom.fragment.NewsListFragment;
import com.teamtf.portalamikom.handler.DatabaseHandler;

public class AdminPanelActivity extends AppCompatActivity{

    private DatabaseHandler dbHelper;
    private SharedPreferences prefs;
    private Toolbar toolbar;
    private FragmentManager manager;
    private AdminMenuFragment adminMenuFragment;
    private NewsListFragment newsListFragment;
    private AddNewsFragment addNewsFragment;

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

        adminMenuFragment = AdminMenuFragment.newInstance();
        newsListFragment = NewsListFragment.newInstance();
        addNewsFragment = AddNewsFragment.newInstance();

        if(getIntent().getExtras() != null){
            if (getIntent().getIntExtra("position",0) == 2){
                replaceFragment(adminMenuFragment, getString(R.string.tag_admin_menu_fragment));
                replaceFragment(newsListFragment, getString(R.string.tag_news_list_fragment),getString(R.string.tag_admin_menu_fragment));
                addNewsFragment.setArguments(getIntent().getExtras());
                replaceFragment(addNewsFragment, getString(R.string.tag_add_news_fragment), getString(R.string.tag_news_list_fragment));
            }
        } else {
            replaceFragment(adminMenuFragment,getString(R.string.tag_admin_menu_fragment));
        }
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
        if(manager.getBackStackEntryCount()>0){
            manager.popBackStack();
        } else {
            Intent i = new Intent(this,MainActivity.class);
            i.putExtra("position",2);
            startActivity(i);
        }

    }

    public void setUpToolbar(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.replaced,fragment,tag);
        transaction.commit();

    }

    public void replaceFragment(Fragment fragment, String tag, String backStackTag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.replaced,fragment,tag);
        transaction.addToBackStack(backStackTag);
        transaction.commit();

    }
}
