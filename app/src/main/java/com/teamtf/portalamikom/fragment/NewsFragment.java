package com.teamtf.portalamikom.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamtf.portalamikom.R;
import com.teamtf.portalamikom.adapter.NewsListHomeAdapter;
import com.teamtf.portalamikom.handler.DatabaseHandler;
import com.teamtf.portalamikom.model.NewsListHome;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private ArrayList<NewsListHome> newsListHomes;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(){
        return new NewsFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_news, container, false);

        DatabaseHandler dbHandler = new DatabaseHandler(getActivity());
        newsListHomes = dbHandler.getNewsListHomeData(getString(R.string.news));

        RecyclerView rvHomeNewsList = v.findViewById(R.id.rv_home_news_list);
        rvHomeNewsList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,true));

        NewsListHomeAdapter newsAdapter = new NewsListHomeAdapter(newsListHomes,getActivity());
        rvHomeNewsList.setAdapter(newsAdapter);

        return v;


    }

}
