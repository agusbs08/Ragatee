package com.hackaton.bri.ragatee.ui.list;

import com.hackaton.bri.ragatee.base.BaseView;
import com.hackaton.bri.ragatee.model.ProdukWithMap;

import java.util.List;

public interface ListCrowFundingView extends BaseView {
    void onSuccessGetListCrowFunderLocation(List<ProdukWithMap> listProdukWithMap);
}
