package com.example.perpustakaan.Model;

public class UlasanModel {
    private int id_ulasan, id_user, id_buku;
    private String ulasan, rating, username;

    public UlasanModel(int id_ulasan, int id_user, int id_buku, String ulasan, String rating, String username) {
        this.id_ulasan = id_ulasan;
        this.id_user = id_user;
        this.id_buku = id_buku;
        this.ulasan = ulasan;
        this.rating = rating;
        this.username = username;
    }

    public int getId_ulasan() {
        return id_ulasan;
    }

    public void setId_ulasan(int id_ulasan) {
        this.id_ulasan = id_ulasan;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_buku() {
        return id_buku;
    }

    public void setId_buku(int id_buku) {
        this.id_buku = id_buku;
    }

    public String getUlasan() {
        return ulasan;
    }

    public void setUlasan(String ulasan) {
        this.ulasan = ulasan;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
