package com.hackaton.bri.ragatee.ui.history;

import com.hackaton.bri.ragatee.model.DetailProduk;
import com.hackaton.bri.ragatee.model.History;
import com.hackaton.bri.ragatee.networking.MyClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPresenter {

    private HistoryView view;

    public HistoryPresenter(HistoryView view) {
        this.view = view;
    }

    public void getDetailProduk(String id) {
        view.onShowProgressDialog();

        MyClient.getClient().getDetailProduk(id).enqueue(new Callback<DetailProduk>() {
            @Override
            public void onResponse(Call<DetailProduk> call, Response<DetailProduk> response) {
                if(response.isSuccessful()) {
                    view.onSuccessGetDetailProduk(response.body());
                } else {
                    view.onHideProgressDialog();
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

    public void getListHistory(String mitraId) {
        MyClient.getClient().getListHistory(mitraId).enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                view.onHideProgressDialog();
                if(response.isSuccessful()) {
                    view.onSuccessGetListHistory(response.body());
                } else {
                    view.onFailureMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                view.onHideProgressDialog();
                view.onFailureMessage("Terjadi Kesalahan pada Jaringan");
            }
        });
    }
}
