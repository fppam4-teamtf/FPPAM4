package com.teamtf.portalamikom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.teamtf.portalamikom.adapter.NewsListAdapter;
import com.teamtf.portalamikom.handler.DatabaseHandler;

public class NewsListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private DatabaseHandler dbHandler;
    private RecyclerView rvNewsList;
    private AlertDialog dialog;
    private NewsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        dbHandler = new DatabaseHandler(this);

        rvNewsList = findViewById(R.id.rv_content_list);
        rvNewsList.setLayoutManager(new LinearLayoutManager(this));

        ArrayAdapter<CharSequence> spinnerArrayAdapter = ArrayAdapter.createFromResource(this, R.array.news_category, R.layout.support_simple_spinner_dropdown_item);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        Spinner categorySpinner = findViewById(R.id.sp_category);
        categorySpinner.setAdapter(spinnerArrayAdapter);
        categorySpinner.setOnItemSelectedListener(this);

        Intent i = getIntent();
        if (i != null){
            if (i.getStringExtra(getString(R.string.category)) == getString(R.string.news)){
                categorySpinner.setSelection(0);
            } else  if (i.getStringExtra(getString(R.string.category)) == getString(R.string.event)){
                categorySpinner.setSelection(1);
            }
        }

        CardView cvAdd = findViewById(R.id.cv_add);
        cvAdd.setOnClickListener(this);
        dialog = new AlertDialog.Builder(this).create();

        rvNewsList.setAdapter(adapter);
        Log.d("NOTAG", "onCreateView: here data initialized");

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.cv_add:
                @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_content, null);
                dialog.setView(view);
                dialog.setTitle(getString(R.string.add));
                dialog.show();
                CardView cvAddNews = dialog.findViewById(R.id.cv_add_news_dialog);
                cvAddNews.setOnClickListener(this);
                CardView cvAddEvent = dialog.findViewById(R.id.cv_add_event_dialog);
                cvAddEvent.setOnClickListener(this);
                break;
            case R.id.cv_add_news_dialog:
                i = new Intent(this,AddNewsActivity.class);
                i.putExtra(getString(R.string.category),getString(R.string.news));
                startActivity(i);
                dialog.dismiss();
                break;
            case R.id.cv_add_event_dialog:
                i = new Intent(this,AddNewsActivity.class);
                i.putExtra(getString(R.string.category),getString(R.string.event));
                startActivity(i);
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String category = parent.getItemAtPosition(position).toString();
        initializedData(category);
        rvNewsList.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initializedData(String category) {
        adapter = new NewsListAdapter(dbHandler.getNewsListData(category), this);
        adapter.notifyDataSetChanged();
        Log.d("Initialized", "initializedData: Data Inisialized");
    }
}
