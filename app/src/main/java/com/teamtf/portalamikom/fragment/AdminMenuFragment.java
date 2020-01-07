package com.teamtf.portalamikom.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.teamtf.portalamikom.AdminPanelActivity;
import com.teamtf.portalamikom.R;

public class AdminMenuFragment extends Fragment implements View.OnClickListener {

    private AdminPanelActivity adminPanel;
    private AddNewsFragment addNews;
    private NewsListFragment newsList;
    private Bundle bundle;

    public AdminMenuFragment() {
        // Required empty public constructor
    }

    public static AdminMenuFragment newInstance(){
        return new AdminMenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_menu, container, false);

        adminPanel = (AdminPanelActivity) getActivity();
        addNews = AddNewsFragment.newInstance();
        newsList = NewsListFragment.newInstance();

        CardView cvContentList = v.findViewById(R.id.cv_content_list);
        cvContentList.setOnClickListener(this);
        CardView cvAddNews = v.findViewById(R.id.cv_add_news);
        cvAddNews.setOnClickListener(this);
        CardView cvAddEvent = v.findViewById(R.id.cv_add_event);
        cvAddEvent.setOnClickListener(this);

        bundle = new Bundle();

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_content_list:
                bundle.putInt("position",0);
                newsList.setArguments(bundle);
                adminPanel.replaceFragment(newsList, getString(R.string.tag_news_list_fragment));
                adminPanel.setUpToolbar("Daftar Content");
                break;
            case R.id.cv_add_news:
                bundle.putString("category", getString(R.string.news));
                addNews.setArguments(bundle);
                adminPanel.replaceFragment(addNews,  getString(R.string.tag_add_news_fragment));
                adminPanel.setUpToolbar("Tambah Berita");
                break;
            case R.id.cv_add_event:
                bundle.putString("category", getString(R.string.event));
                addNews.setArguments(bundle);
                adminPanel.replaceFragment(addNews, getString(R.string.tag_add_news_fragment));
                adminPanel.setUpToolbar("Tambah Event");
                break;
            default:
                break;
        }
    }
}
