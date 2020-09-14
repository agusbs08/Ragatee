package com.hackaton.bri.ragatee.ui.login;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hackaton.bri.ragatee.model.User;
import com.hackaton.bri.ragatee.model.request.Login;
import com.hackaton.bri.ragatee.networking.MyClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {

    private LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount account, final FirebaseAuth auth, Activity activity) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            view.onSuccessGoogleAuth(user);
                        }
                        else{
                            view.onFailureMessage("User tidak ditemukan");
                        }
                    }
                });
    }

    public void authentication(final Login requestLogin) {
        view.onShowProgressDialog();

        MyClient.getClient().authentication(requestLogin).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                view.onHideProgressDialog();
                if(response.isSuccessful()) {
                    view.onSuccessAuthentication(response.body());
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
