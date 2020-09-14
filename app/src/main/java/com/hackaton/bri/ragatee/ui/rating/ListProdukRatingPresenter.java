package com.hackaton.bri.ragatee.ui.rating;

import com.hackaton.bri.ragatee.model.Produk;
import com.hackaton.bri.ragatee.model.ProdukWithMap;
import com.hackaton.bri.ragatee.networking.MyClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProdukRatingPresenter {

    private ListProdukRatingView view;

    public ListProdukRatingPresenter(ListProdukRatingView view) {
        this.view = view;
    }

    public void getListProdukSortByRating() {
        view.onShowProgressDialog();

        MyClient.getClient().getListProdukSortRating("rating").enqueue(new Callback<List<Produk>>() {
            @Override
            public void onResponse(Call<List<Produk>> call, Response<List<Produk>> response) {
                view.onHideProgressDialog();
                if(response.isSuccessful()) {
                    view.onSuccessGetListCrowFunderLocation(response.body());
                } else {
                    view.onFailureMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Produk>> call, Throwable t) {
                view.onHideProgressDialog();
                view.onFailureMessage("Terjadi Kesalahan pada Jaringan");
            }
        });
    }

}
