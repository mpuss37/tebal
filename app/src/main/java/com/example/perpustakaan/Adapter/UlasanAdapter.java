package com.example.perpustakaan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.perpustakaan.Handler.UlasanHandler;
import com.example.perpustakaan.Model.UlasanModel;
import com.example.perpustakaan.R;

import java.util.ArrayList;

public class UlasanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<UlasanModel> ulasanModelArrayList;
    UlasanHandler ulasanHandler;
    Bundle bundle;
    Intent intent;
    Context context;
    long id_ulasan, id_user;
    String ulasan, rating;

    public UlasanAdapter(ArrayList<UlasanModel> ulasanModelArrayList, Context context, long id_user, long id_buku) {
        this.ulasanModelArrayList = ulasanModelArrayList;
        this.context = context;
        this.id_user = id_user;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ulasan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UlasanModel ulasanModel = ulasanModelArrayList.get(position);
        ulasanHandler = new UlasanHandler(context);
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.textViewIdUlasan.setText(String.valueOf(ulasanModel.getId_ulasan()));
            viewHolder.textViewIdUser.setText(String.valueOf(ulasanModel.getUsername()));
            viewHolder.textViewUlasan.setText(String.valueOf(ulasanModel.getUlasan()));
            viewHolder.textViewRating.setText(String.valueOf(ulasanModel.getRating()));

            viewHolder.imageViewRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ulasanModelArrayList.remove(holder.getAdapterPosition());
                    id_ulasan = Long.parseLong(viewHolder.textViewIdUlasan.getText().toString());
                    ulasanHandler.deleteUlasan(id_ulasan);
                    Toast.makeText(context, "deleted successfully", Toast.LENGTH_SHORT).show();
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });

            viewHolder.imageViewLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "saved to collection", Toast.LENGTH_SHORT).show();
                    int tint = Color.parseColor("#008000");
                    viewHolder.imageViewLike.setColorFilter(tint);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return ulasanModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIdUlasan, textViewIdUser, textViewJudul, textViewUlasan, textViewRating;
        ImageView imageViewLike, imageViewRemove;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIdUlasan = itemView.findViewById(R.id.textViewIdPeminjaman);
            textViewIdUser = itemView.findViewById(R.id.TextViewIdUser);
            textViewUlasan = itemView.findViewById(R.id.TextViewUlasan);
            textViewRating = itemView.findViewById(R.id.TextViewRating);
            imageViewRemove = itemView.findViewById(R.id.imageViewRemove);
            imageViewLike = itemView.findViewById(R.id.imageViewLike);
            constraintLayout = itemView.findViewById(R.id.cvData);
        }
    }
}
