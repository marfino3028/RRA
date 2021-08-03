package com.example.asus.ta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProsesFragment extends Fragment {

    TextView mProses;

    View view;
    public ProsesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_proses_fragment,container, false);
        mProses = view.findViewById(R.id.proses);
        Intent i = getActivity().getIntent();
        String proses = i.getExtras().getString("proses_resep");

        mProses.setText(proses);
        return view;
    }
}
