package com.teamtf.portalamikom.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamtf.portalamikom.AdminPanelActivity;
import com.teamtf.portalamikom.R;
import com.teamtf.portalamikom.handler.DatabaseHandler;
import com.teamtf.portalamikom.model.News;

public class AddNewsFragment extends Fragment implements View.OnClickListener {

    private DatabaseHandler dbHandler;
    private SharedPreferences prefs;
    private EditText etTitle, etImage, etContent;
    private String category;
    private String action;
    private int id;
    private Button btnAdd;
    private AdminPanelActivity adminPanel;
    private NewsListFragment newsList;
    private Bundle bundle;

    public AddNewsFragment() {
        // Required empty public constructor
    }

    public static AddNewsFragment newInstance() {
        return new AddNewsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_news, container, false);

        dbHandler = new DatabaseHandler(getActivity());
        prefs = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        adminPanel = (AdminPanelActivity) getActivity();
        newsList = NewsListFragment.newInstance();

        bundle = new Bundle();

        adminPanel.setUpToolbar(getString(R.string.add_news));

        etTitle = v.findViewById(R.id.et_title);
        etImage = v.findViewById(R.id.img_src_news);
        etContent = v.findViewById(R.id.et_content);
        btnAdd = v.findViewById(R.id.btn_add);

        if (getArguments() != null) {
            category = getArguments().getString("category");
            action = getArguments().getString("action");
            if (getArguments().getString("action").equals(getString(R.string.edit))) {
                id = getArguments().getInt("id");
                etTitle.setText(getArguments().getString("title"));
                etContent.setText(getArguments().getString("content"));
            }
        }

        adminPanel.setUpToolbar(action + " " + category);

        btnAdd.setText(action + " " + category);
        btnAdd.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                addNews();
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    public void addNews() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        if (title.equals("")) {
            Toast.makeText(getActivity(), "Anda belum memasukkan Judul " +
                    category, Toast.LENGTH_SHORT).show();
        } else if (content.equals("")) {
            Toast.makeText(getActivity(), "Anda belum memasukkan Isi " +
                    category, Toast.LENGTH_SHORT).show();
        } else {
            if (action.equals(getString(R.string.add))) {
                Log.d(action, "addNews 1: " + action);
                Boolean addNews = dbHandler.addNews(
                        category,
                        title,
                        content,
                        "image",
                        prefs.getString("userid", getString(R.string.value)));

                if (addNews.equals(true)) {
                    Toast.makeText(getActivity(), category + " berhasil ditambahkan.",
                            Toast.LENGTH_SHORT).show();
                    if (category.equals(getString(R.string.news))) {
                        bundle.putInt("position", 0);
                    } else if (category.equals(getString(R.string.event))) {
                        bundle.putInt("position", 1);
                    }

                    NewsListFragment newsListFragment = NewsListFragment.newInstance();
                    newsListFragment.setArguments(bundle);
                    adminPanel.replaceFragment(newsListFragment, getString(R.string.tag_news_list_fragment));
                    adminPanel.setUpToolbar(getString(R.string.content_list));
                } else {
                    Toast.makeText(getActivity(), category + " gagal ditambahkan. Silahkan Coba lagi.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            if (action.equals(getString(R.string.edit))) {
                Log.d(action, "addNews 2: " + action);

                Boolean editNews = dbHandler.editNews(id,title,content, "image");
                if (editNews.equals(true)) {
                    Toast.makeText(getActivity(), category + " berhasil diperbarui.",
                            Toast.LENGTH_SHORT).show();
                    if (category.equals(getString(R.string.news))) {
                        bundle.putInt("position", 0);
                    } else if (category.equals(getString(R.string.event))) {
                        bundle.putInt("position", 1);
                    }

                    NewsListFragment newsListFragment = NewsListFragment.newInstance();
                    newsListFragment.setArguments(bundle);
                    adminPanel.replaceFragment(newsListFragment, getString(R.string.tag_news_list_fragment));
                    adminPanel.setUpToolbar(getString(R.string.content_list));
                } else {
                    Toast.makeText(getActivity(), category + " gagal ditambahkan. Silahkan Coba lagi.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
