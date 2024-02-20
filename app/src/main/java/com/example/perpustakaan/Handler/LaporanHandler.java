package com.example.perpustakaan.Handler;

import android.content.ContentValues;
import android.content.Context;

import com.example.perpustakaan.Model.BukuModel;
import com.example.perpustakaan.Model.LaporanModel;
import com.example.perpustakaan.View.Database;
import com.example.perpustakaan.View.MainActivity;

import java.util.ArrayList;

public class LaporanHandler extends MainActivity {

    Database database;
    String[] whereArgs;
    String whereClause;

    public LaporanHandler(Context context) {
        database = new Database(context);
    }

    public void openWrite() {
        sqLiteDatabase = database.getWritableDatabase();
    }

    public void openRead() {
        sqLiteDatabase = database.getReadableDatabase();
    }

    public long insertBuku(String judul, String penulis, String penerbit, String tahunterbit) {
        openWrite();
        contentValues = new ContentValues();
        contentValues.put(database.col_judul, judul);
        contentValues.put(database.col_penulis, penulis);
        contentValues.put(database.col_penerbit, penerbit);
        contentValues.put(database.col_tahun_terbit, tahunterbit);
        return sqLiteDatabase.insert(database.table_buku, null, contentValues);
    }

    public long updateBuku(int id_buku, int id_user, int id_ulasan, String judul, String penulis, String penerbit, String tahunterbit) {
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

    public long readBuku(String judul, String penulis) {
        openRead();
        id_data = -1;
        query = "select * from buku where judul = '" + judul + "' and penulis = '" + penulis + "'";
        cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            id_data = cursor.getInt(0);
        }
        return id_data;
    }

    public void deleteBuku(long id_buku) {
        openWrite();
        sqLiteDatabase.delete(database.table_buku, "id_buku = '" + id_buku + "'", null);
    }

    public ArrayList<LaporanModel> displayLaporan() {
        openRead();
//        String query = "SELECT p.id_peminjaman, " +
//                "       p.id_user, " +
//                "       p.id_buku, " +
//                "       b.judul, " +
//                "       b.penulis, " +
//                "       b.penerbit, " +
//                "       b.kategori, " +
//                "       p.status, " +
//                "       p.tanggalAwal, " +
//                "       p.tanggalAkhir, " +
//                "       u.username " +
//                "FROM peminjaman p " +
//                "JOIN buku b ON p.id_buku = b.id_buku " +
//                "JOIN kategori b ON p.id_buku = b.id_buku " +
//                "JOIN user u ON p.id_user = u.id_user " +
//                "ORDER BY p.id_peminjaman;";

        String query = "SELECT p.id_peminjaman, " +
                "       p.id_user, " +
                "       p.id_buku, " +
                "       b.judul, " +
                "       b.penulis, " +
                "       b.penerbit, " +
                "       k.nama_kategori, " +  // Mengambil nama_kategori dari tabel kategori
                "       p.status, " +
                "       p.tanggal_awal, " +
                "       p.tanggal_akhir, " +
                "       u.username " +
                "FROM peminjaman p " +
                "JOIN buku b ON p.id_buku = b.id_buku " +
                "JOIN user u ON p.id_user = u.id_user " +
                "JOIN kategori_relasi kr ON b.id_buku = kr.id_buku " +  // Bergabung dengan tabel kategori_relasi
                "JOIN kategori k ON kr.id_kategori = k.id_kategori " +  // Bergabung dengan tabel kategori
                "ORDER BY p.id_peminjaman;";



        cursor = sqLiteDatabase.rawQuery(query, null);
        laporanModelArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                laporanModelArrayList.add(new LaporanModel(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return laporanModelArrayList;
    }
}
