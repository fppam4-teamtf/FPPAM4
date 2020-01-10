package com.teamtf.portalamikom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.teamtf.portalamikom.fragment.AddNewsFragment;
import com.teamtf.portalamikom.fragment.AdminMenuFragment;
import com.teamtf.portalamikom.fragment.NewsListFragment;

public class AdminPanelActivity extends AppCompatActivity {

    private FragmentManager manager;

    public AdminPanelActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        manager = getSupportFragmentManager();

        AdminMenuFragment adminMenuFragment = AdminMenuFragment.newInstance();
        NewsListFragment newsListFragment = NewsListFragment.newInstance();
        AddNewsFragment addNewsFragment = AddNewsFragment.newInstance();

        if (getIntent().getExtras() != null) {
            if (getIntent().getIntExtra("position", 0) == 2) {
                replaceFragment(adminMenuFragment, getString(R.string.tag_admin_menu_fragment));
                replaceFragment(newsListFragment, getString(R.string.tag_news_list_fragment), getString(R.string.tag_admin_menu_fragment));
                addNewsFragment.setArguments(getIntent().getExtras());
                replaceFragment(addNewsFragment, getString(R.string.tag_add_news_fragment), getString(R.string.tag_news_list_fragment));
            }
        } else {
            replaceFragment(adminMenuFragment, getString(R.string.tag_admin_menu_fragment));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
        } else {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("position", 2);
            startActivity(i);
        }
    }

    public void setUpToolbar(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.replaced, fragment, tag);
        transaction.commit();
    }

    public void replaceFragment(Fragment fragment, String tag, String backStackTag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.replaced, fragment, tag);
        transaction.addToBackStack(backStackTag);
        transaction.commit();
    }
}
