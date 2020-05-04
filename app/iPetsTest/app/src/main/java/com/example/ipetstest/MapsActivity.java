package com.example.ipetstest;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    Location mLocation;
    private static int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private static boolean rLocationGranted = false;
    private FusedLocationProviderClient mFusedLocationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        //如果true則初始化Map
        if(chkPlayService()==true) {
            initialMap();
            if(rLocationGranted==true){
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            }
        }
    }

    //建立初始化Map的方法
    private void initialMap() {
        //制訂權限有哪些
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        //檢查權限[0][1]有無符合要求
        if(  (ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[0])
                == PackageManager.PERMISSION_GRANTED ||
                (ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[1])
                        == PackageManager.PERMISSION_GRANTED))) {
            //可以取得FINE.....LOCATION
            rLocationGranted = true;
        }
        else {
            ActivityCompat.requestPermissions(this, permissions,
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        }
    }

    private void getDeviceLocation() {
        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        try {
            if(rLocationGranted == true){
                final Task location = mFusedLocationProvider.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            //找到位置
                            Location mLocation = (Location) task.getResult();
                            LatLng latLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                            mMap.addMarker(markerOptions);
                            //這個也可以，同上animateCamera功能 : mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            Log.i("location","("+mLocation.getLatitude()+", "+mLocation.getLongitude()+") ");
                        }
                    }
                });
            }
        }
        catch (Exception ex){
            Log.e("LocationError",ex.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        rLocationGranted = false;
        switch (requestCode){
            case 1001:{

                if(grantResults.length>0){
                    for (int i=0 ; i<grantResults.length;i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            rLocationGranted = false;
                            return;
                        }
                    }
                    rLocationGranted = true;
                }
            }
        }
    }

    private boolean chkPlayService(){
        int avai = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);
        if (avai == ConnectionResult.SUCCESS){
            Log.i("Map Test","版本符合，立即執行MAP");
            return true;
        }
        else {
            Toast.makeText(this, "版本不符合，無法執行MAP", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getDeviceLocation();
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