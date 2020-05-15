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

    ArrayList<LatLng> arrayList=new ArrayList<LatLng>();

    //widgets
    private EditText mSearchText;
    private ImageView mGps;
    private Button mSearch_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

        mSearchText = (EditText) findViewById(R.id.input_search);
        mGps = (ImageView) findViewById(R.id.ic_gps);
        mSearch_button = (Button) findViewById(R.id.search_button);


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
                            LatLng nowlatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(nowlatLng).title("I am here!").draggable(true);
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(nowlatLng));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nowlatLng, 17));
                            //這個也可以，同上animateCamera功能 : mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            mMap.getUiSettings().setZoomGesturesEnabled(true);
                            mMap.getUiSettings().setZoomControlsEnabled(true);
                            mMap.getUiSettings().setCompassEnabled(true);
                            mMap.addMarker(markerOptions);
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

            Log.d("geoLocate", "geoLocate : found a location: " + address.toString());
            LatLng searchlatLng = new LatLng(address.getLatitude(),address.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(searchlatLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchlatLng, 17));
            MarkerOptions options = new MarkerOptions().position(searchlatLng);
            mMap.addMarker(options);
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
        LatLng PetSuppliesStore1 = new LatLng(25.062097, 121.525432);
        LatLng PetSuppliesStore2 = new LatLng(25.031245, 121.529331);
        LatLng PetSuppliesStore3 = new LatLng(25.028951, 121.538968);
        LatLng PetSuppliesStore4 = new LatLng(25.035700, 121.532458);

        arrayList.add(PetSuppliesStore1);
        arrayList.add(PetSuppliesStore2);
        arrayList.add(PetSuppliesStore3);
        arrayList.add(PetSuppliesStore4);

        for (int i=0;i<arrayList.size();i++){
            mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }
    }

    public void PetGroomingShop(View view) {
        LatLng PetGroomingShop1 = new LatLng(25.057657, 121.523878);
        LatLng PetGroomingShop2 = new LatLng(25.038043, 121.529479);
        LatLng PetGroomingShop3 = new LatLng(25.049152, 121.525224);
        LatLng PetGroomingShop4 = new LatLng(25.034107, 121.545246);

        arrayList.add(PetGroomingShop1);
        arrayList.add(PetGroomingShop2);
        arrayList.add(PetGroomingShop3);
        arrayList.add(PetGroomingShop4);

        for (int i=0;i<arrayList.size();i++){
            mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }
    }

    public void PetHospital(View view) {
        LatLng PetHospital1 = new LatLng(25.043239, 121.525051);
        LatLng PetHospital2 = new LatLng(25.043171, 121.528863);
        LatLng PetHospital3 = new LatLng(25.047033, 121.531570);
        LatLng PetHospital4 = new LatLng(25.036919, 121.532993);

        arrayList.add(PetHospital1);
        arrayList.add(PetHospital2);
        arrayList.add(PetHospital3);
        arrayList.add(PetHospital4);

        for (int i=0;i<arrayList.size();i++){
            mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }
    }

    public void PetHotel(View view) {
        LatLng PetHotel1 = new LatLng(25.032257, 121.516281);
        LatLng PetHotel2 = new LatLng(25.034057, 121.543851);
        LatLng PetHotel3 = new LatLng(25.033744, 121.537176);
        LatLng PetHotel4 = new LatLng(25.043526, 121.543569);

        arrayList.add(PetHotel1);
        arrayList.add(PetHotel2);
        arrayList.add(PetHotel3);
        arrayList.add(PetHotel4);

        for (int i=0;i<arrayList.size();i++){
            mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }
    }
}