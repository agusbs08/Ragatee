package com.hackaton.bri.ragatee.ui.detail;

import com.hackaton.bri.ragatee.model.DetailProduk;
import com.hackaton.bri.ragatee.model.Produk;
import com.hackaton.bri.ragatee.model.request.CartRequest;
import com.hackaton.bri.ragatee.model.request.Payment;
import com.hackaton.bri.ragatee.networking.MyClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter {

    private DetailView view;

    public DetailPresenter(DetailView view) {
        this.view = view;
    }

    public void getDetailProduk(String id, String auth) {
        view.onShowProgressDialog();

        MyClient.getClient().getDetailProduk(id).enqueue(new Callback<DetailProduk>() {
            @Override
            public void onResponse(Call<DetailProduk> call, Response<DetailProduk> response) {
                view.onHideProgressDialog();
                if (response.isSuccessful()) {
                    view.onSuccessGetDetailProduk(response.body());
                } else {
                    view.onFailureMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<DetailProduk> call, Throwable t) {
                view.onHideProgressDialog();
                view.onFailureMessage("Terjadi Kesalahan pada Jaringan");
            }
        });
    }

    public void addToCart(final CartRequest request) {
        view.onShowProgressDialog();

        MyClient.getClient().addToCart(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view.onHideProgressDialog();
                if(response.isSuccessful()) {
                    view.onSuccessAddToCart();
                } else {
                    view.onFailureMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.onHideProgressDialog();
                view.onFailureMessage("Terjadi Kesalahan pada Jaringan");
            }
        });
    }
}
