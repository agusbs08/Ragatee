package com.hackaton.bri.ragatee.ui.home.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.model.Produk;
import com.hackaton.bri.ragatee.ui.detail.DetailActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder> {

    private List<Produk> listProduk;

    public HomeRecyclerViewAdapter(List<Produk> listProduk) {
        this.listProduk = listProduk;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.bind(listProduk.get(position));
    }

    @Override
    public int getItemCount() {
        return listProduk.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {

        private TextView tvJudul, tvKebutuhanPendanaan, tvTerdanaiPersen;
        private ConstraintLayout constraintLayout;
        private ProgressBar pbPendanaan;

        private Context context;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponent(itemView);
        }

        private void initComponent(View view) {
            context = view.getContext();
            tvJudul = view.findViewById(R.id.tv_judul_produk);
            tvKebutuhanPendanaan = view.findViewById(R.id.tv_kebutuhan_pendanaan);
            constraintLayout = view.findViewById(R.id.constraint_img_product);
            tvTerdanaiPersen = view.findViewById(R.id.tv_terdanai_persen);
            pbPendanaan = view.findViewById(R.id.pb_pendanaan);
        }

        public void bind(final Produk produk) {
            tvJudul.setText(produk.getTitle());

            DecimalFormat df= new DecimalFormat("#,###");
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
            tvKebutuhanPendanaan.setText("Rp " + df.format(produk.getFundneeded()));

            Glide.with(context)
                    .asBitmap()
                    .load(produk.getImage())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            BitmapDrawable drawable = new BitmapDrawable(context.getResources(), resource);
                            constraintLayout.setBackground(drawable);
                        }
                    });

            Double percentDbl = produk.getFundingprogresspercentage();
            pbPendanaan.setProgress(percentDbl.intValue());
            tvTerdanaiPersen.setText(percentDbl.intValue() + "%");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("id_produk", produk.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
