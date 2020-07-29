package com.example.ipets;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    Location mLocation;
    private static int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private static boolean rLocationGranted = false;
    private FusedLocationProviderClient mFusedLocationProvider;

    //widgets小部件
    private EditText mSearchText;
    private ImageView mGps;
    private Button mSearch_button;

    //標記
    Marker marker;
    Marker mCurrLocationMarker;

    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

        mSearchText = findViewById(R.id.input_search);
        mGps = findViewById(R.id.ic_gps);
        mSearch_button = findViewById(R.id.search_button);

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
        init();
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

    private void init(){
        Log.d("init", "init : Initializing");

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){
                    //execute our method  for searching
                    geoLocate();
                }
                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });

        mSearch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geoLocate();
            }
        });
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
                            LatLng nowlatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(nowlatLng));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nowlatLng, 17));
                            marker = mMap.addMarker(new MarkerOptions()
                                    .position(nowlatLng)
                                    .draggable(true));


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

    private void geoLocate(){
        Log.d("geoLocate", "geoLocate : geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString,1);
        }catch (IOException e){
            Log.e("geoLocate", "geoLocate : IOException: " + e.getMessage());
        }
        if (list.size() > 0){
            Address address = list.get(0);

            if (marker != null) {
                marker.remove();
            }
            LatLng searchlatLng = new LatLng(address.getLatitude(),address.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(searchlatLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchlatLng, 17));
            marker = mMap.addMarker(new MarkerOptions()
                    .position(searchlatLng)
                    .draggable(true));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getDeviceLocation();

        Button btnPetStore = findViewById(R.id.PetStore);
        btnPetStore.setOnClickListener(new View.OnClickListener() {
            String PetStore = "PetStore";
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(latitude, longitude, PetStore);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(MapsActivity.this,"Nearby PetStore", Toast.LENGTH_LONG).show();
            }
        });

        Button btnPetSalon = findViewById(R.id.PetSalon);
        btnPetSalon.setOnClickListener(new View.OnClickListener() {
            String PetSalon = "PetSalon";
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(latitude, longitude, PetSalon);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(MapsActivity.this,"Nearby PetSalon", Toast.LENGTH_LONG).show();
            }
        });

        Button btnPetHospital = findViewById(R.id.PetHospital);
        btnPetHospital.setOnClickListener(new View.OnClickListener() {
            String PetHospital = "PetHospital";
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(latitude, longitude, PetHospital);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(MapsActivity.this,"Nearby PetHospital", Toast.LENGTH_LONG).show();
            }
        });

        Button btnPetHotel = findViewById(R.id.PetHotel);
        btnPetHotel.setOnClickListener(new View.OnClickListener() {
            String PetHotel = "PetHotel";
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                String url = getUrl(latitude, longitude, PetHotel);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(MapsActivity.this,"Nearby PetHotel", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyBiU7Qs7b5GjLBZ8kxHrJU-2VOmRXR6XpY&radius=500&location=25.042036, 121.525350");
        //googlePlacesUrl.append("location=" + latitude + "," + longitude);
        //googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        //googlePlacesUrl.append("&type=" +  nearbyPlace);
        //googlePlacesUrl.append("&sensor=true");
        //googlePlacesUrl.append("&key=" + "AIzaSyBiU7Qs7b5GjLBZ8kxHrJU-2VOmRXR6XpY");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }
}