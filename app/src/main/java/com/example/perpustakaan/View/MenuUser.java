package com.example.perpustakaan.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perpustakaan.Adapter.BukuAdapter;
import com.example.perpustakaan.Adapter.UlasanAdapter;
import com.example.perpustakaan.Handler.BukuHandler;
import com.example.perpustakaan.Model.BukuModel;
import com.example.perpustakaan.Model.UlasanModel;
import com.example.perpustakaan.R;

import java.util.ArrayList;

public class MenuUser extends AppCompatActivity {
    RecyclerView recyclerViewBuku;
    RadioGroup radioGroup;
    ImageView imageViewHome, imageViewAdd, imageViewprofile, imageViewBorrow;
    RadioButton radioButtonJudul, radioButtonKategori;
    SearchView searchViewBuku;
    Bundle bundle;
    Long id_data;
    Boolean benar = false;
    String username, password;
    ArrayList<BukuModel> bukuModelArrayList;
    ArrayList<UlasanModel> ulasanModelArrayList;
    BukuHandler bukuHandler;
    BukuAdapter bukuAdapter;
    UlasanAdapter ulasanAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Menu User");
        bundle = this.getIntent().getExtras();
        id_data = bundle.getLong("key_id_user");
        username = bundle.getString("key_username");
        password = bundle.getString("key_password");
        Toast.makeText(this, "welkam bek " + username, Toast.LENGTH_SHORT).show();
        bukuModelArrayList = new ArrayList<>();
        bukuHandler = new BukuHandler(MenuUser.this);
        bukuAdapter = new BukuAdapter(bukuModelArrayList, this, id_data, username);
        bukuModelArrayList.clear();
        bukuModelArrayList.addAll(bukuHandler.displayBuku(id_data));
        bukuAdapter.notifyDataSetChanged();
        recyclerViewBuku = findViewById(R.id.rvBuku);
        searchViewBuku = findViewById(R.id.searchViewBuku);
        radioGroup = findViewById(R.id.radioGroup);
        imageViewHome = findViewById(R.id.imageViewHome);
        imageViewAdd = findViewById(R.id.imageViewAdd);
        if (!username.equals("admin") || equals("petugas")) {
            imageViewAdd.setImageResource(R.drawable.ebook);
        }
        imageViewprofile = findViewById(R.id.imageViewProfile);
        radioButtonJudul = findViewById(R.id.radioButtonJudul);
        searchViewBuku.clearFocus();
        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMenu(username);
            }
        });

        imageViewAdd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (username.equals("admin") || username.equals("petugas")) {
                    intent = new Intent(MenuUser.this, MenuLaporan.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return false;
            }
        });

        imageViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuUser.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        imageViewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuUser.this, MenuProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("key_id_user", id_data);
                intent.putExtra("key_username", username);
                intent.putExtra("key_password", password);
                startActivity(intent);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonKategori) {
                    benar = true;
                    Toast.makeText(MenuUser.this, "filter by kategori", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuUser.this, "filter by judul", Toast.LENGTH_SHORT).show();
                }
            }
        });

        searchViewBuku.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<BukuModel> arrayList = new ArrayList<>();
                if (newText.isEmpty()) {
                    arrayList.addAll(bukuModelArrayList);
                } else {
                    for (BukuModel bukuModel : bukuModelArrayList) {
                        String judul = bukuModel.getJudul().toLowerCase();
                        String kategori = bukuModel.getKategori();
                        if (benar == true) {
                            if (kategori != null && kategori.toLowerCase().contains(newText)) {
                                arrayList.add(bukuModel);
                            }
                        } else {
                            if (judul.contains(newText)) {
                                arrayList.add(bukuModel);
                            }
                        }
                    }
                }
                bukuAdapter.setFilter(arrayList);
                return true;
            }
        });
    }

    void addMenu(String username) {
        if (username.equals("admin") || username.equals("petugas")) {
            intent = new Intent(MenuUser.this, AddBuku.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            intent = new Intent(MenuUser.this, MenuPeminjaman.class);
            intent.putExtra("key_id_user", id_data);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MenuUser.this, RecyclerView.VERTICAL, false);
        recyclerViewBuku.setLayoutManager(linearLayoutManager);
        bukuModelArrayList = bukuHandler.displayBuku(id_data);
        bukuAdapter = new BukuAdapter(bukuModelArrayList, this, id_data, username);
        recyclerViewBuku.setAdapter(bukuAdapter);
        bukuAdapter.notifyDataSetChanged();
    }
}