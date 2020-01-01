package com.teamtf.portalamikom.fragment;

import android.animation.Animator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teamtf.portalamikom.MainActivity;
import com.teamtf.portalamikom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment{

    private SharedPreferences prefs;
    private RelativeLayout relativeLayout;
    private Context context;

    private MainActivity main;

    private TextView tvUserId, tvUserName, tvGender, tvAddress;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(){
        return new AccountFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_account, container, false);

        prefs = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);

        relativeLayout = v.findViewById(R.id.rl_fmt_account);

        main = (MainActivity) getActivity();

        tvUserId = v.findViewById(R.id.tv_userid);
        tvUserName = v.findViewById(R.id.tv_nama);
        tvGender = v.findViewById(R.id.tv_gender);
        tvAddress = v.findViewById(R.id.tv_address);

        if (!prefs.getAll().isEmpty()){
            tvUserId.setText(prefs.getString("userid","value"));
            tvUserName.setText(prefs.getString("name","value"));
            tvGender.setText(prefs.getString("gender","value"));
            tvAddress.setText(prefs.getString("address","value"));
        }

        Button btnLogout = v.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();

                editor.clear();
                editor.apply();

                main.reloadFragmnet();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

}
