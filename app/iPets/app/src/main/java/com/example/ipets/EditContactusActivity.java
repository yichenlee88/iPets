package com.example.ipets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;

public class EditContactusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contactus);

        Toolbar toolbar8 = findViewById(R.id.toolbar8);
        setSupportActionBar(toolbar8);
        getSupportActionBar().setTitle("聯絡我們");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar8.setTitleTextColor(Color.WHITE);
    }
}
