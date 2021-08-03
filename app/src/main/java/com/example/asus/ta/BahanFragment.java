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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BahanFragment extends Fragment {

    TextView mBahan;

    View view;
    public BahanFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_bahan_fragment,container, false);
        mBahan = view.findViewById(R.id.bahan);
        Intent i = getActivity().getIntent();
        String bahan = i.getExtras().getString("bahan_resep");

        mBahan.setText(bahan);
        return view;
    }
}
