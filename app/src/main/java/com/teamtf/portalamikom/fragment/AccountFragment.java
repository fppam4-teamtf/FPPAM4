package com.teamtf.portalamikom.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamtf.portalamikom.AdminPanelActivity;
import com.teamtf.portalamikom.MainActivity;
import com.teamtf.portalamikom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences prefs;
    private MainActivity main;
    private ActionBar actionBar;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_account, container, false);

        prefs = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);

        main = (MainActivity) getActivity();
        assert main != null;
        actionBar = main.getSupportActionBar();

        TextView tvUserId = v.findViewById(R.id.tv_userid);
        TextView tvUserName = v.findViewById(R.id.tv_nama);
        TextView tvProdi = v.findViewById(R.id.tv_prodi);

        if (!prefs.getAll().isEmpty()) {
            tvUserId.setText(prefs.getString("userid", "value"));
            tvUserName.setText(prefs.getString("name", "value"));
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(prefs.getString("privilages","").equals("admin")){
            inflater.inflate(R.menu.account_menu_admin, menu);
        } else {
            inflater.inflate(R.menu.account_menu, menu);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_admin_panel:
                Intent i = new Intent(main, AdminPanelActivity.class);
                startActivity(i);
                getActivity().finish();
                break;
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle(getString(R.string.logout));
                builder.setMessage(getString(R.string.quest_sure));
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = prefs.edit();

                        editor.clear();
                        editor.apply();

                        main.replaceFragment(AuthFragment.newInstance(), getString(R.string.tag_auth_fragment));

                        actionBar.setTitle(R.string.login);
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        actionBar.setDisplayShowHomeEnabled(true);
                    }
                });
                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            default:
                break;
        }
        return true;
    }
}
