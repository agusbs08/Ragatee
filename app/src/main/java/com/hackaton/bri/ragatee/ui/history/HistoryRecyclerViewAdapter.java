package com.hackaton.bri.ragatee.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.model.History;

import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.HistoryViewHolder> {

    private List<History> listHistory;

    public HistoryRecyclerViewAdapter(List<History> listHistory) {
        this.listHistory = listHistory;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(listHistory.get(position));
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTanggal, tvPosisiNeraca, tvMutasiDebit, tvMutasiKredit, tvKeteranganTransaksi,
        tvKodeTransaksi, tvSaldoAwalMutasi, tvSaldoAkhirMutasi;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponent(itemView);
        }

        private void initComponent(View view) {
            tvTanggal = view.findViewById(R.id.tv_tanggal);
            tvPosisiNeraca = view.findViewById(R.id.tv_posisi_neraca);
            tvMutasiDebit = view.findViewById(R.id.tv_mutasi_debit);
            tvMutasiKredit = view.findViewById(R.id.tv_mutasi_kredit);
            tvKeteranganTransaksi = view.findViewById(R.id.tv_keterangan);
            tvKodeTransaksi = view.findViewById(R.id.tv_kode_transaksi);
            tvSaldoAwalMutasi = view.findViewById(R.id.tv_saldo_awal_mutasi);
            tvSaldoAkhirMutasi = view.findViewById(R.id.tv_saldo_akhir_mutasi);
        }

        void bind(History history) {
            tvTanggal.setText(history.getTanggalTran());
            tvPosisiNeraca.setText(history.getPosisiNeraca());
            tvMutasiDebit.setText(history.getMutasiDebet());
            tvMutasiKredit.setText(history.getMutasiKredit());
            tvKeteranganTransaksi.setText(history.getKetTran());
            tvKodeTransaksi.setText(history.getKodeTran());
            tvSaldoAwalMutasi.setText(history.getSaldoAwalMutasi());
            tvSaldoAkhirMutasi.setText(history.getSaldoAkhirMutasi());
        }
    }
}