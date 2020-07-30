package com.example.ipets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class EditMasterinfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    LinearLayout btn_editAcct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_masterinfo);

        Toolbar toolbar3 = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar3);
        getSupportActionBar().setTitle("編輯主人基本資料");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar3.setTitleTextColor(Color.WHITE);

        Spinner sexSpinner = findViewById(R.id.sexSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Spinner_sex, R.layout.myspinner_layout);
        adapter.setDropDownViewResource(R.layout.myspinner_dropdown_layout);
        sexSpinner.setAdapter(adapter);
        sexSpinner.setOnItemSelectedListener(this);

        btn_editAcct = (LinearLayout) findViewById(R.id.btnAccount);
        btn_editAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAcct = new Intent(EditMasterinfoActivity.this,EditAccountActivity.class);
                startActivity(intentAcct);
            }
        });

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
