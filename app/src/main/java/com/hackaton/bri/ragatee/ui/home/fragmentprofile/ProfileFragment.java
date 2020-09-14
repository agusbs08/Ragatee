package com.hackaton.bri.ragatee.ui.home.fragmentprofile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.base.BaseFragment;
import com.hackaton.bri.ragatee.helper.CircleImageView;
import com.hackaton.bri.ragatee.model.Transaksi;
import com.hackaton.bri.ragatee.model.User;
import com.hackaton.bri.ragatee.ui.register.RegisterActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class ProfileFragment extends BaseFragment implements ProfileView {

    private CircleImageView ivProfile;
    private ImageView ivKtp;
    private TextView tvNama, tvid, tvKtp, tvNpwp, tvPhone, tvLokasi, tvRegistration, tvBalance;
    private RecyclerView rvPortofolio;

    private ProfileRecyclerViewAdapter adapter;
    private ProfilePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view);
        initData();
        setActionComponent();
        presenter.getListTransaksi(mAppPreference.getUserLogin().getId());
    }

    private void initComponent(View view) {
        ivProfile = view.findViewById(R.id.iv_profile);
        ivKtp = view.findViewById(R.id.iv_ktp);
        tvNama = view.findViewById(R.id.tv_nama);
        tvid = view.findViewById(R.id.tv_id);
        tvKtp = view.findViewById(R.id.tv_ktp);
        tvNpwp = view.findViewById(R.id.tv_npwp);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvLokasi = view.findViewById(R.id.tv_location);
        tvBalance = view.findViewById(R.id.tv_balance);
        tvRegistration = view.findViewById(R.id.tv_registration);
        rvPortofolio = view.findViewById(R.id.rv_portofolio);
        presenter = new ProfilePresenter(this);
    }

    private void initData() {
        User user = mAppPreference.getUserLogin();
        if(user != null) {
            Glide.with(getContext()).load(user.getProfilepicture()).into(ivProfile);
            tvNama.setText(user.getName());
            tvid.setText(user.getId());
            if(user.getKtpno() != null) {
                tvKtp.setText(user.getKtpno());
                tvNpwp.setText(user.getNpwpno());
                tvPhone.setText(user.getPhone());
                tvLokasi.setText(user.getAddress());
                tvRegistration.setVisibility(View.GONE);

                DecimalFormat df= new DecimalFormat("#,###");
                df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
                Log.d("User Data Njing", new Gson().toJson(user));
                tvBalance.setText("Rp " + df.format(Double.parseDouble(user.getAccountbalance())));
            }
        }
    }

    private void setActionComponent() {
        tvRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(RegisterActivity.class);
            }
        });
    }

    @Override
    public void onSuccessGetTransaksi(List<Transaksi> listTransaksi) {
        adapter = new ProfileRecyclerViewAdapter(listTransaksi);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvPortofolio.setLayoutManager(layoutManager);
        rvPortofolio.setAdapter(adapter);
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
}
