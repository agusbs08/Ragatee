package com.hackaton.bri.ragatee.ui.nearmap;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

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
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.base.BaseActivity;
import com.hackaton.bri.ragatee.model.ProdukWithMap;
import com.hackaton.bri.ragatee.ui.detail.DetailActivity;

import java.util.List;

public class NearMapActivity extends BaseActivity implements LocationListener, NearMapView {

    protected LocationManager locationManager;
    private boolean stateFlage = false;
    private MapView mapView;
    private GoogleMap googleMap;
    private Bundle savedInstanceState;
    private double currentLat, currentLng;
    private LatLng currentLocation;
    private List<ProdukWithMap> listProdukWithMap;
    private boolean statusOfGPS;

    private NearMapPresenter presenter;
    private MarkerOptions currentMarker;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private int radius = 15;

    private final GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            if (marker.getPosition().latitude != currentLat || marker.getPosition().longitude != currentLng)
            {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("id_produk", marker.getSnippet());
                startActivity(intent);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_near_map);
        initComponent();

        if(!statusOfGPS) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        setCurrentLocation();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    private void initComponent() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        mapView = findViewById(R.id.map_view);
        presenter = new NearMapPresenter(this);
    }

    private void setCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        }
    }

    private void requestListCrowFunderLocation() {
        presenter.getListCrowFunderLocation(currentLat, currentLng, radius);
    }

    private void setMaps() {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setActionMap();
        mapView.onResume();
    }

    private void setActionMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                //googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                currentLocation = new LatLng(currentLat, currentLng);
                currentMarker = new MarkerOptions().position(currentLocation).title("Lokasi Saya").snippet("Ini Lokasi Saya");
                googleMap.addMarker(currentMarker);

                if(listProdukWithMap != null) {
                    for(ProdukWithMap produk : listProdukWithMap) {
                        LatLng location = new LatLng(produk.getLat(), produk.getLng());
                        googleMap.addMarker(new MarkerOptions().position(location).title(produk.getTitle()).snippet(produk.getId()));
                    }
                }

                googleMap.setOnMarkerClickListener(markerClickListener);
                googleMap.addCircle(new CircleOptions()
                        .center(currentLocation)
                        .radius(radius * 1000)
                        .strokeWidth(0f)
                        .fillColor(0x550000FF));
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(!stateFlage){
            stateFlage = true;
            currentLat = location.getLatitude();
            currentLng = location.getLongitude();
            requestListCrowFunderLocation();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("MAPPROVIDER", "DISABLE");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("MAPPROVIDER", "DISABLE");
    }

    @Override
    public void onSuccessGetListCrowFunderLocation(List<ProdukWithMap> listProdukWithMap) {
        this.listProdukWithMap = listProdukWithMap;
        setMaps();
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
