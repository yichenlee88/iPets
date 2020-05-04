package com.example.ipets;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(25.042260, 121.525331);
        float zoom = 17;
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("我在這!");
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.addMarker(markerOptions);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    public void PetSuppliesStore(View view) {
        LatLng latLng2 = new LatLng(25.055901, 121.514937);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng2).title("寵物公園用品店");
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng2));
        mMap.addMarker(markerOptions);
    }


    public void PetGroomingShop(View view) {
        LatLng latLng3 = new LatLng(25.057675, 121.523853);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng3).title("Wang's Beauty 旺城寵物精品美容");
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng3));
        mMap.addMarker(markerOptions);
    }

    public void PetHospital(View view) {
        LatLng latLng4 = new LatLng(25.036141, 121.527525);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng4).title("恩孺動物診所");
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng4));
        mMap.addMarker(markerOptions);
    }

    public void PetHotel(View view) {
        LatLng latLng5 = new LatLng(25.025679, 121.536246);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng5).title("北歐寵物旅館");
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng5));
        mMap.addMarker(markerOptions);
    }
}
