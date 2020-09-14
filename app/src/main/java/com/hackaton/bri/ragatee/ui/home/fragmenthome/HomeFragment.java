package com.hackaton.bri.ragatee.ui.home.fragmenthome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.base.BaseFragment;
import com.hackaton.bri.ragatee.model.Images;
import com.hackaton.bri.ragatee.model.DetailProduk;
import com.hackaton.bri.ragatee.model.Produk;
import com.hackaton.bri.ragatee.ui.list.ListCrowFundingActivity;
import com.hackaton.bri.ragatee.ui.nearmap.NearMapActivity;
import com.hackaton.bri.ragatee.ui.rating.ListProdukRatingActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements HomeView{

    private RecyclerView rvTersedia, rvTerpenuhi;
    private LinearLayout linearRating, linearList, linearNearMap;
    private HomePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view);
        setActionComponent();
        //setWithDummyData();

        presenter.getListProduk("auth");
    }

    private void setWithDummyData() {
        ArrayList<Produk> listProduk = getDummy();
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(listProduk);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvTersedia.setLayoutManager(layoutManager);
        rvTersedia.setAdapter(adapter);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvTerpenuhi.setLayoutManager(layoutManager2);
        rvTerpenuhi.setAdapter(adapter);
    }

    private void initComponent(View view) {
        rvTersedia = view.findViewById(R.id.rv_kesempatan_terbaru);
        rvTerpenuhi = view.findViewById(R.id.rv_terdanai);
        linearRating = view.findViewById(R.id.linear_rating);
        linearList = view.findViewById(R.id.linear_list);
        linearNearMap = view.findViewById(R.id.linear_near_map);
        presenter = new HomePresenter(this);
    }

    private void setActionComponent() {
        linearRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(ListProdukRatingActivity.class);
            }
        });

        linearList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(ListCrowFundingActivity.class);
            }
        });

        linearNearMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(NearMapActivity.class);
            }
        });
    }

    private ArrayList<Produk> getDummy() {
        Produk produk = new Produk(0, false, 100, "Trading Perikanan Hasil Laut Unit 14 Banyuwangi", "https://static.growpal.co.id/uploads/galleryListing-Gallery1575443299921-1-17680Cropped.jpg", "5e3c1018fd1f6f13a7cb9295");
        ArrayList<Produk> list = new ArrayList<>();
        for(int i=0; i<3; i++) {
            list.add(produk);
        }
        return list;
    }

    @Override
    public void onSuccessGetListProduct(List<Produk> listProduk) {
        List<Produk> listProdukFalse = new ArrayList<>();
        List<Produk> listProdukTrue = new ArrayList<>();

        if(listProduk != null) {
            for(Produk produk : listProduk) {
                if (produk.getFundedstatus()) {
                    listProdukTrue.add(produk);
                } else {
                   listProdukFalse.add(produk);
                }
            }
        }

        HomeRecyclerViewAdapter adapterListProdukTrue = new HomeRecyclerViewAdapter(listProdukFalse);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvTersedia.setLayoutManager(layoutManager);
        rvTersedia.setAdapter(adapterListProdukTrue);

        HomeRecyclerViewAdapter adapterListProdukFalse = new HomeRecyclerViewAdapter(listProdukTrue);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvTerpenuhi.setLayoutManager(layoutManager2);
        rvTerpenuhi.setAdapter(adapterListProdukFalse);
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
