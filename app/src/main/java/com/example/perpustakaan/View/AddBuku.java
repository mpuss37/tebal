package com.example.perpustakaan.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.perpustakaan.Handler.BukuHandler;
import com.example.perpustakaan.R;

public class AddBuku extends AppCompatActivity {
    EditText editTextJudul, editTextPenulis, editTextPenerbit, editTextTahunTerbit, editTextKategori;
    Button buttonSave;
    BukuHandler bukuHandler;
    Database database;
    Intent intent;
    String judul, penulis, penerbit, tahunterbit, kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buku);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = new Database(this);
        bukuHandler = new BukuHandler(this);
        intent = getIntent();
        bukuHandler.openWrite();
        editTextJudul = findViewById(R.id.editTextJudul);
        editTextPenulis = findViewById(R.id.editTextPenulis);
        editTextPenerbit = findViewById(R.id.editTextPenerbit);
        editTextTahunTerbit = findViewById(R.id.editTextTahunTerbit);
        editTextKategori = findViewById(R.id.editTextKategori);

        judul = intent.getStringExtra("key_judul");
        penulis = intent.getStringExtra("key_penulis");
        penerbit = intent.getStringExtra("key_penerbit");
        tahunterbit = intent.getStringExtra("key_tahun_terbit");
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
                String[] nilai = {judul, penerbit, penerbit, tahunterbit};
                if (nilai.equals("")) {
                    Toast.makeText(AddBuku.this, "input field", Toast.LENGTH_SHORT).show();
                } else {
                    bukuHandler.insertBuku(nilai[0], nilai[1], nilai[2], nilai[3]);
                    Toast.makeText(AddBuku.this, "data has been add", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}