package com.example.ipets;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
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
import com.google.android.gms.common.api.GoogleApiClient;
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


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int PROXIMITY_RADIUS = 1000;
    private static boolean rLocationGranted = false;
    private FusedLocationProviderClient mFusedLocationProvider;
    private static int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    LocationManager lm;
    LocationListener ll;

    //宣告經緯度
    double latitude, longitude;

    //宣告小部件
    private EditText mSearchText;
    private ImageView mGps;
    private Button mSearch_button;
    EditText etSource;
    TextView etDestination;
    Button btTrack;

    //宣告標記
    Marker marker;
    Marker mCurrLocationMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

        mGps = findViewById(R.id.ic_gps);

        mSearchText = findViewById(R.id.input_search);
        mSearch_button = findViewById(R.id.search_button);

        etSource = findViewById(R.id.input_search);
        etDestination = findViewById(R.id.te_ntub);
        btTrack = findViewById(R.id.bt_track);

        //如果true則初始化Map
        if (chkPlayService() == true) {
            initialMap();
            if (rLocationGranted == true) {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            }
        }
        init();

        //導航按鈕
        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get value from edit text
                String sSource = mSearchText.getText().toString().trim();
                String sDestination =etDestination.getText().toString().trim();

                //Check condition
                if(sSource.equals("") && sDestination.equals("")){
                    //When both value blank
                    Toast.makeText(getApplicationContext()
                            ,"Enter both location",Toast.LENGTH_SHORT).show();
                }else {
                    //When both value fill
                    //Display track
                    DisplayTrack(sSource,sDestination);
                }
            }
        });
    }

    //建立初始化Map的方法
    private void initialMap() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if ((ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0])
                == PackageManager.PERMISSION_GRANTED ||
                (ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1])
                        == PackageManager.PERMISSION_GRANTED))) {
            rLocationGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, permissions,
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        }
    }

    // 初始化，給一些對象賦值
    private void init() {
        Log.d("init", "init : Initializing");

        //目前定位點按鈕
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });

        //搜尋地標輸入欄
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    geoLocate();
                }
                return false;
            }
        });

        //搜尋地標按鈕
        mSearch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geoLocate();
            }
        });
    }


    private boolean chkPlayService() {
        int avai = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);
        if (avai == ConnectionResult.SUCCESS) {
            Log.i("Map Test", "版本符合，立即執行MAP");
            return true;
        } else {
            Toast.makeText(this, "版本不符合，無法執行MAP", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        rLocationGranted = false;
        switch (requestCode) {
            case 1001: {

                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            rLocationGranted = false;
                            return;
                        }
                    }
                    rLocationGranted = true;
                }
            }
        }
    }

    //取得目前位置
    private void getDeviceLocation() {
        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (rLocationGranted == true) {
                final Task location = mFusedLocationProvider.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            //找到位置
                            Location mLocation = (Location) task.getResult();
                            LatLng nowlatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(nowlatLng));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nowlatLng, 17));
                            marker = mMap.addMarker(new MarkerOptions()
                                    .position(nowlatLng)
                                    .draggable(true)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                            Log.i("location", "(" + mLocation.getLatitude() + ", " + mLocation.getLongitude() + ") ");
                        }
                    }
                });
            }
        } catch (Exception ex) {
            Log.e("LocationError", ex.getMessage());
        }
    }

    //取得搜尋地標位置
    private void geoLocate() {
        Log.d("geoLocate", "geoLocate : geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e("geoLocate", "geoLocate : IOException: " + e.getMessage());
        }
        if (list.size() > 0) {
            Address address = list.get(0);

            if (marker != null) {
                marker.remove();
            }
            LatLng searchlatLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(searchlatLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchlatLng, 17));
            marker = mMap.addMarker(new MarkerOptions()
                    .position(searchlatLng)
                    .draggable(true));
        }
    }

    //取得導航路線
    private void DisplayTrack(String sSource,String sDestination){
        //If the device does not have a map installed, then redirect it to play store
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + sSource + "/"
                    + sDestination);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getDeviceLocation();

        //附近寵物店按鈕
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
            }

            private String getUrl(double latitude, double longitude, String nearbyPlace) {
                StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyBiU7Qs7b5GjLBZ8kxHrJU-2VOmRXR6XpY&radius=1000&location=25.042036,121.525350&radius=500&type=pet_store&sensor=true");
                Log.d("getUrl", googlePlacesUrl.toString());
                return (googlePlacesUrl.toString());
            }

        });

        //附近寵物沙龍店按鈕
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
            }

            private String getUrl(double latitude, double longitude, String nearbyPlace) {
                StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyBiU7Qs7b5GjLBZ8kxHrJU-2VOmRXR6XpY&radius=1000&location=25.042036,121.525350&radius=500&type=pet_store&sensor=true");
                Log.d("getUrl", googlePlacesUrl.toString());
                return (googlePlacesUrl.toString());
            }
        });

        //附近寵物醫院按鈕
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
            }

            private String getUrl(double latitude, double longitude, String nearbyPlace) {
                StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyBiU7Qs7b5GjLBZ8kxHrJU-2VOmRXR6XpY&radius=1000&location=25.042036,121.525350&type=veterinary_care&sensor=true");
                Log.d("getUrl", googlePlacesUrl.toString());
                return (googlePlacesUrl.toString());
            }
        });

        //附近公園按鈕
        Button btnPark = findViewById(R.id.Park);
        btnPark.setOnClickListener(new View.OnClickListener() {
            String Park = "Park";

            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");

                mMap.clear();
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                String url = getUrl(latitude, longitude, Park);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
            }

            private String getUrl(double latitude, double longitude, String nearbyPlace) {
                StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyBiU7Qs7b5GjLBZ8kxHrJU-2VOmRXR6XpY&radius=1000&location=25.042036,121.525350&type=park&sensor=true");
                Log.d("getUrl", googlePlacesUrl.toString());
                return (googlePlacesUrl.toString());
            }
        });
    }
}