package com.example.perpustakaan.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.perpustakaan.Adapter.PeminjamanAdapter;
import com.example.perpustakaan.Handler.PeminjamanHandler;
import com.example.perpustakaan.Model.PeminjamanModel;
import com.example.perpustakaan.R;

import java.util.ArrayList;

public class MenuPeminjaman extends AppCompatActivity {
    RecyclerView recyclerViewPeminjaman;
    PeminjamanHandler peminjamanHandler;
    PeminjamanAdapter peminjamanAdapter;
    Bundle bundle;
    long id_data;
    ArrayList<PeminjamanModel> peminjamanModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_peminjaman);
        getSupportActionBar().show();
        getSupportActionBar().setTitle("Menu Peminjaman");
        bundle = this.getIntent().getExtras();
        id_data = bundle.getLong("key_id_user");
        peminjamanHandler = new PeminjamanHandler(this);
        peminjamanModelArrayList = new ArrayList<>();
        recyclerViewPeminjaman = findViewById(R.id.rvPeminjaman);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MenuPeminjaman.this, RecyclerView.VERTICAL, false);
        recyclerViewPeminjaman.setLayoutManager(linearLayoutManager);
        peminjamanModelArrayList = peminjamanHandler.displayPeminjaman(id_data);
        peminjamanAdapter = new PeminjamanAdapter(peminjamanModelArrayList, this, id_data);
        recyclerViewPeminjaman.setAdapter(peminjamanAdapter);
        peminjamanAdapter.notifyDataSetChanged();
    }
}