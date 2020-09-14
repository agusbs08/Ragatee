package com.hackaton.bri.ragatee.ui.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.base.BaseActivity;
import com.hackaton.bri.ragatee.model.DetailProduk;
import com.hackaton.bri.ragatee.model.History;

import java.util.List;

public class HistoryActivity extends BaseActivity implements HistoryView {


    private RecyclerView rvList;
    private HistoryPresenter presenter;
    private String mitraId, crowfundingId;
    private HistoryRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initData();
        initComponent();
        presenter.getDetailProduk(crowfundingId);
        //presenter.getListHistory(mitraId);
    }

    private void initData() {
        mitraId = getIntent().getStringExtra("mitra_id");
        crowfundingId = getIntent().getStringExtra("crowfunding_id");
    }

    private void initComponent() {
        rvList = findViewById(R.id.rv_list);
        presenter = new HistoryPresenter(this);
    }

    @Override
    public void onSuccessGetDetailProduk(DetailProduk detailProduk) {
        presenter.getListHistory(detailProduk.getCampaigner());
    }

    @Override
    public void onSuccessGetListHistory(List<History> listHistory) {
        adapter = new HistoryRecyclerViewAdapter(listHistory);
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
