package com.teamtf.portalamikom.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.teamtf.portalamikom.AdminPanelActivity;
import com.teamtf.portalamikom.R;
import com.teamtf.portalamikom.adapter.NewsListAdapter;
import com.teamtf.portalamikom.handler.DatabaseHandler;
import com.teamtf.portalamikom.model.NewsList;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private DatabaseHandler dbHandler;
    private RecyclerView rvNewsList;
    private AlertDialog dialog;
    private NewsListAdapter adapter;
    private ArrayList<NewsList> newsData;
    private AdminPanelActivity adminPanel;
    private AddNewsFragment addNews;
    private Bundle bundle;

    public NewsListFragment() {
        // Required empty public constructor
    }

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);

        dbHandler = new DatabaseHandler(getActivity());

        adminPanel = (AdminPanelActivity) getActivity();
        addNews = AddNewsFragment.newInstance();
        bundle = new Bundle();

        adminPanel.setUpToolbar(getString(R.string.news_content));

        rvNewsList = v.findViewById(R.id.rv_content_list);
        rvNewsList.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayAdapter<CharSequence> spinnerArrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.news_category, R.layout.support_simple_spinner_dropdown_item);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        Spinner categorySpinner = v.findViewById(R.id.sp_category);
        categorySpinner.setAdapter(spinnerArrayAdapter);
        categorySpinner.setOnItemSelectedListener(this);

        if (getArguments() != null) {
            categorySpinner.setSelection(getArguments().getInt("position"));
        }

        CardView cvAdd = v.findViewById(R.id.cv_add);
        cvAdd.setOnClickListener(this);

        dialog = new AlertDialog.Builder(getActivity()).create();

        newsData = new ArrayList<NewsList>();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv_add:
                @SuppressLint("InflateParams") View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_content, null);
                dialog.setView(view);
                dialog.setTitle(getString(R.string.add));
                dialog.show();
                CardView cvAddNews = dialog.findViewById(R.id.cv_add_news_dialog);
                cvAddNews.setOnClickListener(this);
                CardView cvAddEvent = dialog.findViewById(R.id.cv_add_event_dialog);
                cvAddEvent.setOnClickListener(this);
                break;
            case R.id.cv_add_news_dialog:
                dialog.dismiss();
                bundle.putString("category", getString(R.string.news));
                bundle.putString("action", getString(R.string.add));
                addNews.setArguments(bundle);
                adminPanel.replaceFragment(addNews,  getString(R.string.tag_add_news_fragment), getString(R.string.tag_news_list_fragment));
                break;
            case R.id.cv_add_event_dialog:
                dialog.dismiss();
                bundle.putString("category", getString(R.string.event));
                bundle.putString("action", getString(R.string.add));
                addNews.setArguments(bundle);
                adminPanel.replaceFragment(addNews, getString(R.string.tag_add_news_fragment),getString(R.string.tag_news_list_fragment));
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String category = parent.getItemAtPosition(position).toString();
        initializedData(category);
        adapter.notifyDataSetChanged();
        rvNewsList.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initializedData(String category) {
        newsData.clear();
        newsData = dbHandler.getNewsListData(category);
        adapter = new NewsListAdapter(newsData,getActivity());
    }
}
