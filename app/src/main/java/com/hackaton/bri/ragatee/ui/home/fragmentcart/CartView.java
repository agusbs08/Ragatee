package com.hackaton.bri.ragatee.ui.home.fragmentcart;

import com.hackaton.bri.ragatee.base.BaseView;
import com.hackaton.bri.ragatee.model.Cart;
import com.hackaton.bri.ragatee.model.User;

import java.util.List;

public interface CartView extends BaseView {
    void onSuccessGetListCart(List<Cart> listCart);
    void onSuccessInvestPayment();
    void onSuccessGetDetailUser(User user);
}
