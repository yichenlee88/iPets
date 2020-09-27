package com.example.ipets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    String userUID = currentUser.getUid();
        private static final int ADD_NOTE = 44;
        private CalendarView mCalendarView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_calendar);
            mCalendarView = findViewById(R.id.calendarView);
            FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.Add);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addNote();
                }
            });

            mCalendarView.setOnDayClickListener(new OnDayClickListener() {
                @Override
                public void onDayClick(EventDay eventDay) {
                    previewNote(eventDay);
                }
            });

        }

        private void addNote() {
            Intent intent = new Intent(this, AddNoteActivity.class);
            startActivityForResult(intent, ADD_NOTE);
        }

        private void previewNote(EventDay eventDay) {
            Intent intent = new Intent(this, NotePreviewActivity.class);
            startActivity(intent);
        }
        protected void onStart() {
            super.onStart();

            mCalendarView = findViewById(R.id.calendarView);
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            List events = new ArrayList<>();
            db.collection("userInformation").document(userUID).collection("calendar").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String date = document.getId();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        Date calenderdate = sdf.parse(date);
                                        Calendar cal = Calendar.getInstance();
                                        cal.setTime(calenderdate);
                                        events.add(new EventDay(cal, R.drawable.ic_home));
                                        mCalendarView.setEvents(events);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {

                            }
                        }
                    });

        }

    }