package com.example.perpustakaan.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.perpustakaan.Handler.BukuHandler;
import com.example.perpustakaan.Handler.KategoriHandler;
import com.example.perpustakaan.Handler.KategoriRelasiHandler;
import com.example.perpustakaan.R;

public class AddBuku extends AppCompatActivity {
    EditText editTextJudul, editTextPenulis, editTextPenerbit, editTextTahunTerbit, editTextKategori;
    Button buttonSave;
    RecyclerView recyclerViewUlasan;
    BukuHandler bukuHandler;
    KategoriHandler kategoriHandler;
    KategoriRelasiHandler kategoriRelasiHandler;
    Database database;
    Intent intent;
    String judul, penulis, penerbit, tahunterbit, kategori, username;
    long idBuku, idKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buku);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = new Database(this);
        bukuHandler = new BukuHandler(this);
        kategoriHandler = new KategoriHandler(this);
        kategoriRelasiHandler = new KategoriRelasiHandler(this);
        intent = getIntent();
        editTextJudul = findViewById(R.id.editTextJudul);
        editTextPenulis = findViewById(R.id.editTextPenulis);
        editTextPenerbit = findViewById(R.id.editTextPenerbit);
        editTextTahunTerbit = findViewById(R.id.editTextTahunTerbit);
        editTextKategori = findViewById(R.id.editTextKategori);
        recyclerViewUlasan = findViewById(R.id.rvUlasan);

        judul = intent.getStringExtra("key_judul");
        penulis = intent.getStringExtra("key_penulis");
        penerbit = intent.getStringExtra("key_penerbit");
        tahunterbit = intent.getStringExtra("key_tahun_terbit");
        username = intent.getStringExtra("key_username");
        if (username.equals("admin") || equals("petugas")){
            editTextJudul.setVisibility(View.GONE);
            editTextPenulis.setVisibility(View.GONE);
            editTextPenerbit.setVisibility(View.GONE);
            editTextTahunTerbit.setVisibility(View.GONE);
            editTextKategori.setVisibility(View.GONE);

            editTextJudul.setHint("ulasan......");
            recyclerViewUlasan.setVisibility(View.VISIBLE);
        }
        editTextJudul.setText(judul);
        editTextPenulis.setText(penulis);
        editTextPenerbit.setText(penerbit);
        editTextTahunTerbit.setText(tahunterbit);

        buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judul = editTextJudul.getText().toString();
                penulis = editTextPenulis.getText().toString();
                penerbit = editTextPenerbit.getText().toString();
                tahunterbit = editTextTahunTerbit.getText().toString();
                kategori = editTextKategori.getText().toString();
                String[] nilai = {judul, penulis, penerbit, tahunterbit};
                if (nilai.equals("")) {
                    Toast.makeText(AddBuku.this, "input field", Toast.LENGTH_SHORT).show();
                } else {
                    idBuku = bukuHandler.readBuku(nilai[0], nilai[1]);
                    if (idBuku == -1) {
                        bukuHandler.insertBuku(nilai[0], nilai[1], nilai[2], nilai[3]);
                        idBuku = bukuHandler.readBuku(nilai[0], nilai[1]);
                        if (!kategori.equals("")) {
                            kategoriHandler.insertKategori(kategori);
                            idKategori = kategoriHandler.readkategori(kategori);
                            kategoriRelasiHandler.insertKategoriRelasi((int)idBuku, (int) idKategori);
                            if (idKategori != -1){
                                Toast.makeText(AddBuku.this, "data kategori relation has been add"+idKategori, Toast.LENGTH_SHORT).show();
                            }
//                            kategoriRelasiHandler.insertKategoriRelasi(id_buku, );
                        }
                        Toast.makeText(AddBuku.this, "data has been add", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AddBuku.this, "change yours data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}