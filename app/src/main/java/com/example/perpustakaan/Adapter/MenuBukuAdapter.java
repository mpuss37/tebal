package com.example.perpustakaan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpustakaan.Model.BukuModel;
import com.example.perpustakaan.R;

import java.util.ArrayList;

public class MenuBukuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<BukuModel> bukuModelArrayList;
    private Context context;

    public MenuBukuAdapter(ArrayList<BukuModel> bukuModelArrayList, Context context) {
        this.bukuModelArrayList = bukuModelArrayList;
        this.context = context;
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
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.textViewIdBuku.setText(String.valueOf(bukuModel.getId_buku()));
            viewHolder.textViewJudul.setText(String.valueOf(bukuModel.getJudul()));
            viewHolder.textViewPenulis.setText(String.valueOf(bukuModel.getPenulis()));
            viewHolder.textViewPenerbit.setText(String.valueOf(bukuModel.getPenerbit()));
            viewHolder.textViewTahunTerbit.setText(String.valueOf(bukuModel.getTahunterbit()));
        }
    }

    @Override
    public int getItemCount() {
        return bukuModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIdBuku, textViewJudul, textViewPenulis, textViewPenerbit, textViewTahunTerbit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIdBuku = itemView.findViewById(R.id.textViewIdBuku);
            textViewJudul = itemView.findViewById(R.id.textViewJudul);
            textViewPenulis = itemView.findViewById(R.id.textViewPenulis);
            textViewPenerbit = itemView.findViewById(R.id.textViewPenerbit);
            textViewTahunTerbit = itemView.findViewById(R.id.TextViewTahunTerbit);
        }
    }
}
