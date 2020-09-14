package com.hackaton.bri.ragatee.ui.register;

import com.hackaton.bri.ragatee.base.BaseView;
import com.hackaton.bri.ragatee.model.User;

public interface RegisterView extends BaseView {
    void onSuccessRegistration();
    void onSuccessGetDetailUser(User user);
}
