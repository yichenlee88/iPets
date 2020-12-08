package com.example.ipets;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navView = findViewById(R.id.nav_bottom);
        // Passing each menu ID as a set of Ids because each
        // menu should be considereas top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home, R.id.calendar,R.id.locate, R.id.setting)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //toolbarNavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;

                switch (menuItem.getItemId()){
                    case R.id.home:
                        //toolbar.setTitle("Home");
                        Intent intentHome = new Intent(homeActivity.this, homeActivity.class);
                        intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentHome);
                        return true;

                    case R.id.calendar:
                        Intent intentCalendar = new Intent(homeActivity.this, calendarActivity.class);
                        intentCalendar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentCalendar);
                        return true;

                    case R.id.photoGallery:
                        Intent intentGallery = new Intent(homeActivity.this, photoGalleryActivity.class);
                        intentGallery.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentGallery);
                        return true;

                    case R.id.locate:
                        Intent intentMap = new Intent(homeActivity.this,MapsActivity.class);
                        intentMap.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentMap);
                        return true;
                        //toolbar.setTitle("Locate123");
                        /*
                        fragment = new MapsFragment();
                        loadFragment(fragment);
                        return true;

                         */
                    case R.id.setting:
                        Intent intentSet = new Intent(homeActivity.this, settingActivity.class);
                        intentSet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentSet);
                        return true;
                }
                return false;
            }
        });

    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
