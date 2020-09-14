package com.hackaton.bri.ragatee.ui.home.fragmentcart;

import com.hackaton.bri.ragatee.model.Cart;
import com.hackaton.bri.ragatee.model.User;
import com.hackaton.bri.ragatee.model.request.Payment;
import com.hackaton.bri.ragatee.networking.MyClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenter {

    private CartView view;

    public CartPresenter(CartView view) {
        this.view = view;
    }

    public void getListCart(String userId) {
        view.onShowProgressDialog();

        MyClient.getClient().getListCart(userId).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                view.onHideProgressDialog();
                if(response.isSuccessful()) {
                    view.onSuccessGetListCart(response.body());
                } else {
                    view.onFailureMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                view.onHideProgressDialog();
                view.onFailureMessage("Terjadi Kesalahan pada Jaringan");
            }
        });
    }

    public void investPayment(String userId) {
        view.onShowProgressDialog();

        MyClient.getClient().payment(new Payment(userId)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    view.onSuccessInvestPayment();
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

    public void getDetailUser(String userId) {
        MyClient.getClient().getDetailUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                view.onHideProgressDialog();
                if(response.isSuccessful()){
                    view.onSuccessGetDetailUser(response.body());
                } else {
                    view.onFailureMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.onHideProgressDialog();
                view.onFailureMessage("Terjadi Kesalahan pada Jaringan");
            }
        });
    }
}
