package com.hackaton.bri.ragatee.ui.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.model.Report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DailyReportRecyclerViewAdapter extends RecyclerView.Adapter<DailyReportRecyclerViewAdapter.DailyReportViewHolder> {

    private ArrayList<Report> dailyReports;

    public DailyReportRecyclerViewAdapter(ArrayList<Report> dailyReports) {
        this.dailyReports = dailyReports;
    }

    @NonNull
    @Override
    public DailyReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DailyReportViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DailyReportViewHolder holder, int position) {
        holder.bind(dailyReports.get(position));
    }

    @Override
    public int getItemCount() {
        return dailyReports.size();
    }

    class DailyReportViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private TextView tvTanggal, tvJudul, tvDeskripsiReport;
        private ImageView ivReport;

        public DailyReportViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponent(itemView);
        }

        private void initComponent(View view) {
            context = view.getContext();
            tvTanggal = view.findViewById(R.id.tv_tanggal_report);
            tvJudul = view.findViewById(R.id.tv_judul_report);
            tvDeskripsiReport = view.findViewById(R.id.tv_deskripsi_report);
            ivReport = view.findViewById(R.id.iv_report);
        }

        void bind(Report report) {
            Date date = new Date(report.getTime());
            String str = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(date);
            tvTanggal.setText(str);
            tvJudul.setText(report.getTitle());
            tvDeskripsiReport.setText(report.getDescription());
            Glide.with(context).load(report.getImage()).into(ivReport);
        }
    }
}
