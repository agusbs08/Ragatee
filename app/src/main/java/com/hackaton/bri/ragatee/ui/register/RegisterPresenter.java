package com.hackaton.bri.ragatee.ui.register;

import com.hackaton.bri.ragatee.model.User;
import com.hackaton.bri.ragatee.model.request.RegistrationRequest;
import com.hackaton.bri.ragatee.networking.MyClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {

    private RegisterView view;

    public RegisterPresenter(RegisterView view) {
        this.view = view;
    }

    public void registration(RegistrationRequest request) {
        view.onShowProgressDialog();

        MyClient.getClient().userRegistration(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    view.onSuccessRegistration();
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
