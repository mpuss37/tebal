package com.example.perpustakaan.Model;

public class KategoriModel {
    private int id_kategori;
    private String kategori;

    public KategoriModel(int id_kategori, String kategori) {
        this.id_kategori = id_kategori;
        this.kategori = kategori;
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(int id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
