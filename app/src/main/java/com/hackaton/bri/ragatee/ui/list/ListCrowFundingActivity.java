package com.hackaton.bri.ragatee.ui.list;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.base.BaseActivity;
import com.hackaton.bri.ragatee.model.ProdukWithMap;
import com.hackaton.bri.ragatee.ui.nearmap.NearMapPresenter;

import java.util.List;

public class ListCrowFundingActivity extends BaseActivity implements ListCrowFundingView, LocationListener {

    protected LocationManager locationManager;
    private boolean statusOfGPS, stateFlage = false;
    private double lat, lng;
    private int radius = 15;

    private RecyclerView rvList;
    private ListCrowFundingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_crow_funding);
        initComponent();

        if(!statusOfGPS) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        getLocationPermission();
    }

    private void getLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        }
    }

    private void initComponent() {
        rvList = findViewById(R.id.rv_list);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        presenter = new ListCrowFundingPresenter(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onSuccessGetListCrowFunderLocation(List<ProdukWithMap> listProdukWithMap) {
        ListCrowFundingRecyclerViewAdapter adapter = new ListCrowFundingRecyclerViewAdapter(listProdukWithMap);
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

    @Override
    public void onLocationChanged(Location location) {
        if(!stateFlage) {
            stateFlage = true;
            lat = location.getLatitude();
            lng = location.getLongitude();
            presenter.getListCrowFunderLocation(lat, lng, radius);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
