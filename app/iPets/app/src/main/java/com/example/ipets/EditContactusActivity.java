package com.example.ipets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditContactusActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    String userUID = currentUser.getUid();
    TextInputEditText edmasterName,edemail,eddescription;
    Button edbutton;
    Spinner problemSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contactus);

        Toolbar toolbar8 = findViewById(R.id.toolbar8);
        setSupportActionBar(toolbar8);
        getSupportActionBar().setTitle("聯絡我們");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar8.setTitleTextColor(Color.WHITE);

        Spinner problemSpinner = findViewById(R.id.problemSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Spinner_problem, R.layout.myspinner_layout);
        adapter.setDropDownViewResource(R.layout.myspinner_dropdown_layout);
        problemSpinner.setAdapter(adapter);
        problemSpinner.setOnItemSelectedListener(this);

        edbutton = findViewById(R.id.buttongo);
        edbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactUs();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        //Toast.makeText(this,adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void addContactUs() {
        edmasterName = findViewById(R.id.masterName);
        String masterName = edmasterName.getText().toString();
        edemail = findViewById(R.id.email);
        String Contactemail = edemail.getText().toString();
        eddescription = findViewById(R.id.description);
        String description = eddescription.getText().toString();
        problemSpinner = findViewById(R.id.problemSpinner);
        String problemtype = problemSpinner.getSelectedItem().toString();
        Calendar now = Calendar.getInstance();
        Date NOW = now.getTime();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        Map<String, Object> newContact = new HashMap<>();
        newContact.put("userName", masterName);
        newContact.put("email", Contactemail);
        newContact.put("description", description);
        newContact.put("problemType", problemtype);
        newContact.put("timestamp", NOW);
        db.collection("contact").document().set(newContact);
        AlertDialog.Builder finishsignup = new AlertDialog.Builder(EditContactusActivity.this);
        finishsignup.setMessage("傳送成功");
        finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(EditContactusActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        finishsignup.setCancelable(false);
        finishsignup.show();
    }
}









