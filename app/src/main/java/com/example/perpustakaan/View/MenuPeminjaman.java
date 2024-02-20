package com.example.perpustakaan.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perpustakaan.Adapter.PeminjamanAdapter;
import com.example.perpustakaan.Handler.PeminjamanHandler;
import com.example.perpustakaan.Model.PeminjamanModel;
import com.example.perpustakaan.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MenuPeminjaman extends AppCompatActivity {
    RecyclerView recyclerViewPeminjaman;
    PeminjamanHandler peminjamanHandler;
    PeminjamanAdapter peminjamanAdapter;
    Button buttonGenerate;
    Bundle bundle;
    long id_data;
    ArrayList<PeminjamanModel> peminjamanModelArrayList;

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
        recyclerViewPeminjaman = findViewById(R.id.rvPeminjaman);
        buttonGenerate = findViewById(R.id.buttonGenerate);

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    generateReport("heker", "PDF");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
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

    public String generateReport(String nama, String tipe) throws FileNotFoundException {

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), nama);
        Uri uriFile = Uri.fromFile(file);

        int pageWidth = 612;
        int pageHeight = 1208;

        Bitmap bmp = null;
        Canvas canvas = null;
        PdfDocument pdfDocument = null;
        PdfDocument.PageInfo pageInfos;
        PdfDocument.Page page = null;

        if (tipe.equalsIgnoreCase( "PDF")) {
            Toast.makeText(this, "kenek", Toast.LENGTH_SHORT).show();
            pdfDocument = new PdfDocument();
            pageInfos = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
            page = pdfDocument.startPage(pageInfos);
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
        canvas.drawText("LAPORAN SEDERHANA", pageWidth / 2, 50, paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(12f);
        paint.setFakeBoldText(false);

        int x = 45;
        int y = 75;
        int space = 16;

        String text = "Halo, aku keren!";

        canvas.drawText(text, x, y, paint);

        if (tipe.equals("PDF")) {
            pdfDocument.finishPage(page);
            savePdf(uriFile, pdfDocument);
        }

        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.d("appname", "image is saved in gallery and gallery is refreshed.");
                    }
                }
        );

        return file.getAbsolutePath();
    }

    private void savePdf(Uri uriFile, PdfDocument pdfDocument) {
        try {
            BufferedOutputStream stream = new BufferedOutputStream(Objects.requireNonNull(getContentResolver().openOutputStream(uriFile)));
            pdfDocument.writeTo(stream);
            pdfDocument.close();
            stream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}