package com.example.perpustakaan.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.perpustakaan.Adapter.LaporanAdapter;
import com.example.perpustakaan.Adapter.PeminjamanAdapter;
import com.example.perpustakaan.Handler.LaporanHandler;
import com.example.perpustakaan.Model.LaporanModel;
import com.example.perpustakaan.Model.PeminjamanModel;
import com.example.perpustakaan.R;

import java.util.ArrayList;

public class MenuLaporan extends AppCompatActivity {
    RecyclerView recyclerViewLaporan;
    LaporanHandler laporanHandler;
    LaporanAdapter laporanAdapter;
    ArrayList<LaporanModel> laporanModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_laporan);
        getSupportActionBar().show();
        getSupportActionBar().setTitle("Menu Laporan");
        recyclerViewLaporan = findViewById(R.id.rvLaporan);
        laporanModelArrayList = new ArrayList<>();
        laporanHandler = new LaporanHandler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MenuLaporan.this, RecyclerView.VERTICAL, false);
        recyclerViewLaporan.setLayoutManager(linearLayoutManager);
        laporanModelArrayList = laporanHandler.displayLaporan();
        laporanAdapter = new LaporanAdapter(laporanModelArrayList, this);
        recyclerViewLaporan.setAdapter(laporanAdapter);
        laporanAdapter.notifyDataSetChanged();
    }
}