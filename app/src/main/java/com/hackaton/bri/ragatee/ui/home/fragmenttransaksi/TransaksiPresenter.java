package com.hackaton.bri.ragatee.ui.home.fragmenttransaksi;

import com.hackaton.bri.ragatee.model.Transaksi;
import com.hackaton.bri.ragatee.networking.MyClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiPresenter {

    private TransaksiView view;

    public TransaksiPresenter(TransaksiView view) {
        this.view = view;
    }

    public void getListTransaksi(String userId) {
        view.onShowProgressDialog();

        MyClient.getClient().getListTransaksi(userId).enqueue(new Callback<List<Transaksi>>() {
            @Override
            public void onResponse(Call<List<Transaksi>> call, Response<List<Transaksi>> response) {
                view.onHideProgressDialog();
                if (response.isSuccessful()) {
                    view.onSuccessGetListTransaksi(response.body());
                } else {
                    view.onFailureMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Transaksi>> call, Throwable t) {
                view.onHideProgressDialog();
                view.onFailureMessage("Terjadi Kesalahan pada Jaringan");
            }
        });
    }
}
