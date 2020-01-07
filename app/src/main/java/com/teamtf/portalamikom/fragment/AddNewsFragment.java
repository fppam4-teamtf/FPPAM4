package com.teamtf.portalamikom.fragment;

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

public class AddNewsFragment extends Fragment implements View.OnClickListener {

    private DatabaseHandler dbHandler;
    private SharedPreferences prefs;
    private EditText etTitle, etImage, etContent;
    private String category;
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

        etTitle = v.findViewById(R.id.et_title);
        etImage = v.findViewById(R.id.img_src_news);
        etContent = v.findViewById(R.id.et_content);

        category = getArguments().getString("category");

        btnAdd  = v.findViewById(R.id.btn_add);
        btnAdd.setText(getText(R.string.add)+" "+category);
        btnAdd.setOnClickListener(this);

        adminPanel = (AdminPanelActivity) getActivity();
        newsList = NewsListFragment.newInstance();

        bundle = new Bundle();

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

    public void addNews(){

        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        if (title.equals("")){
            Toast.makeText(getActivity(), "Anda belum memasukkan Judul Berita",Toast.LENGTH_SHORT).show();
        } else if (content.equals("")){
            Toast.makeText(getActivity(), "Anda belum memasukkan Judul Berita",Toast.LENGTH_SHORT).show();
        } else {
            Boolean addNews = dbHandler.addNews(category,title,content,"image", prefs.getString("userid",getString(R.string.value)));
            if (addNews.equals(true)){
                Toast.makeText(getActivity(),category+" berhasil ditambahkan.",Toast.LENGTH_SHORT).show();
                bundle.putInt("position",1);
                newsList.setArguments(bundle);
                adminPanel.replaceFragment(newsList,getString(R.string.tag_news_list_fragment));
                adminPanel.setUpToolbar(getString(R.string.content_list));
            } else {
                Toast.makeText(getActivity(),category+" gagal ditambahkan. Silahkan Coba lagi.",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
