package com.example.ipets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

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
                        fragment = new homeFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.locate:
                        Intent intentMap = new Intent(HomeActivity.this,MapsActivity.class);
                        intentMap.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentMap);
                        //toolbar.setTitle("Locate123");
                        /*
                        fragment = new MapsFragment();
                        loadFragment(fragment);
                        return true;

                         */
                    case R.id.setting:
                        Intent intentSet = new Intent(HomeActivity.this,SettingActivity.class);
                        intentSet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentSet);
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
