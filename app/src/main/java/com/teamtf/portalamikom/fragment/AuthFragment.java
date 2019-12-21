package com.teamtf.portalamikom.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teamtf.portalamikom.MainActivity;
import com.teamtf.portalamikom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends Fragment {

    private Button btnLogin;

    public AuthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_auth, container, false);

        btnLogin = v.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment mainFragment = new MainFragment();
                mainFragment.setLogin(true);

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setCurrentItem(0);
            }
        });

        return v;
    }

}
