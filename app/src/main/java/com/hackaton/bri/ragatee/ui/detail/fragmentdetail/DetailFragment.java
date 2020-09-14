package com.hackaton.bri.ragatee.ui.detail.fragmentdetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.model.DetailProduk;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DetailFragment extends Fragment {

    private TextView tvHarga, tvLokasi, tvEstimasiROI, tvPeriodeKontrak, tvProfit, tvPeriode;
    private DetailProduk detailProduk;

    public DetailFragment() { }

    public DetailFragment(DetailProduk detailProduk) {
        this.detailProduk = detailProduk;
    }

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view);
        initData();
    }

    private void initComponent(View view) {
        tvHarga = view.findViewById(R.id.tv_harga);
        tvLokasi = view.findViewById(R.id.tv_lokasi);
        tvEstimasiROI = view.findViewById(R.id.tv_estimasi_roi);
        tvPeriodeKontrak = view.findViewById(R.id.tv_periode_kontrak);
        tvProfit = view.findViewById(R.id.tv_profit);
        tvPeriode = view.findViewById(R.id.tv_periode);
    }

    private void initData() {
        Log.d("DetailProduk", new Gson().toJson(detailProduk));
        if(detailProduk != null) {
            DecimalFormat df= new DecimalFormat("#,##0.00");
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
            tvHarga.setText("Rp " + df.format(detailProduk.getFundneeded()));
            tvLokasi.setText("Surabaya, Jawa Timur");
            tvEstimasiROI.setText(detailProduk.getROI() + "%");
            tvPeriode.setText("1 Tahun");
            tvProfit.setText(detailProduk.getROI() + "%");
            tvPeriodeKontrak.setText("2 Tahun");
        }
    }
}
