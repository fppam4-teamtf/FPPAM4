package com.teamtf.portalamikom;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamtf.portalamikom.fragment.AddNewsFragment;
import com.teamtf.portalamikom.handler.DatabaseHandler;
import com.teamtf.portalamikom.model.News;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class PreviewActivity extends AppCompatActivity implements View.OnClickListener {

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
    private ImageView ivPreview;

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
        addNews = AddNewsFragment.newInstance();
        bundle = new Bundle();

        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit);
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fab_delete);

        tvTitle = findViewById(R.id.tv_news_title);
        tvDate = findViewById(R.id.tv_news_date);
        tvPublisher = findViewById(R.id.tv_news_publisher);
        tvContent = findViewById(R.id.tv_news_content);
        ivPreview = findViewById(R.id.iv_img_preview);

        News news = dbHandler.getNewsData(getIntent().getIntExtra("id", 0));

        id = news.getId();
        category = news.getCategory();
        tvTitle.setText(news.getTitle());
        tvPublisher.setText("Publisher : " + news.getPublisher());
        tvDate.setText(news.getDate());
        tvContent.setText(news.getContent());
        Bitmap bitmap = news.getImgResource();
        ivPreview.setImageBitmap(bitmap);

        if (prefs.getString("privilages", "").equals("admin")) {
            fabEdit.setOnClickListener(this);
            fabEdit.show();
            fabDelete.setOnClickListener(this);
            fabDelete.show();
        } else {
            fabEdit.setEnabled(false);
            fabEdit.hide();
            fabDelete.setEnabled(false);
            fabDelete.hide();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_edit:
                addNews = AddNewsFragment.newInstance();
                bundle.putString("category", category);
                bundle.putString("action", getString(R.string.edit));
                bundle.putInt("id", id);
                bundle.putString("title", tvTitle.getText().toString());
                bundle.putString("content", tvContent.getText().toString());
                Bitmap bitmap = ((BitmapDrawable) ivPreview.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] image = stream.toByteArray();
                bundle.putByteArray("image", image);
                addNews.setArguments(bundle);
                Intent i = new Intent(PreviewActivity.this, AdminPanelActivity.class);
                i.putExtra("position", 2);
                i.putExtras(bundle);
                startActivity(i);
                finish();
                break;
            case R.id.fab_delete:
                AlertDialog.Builder builder = new  AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.delete)+" "+category);
                builder.setMessage("Data yang telah di hapus tidak dapat dikembalikan lagi." +
                        "\nAnda yakin untuk menghapus ?");
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("FAB_DELETE", "onClick: delete fab Yes active");
                    }
                });
                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
