package com.hackaton.bri.ragatee.ui.home.fragmenthome;

import android.util.Log;

import com.google.gson.Gson;
import com.hackaton.bri.ragatee.model.Produk;
import com.hackaton.bri.ragatee.networking.MyClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {

    private HomeView view;

    public HomePresenter(HomeView view) {
        this.view = view;
    }

    public void getListProduk(String auth) {
        view.onShowProgressDialog();

        MyClient.getClient().getListProduk("").enqueue(new Callback<List<Produk>>() {
            @Override
            public void onResponse(Call<List<Produk>> call, Response<List<Produk>> response) {
                view.onHideProgressDialog();
                if(response.isSuccessful()) {
                    view.onSuccessGetListProduct(response.body());
                } else {
                    view.onFailureMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Produk>> call, Throwable t) {
                view.onHideProgressDialog();
                view.onFailureMessage("Terjadi Kesalahan Jaringan");
            }
        });
    }
}
