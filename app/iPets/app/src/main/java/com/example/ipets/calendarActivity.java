package com.example.ipets;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class calendarActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    String userUID = currentUser.getUid();
    List<EventDay> events = new ArrayList();
    private static final int ADD_NOTE = 44;
    private CalendarView mCalendarView;
    String id1;
    String id2;
    String id3;
    String id4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar10 = findViewById(R.id.toolbar10);
        setSupportActionBar(toolbar10);
        getSupportActionBar().setTitle("行事曆");
        mCalendarView = findViewById(R.id.calendarView);
        toolbar10.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addNote:
                        addNote();
                        break;
                }
                return false;
            }
        });

        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                List details = new ArrayList<>();
                List eventnames = new ArrayList<>();
                List ids = new ArrayList<>();
                TextView noteTitle = findViewById(R.id.noteTitle);
                TextView noteContent = findViewById(R.id.noteContent);
                TextView noteTitle2 = findViewById(R.id.noteTitle2);
                TextView noteContent2 = findViewById(R.id.noteContent2);
                TextView noteTitle3 = findViewById(R.id.noteTitle3);
                TextView noteContent3 = findViewById(R.id.noteContent3);
                TextView noteTitle4 = findViewById(R.id.noteTitle4);
                TextView noteContent4 = findViewById(R.id.noteContent4);
                noteTitle.setText("");
                noteContent.setText("");
                noteTitle2.setText("");
                noteContent2.setText("");
                noteTitle3.setText("");
                noteContent3.setText("");
                noteTitle4.setText("");
                noteContent4.setText("");
                Calendar clickedDayCalendar = eventDay.getCalendar();
                Date date = clickedDayCalendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sdf.format(date);
                db.collection("users").document(userUID).collection("calEvent").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String id = document.getId();
                                        db.collection("users").document(userUID).collection("calEvent").document(id)
                                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot doc = task.getResult();
                                                    StringBuilder fields = new StringBuilder("");
                                                    StringBuilder fields1 = new StringBuilder("");
                                                    StringBuilder fields2 = new StringBuilder("");
                                                    StringBuilder fields4 = new StringBuilder("");
                                                    StringBuilder fields5= new StringBuilder("");
                                                    String start = fields.append(doc.get("startDate")).toString();
                                                    String end = fields1.append(doc.get("endDate")).toString();
                                                    String detail = fields2.append(doc.get("details")).toString();
                                                    String eventname = fields4.append(doc.get("eventName")).toString();
                                                    try {
                                                        Date eventday = sdf.parse(dateStr);
                                                        Date strday = sdf.parse(start);
                                                        Calendar startcal = Calendar.getInstance();
                                                        startcal.setTime(strday);
                                                        Date endday = sdf.parse(end);
                                                        Calendar endcal = Calendar.getInstance();
                                                        endcal.setTime(endday);
                                                        endcal.add(Calendar.DATE, 1);
                                                        endday = endcal.getTime();
                                                        do {
                                                           if (strday.equals(eventday)) {
                                                               String eventid = document.getId();
                                                               ids.add(eventid);
                                                               details.add(detail);
                                                               eventnames.add(eventname);
                                                               for (int i = 0; i < ids.size(); i++) {
                                                                   if (i == 0) {
                                                                       id1 = (String) ids.get(i);
                                                                   }
                                                                   if (i == 1) {
                                                                       id2 = (String) ids.get(i);
                                                                   }
                                                                   if (i == 2) {
                                                                       id3 = (String) ids.get(i);
                                                                   }
                                                                   if (i == 3) {
                                                                       id4 = (String) ids.get(i);
                                                                   }
                                                               }
                                                               for (int i = 0; i < eventnames.size(); i++) {
                                                                   if (i == 0) {
                                                                       noteTitle.setText((String) eventnames.get(i));
                                                                   }
                                                                   if (i == 1) {
                                                                       noteTitle2.setText((String) eventnames.get(i));
                                                                   }
                                                                   if (i == 2) {
                                                                       noteTitle3.setText((String) eventnames.get(i));
                                                                   }
                                                                   if (i == 3) {
                                                                       noteTitle4.setText((String) eventnames.get(i));
                                                                   }
                                                               }
                                                               for (int i = 0; i < details.size(); i++) {
                                                                   if (i == 0) {
                                                                       noteContent.setText((String) details.get(i));
                                                                   }
                                                                   if (i == 1) {
                                                                       noteContent2.setText((String) details.get(i));
                                                                   }
                                                                   if (i == 2) {
                                                                       noteContent3.setText((String) details.get(i));
                                                                   }
                                                                   if (i == 3) {
                                                                       noteContent4.setText((String) details.get(i));
                                                                   }
                                                               }
                                                                break;
                                                            }
                                                            startcal.add(Calendar.DATE, 1);
                                                            strday = startcal.getTime();
                                                        }while (strday.compareTo(endday) < 0);
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }
                                        });
                                    }
                                } else {

                                }
                            }
                        });

            }
        });

        LinearLayout btnedit = findViewById(R.id.btnedit);
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView noteContent = findViewById(R.id.noteContent);
                TextView noteTitle = findViewById(R.id.noteTitle);
                bottomSheetNotePreview bottomSheetDialog = new bottomSheetNotePreview(noteTitle.getText().toString() , noteContent.getText().toString() , id1);
                bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheetNotePreview");
            }
        });
        LinearLayout btnedit2 = findViewById(R.id.btnedit2);
        btnedit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView noteContent2 = findViewById(R.id.noteContent2);
                TextView noteTitle2 = findViewById(R.id.noteTitle2);
                bottomSheetNotePreview bottomSheetDialog = new bottomSheetNotePreview(noteTitle2.getText().toString() , noteContent2.getText().toString() , id2);
                bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheetNotePreview");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_add, menu);
        return true;
    }

    private void addNote() {
        Intent intent = new Intent(this, addNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE);
    }

    private void previewNote(EventDay eventDay) {
        Calendar clickedDayCalendar = eventDay.getCalendar();
        Date date = clickedDayCalendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
    }

    protected void onStart() {
        super.onStart();

        mCalendarView = findViewById(R.id.calendarView);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(userUID).collection("calEvent").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                db.collection("users").document(userUID).collection("calEvent").document(id)
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot doc = task.getResult();
                                            StringBuilder fields = new StringBuilder("");
                                            StringBuilder fields3 = new StringBuilder("");
                                            StringBuilder fields5 = new StringBuilder("");
                                            String color = fields.append(doc.get("color")).toString();
                                            String enddate = fields3.append(doc.get("endDate")).toString();
                                            String startdate = fields5.append(doc.get("startDate")).toString();
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                            try {
                                                Date strday = sdf.parse(startdate);
                                                Calendar startcal = Calendar.getInstance();
                                                startcal.setTime(strday);
                                                Date endday = sdf.parse(enddate);
                                                Calendar endcal = Calendar.getInstance();
                                                endcal.setTime(endday);
                                                if (color.equals("red")) {
                                                    for(;startcal.compareTo(endcal) <= 0;startcal.add(Calendar.DATE, 1)){
                                                        Calendar c_temp = Calendar.getInstance();
                                                        c_temp.setTime(startcal.getTime());
                                                        events.add(new EventDay(c_temp, R.drawable.ic_red));
                                                    }
                                                    mCalendarView.setEvents(events);
                                                }
                                                if (color.equals("green")) {
                                                    for(;startcal.compareTo(endcal) <= 0;startcal.add(Calendar.DATE, 1)){
                                                        Calendar c_temp = Calendar.getInstance();
                                                        c_temp.setTime(startcal.getTime());
                                                        events.add(new EventDay(c_temp, R.drawable.ic_green));
                                                    }
                                                    mCalendarView.setEvents(events);

                                                }
                                                if (color.equals("orange")) {
                                                    for(;startcal.compareTo(endcal) <= 0;startcal.add(Calendar.DATE, 1)){
                                                        Calendar c_temp = Calendar.getInstance();
                                                        c_temp.setTime(startcal.getTime());
                                                        events.add(new EventDay(c_temp, R.drawable.ic_orange));
                                                    }
                                                    mCalendarView.setEvents(events);
                                                }
                                                if (color.equals("yellow")) {
                                                    for(;startcal.compareTo(endcal) <= 0;startcal.add(Calendar.DATE, 1)){
                                                        Calendar c_temp = Calendar.getInstance();
                                                        c_temp.setTime(startcal.getTime());
                                                        events.add(new EventDay(c_temp, R.drawable.ic_yellow));
                                                    }
                                                    mCalendarView.setEvents(events);
                                                }
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        mCalendarView.setEvents(events);
                                    }
                                });
                            }
                        } else {

                        }
                    }
                });
    }

}