package com.example.perpustakaan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpustakaan.Handler.PeminjamanHandler;
import com.example.perpustakaan.Model.LaporanModel;
import com.example.perpustakaan.Model.PeminjamanModel;
import com.example.perpustakaan.R;
import com.example.perpustakaan.View.MenuPeminjaman;

import java.time.LocalDate;
import java.util.ArrayList;

public class PeminjamanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<PeminjamanModel> peminjamanModelArrayList;
    ArrayList<LaporanModel> laporanModelArrayList;
    Intent intent;
    Context context;
    int id;
    long id_user, id_buku, id_peminjaman;
    private String username, status, tanggalAwal, tanggalAkhir;

    public PeminjamanAdapter(ArrayList<PeminjamanModel> peminjamanModelArrayList, Context context, long id_user) {
        this.peminjamanModelArrayList = peminjamanModelArrayList;
        this.context = context;
        this.id_user = id_user;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peminjaman, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PeminjamanModel peminjamanModel = peminjamanModelArrayList.get(position);
        PeminjamanHandler peminjamanHandler = new PeminjamanHandler(context);
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.textViewIdUser.setText(String.valueOf(peminjamanModel.getId_user()));
            viewHolder.textViewIdBuku.setText(String.valueOf(peminjamanModel.getId_buku()));
            viewHolder.textViewTanggalAwal.setText(String.valueOf(peminjamanModel.getTanggalAwal()));
            viewHolder.textViewTanggalAkhir.setText(String.valueOf(peminjamanModel.getTanggalAkhir()));
            viewHolder.textViewStatus.setText(String.valueOf(peminjamanModel.getStatus()));
            viewHolder.textViewJudul.setText(String.valueOf(peminjamanModel.getJudul()));

//            viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    LocalDate localDate = LocalDate.now();
//                    generateReport(context, localDate.toString(), "PDF");
//                }
//            });

            viewHolder.imageViewRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    peminjamanModelArrayList.remove(holder.getAdapterPosition());
                    id_buku = Long.parseLong(viewHolder.textViewIdBuku.getText().toString());
                    id_user = Long.parseLong(viewHolder.textViewIdUser.getText().toString());
                    peminjamanHandler.deletePeminjaman(id_buku, id_user);
                    Toast.makeText(context, "delete successfully", Toast.LENGTH_SHORT).show();
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });
        }
    }

    public String generateReport(Context context, String nama, String tipe) {
        // Lakukan proses generate report di sini dengan menggunakan context yang diterima
        // Misalnya, panggil metode generateReport dari activity atau fragment utama
        if (context instanceof MenuPeminjaman) {
            ((MenuPeminjaman) context).generateReport(context, nama, tipe);
        }

        return nama;
    }


    @Override
    public int getItemCount() {
        return peminjamanModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIdBuku, textViewIdUser, textViewTanggalAwal, textViewTanggalAkhir, textViewStatus, textViewJudul;
        ImageView imageViewRemove;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIdUser = itemView.findViewById(R.id.TextViewIdUser);
            textViewIdBuku = itemView.findViewById(R.id.textViewIdBuku);
            textViewTanggalAwal = itemView.findViewById(R.id.textViewAwal);
            textViewTanggalAkhir = itemView.findViewById(R.id.textViewUsername);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewJudul = itemView.findViewById(R.id.textViewJudul);
            imageViewRemove = itemView.findViewById(R.id.imageViewRemove);
            constraintLayout = itemView.findViewById(R.id.cvData);
        }
    }
}
