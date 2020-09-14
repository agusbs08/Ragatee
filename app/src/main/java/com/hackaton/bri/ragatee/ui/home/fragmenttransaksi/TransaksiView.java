package com.hackaton.bri.ragatee.ui.home.fragmenttransaksi;

import com.hackaton.bri.ragatee.base.BaseView;
import com.hackaton.bri.ragatee.model.Transaksi;

import java.util.List;

public interface TransaksiView extends BaseView {
    void onSuccessGetListTransaksi(List<Transaksi> listTransaksi);
}
