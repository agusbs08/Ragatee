package com.hackaton.bri.ragatee.ui.login;

import com.google.firebase.auth.FirebaseUser;
import com.hackaton.bri.ragatee.base.BaseView;
import com.hackaton.bri.ragatee.model.User;

public interface LoginView extends BaseView {
    void onSuccessGoogleAuth(FirebaseUser user);
    void onSuccessAuthentication(User user);
}

