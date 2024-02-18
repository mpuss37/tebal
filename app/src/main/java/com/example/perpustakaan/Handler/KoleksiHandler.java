package com.example.perpustakaan.Handler;

import android.content.ContentValues;
import android.content.Context;

import com.example.perpustakaan.Model.BukuModel;
import com.example.perpustakaan.View.Database;
import com.example.perpustakaan.View.MainActivity;

import java.util.ArrayList;

public class KoleksiHandler extends MainActivity {

    Database database;
    String[] whereArgs;
    String whereClause;

    public KoleksiHandler(Context context) {
        database = new Database(context);
    }

    public void openWrite() {
        sqLiteDatabase = database.getWritableDatabase();
    }

    public void openRead() {
        sqLiteDatabase = database.getReadableDatabase();
    }

    public long insertKoleksi(int id_user, int id_buku) {
        contentValues = new ContentValues();
        contentValues.put("id_user", id_user);
        contentValues.put("id_buku", id_buku);
        return sqLiteDatabase.insert(database.table_buku, null, contentValues);
    }

    public long updateKategori(int id_buku, int id_user, int id_ulasan, String judul, String penulis, String penerbit, String tahunterbit) {
        contentValues = new ContentValues();
        whereClause = "id_buku = ?";
        whereArgs = new String[]{String.valueOf(id_buku)};
        contentValues.put("id_user", id_user);
        contentValues.put("id_ulasan", id_ulasan);
        contentValues.put(database.col_judul, judul);
        contentValues.put(database.col_judul, judul);
        contentValues.put(database.col_penulis, penulis);
        contentValues.put(database.col_penerbit, penerbit);
        contentValues.put(database.col_tahun_terbit, tahunterbit);
        return sqLiteDatabase.update(database.table_buku, contentValues, whereClause, whereArgs);
    }

    public long readBuku(String username, String password) {
        id_data = -1;
        query = "select * from user where username = '" + username + "' and password = '" + password + "'";
        cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            id_data = cursor.getInt(0);
        }
        return id_data;
    }

    public void deleteBuku(int id_buku) {
        whereClause = "id_buku = ?";
        whereArgs = new String[]{String.valueOf(id_buku)};
        sqLiteDatabase.delete(database.table_buku, whereClause, whereArgs);
    }


    public ArrayList<BukuModel> displayBuku(long id_user) {
        sqLiteDatabase = database.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("select * from buku", null);
        bukuModelArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                bukuModelArrayList.add(new BukuModel(cursor.getInt(0), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bukuModelArrayList;
    }
}
