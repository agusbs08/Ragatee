package com.hackaton.bri.ragatee.ui.home.fragmenttransaksi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.model.Transaksi;
import com.hackaton.bri.ragatee.ui.detail.DetailActivity;
import com.hackaton.bri.ragatee.ui.report.DailyReportActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class TransaksiRecyclerViewAdapter extends RecyclerView.Adapter<TransaksiRecyclerViewAdapter.TransaksiViewHolder> {

    private List<Transaksi> listTransaksi;

    public TransaksiRecyclerViewAdapter(List<Transaksi> listTransaksi) {
        this.listTransaksi = listTransaksi;
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransaksiViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiViewHolder holder, int position) {
        holder.bind(listTransaksi.get(position));
    }

    @Override
    public int getItemCount() {
        return listTransaksi.size();
    }

    class TransaksiViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private ConstraintLayout constraintImage;
        private TextView tvJudulProduk, tvJumlahPendanaan, tvStatus, tvDailyReport;

        public TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponent(itemView);
        }

        private void initComponent(View view) {
            context = view.getContext();
            constraintImage = view.findViewById(R.id.linear_image_transaction);
            tvJudulProduk = view.findViewById(R.id.tv_judul_produk);
            tvJumlahPendanaan = view.findViewById(R.id.tv_jumlah_pendanaan);
            tvStatus = view.findViewById(R.id.tv_status);
            tvDailyReport = view.findViewById(R.id.tv_daily_report);
        }

        void bind(final Transaksi transaksi) {
            Glide.with(context).asBitmap().load(transaksi.getImage()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    BitmapDrawable drawable = new BitmapDrawable(context.getResources(), resource);
                    constraintImage.setBackground(drawable);
                }
            });

            DecimalFormat df= new DecimalFormat("#,###");
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
            tvJumlahPendanaan.setText("Rp " + df.format(transaksi.getFund()));
            tvJudulProduk.setText(transaksi.getTitle());
            tvStatus.setText(transaksi.getFundingstatus());

            tvDailyReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DailyReportActivity.class);
                    intent.putExtra("daily_reports", transaksi.getDailyReports());
                    context.startActivity(intent);
                }
            });
        }
    }
}
