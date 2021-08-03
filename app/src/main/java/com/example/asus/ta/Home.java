package com.example.asus.ta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = FirebaseDatabase.getInstance().getReference().child("/Resep");

        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerAdapter<Resep,ResepViewHolder> adapter = new FirebaseRecyclerAdapter<Resep, ResepViewHolder>(
                Resep.class,
                R.layout.card_view,
                ResepViewHolder.class,
                database

        ) {
            @Override
            protected void populateViewHolder(final ResepViewHolder viewHolder, final Resep model, int position) {
                viewHolder.setNama(model.getNama());
                viewHolder.setFoto(model.getFoto());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent detail = new Intent();
                        detail.setClass(Home.this, Detail.class);
                        detail.putExtra("nama_resep", model.getNama());
                        detail.putExtra("foto_resep", model.getFoto());
                        detail.putExtra("des_resep", model.getDes());
                        detail.putExtra("bahan_resep", model.getBahan());
                        detail.putExtra("proses_resep", model.getProses());

                        startActivity(detail);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }

    public static class ResepViewHolder extends RecyclerView.ViewHolder{
        TextView txt_nama,txt_des,txt_bahan,txt_proses;
        ImageView img_foto;
        public ResepViewHolder(View itemView) {
            super(itemView);
            txt_nama = itemView.findViewById(R.id.nama);
            img_foto = itemView.findViewById(R.id.foto);
            txt_des = itemView.findViewById(R.id.des);
            txt_bahan = itemView.findViewById(R.id.bahan);
            txt_proses = itemView.findViewById(R.id.proses);
        }

        public void setNama(String nama) {
            txt_nama.setText(nama);
        }


        public void setFoto(String foto) {
            Picasso.get()
                    .load(foto)
                    .fit()
                    .centerInside()
                    .into(img_foto);
        }
    }

}
