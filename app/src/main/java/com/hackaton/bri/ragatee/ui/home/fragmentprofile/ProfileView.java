package com.hackaton.bri.ragatee.ui.home.fragmentprofile;

import com.hackaton.bri.ragatee.base.BaseView;
import com.hackaton.bri.ragatee.model.Transaksi;

import java.util.List;

public interface ProfileView extends BaseView {
    void onSuccessGetTransaksi(List<Transaksi> listTransaksi);
}
