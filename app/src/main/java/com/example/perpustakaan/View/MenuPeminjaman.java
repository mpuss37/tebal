package com.example.perpustakaan.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.perpustakaan.Adapter.PeminjamanAdapter;
import com.example.perpustakaan.Handler.PeminjamanHandler;
import com.example.perpustakaan.Model.LaporanModel;
import com.example.perpustakaan.Model.PeminjamanModel;
import com.example.perpustakaan.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MenuPeminjaman extends AppCompatActivity {
    RecyclerView recyclerViewPeminjaman;
    PeminjamanHandler peminjamanHandler;
    PeminjamanAdapter peminjamanAdapter;
    Button buttonGenerate;
    Bundle bundle;
    long id_data;
    ArrayList<PeminjamanModel> peminjamanModelArrayList;
    ArrayList<LaporanModel> laporanModelArrayList;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_peminjaman);
        getSupportActionBar().show();
        getSupportActionBar().setTitle("Menu Peminjaman");
        bundle = this.getIntent().getExtras();
        id_data = bundle.getLong("key_id_user");
        peminjamanHandler = new PeminjamanHandler(this);
        peminjamanModelArrayList = new ArrayList<>();
        laporanModelArrayList = new ArrayList<>();
        recyclerViewPeminjaman = findViewById(R.id.rvPeminjaman);
        buttonGenerate = findViewById(R.id.buttonGenerate);

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateReport(MenuPeminjaman.this, "heker", "PDF");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MenuPeminjaman.this, RecyclerView.VERTICAL, false);
        recyclerViewPeminjaman.setLayoutManager(linearLayoutManager);
        peminjamanModelArrayList = peminjamanHandler.displayPeminjaman(id_data);
        peminjamanAdapter = new PeminjamanAdapter(peminjamanModelArrayList, this, id_data);
        recyclerViewPeminjaman.setAdapter(peminjamanAdapter);
        peminjamanAdapter.notifyDataSetChanged();
    }

    public String generateReport(Context context, String nama, String tipe) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), nama + ".pdf");
        Uri uriFile = Uri.fromFile(file);

        int pageWidth = 612;
        int pageHeight = 1208;

        Bitmap bmp = null;
        Canvas canvas = null;
        PdfDocument pdfDocument = null;
        PdfDocument.Page page = null;

        if (tipe.equalsIgnoreCase("PDF")) {
            pdfDocument = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
            page = pdfDocument.startPage(pageInfo);
            canvas = page.getCanvas();
        } else {
            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
            bmp = Bitmap.createBitmap(pageWidth, pageHeight, conf);
            canvas = new Canvas(bmp);
            canvas.drawColor(Color.WHITE);
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(14f);
        paint.setColor(Color.parseColor("#000000"));
        paint.setFakeBoldText(true);
        canvas.drawText("LAPORAN PEMINJAMAN BUKU", pageWidth / 2, 50, paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(12f);
        paint.setFakeBoldText(false);

        int x = 45;
        int y = 75;
        int space = 16;

//        for (LaporanModel laporanModel : laporanModelArrayList) {
//            // Menggambar kolom-kolom laporan
//            canvas.drawText("Username: " + laporanModel.getUsername(), x, y, paint);
//            y += space;
//            canvas.drawText("Judul: " + laporanModel.getJudul(), x, y, paint);
//            y += space;
//            canvas.drawText("Tanggal Peminjaman: " + laporanModel.getTanggalAwal(), x, y, paint);
//            y += space;
//            canvas.drawText("Tanggal Pengembalian: " + laporanModel.getTanggalAkhir(), x, y, paint);
//            y += space;
//            canvas.drawText("Status: " + laporanModel.getStatus(), x, y, paint);
//            y += space;
//            canvas.drawText("Kategori: " + laporanModel.getKategori(), x, y, paint);
//            y += space;
//        }

//        String text = "Halo, aku keren!";
//        canvas.drawText(text, x, y, paint);

        if (tipe.equalsIgnoreCase("PDF")) {
            pdfDocument.finishPage(page);
            savePdf(context, uriFile, pdfDocument);
        }

        return file.getAbsolutePath();
    }

    private void savePdf(Context context, Uri uriFile, PdfDocument pdfDocument) {
        try {
            // Ambil content URI menggunakan FileProvider
            Uri contentUri = FileProvider.getUriForFile(context, getPackageName() + ".provider", new File(uriFile.getPath()));

            // Membuka output stream ke content URI
            OutputStream outputStream = getContentResolver().openOutputStream(contentUri);
            if (outputStream != null) {
                BufferedOutputStream stream = new BufferedOutputStream(outputStream);

                // Menulis konten PDF ke output stream
                pdfDocument.writeTo(stream);

                // Menutup dokumen PDF dan output stream
                pdfDocument.close();
                stream.flush();
                stream.close();

                // Memberi tahu pengguna bahwa file telah disimpan
                Toast.makeText(context, "File PDF telah disimpan", Toast.LENGTH_SHORT).show();
            } else {
                // Jika outputStream null, mungkin terjadi kesalahan dalam membuka file
                Toast.makeText(context, "Gagal menyimpan file: OutputStream null", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "Gagal menyimpan file: FileNotFoundException", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Gagal menyimpan file: IOException", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Gagal menyimpan file: Exception", Toast.LENGTH_SHORT).show();
        }
    }

}