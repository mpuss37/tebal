package com.example.perpustakaan.View;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static final String database_name = "perpustakaan";
    private static final int database_version = 1;
    private Context context;
    private ContentValues contentValues;

    String table_user = "user";
    String col_username = "username";
    String col_password = "password";
    String col_email = "email";
    String col_nama_lengkap = "nama_lengkap";
    String col_alamat = "alamat";

    String table_buku = "buku";
    String col_judul = "judul";
    String col_penulis = "penulis";
    String col_penerbit = "penerbit";
    String col_tahun_terbit = "tahun_terbit";

    String table_peminjaman = "peminjaman";
    String col_tanggal_awal = "tanggal_awal";
    String col_tanggal_akhir = "tanggal_akhir";
    String col_status = "status";

    String table_ulasan = "ulasan";
    String col_ulasan = "ulasan";
    String col_rating = "rating";

    String table_kategori = "kategori";
    String col_kategori = "nama_kategori";

    String table_kategori_relasi = "kategori_relasi";

    String table_koleksi = "koleksi";

    public Database(Context context) {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS " + table_user + " (id_user integer primary key autoincrement, " + col_username + " text, " + col_password + " text, " + col_email + " text, " + col_nama_lengkap + " text, " + col_alamat + " text)");
        db.execSQL("create table IF NOT EXISTS " + table_buku + " (id_buku integer primary key autoincrement, " + "id_ulasan integer, " + "id_user integer, " + col_judul + " text , " + col_penulis + " text, " + col_penerbit + " text, " + col_tahun_terbit + " text," + "FOREIGN KEY (id_ulasan) REFERENCES " + col_ulasan + "(id_ulasan)," + "FOREIGN KEY (id_user) REFERENCES " + table_user + "(id_user))");
        db.execSQL("create table IF NOT EXISTS " + table_peminjaman + " (id_peminjaman integer primary key autoincrement, " + "id_user integer, " + "id_buku integer, " + col_tanggal_awal + " date, " + col_tanggal_akhir + " date, " + col_status + " text, " + "FOREIGN KEY (id_user) REFERENCES " + table_user + "(id_user)," + "FOREIGN KEY (id_buku) REFERENCES " + table_buku + "(id_buku))");
        db.execSQL("create table IF NOT EXISTS " + table_ulasan + " (id_ulasan integer primary key autoincrement, " + "id_user integer, " + "id_buku integer, " + col_ulasan + " text, " + col_rating + " text, " + "FOREIGN KEY (id_user) REFERENCES " + table_user + "(id_user), " + "FOREIGN KEY (id_buku) REFERENCES " + table_buku + "(id_buku))");
        db.execSQL("create table IF NOT EXISTS " + table_kategori + " (id_kategori integer primary key autoincrement, " + col_kategori + " text)");
        db.execSQL("create table IF NOT EXISTS " + table_kategori_relasi + " (id_kategori_relasi integer primary key autoincrement, " + "id_buku integer, " + "id_kategori integer, " + "FOREIGN KEY (id_buku) REFERENCES " + table_buku + "(id_buku), " + "FOREIGN KEY (id_kategori) REFERENCES " + col_kategori + "(id_kategori))");
        db.execSQL("create table IF NOT EXISTS " + table_koleksi + " (id_koleksi integer primary key autoincrement, " + "id_user integer, " + "id_buku integer, " + "FOREIGN KEY (id_user) REFERENCES " + table_user + "(id_user), " + "FOREIGN KEY (id_buku) REFERENCES " + table_buku + "(id_buku))");


        //        contentValues = new ContentValues();
//        contentValues.put(col_username, "admin");
//        contentValues.put(col_pass, "miminganteng");
//        db.insert(table_user, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
