package com.example.perpustakaan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpustakaan.Handler.BukuHandler;
import com.example.perpustakaan.Handler.KoleksiHandler;
import com.example.perpustakaan.Model.BukuModel;
import com.example.perpustakaan.R;
import com.example.perpustakaan.View.AddBuku;

import java.util.ArrayList;

public class BukuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<BukuModel> bukuModelArrayList;
    BukuHandler bukuHandler;
    KoleksiHandler koleksiHandler;
    Bundle bundle;
    Intent intent;
    Context context;
    AddBuku addBuku;
    int id;
    String id_buku, id_user, judul, penulis, penerbit, tahunterbit, username;

    public BukuAdapter(ArrayList<BukuModel> bukuModelArrayList, Context context, String username) {
        this.bukuModelArrayList = bukuModelArrayList;
        this.context = context;
        this.username = username;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buku, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BukuModel bukuModel = bukuModelArrayList.get(position);
        bukuHandler = new BukuHandler(context);
        koleksiHandler = new KoleksiHandler(context);
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            Toast.makeText(context, "welkam bek " + username, Toast.LENGTH_SHORT).show();
            if (username.equals("admin") || equals("petugas")) {
                viewHolder.imageViewBorrow.setVisibility(View.GONE);
            }
            viewHolder.textViewIdBuku.setText(String.valueOf(bukuModel.getId_buku()));
            viewHolder.textViewJudul.setText(String.valueOf(bukuModel.getJudul()));
            viewHolder.textViewPenulis.setText(String.valueOf(bukuModel.getPenulis()));
            viewHolder.textViewPenerbit.setText(String.valueOf(bukuModel.getPenerbit()));
            viewHolder.textViewTahunTerbit.setText(String.valueOf(bukuModel.getTahunterbit()));

            viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id_buku = viewHolder.textViewIdBuku.getText().toString();
                    id_user = viewHolder.textViewIdUser.getText().toString();
                    judul = viewHolder.textViewJudul.getText().toString();
                    penulis = viewHolder.textViewPenulis.getText().toString();
                    penerbit = viewHolder.textViewPenerbit.getText().toString();
                    tahunterbit = viewHolder.textViewTahunTerbit.getText().toString();
                    Toast.makeText(context, "edit buku", Toast.LENGTH_SHORT).show();
                    intent = new Intent(context, AddBuku.class);
                    intent.putExtra("key_judul", judul);
                    intent.putExtra("key_penulis", penulis);
                    intent.putExtra("key_penerbit", penerbit);
                    intent.putExtra("key_tahun_terbit", tahunterbit);
                    intent.putExtra("key_username", username);
                    context.startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
            });

            viewHolder.imageViewBorrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    koleksiHandler.insertKoleksi();
                }
            });

            viewHolder.imageViewRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bukuModelArrayList.remove(holder.getAdapterPosition());
                    id_buku = viewHolder.textViewIdBuku.getText().toString();
                    judul = viewHolder.textViewJudul.getText().toString();
                    bukuHandler.deleteBuku(id_buku);
                    Toast.makeText(context, "delete, Username : " + judul, Toast.LENGTH_SHORT).show();
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return bukuModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIdBuku, textViewIdUser, textViewJudul, textViewPenulis, textViewPenerbit, textViewTahunTerbit, textViewKategori;
        ImageView imageViewRemove, imageViewBorrow;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIdUser = itemView.findViewById(R.id.textViewIdUser);
            textViewIdBuku = itemView.findViewById(R.id.textViewIdBuku);
            textViewJudul = itemView.findViewById(R.id.textViewJudul);
            textViewPenulis = itemView.findViewById(R.id.textViewPenulis);
            textViewPenerbit = itemView.findViewById(R.id.textViewPenerbit);
            textViewTahunTerbit = itemView.findViewById(R.id.TextViewTahunTerbit);
            imageViewRemove = itemView.findViewById(R.id.imageViewRemove);
            imageViewBorrow = itemView.findViewById(R.id.imageViewBorrow);
            constraintLayout = itemView.findViewById(R.id.cvData);
        }
    }

    public void setFilter(ArrayList<BukuModel> filter) {
        bukuModelArrayList = new ArrayList<>();
        bukuModelArrayList.addAll(filter);
        notifyDataSetChanged();
    }
}
