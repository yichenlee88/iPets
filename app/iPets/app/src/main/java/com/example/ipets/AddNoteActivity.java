package com.example.ipets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddNoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        final CalendarView datePicker = findViewById(R.id.datePicker);
        Button button = (Button) findViewById(R.id.addNoteButton);
        final EditText noteEditText = findViewById(R.id.noteEditText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = auth.getCurrentUser();
                String userUID = currentUser.getUid();
                Calendar cal=Calendar.getInstance();
                cal = datePicker.getFirstSelectedDate();
                Date date = cal.getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String selectedDate = simpleDateFormat.format(date);
                String eventNote = noteEditText.getText().toString();
                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("Eventnote", eventNote);
                db.collection("userInformation").document(userUID).collection("calendar").document(selectedDate).set(userInfo);
                AlertDialog.Builder finishsignup = new AlertDialog.Builder(AddNoteActivity.this);
                finishsignup.setMessage("新增成功");
                finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClass(AddNoteActivity.this, CalendarActivity.class);
                        startActivity(intent);
                    }
                });
                finishsignup.setCancelable(false);
                finishsignup.show();
            }
        });
    }
}
