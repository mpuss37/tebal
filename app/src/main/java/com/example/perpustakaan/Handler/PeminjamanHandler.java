package com.example.perpustakaan.Handler;

import android.content.ContentValues;
import android.content.Context;

import com.example.perpustakaan.Model.BukuModel;
import com.example.perpustakaan.Model.PeminjamanModel;
import com.example.perpustakaan.View.Database;
import com.example.perpustakaan.View.MainActivity;

import java.util.ArrayList;

public class PeminjamanHandler extends MainActivity {

    Database database;
    String[] whereArgs;
    String whereClause;

    public PeminjamanHandler(Context context) {
        database = new Database(context);
    }

    public void openWrite() {
        sqLiteDatabase = database.getWritableDatabase();
    }

    public void openRead() {
        sqLiteDatabase = database.getReadableDatabase();
    }

    public long insertPeminjaman(long id_user, long id_buku, String tanggalAwal, String tanggalAkhir, String status) {
        openWrite();
        contentValues = new ContentValues();
        contentValues.put("id_user", id_user);
        contentValues.put("id_buku", id_buku);
        contentValues.put(database.col_tanggal_awal, tanggalAwal);
        contentValues.put(database.col_tanggal_akhir, tanggalAkhir);
        contentValues.put(database.col_status, status);
        return sqLiteDatabase.insert(database.table_peminjaman, null, contentValues);
    }

    public long readPeminjaman(long id_user, long id_buku) {
        openRead();
        id_data = -1;
        query = "select * from peminjaman where id_buku = '" + id_buku + "' and id_user = '" + id_user + "'";
        cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            id_data = cursor.getInt(0);
        }
        return id_data;
    }

    public long checkPeminjamanStatus(long id_user, long id_buku) {
        openRead();
        id_data = -1;
        query = "select * from peminjaman where id_buku = '" + id_buku + "' and id_user = '" + id_user + "'";
        cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            id_data = cursor.getInt(0);
        }
        return id_data;
    }

    public void deletePeminjaman(long id_buku, long id_user) {
        openWrite();
        sqLiteDatabase.delete(database.table_peminjaman, "id_buku = '" + id_buku + "' and id_user = '" + id_user + "'", null);
    }

    public ArrayList<PeminjamanModel> displayPeminjaman(long id_user) {
        openRead();
        String query = "select * from peminjaman where id_user = '" + id_user + "'";
        cursor = sqLiteDatabase.rawQuery(query, null);
        peminjamanModelArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                peminjamanModelArrayList.add(new PeminjamanModel(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return peminjamanModelArrayList;
    }
}
