package com.teamtf.portalamikom.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamtf.portalamikom.AdminPanelActivity;
import com.teamtf.portalamikom.R;

public class AdminMenuFragment extends Fragment implements View.OnClickListener {

    private AdminPanelActivity adminPanel;
    private AddNewsFragment addNewsFragment;
    private NewsListFragment newsListFragment;
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
        addNewsFragment = AddNewsFragment.newInstance();
        newsListFragment = NewsListFragment.newInstance();

        adminPanel.setUpToolbar(getString(R.string.admin_panel));

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
                newsListFragment.setArguments(bundle);
                adminPanel.replaceFragment(newsListFragment, getString(R.string.tag_news_list_fragment), getString(R.string.tag_admin_menu_fragment));
                break;
            case R.id.cv_add_news:
                bundle.putString("category", getString(R.string.news));
                bundle.putString("action", getString(R.string.add));
                addNewsFragment.setArguments(bundle);
                adminPanel.replaceFragment(addNewsFragment,  getString(R.string.tag_add_news_fragment), getString(R.string.tag_admin_menu_fragment));
                break;
            case R.id.cv_add_event:
                bundle.putString("category", getString(R.string.event));
                bundle.putString("action", getString(R.string.add));
                addNewsFragment.setArguments(bundle);
                adminPanel.replaceFragment(addNewsFragment, getString(R.string.tag_add_news_fragment), getString(R.string.tag_admin_menu_fragment));
                break;
            default:
                break;
        }
    }
}
