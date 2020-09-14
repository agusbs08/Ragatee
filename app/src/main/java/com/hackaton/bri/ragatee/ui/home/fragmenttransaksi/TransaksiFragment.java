package com.hackaton.bri.ragatee.ui.home.fragmenttransaksi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.base.BaseFragment;
import com.hackaton.bri.ragatee.model.Transaksi;

import java.util.List;

public class TransaksiFragment extends BaseFragment implements TransaksiView {

    private RecyclerView rvTransaksi;
    private TransaksiRecyclerViewAdapter adapter;
    private TransaksiPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaksi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view);

        presenter.getListTransaksi(mAppPreference.getUserLogin().getId());
    }

    private void initComponent(View view) {
        rvTransaksi = view.findViewById(R.id.rv_transaction);
        presenter = new TransaksiPresenter(this);
    }

    @Override
    public void onSuccessGetListTransaksi(List<Transaksi> listTransaksi) {
        adapter = new TransaksiRecyclerViewAdapter(listTransaksi);
        rvTransaksi.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTransaksi.setAdapter(adapter);
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
