package com.teamtf.portalamikom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.teamtf.portalamikom.fragment.AddNewsFragment;
import com.teamtf.portalamikom.handler.DatabaseHandler;
import com.teamtf.portalamikom.model.News;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewActivity extends AppCompatActivity {

    private DatabaseHandler dbHandler;
    private SharedPreferences prefs;
    private AdminPanelActivity adminPanel;
    private AddNewsFragment addNews;
    private Bundle bundle;

    private int id;
    private String category;
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvPublisher;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_preview);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dbHandler = new DatabaseHandler(this);
        prefs = getSharedPreferences("login", MODE_PRIVATE);
        adminPanel = new AdminPanelActivity();
        bundle = new Bundle();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        tvTitle = findViewById(R.id.tv_news_title);
        tvDate = findViewById(R.id.tv_news_date);
        tvPublisher = findViewById(R.id.tv_news_publisher);
        tvContent = findViewById(R.id.tv_news_content);

        News news = dbHandler.getNewsData(getIntent().getIntExtra("id",0));

        id = news.getId();
        category = news.getCategory();
        tvTitle.setText(news.getTitle());
        tvPublisher.setText("Publisher : "+news.getPublisher());
        tvDate.setText(news.getDate());
        tvContent.setText(news.getContent());

        if (!prefs.getBoolean("isLogin",false) && !prefs.getString("privilages","").equals("admin")){
            fab.setEnabled(false);
            fab.hide();
        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    addNews = AddNewsFragment.newInstance();
                    bundle.putString("category", category);
                    bundle.putString("action", getString(R.string.edit));
                    bundle.putInt("id",id);
                    bundle.putString("title", tvTitle.getText().toString());
                    bundle.putString("content", tvContent.getText().toString());
                    addNews.setArguments(bundle);
                    Intent i = new Intent(PreviewActivity.this,AdminPanelActivity.class);
                    i.putExtra("position",2);
                    i.putExtras(bundle);
                    startActivity(i);
                    finish();
                }
            });
            fab.show();
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
        super.onBackPressed();
    }
}
