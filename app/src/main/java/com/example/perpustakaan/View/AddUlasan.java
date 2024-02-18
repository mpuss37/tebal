package com.example.perpustakaan.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perpustakaan.Adapter.UlasanAdapter;
import com.example.perpustakaan.Handler.UlasanHandler;
import com.example.perpustakaan.Model.UlasanModel;
import com.example.perpustakaan.R;

import java.util.ArrayList;

public class AddUlasan extends AppCompatActivity {
    EditText editTextUlasan;
    RecyclerView recyclerViewUlasan;
    TextView textViewRating;
    Button buttonSave;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    UlasanAdapter ulasanAdapter;
    UlasanHandler ulasanHandler;
    Bundle bundle;
    String ulasan, rating, username;
    long id_buku, id_ulasan, id_user;
    ArrayList<UlasanModel> ulasanModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ulasan);
        ulasanHandler = new UlasanHandler(this);
        bundle = this.getIntent().getExtras();
        id_user = bundle.getLong("key_id_user");
        id_buku = bundle.getLong("key_id_buku");
        username = bundle.getString("key_username");
        textViewRating = findViewById(R.id.textViewRating);
        editTextUlasan = findViewById(R.id.editTextUlasan);
        buttonSave = findViewById(R.id.buttonSave);
        radioGroup = findViewById(R.id.radioGroup2);
        radioButton1 = findViewById(R.id.radioButtonRate1);
        radioButton2 = findViewById(R.id.radioButtonRate2);
        radioButton3 = findViewById(R.id.radioButtonRate3);
        radioButton4 = findViewById(R.id.radioButtonRate4);
        radioButton5 = findViewById(R.id.radioButtonRate5);
        recyclerViewUlasan = findViewById(R.id.rvUlasan);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonRate1) {
                    textViewRating.setText("1");
                } else if (checkedId == R.id.radioButtonRate2) {
                    textViewRating.setText("2");
                } else if (checkedId == R.id.radioButtonRate3) {
                    textViewRating.setText("3");
                } else if (checkedId == R.id.radioButtonRate4) {
                    textViewRating.setText("4");
                } else if (checkedId == R.id.radioButtonRate5) {
                    textViewRating.setText("5");
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ulasan = editTextUlasan.getText().toString();
                rating = textViewRating.getText().toString() + "+";
                if (ulasan.equals("")) {
                    Toast.makeText(AddUlasan.this, "input field", Toast.LENGTH_SHORT).show();
                } else {
                    id_ulasan = ulasanHandler.readUlasan(ulasan);
                    if (id_ulasan == -1) {
                        ulasanHandler.insertUlasan(id_user, id_buku, ulasan, rating);
                        ulasanModelArrayList.clear();
                        ulasanModelArrayList.addAll(ulasanHandler.displayUlasan());
                        ulasanAdapter.notifyDataSetChanged();
                        Toast.makeText(AddUlasan.this, "data has been add", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddUlasan.this, "change yours data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddUlasan.this, RecyclerView.VERTICAL, false);
        recyclerViewUlasan.setLayoutManager(linearLayoutManager);
        ulasanModelArrayList = ulasanHandler.displayUlasan();
        ulasanAdapter = new UlasanAdapter(ulasanModelArrayList, this, id_user, id_buku);
        recyclerViewUlasan.setAdapter(ulasanAdapter);
        ulasanAdapter.notifyDataSetChanged();
    }
}