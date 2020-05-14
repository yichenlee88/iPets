package com.example.ipets;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment {

    public homeFragment() {
        // Required empty public constructor
    }

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setContentView(R.layout.fragment_home);
        BottomNavigationView bottomNavigationView = getView().findViewById(R.id.nav_bottom);
        Fragment home = getFragmentManager().findFragmentById(R.id.homeFragment);
        Fragment analyze = getFragmentManager().findFragmentById(R.id.analyzeFragment);
        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()){
                case R.id.home:
                    home.getFragmentManager().findFragmentById(R.id.homeFragment);
                    break;
                case R.id.analyze:
                    analyze.getFragmentManager().findFragmentById(R.id.analyzeFragment);
                    break;
            }
            return true;
        });
    }
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button mapbutton = getView().findViewById(R.id.mapbutton);
        mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_homeFragment_to_mapsActivity);
            }
        });
        super.onActivityCreated(savedInstanceState);
        Button petsinfobutton = getView().findViewById(R.id.petsinfo);
        petsinfobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_homeFragment_to_petsinfoFragment);
            }
        });
    }


}
