package com.hackaton.bri.ragatee.ui.rating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.base.BaseActivity;
import com.hackaton.bri.ragatee.model.Produk;

import java.util.List;

public class ListProdukRatingActivity extends BaseActivity implements ListProdukRatingView {

    private RecyclerView rvList;
    private ListProdukRatingRecyclerView adapter;
    private ListProdukRatingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produk_rating);
        initComponent();
        presenter.getListProdukSortByRating();
    }

    private void initComponent() {
        rvList = findViewById(R.id.rv_list);
        presenter = new ListProdukRatingPresenter(this);
    }

    @Override
    public void onSuccessGetListCrowFunderLocation(List<Produk> listProduk) {
        adapter = new ListProdukRatingRecyclerView(listProduk);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(adapter);
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
