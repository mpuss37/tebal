package com.example.perpustakaan.Model;

public class PeminjamanModel {
    private int id_peminjaman, id_user, id_buku;
    private String tanggalAwal, tanggalAkhir, status;

    public PeminjamanModel(int id_peminjaman, int id_user, int id_buku, String tanggalAwal, String tanggalAkhir, String status) {
        this.id_peminjaman = id_peminjaman;
        this.id_user = id_user;
        this.id_buku = id_buku;
        this.tanggalAwal = tanggalAwal;
        this.tanggalAkhir = tanggalAkhir;
        this.status = status;
    }

    public int getId_peminjaman() {
        return id_peminjaman;
    }

    public void setId_peminjaman(int id_peminjaman) {
        this.id_peminjaman = id_peminjaman;
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

    public String getTanggalAwal() {
        return tanggalAwal;
    }

    public void setTanggalAwal(String tanggalAwal) {
        this.tanggalAwal = tanggalAwal;
    }

    public String getTanggalAkhir() {
        return tanggalAkhir;
    }

    public void setTanggalAkhir(String tanggalAkhir) {
        this.tanggalAkhir = tanggalAkhir;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
