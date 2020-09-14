package com.hackaton.bri.ragatee.ui.home.fragmenthome;

import com.hackaton.bri.ragatee.base.BaseView;
import com.hackaton.bri.ragatee.model.Produk;

import java.util.List;

public interface HomeView extends BaseView {
    void onSuccessGetListProduct(List<Produk> listProduk);
}
