package com.hackaton.bri.ragatee.ui.home.fragmentcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.base.BaseFragment;
import com.hackaton.bri.ragatee.model.Cart;
import com.hackaton.bri.ragatee.model.User;
import com.hackaton.bri.ragatee.ui.home.HomeActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartFragment extends BaseFragment implements CartView, CartDeleteOnFragmentView {

    private RecyclerView rvCart;
    private TextView tvSubtotal, tvTotal;
    private Button btnCheckout;
    private AlertDialog.Builder dialogInvest;
    private View dialogInvestView;

    private CartRecyclerViewAdapter adapter;
    private CartPresenter presenter;

    private int totalFund;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view);
        setActionComponent();
        presenter.getListCart(mAppPreference.getUserLogin().getId());
        //setDummy();
    }

    private void setDummy() {
        Cart cart = new Cart(100, 10, 1000, "Pemberdayaan Ikan LALA", "https://static.growpal.co.id/uploads/galleryListing-Gallery1575443299921-1-17680Cropped.jpg", "cartItemId");
        List<Cart> listCart = new ArrayList<>();

        for(int i=0; i<3; i++) {
            listCart.add(cart);
        }

        onSuccessGetListCart(listCart);
    }

    private void initComponent(View view) {
        rvCart = view.findViewById(R.id.rv_cart);
        tvSubtotal = view.findViewById(R.id.tv_subtotal_pendanaan);
        tvTotal = view.findViewById(R.id.tv_total_pendanaan);
        btnCheckout = view.findViewById(R.id.btn_checkout);
        presenter = new CartPresenter(this);
    }

    private void setActionComponent() {
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Double balanceDouble = Double.parseDouble(mAppPreference.getUserLogin().getAccountbalance());
                    int balance = balanceDouble.intValue();
                    if(balance < totalFund) {
                        onMessage("Saldo Anda Tidak Mencukupi");
                    } else {
                        showDialogInvest();
                    }
                } catch (Exception e) {
                    onMessage("Isi Kelengkapan Data Anda Terlebih Dahulu");
                }
            }
        });
    }

    private void showDialogInvest() {
        dialogInvest = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        dialogInvestView = inflater.inflate(R.layout.dialog_invest, null);
        dialogInvest.setView(dialogInvestView);
        dialogInvest.setCancelable(true);

        TextView tvJumlahPendanaan = dialogInvestView.findViewById(R.id.tv_jumlah_pendanaan);
        TextView tvInvest = dialogInvestView.findViewById(R.id.tv_invest);
        TextView tvCancel = dialogInvestView.findViewById(R.id.tv_calcel);

        final AlertDialog show = dialogInvest.show();

        DecimalFormat df= new DecimalFormat("#,###");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
        tvJumlahPendanaan.setText("Rp " + df.format(totalFund));

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.cancel();
            }
        });

        tvInvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.cancel();
                presenter.investPayment(mAppPreference.getUserLogin().getId());
            }
        });

    }

    @Override
    public void onSuccessGetListCart(List<Cart> listCart) {
        adapter = new CartRecyclerViewAdapter(listCart, this, mAppPreference.getUserLogin().getId());
        rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCart.setAdapter(adapter);

        if(listCart != null) {
            for(Cart cart : listCart) {
                totalFund += cart.getFund();
            }
        }

        updateFunding();
    }

    @Override
    public void onSuccessInvestPayment() {
        presenter.getDetailUser(mAppPreference.getUserLogin().getId());
    }

    @Override
    public void onSuccessGetDetailUser(User user) {
        mAppPreference.setUserLogin(user);
        onMessage("Payment Produk Anda Sukses");
        launchActivity(HomeActivity.class);
    }

    private void updateFunding() {
        DecimalFormat df= new DecimalFormat("#,###");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
        tvSubtotal.setText(df.format(totalFund));
        tvTotal.setText(df.format(totalFund));
    }

    @Override
    public void onFailureMessage(String message) {
        onMessage(message);
    }

    @Override
    public void onShowProgressDialog() {
        showProgressDialog();
    }

    @Override
    public void onHideProgressDialog() {
        hideProgressDialog();
    }

    @Override
    public void onSuccessDeleteCart(int fundDeleted) {
        totalFund -= fundDeleted;
        updateFunding();
    }

    @Override
    public void onFailureDeleteMessage(String message) {
        onMessage(message);
    }
}
