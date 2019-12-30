package com.teamtf.portalamikom.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamtf.portalamikom.MainActivity;
import com.teamtf.portalamikom.R;
import com.teamtf.portalamikom.handler.DatabaseHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends Fragment {

    DatabaseHandler dbHandler;

    private EditText etUserId;
    private EditText etPassword;
    private Button btnLogin;

    public AuthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_auth, container, false);

        dbHandler = new DatabaseHandler(getContext());

        etUserId = v.findViewById(R.id.et_userid);
        etPassword = v.findViewById(R.id.et_pass);
        btnLogin = v.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = etUserId.getText().toString();
                String pass = etPassword.getText().toString();

                if (id.equals("")||pass.equals("")){
                    Toast.makeText(getContext(),"Anda belum memasukkan User Id atau Password", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean auth = dbHandler.authUser(id,pass);
                    if (auth.equals(true)){
                        Toast.makeText(getContext(),"Login Sukses",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),"User Id atau Password tidak dikenali, Silahkan coba lagi.",Toast.LENGTH_SHORT).show();
                    }
                }

//                MainFragment mainFragment = new MainFragment();
//                mainFragment.setLogin(true);
//
//                MainActivity mainActivity = (MainActivity) getActivity();
//                mainActivity.setCurrentItem(0);
            }
        });

        return v;
    }

}
