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

public class GambarFragment extends Fragment {

    TextView mNama,mDes;
    ImageView mFoto;

    View view;
    public GambarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_gambar_fragment,container, false);
        mNama = view.findViewById(R.id.nama);
        mDes = view.findViewById(R.id.des);
        mFoto = view.findViewById(R.id.foto);
        Intent i = getActivity().getIntent();
        String nama = i.getExtras().getString("nama_resep");
        String foto = i.getExtras().getString("foto_resep");
        String des = i.getExtras().getString("des_resep");

        mNama.setText(nama);
        mDes.setText(des);
        Picasso.get().load(foto).into(mFoto);
        return view;
    }
}
