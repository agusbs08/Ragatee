package com.hackaton.bri.ragatee.ui.detail.fragmentmap;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hackaton.bri.ragatee.R;

public class MapFragment extends Fragment {

    private MapView mapView;
    private GoogleMap googleMap;
    private double lat, lng;

    public MapFragment() {
        this.lat = 0;
        this.lng = 0;
    }

    public MapFragment(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view);
        mapView.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setActionComponent();
        mapView.onResume();
    }

    private void initComponent(View view) {
        mapView = view.findViewById(R.id.map_view);
    }

    private void setActionComponent() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                //googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng surabaya;
                if (lat != 0 && lng != 0) {
                    surabaya = new LatLng(lat, lng);
                } else {
                    surabaya = new LatLng(-7.250445, 112.768845);
                }
                googleMap.addMarker(new MarkerOptions().position(surabaya).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(surabaya).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }
}
