package com.example.ipets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {
    Button btn_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setTitle("設定");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar2.setTitleTextColor(Color.WHITE);

        btn_info = (Button) findViewById(R.id.masterInfo);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInfo = new Intent(SettingActivity.this,EditMasterinfoActivity.class);
                startActivity(intentInfo);
            }
        });
    }
}
