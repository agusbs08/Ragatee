package com.hackaton.bri.ragatee.ui.rating;

import com.hackaton.bri.ragatee.base.BaseView;
import com.hackaton.bri.ragatee.model.Produk;

import java.util.List;

public interface ListProdukRatingView extends BaseView {
    void onSuccessGetListCrowFunderLocation(List<Produk> listProduk);
}
