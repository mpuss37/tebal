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

import com.example.perpustakaan.Handler.LaporanHandler;
import com.example.perpustakaan.Handler.PeminjamanHandler;
import com.example.perpustakaan.Model.LaporanModel;
import com.example.perpustakaan.Model.PeminjamanModel;
import com.example.perpustakaan.R;

import java.util.ArrayList;

public class LaporanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<LaporanModel> laporanModelArrayList;
    Intent intent;
    Context context;
    int id;
    long id_user, id_buku, id_peminjaman;
    private String judul, penulis, penerbit, kategori, status, tanggalAwal, tanggalAkhir, username;

    public LaporanAdapter(ArrayList<LaporanModel> laporanModelArrayList, Context context) {
        this.laporanModelArrayList = laporanModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LaporanModel laporanModel = laporanModelArrayList.get(position);
        LaporanHandler laporanHandler = new LaporanHandler(context);
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
//    textViewIdPeminjaman, textViewIdBuku, textViewIdUser, textViewJudul, textViewPenulis, textViewPenerbit, textViewKategori, textViewTanggalAwal, textViewTanggalAkhir, textViewStatus, textViewUsername
            viewHolder.textViewIdPeminjaman.setText(String.valueOf(laporanModel.getId_peminjaman()));
            viewHolder.textViewIdBuku.setText(String.valueOf(laporanModel.getId_buku()));
            viewHolder.textViewIdUser.setText(String.valueOf(laporanModel.getId_user()));
            viewHolder.textViewJudul.setText(String.valueOf(laporanModel.getJudul()));
            viewHolder.textViewPenulis.setText(String.valueOf(laporanModel.getPenulis()));
            viewHolder.textViewPenerbit.setText(String.valueOf(laporanModel.getPenerbit()));
            viewHolder.textViewKategori.setText(String.valueOf(laporanModel.getKategori()));
            viewHolder.textViewTanggalAwal.setText(String.valueOf(laporanModel.getTanggalAwal()));
            viewHolder.textViewTanggalAkhir.setText(String.valueOf(laporanModel.getTanggalAkhir()));
            viewHolder.textViewStatus.setText(String.valueOf(laporanModel.getStatus()));
            viewHolder.textViewUsername.setText(String.valueOf(laporanModel.getUsername()));

            viewHolder.imageViewRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    peminjamanModelArrayList.remove(holder.getAdapterPosition());
                    id_buku = Long.parseLong(viewHolder.textViewIdBuku.getText().toString());
                    id_user = Long.parseLong(viewHolder.textViewIdUser.getText().toString());
//                    peminjamanHandler.deletePeminjaman(id_buku, id_user);
                    Toast.makeText(context, "delete successfully", Toast.LENGTH_SHORT).show();
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });

//            viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    id_buku = Long.parseLong(viewHolder.textViewIdBuku.getText().toString());
//                    intent = new Intent(context, AddUlasan.class);
//                    intent.putExtra("key_id_user", id_user);
//                    intent.putExtra("key_username", username);
//                    intent.putExtra("key_id_buku", id_buku);
//                    context.startActivity(intent);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                }
//            });
        }
    }


    @Override
    public int getItemCount() {
        return laporanModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIdPeminjaman, textViewIdBuku, textViewIdUser, textViewJudul, textViewPenulis, textViewPenerbit, textViewKategori, textViewTanggalAwal, textViewTanggalAkhir, textViewStatus, textViewUsername;
        ImageView imageViewRemove;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIdPeminjaman = itemView.findViewById(R.id.textViewIdPeminjaman);
            textViewIdUser = itemView.findViewById(R.id.TextViewIdUser);
            textViewIdBuku = itemView.findViewById(R.id.textViewIdBuku);
            textViewJudul = itemView.findViewById(R.id.textViewJudul);
            textViewPenulis = itemView.findViewById(R.id.textViewJudul);
            textViewPenerbit = itemView.findViewById(R.id.textViewPenerbit);
            textViewTanggalAwal = itemView.findViewById(R.id.textViewAwal);
            textViewTanggalAkhir = itemView.findViewById(R.id.textViewAkhir);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewKategori = itemView.findViewById(R.id.textViewKategori);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            imageViewRemove = itemView.findViewById(R.id.imageViewRemove);
            constraintLayout = itemView.findViewById(R.id.cvData);
        }
    }
}
