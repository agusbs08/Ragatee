package com.hackaton.bri.ragatee.ui.history;

import com.hackaton.bri.ragatee.base.BaseView;
import com.hackaton.bri.ragatee.model.DetailProduk;
import com.hackaton.bri.ragatee.model.History;

import java.util.List;

public interface HistoryView extends BaseView {
    void onSuccessGetDetailProduk(DetailProduk detailProduk);
    void onSuccessGetListHistory(List<History> listHistory);
}
