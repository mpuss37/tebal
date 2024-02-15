package com.example.perpustakaan.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    RadioButton radioButtonJudul, radioButtonKategori;
    SearchView searchViewBuku;
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
        bukuAdapter = new BukuAdapter(bukuModelArrayList, this);
        recyclerViewBuku = findViewById(R.id.rvBuku);
        searchViewBuku = findViewById(R.id.searchViewBuku);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonJudul = findViewById(R.id.radioButtonJudul);
        buttonSave = findViewById(R.id.buttonSave);
        searchViewBuku.clearFocus();

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

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuUser.this, AddBuku.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        searchViewBuku.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String newText) {
        ArrayList<BukuModel> bukuModelArrayList1 = new ArrayList<>();
        bukuModelArrayList1.clear();
        for (BukuModel bukuModel : bukuModelArrayList) {
            if (bukuModel.getJudul().toLowerCase().contains(newText.toLowerCase())) {
                bukuModelArrayList1.add(bukuModel);
            }
        }

        if (bukuModelArrayList1.isEmpty()) {
            Toast.makeText(this, "data not found", Toast.LENGTH_SHORT).show();
        } else {
            bukuAdapter.setFilteredList(bukuModelArrayList1);
        }
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