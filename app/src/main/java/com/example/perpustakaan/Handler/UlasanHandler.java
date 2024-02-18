package com.example.perpustakaan.Handler;

import android.content.ContentValues;
import android.content.Context;

import com.example.perpustakaan.Model.BukuModel;
import com.example.perpustakaan.Model.UlasanModel;
import com.example.perpustakaan.View.Database;
import com.example.perpustakaan.View.MainActivity;

import java.util.ArrayList;

public class UlasanHandler extends MainActivity {

    Database database;
    String[] whereArgs;
    String whereClause;

    public UlasanHandler(Context context) {
        database = new Database(context);
    }

    public void openWrite() {
        sqLiteDatabase = database.getWritableDatabase();
    }

    public void openRead() {
        sqLiteDatabase = database.getReadableDatabase();
    }

    public long insertUlasan(long id_user, long id_buku, String ulasan, String rating) {
        openWrite();
        contentValues = new ContentValues();
        contentValues.put("id_user", id_user);
        contentValues.put("id_buku", id_buku);
        contentValues.put(database.col_ulasan, ulasan);
        contentValues.put(database.col_rating, rating);
        return sqLiteDatabase.insert(database.table_ulasan, null, contentValues);
    }

    public long readUlasan(String ulasan) {
        openRead();
        id_data = -1;
        query = "select * from ulasan where ulasan = '" + ulasan + "'";
        cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            id_data = cursor.getInt(0);
        }
        return id_data;
    }

    public void deleteUlasan(long id_ulasan) {
        openWrite();
        sqLiteDatabase.delete(database.table_ulasan, "id_ulasan = '" + id_ulasan + "'", null);
    }

    public ArrayList<UlasanModel> displayUlasan() {
        openRead();
        cursor = sqLiteDatabase.rawQuery("SELECT u.*, us.username FROM ulasan u INNER JOIN user us ON u.id_user = us.id_user", null);
        ulasanModelArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ulasanModelArrayList.add(new UlasanModel(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ulasanModelArrayList;
    }
}
