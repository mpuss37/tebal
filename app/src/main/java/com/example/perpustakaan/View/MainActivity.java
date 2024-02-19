package com.example.perpustakaan.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perpustakaan.Handler.UserHandler;
import com.example.perpustakaan.Model.BukuModel;
import com.example.perpustakaan.Model.PeminjamanModel;
import com.example.perpustakaan.Model.UlasanModel;
import com.example.perpustakaan.Model.UserModel;
import com.example.perpustakaan.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editTextUsername, editTextPass;
    protected String username, pass;
    protected Button buttonSave;
    ConstraintLayout constraintLayoutMain;
    TextView textViewRegister, textViewAdmin;
    protected SQLiteDatabase sqLiteDatabase;
    protected ContentValues contentValues;
    protected Intent intent;
    protected Cursor cursor;
    protected ArrayList<BukuModel> bukuModelArrayList;
    protected ArrayList<UserModel> userModelArrayList;
    protected ArrayList<UlasanModel> ulasanModelArrayList;
    protected ArrayList<PeminjamanModel> peminjamanModelArrayList;
    protected UserHandler userHandler;
    protected String query;
    protected long id_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        userHandler = new UserHandler(this);
        editTextUsername = findViewById(R.id.editTextPassword);
        editTextPass = findViewById(R.id.editTextPass);
        buttonSave = findViewById(R.id.buttonSave);
        textViewRegister = findViewById(R.id.textViewRegister);
        textViewAdmin = findViewById(R.id.textViewAdmin);
        constraintLayoutMain = findViewById(R.id.clMain);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editTextUsername.getText().toString();
                pass = editTextPass.getText().toString();
                intent = new Intent(MainActivity.this, MenuUser.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (username.equals("") || pass.equals("")) {
                    Toast.makeText(MainActivity.this, "input your field", Toast.LENGTH_SHORT).show();
                } else if (buttonSave.getText().toString().equals("Login")) {
                    id_data = userHandler.readUser(username, pass);
                    if (id_data != -1) {
                        intent.putExtra("key_id_user", id_data);
                        intent.putExtra("key_username", username);
                        intent.putExtra("key_password", pass);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "your user/pass false", Toast.LENGTH_SHORT).show();
                    }
                } else if (buttonSave.getText().toString().equals("Register")) {
                    id_data = userHandler.readUser(username, pass);
                    if (id_data != -1) {
                        Toast.makeText(MainActivity.this, "username already used", Toast.LENGTH_SHORT).show();
                    } else {
                        userHandler.insertUser(username, pass);
                        Toast.makeText(MainActivity.this, "data has been add", Toast.LENGTH_SHORT).show();
                        editTextUsername.setText("");
                        editTextPass.setText("");
                    }
                }
            }
        });

        constraintLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKey(v);
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonSave.getText().toString().equals("Login")) {
                    buttonSave.setText("Register");
                    textViewRegister.setText("have account");
                } else {
                    buttonSave.setText("Login");
                    textViewRegister.setText("new account ??");
                    Toast.makeText(MainActivity.this, "login as user", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonSave.getText().toString().equals("Login")) {
                    buttonSave.setText("Admin");
                    Toast.makeText(MainActivity.this, "Login as admin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void hideKey(View view) {
        view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}