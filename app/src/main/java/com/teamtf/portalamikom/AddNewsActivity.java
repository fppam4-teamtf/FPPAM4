package com.teamtf.portalamikom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamtf.portalamikom.handler.DatabaseHandler;

public class AddNewsActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHandler dbHandler;
    private MainActivity main;
    private SharedPreferences prefs;

    private Toolbar toolbar;

    private EditText etTitle, etImage, etContent;
    private String category;
    private Button btnAdd;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        dbHandler = new DatabaseHandler(this);
        main = new MainActivity();
        prefs = getSharedPreferences("login",Context.MODE_PRIVATE);

        Log.d("PREFERENCES", "onCreate: "+prefs.getAll());

        toolbar = findViewById(R.id.toolbar_add_news);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etTitle = findViewById(R.id.et_title);
        etImage = findViewById(R.id.img_src_news);
        etContent = findViewById(R.id.et_content);

        btnAdd  = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

        intent = getIntent();
        category = intent.getStringExtra("Category");

        getSupportActionBar().setTitle(getString(R.string.add)+" "+category);
        btnAdd.setText(getString(R.string.add)+" "+category);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Log.d("btn add", "onClick: button add clicked");
                addNews();
                break;
            default:
                break;
        }
    }

    public void addNews(){

        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        if (title.equals("")){
            Toast.makeText(this, "Anda belum memasukkan Judul Berita",Toast.LENGTH_SHORT).show();
        } else if (content.equals("")){
            Toast.makeText(this, "Anda belum memasukkan Judul Berita",Toast.LENGTH_SHORT).show();
        } else {
            Boolean addNews = dbHandler.addNews(category,title,content,"image", prefs.getString("userid",getString(R.string.value)));
            if (addNews.equals(true)){
                Log.d(getString(R.string.add_news), "addNews: News successfully added");
                Toast.makeText(this,category+" berhasil ditambahkan.",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this,NewsListActivity.class);
                intent.putExtra(getString(R.string.category),category);
                startActivity(intent);
                finish();
            } else {
                Log.d(getString(R.string.add_news), "addNews: Something error please try again");
                Toast.makeText(this,category+" gagal ditambahkan. Silahkan Coba lagi.",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
