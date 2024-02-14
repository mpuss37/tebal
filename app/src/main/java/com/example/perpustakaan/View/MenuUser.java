package com.example.perpustakaan.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.perpustakaan.Adapter.BukuAdapter;
import com.example.perpustakaan.Handler.BukuHandler;
import com.example.perpustakaan.Model.BukuModel;
import com.example.perpustakaan.R;

import java.util.ArrayList;

public class MenuUser extends AppCompatActivity {
    RecyclerView recyclerViewBuku, recyclerViewKategori;
    Button buttonSave;
    Bundle bundle;
    Long id_data;
    String username;
    ArrayList<BukuModel> bukuModelArrayList;
    BukuHandler bukuHandler;
    BukuAdapter bukuAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = this.getIntent().getExtras();
        id_data = bundle.getLong("key_id_user");
        username = bundle.getString("key_username");
        bukuModelArrayList = new ArrayList<>();
        bukuHandler = new BukuHandler(MenuUser.this);
        recyclerViewBuku = findViewById(R.id.rvBuku);
        recyclerViewKategori = findViewById(R.id.rvKategori);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuUser.this, AddBuku.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MenuUser.this, RecyclerView.VERTICAL, false);
        recyclerViewBuku.setLayoutManager(linearLayoutManager);
        bukuModelArrayList = bukuHandler.displayBuku(id_data);
        bukuAdapter = new BukuAdapter(bukuModelArrayList, this);
        recyclerViewBuku.setAdapter(bukuAdapter);
        bukuAdapter.notifyDataSetChanged();
    }
}