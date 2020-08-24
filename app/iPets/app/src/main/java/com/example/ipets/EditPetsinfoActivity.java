package com.example.ipets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class EditPetsinfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_petsinfo);

        Toolbar toolbar6 = findViewById(R.id.toolbar6);
        setSupportActionBar(toolbar6);
        getSupportActionBar().setTitle("編輯寵物資訊");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar6.setTitleTextColor(Color.WHITE);

        Spinner petSexSpinner = findViewById(R.id.petSexSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Spinner_petSex, R.layout.myspinner_layout);
        adapter.setDropDownViewResource(R.layout.myspinner_dropdown_layout);
        petSexSpinner.setAdapter(adapter);
        petSexSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this,adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
