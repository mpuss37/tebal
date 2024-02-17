package com.example.perpustakaan.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.perpustakaan.Handler.UserHandler;
import com.example.perpustakaan.R;
import com.google.android.material.textfield.TextInputLayout;

public class MenuProfile extends AppCompatActivity {
    EditText editTextUsername, editTextPassword, editTextEmail, editTextNamaLengkap, editTextAlamat;
    TextInputLayout textInputLayoutPassword;
    String username, password, email, namalengkap, alamat;
    Button buttonSave;
    Bundle bundle;
    long id_data;

    UserHandler userHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_profile);
        bundle = this.getIntent().getExtras();
        id_data = bundle.getLong("key_id_user");
        username = bundle.getString("key_username");
        password = bundle.getString("key_password");
        userHandler = new UserHandler(MenuProfile.this);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNamaLengkap = findViewById(R.id.editTextNamaLengkap);
        editTextAlamat = findViewById(R.id.editTextAlamat);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPass);
        buttonSave = findViewById(R.id.buttonSave);

        if (username.equals("admin") || equals("petugas")) {
            editTextUsername.setVisibility(View.GONE);
        }

        editTextUsername.setText(username);
        editTextPassword.setText(password);

        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();
        email = editTextEmail.getText().toString();
        namalengkap = editTextNamaLengkap.getText().toString();
        alamat = editTextAlamat.getText().toString();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userHandler.updateUser(id_data, username, password, email, namalengkap, alamat);
                Toast.makeText(MenuProfile.this, "data has been updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}