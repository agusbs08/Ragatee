package com.hackaton.bri.ragatee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {

    @Expose
    @SerializedName("saldo_akhir_mutasi")
    private String saldoAkhirMutasi;
    @Expose
    @SerializedName("saldo_awal_mutasi")
    private String saldoAwalMutasi;
    @Expose
    @SerializedName("kode_tran")
    private String kodeTran;
    @Expose
    @SerializedName("ket_tran")
    private String ketTran;
    @Expose
    @SerializedName("mutasi_kredit")
    private String mutasiKredit;
    @Expose
    @SerializedName("mutasi_debet")
    private String mutasiDebet;
    @Expose
    @SerializedName("posisi_neraca")
    private String posisiNeraca;
    @Expose
    @SerializedName("tanggal_tran")
    private String tanggalTran;
    @Expose
    @SerializedName("_id")
    private String Id;

    public String getSaldoAkhirMutasi() {
        return saldoAkhirMutasi;
    }

    public String getSaldoAwalMutasi() {
        return saldoAwalMutasi;
    }

    public String getKodeTran() {
        return kodeTran;
    }

    public String getKetTran() {
        return ketTran;
    }

    public String getMutasiKredit() {
        return mutasiKredit;
    }

    public String getMutasiDebet() {
        return mutasiDebet;
    }

    public String getPosisiNeraca() {
        return posisiNeraca;
    }

    public String getTanggalTran() {
        return tanggalTran;
    }

    public String getId() {
        return Id;
    }
}
