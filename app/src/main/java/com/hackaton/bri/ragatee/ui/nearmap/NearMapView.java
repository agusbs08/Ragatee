package com.hackaton.bri.ragatee.ui.nearmap;

import com.hackaton.bri.ragatee.base.BaseView;
import com.hackaton.bri.ragatee.model.ProdukWithMap;

import java.util.List;

public interface NearMapView extends BaseView {
    void onSuccessGetListCrowFunderLocation(List<ProdukWithMap> listProdukWithMap);
}
