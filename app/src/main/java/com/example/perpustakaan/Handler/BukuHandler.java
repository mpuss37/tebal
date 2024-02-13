package com.example.perpustakaan.Handler;

import android.content.ContentValues;
import android.content.Context;

import com.example.perpustakaan.View.Database;
import com.example.perpustakaan.View.MainActivity;

public class BukuHandler extends MainActivity {

    Database database;

    public BukuHandler(Context context) {
        database = new Database(context);
    }

    public void openWrite() {
        sqLiteDatabase = database.getWritableDatabase();
    }

    public void openRead() {
        sqLiteDatabase = database.getReadableDatabase();
    }

    public long insertBuku(String username, String password) {
        contentValues = new ContentValues();
        contentValues.put(database.col_username, username);
        contentValues.put(database.col_password, password);
        return sqLiteDatabase.insert(database.table_user, null, contentValues);
    }

    public long readBuku(String username, String password) {
        id_data = -1;
        query = "select * from user where username = '" + username + "' and password = '" + password + "'";
        cursor = sqLiteDatabase.rawQuery(query,null);
        if (cursor.moveToFirst()){
            id_data = cursor.getInt(0);
        }
        return id_data;
    }
//
//    public long displayBuku(){
//
//    }
}
