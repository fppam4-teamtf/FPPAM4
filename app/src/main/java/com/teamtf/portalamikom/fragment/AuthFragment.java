package com.teamtf.portalamikom.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamtf.portalamikom.MainActivity;
import com.teamtf.portalamikom.R;
import com.teamtf.portalamikom.handler.DatabaseHandler;
import com.teamtf.portalamikom.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends Fragment {

    private DatabaseHandler dbHandler;
    private SharedPreferences prefs;

    private MainActivity main;

    private EditText etUserId;
    private EditText etPassword;

    public AuthFragment() {
        // Required empty public constructor
    }

    public static AuthFragment newInstance(){
        return new AuthFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_auth, container, false);

        dbHandler = new DatabaseHandler(getContext());
        prefs = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE);

        main = (MainActivity) getActivity();

        etUserId = v.findViewById(R.id.et_userid);
        etPassword = v.findViewById(R.id.et_pass);
        Button btnLogin = v.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etUserId.getText().toString();
                String pass = etPassword.getText().toString();

                if (id.equals("") || pass.equals("")) {
                    Toast.makeText(getContext(), "Anda belum memasukkan User Id atau Password", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean auth = dbHandler.authUser(id, pass);
                    if (auth.equals(true)) {
                        Toast.makeText(getContext(), "Login Sukses", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = prefs.edit();

                        User user = dbHandler.getUserData(id, pass);

                        editor.putBoolean("isLogin", true);
                        editor.putString("userid", user.getUserid());
                        editor.putString("privilages", user.getPrivilages());
                        editor.putString("name", user.getName());
                        editor.putString("gender", user.getGender());
                        editor.putString("address", user.getAddress());

                        editor.apply();

                        Log.d("TRY_SHARED_PREFERENCES", "onClick: " + prefs.getAll());


                        assert main != null;
                        main.replaceFragment(MainFragment.newInstance(),getString(R.string.tag_main_fragment));
                        main.setUpToolbar();
                    } else {
                        Toast.makeText(getContext(), "User Id atau Password tidak dikenali, Silahkan coba lagi.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return v;
    }

}
