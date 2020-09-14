package com.hackaton.bri.ragatee.ui.detail;

import com.hackaton.bri.ragatee.base.BaseView;
import com.hackaton.bri.ragatee.model.DetailProduk;

public interface DetailView extends BaseView {
    void onSuccessGetDetailProduk(DetailProduk detailProduk);
    void onSuccessAddToCart();
}
