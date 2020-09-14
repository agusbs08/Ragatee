package com.hackaton.bri.ragatee.ui.list;

import com.hackaton.bri.ragatee.model.ProdukWithMap;
import com.hackaton.bri.ragatee.networking.MyClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCrowFundingPresenter {

    private ListCrowFundingView view;

    public ListCrowFundingPresenter(ListCrowFundingView view) {
        this.view = view;
    }

    public void getListCrowFunderLocation(double lat, double lng, int radius) {
        view.onShowProgressDialog();

        MyClient.getClient().getListProdukWithMap(lat, lng, radius).enqueue(new Callback<List<ProdukWithMap>>() {
            @Override
            public void onResponse(Call<List<ProdukWithMap>> call, Response<List<ProdukWithMap>> response) {
                view.onHideProgressDialog();
                if(response.isSuccessful()) {
                    view.onSuccessGetListCrowFunderLocation(response.body());
                } else {
                    view.onFailureMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ProdukWithMap>> call, Throwable t) {
                view.onHideProgressDialog();
                view.onFailureMessage("Terjadi Kesalahan pada Jaringan");
            }
        });
    }
}
