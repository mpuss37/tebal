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
import android.widget.Toast;

import com.example.perpustakaan.Adapter.BukuAdapter;
import com.example.perpustakaan.Handler.BukuHandler;
import com.example.perpustakaan.Model.BukuModel;
import com.example.perpustakaan.R;

import java.util.ArrayList;

public class MenuUser extends AppCompatActivity {
    RecyclerView recyclerViewBuku;
    RadioGroup radioGroup;
    ImageView imageViewHome, imageViewAdd, imageViewprofile;
    RadioButton radioButtonJudul, radioButtonKategori;
    SearchView searchViewBuku;
    Bundle bundle;
    Long id_data;
    String username, password;
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
        password = bundle.getString("key_password");
        bukuModelArrayList = new ArrayList<>();
        bukuHandler = new BukuHandler(MenuUser.this);
        bukuAdapter = new BukuAdapter(bukuModelArrayList, this);
        recyclerViewBuku = findViewById(R.id.rvBuku);
        searchViewBuku = findViewById(R.id.searchViewBuku);
        radioGroup = findViewById(R.id.radioGroup);
        imageViewHome = findViewById(R.id.imageViewHome);
        imageViewAdd = findViewById(R.id.imageViewAdd);
        imageViewprofile = findViewById(R.id.imageViewProfile);
        radioButtonJudul = findViewById(R.id.radioButtonJudul);
        searchViewBuku.clearFocus();
        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuUser.this, AddBuku.class);
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
                for (BukuModel bukuModel : bukuModelArrayList) {
                    String judul = bukuModel.getJudul().toLowerCase();
                    if (judul.contains(newText)) {
                        arrayList.add(bukuModel);
                    }
                }
                bukuAdapter.setFilter(arrayList);
                return true;
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