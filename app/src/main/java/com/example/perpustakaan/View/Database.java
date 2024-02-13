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

    public String table_user = "user";
    public String col_username = "username";
    public String col_password = "password";
    public String col_email = "email";
    public String col_nama_lengkap = "nama_lengkap";
    public String col_alamat = "alamat";

    public String table_buku = "buku";
    public String col_judul = "judul";
    public String col_penulis = "penulis";
    public String col_penerbit = "penerbit";
    public String col_tahun_terbit = "tahun_terbit";

    public String table_peminjaman = "peminjaman";
    public String col_tanggal_awal = "tanggal_awal";
    public String col_tanggal_akhir = "tanggal_akhir";
    public String col_status = "status";

    public String table_ulasan = "ulasan";
    public String col_ulasan = "ulasan";
    public String col_rating = "rating";

    public String table_kategori = "kategori";
    public String col_kategori = "nama_kategori";

    public String table_kategori_relasi = "kategori_relasi";

    public String table_koleksi = "koleksi";

    public Database(Context context) {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS " + table_user + " (id_user integer primary key autoincrement, " + col_username + " text, " + col_password + " text, " + col_email + " text, " + col_nama_lengkap + " text, " + col_alamat + " text)");
        contentValues = new ContentValues();
        contentValues.put(col_username, "petugas");
        contentValues.put(col_password, "petugas");
        db.insert(table_user, null, contentValues);

        db.execSQL("create table IF NOT EXISTS " + table_buku + " (id_buku integer primary key autoincrement, " + "id_ulasan integer, " + "id_user integer, " + col_judul + " text , " + col_penulis + " text, " + col_penerbit + " text, " + col_tahun_terbit + " text," + "FOREIGN KEY (id_ulasan) REFERENCES " + col_ulasan + "(id_ulasan)," + "FOREIGN KEY (id_user) REFERENCES " + table_user + "(id_user))");
        db.execSQL("create table IF NOT EXISTS " + table_peminjaman + " (id_peminjaman integer primary key autoincrement, " + "id_user integer, " + "id_buku integer, " + col_tanggal_awal + " date, " + col_tanggal_akhir + " date, " + col_status + " text, " + "FOREIGN KEY (id_user) REFERENCES " + table_user + "(id_user)," + "FOREIGN KEY (id_buku) REFERENCES " + table_buku + "(id_buku))");
        db.execSQL("create table IF NOT EXISTS " + table_ulasan + " (id_ulasan integer primary key autoincrement, " + "id_user integer, " + "id_buku integer, " + col_ulasan + " text, " + col_rating + " text, " + "FOREIGN KEY (id_user) REFERENCES " + table_user + "(id_user), " + "FOREIGN KEY (id_buku) REFERENCES " + table_buku + "(id_buku))");
        db.execSQL("create table IF NOT EXISTS " + table_kategori + " (id_kategori integer primary key autoincrement, " + col_kategori + " text)");
        db.execSQL("create table IF NOT EXISTS " + table_kategori_relasi + " (id_kategori_relasi integer primary key autoincrement, " + "id_buku integer, " + "id_kategori integer, " + "FOREIGN KEY (id_buku) REFERENCES " + table_buku + "(id_buku), " + "FOREIGN KEY (id_kategori) REFERENCES " + col_kategori + "(id_kategori))");
        db.execSQL("create table IF NOT EXISTS " + table_koleksi + " (id_koleksi integer primary key autoincrement, " + "id_user integer, " + "id_buku integer, " + "FOREIGN KEY (id_user) REFERENCES " + table_user + "(id_user), " + "FOREIGN KEY (id_buku) REFERENCES " + table_buku + "(id_buku))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
