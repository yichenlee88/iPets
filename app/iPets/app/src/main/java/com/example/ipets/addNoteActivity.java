package com.example.ipets;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class addNoteActivity extends AppCompatActivity {
Calendar endCalendar,startCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar11 = findViewById(R.id.toolbar11);
        setSupportActionBar(toolbar11);
        getSupportActionBar().setTitle("新增行程");

        Spinner sexSpinner = findViewById(R.id.repeatSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Spinner_repeat, R.layout.myspinner_layout);
        adapter.setDropDownViewResource(R.layout.myspinner_dropdown_layout);
        sexSpinner.setAdapter(adapter);
        EditText startDate = findViewById(R.id.startDate);
        EditText endDate = findViewById(R.id.endDate);
        startDate.setInputType(InputType.TYPE_NULL); //不顯示系統輸入鍵盤
        startDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            startDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            String startstr = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            try {
                                date = sdf.parse(startstr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            startCalendar = Calendar.getInstance();
                            startCalendar.setTime(date);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    DatePicker datePicker = datePickerDialog.getDatePicker();
                    datePicker.setMinDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            }
        });
        endDate.setInputType(InputType.TYPE_NULL); //不顯示系統輸入鍵盤
        endDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            endDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            String endstr = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            try {
                                date = sdf.parse(endstr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            endCalendar = Calendar.getInstance();
                            endCalendar.setTime(date);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    DatePicker datePicker = datePickerDialog.getDatePicker();
                    datePicker.setMinDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            }
        });
        Button addNote = findViewById(R.id.addNoteButton);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });


    }

    private void addEvent() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userUID = currentUser.getUid();
        TextInputEditText ednote = findViewById(R.id.noteEditText);
        String note = ednote.getText().toString();
        TextInputEditText detailEditText = findViewById(R.id.detailEditText);
        String detail = detailEditText.getText().toString();
        //EditText startDate = findViewById(R.id.startDate);
        //String start = startDate.getText().toString();
        //EditText endDate = findViewById(R.id.endDate);
        // String end = endDate.getText().toString();
        String colorselect = null;
        RadioGroup genderselect = findViewById(R.id.eventColor);
        switch (genderselect.getCheckedRadioButtonId()) {
            case R.id.redCircle1:
                colorselect = "red";
                break;
            case R.id.orangeCircle1:
                colorselect = "orange";
                break;
            case R.id.yellowCircle1:
                colorselect = "yellow";
                break;
            case R.id.greenCircle1:
                colorselect = "green";
                break;
        }
        Spinner repeatSpinner = findViewById(R.id.repeatSpinner);
        int idsex = repeatSpinner.getSelectedItemPosition();
        String[] Spinner_repeat = getResources().getStringArray(R.array.Spinner_repeat);
        String repeat = Spinner_repeat[idsex];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startday = sdf.format(startCalendar.getTime());
        String endday = sdf.format(endCalendar.getTime());
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("eventName", note);
        userInfo.put("startDate", startday);
        userInfo.put("endDate", endday);
        userInfo.put("frequency", repeat);
        userInfo.put("details", detail);
        userInfo.put("color", colorselect);
        db.collection("users").document(userUID).collection("calEvent").document().set(userInfo);
        if(repeat.equals("不重複")){
            AlertDialog.Builder finishsignup = new AlertDialog.Builder(addNoteActivity.this);
            finishsignup.setMessage("新增成功");
            finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(addNoteActivity.this, calendarActivity.class);
                    startActivity(intent);
                }
            });
            finishsignup.setCancelable(false);
            finishsignup.show();
        }
        if (repeat.equals("每日")) {
            for (int i = 0; i < 4; i++) {
                startCalendar.add(Calendar.DAY_OF_MONTH,1);
                endCalendar.add(Calendar.DAY_OF_MONTH,1);
                String start = sdf.format(startCalendar.getTime());
                String end = sdf.format(endCalendar.getTime());
                db = FirebaseFirestore.getInstance();
                userInfo.put("eventName", note);
                userInfo.put("startDate", start);
                userInfo.put("endDate", end);
                userInfo.put("frequency", repeat);
                userInfo.put("details", detail);
                userInfo.put("color", colorselect);
                db.collection("users").document(userUID).collection("calEvent").document().set(userInfo);
            }
            AlertDialog.Builder finishsignup = new AlertDialog.Builder(addNoteActivity.this);
            finishsignup.setMessage("新增成功");
            finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(addNoteActivity.this, calendarActivity.class);
                    startActivity(intent);
                }
            });
            finishsignup.setCancelable(false);
            finishsignup.show();
        }
        if (repeat.equals("每週")) {
            for (int i = 0; i < 4; i++) {
                startCalendar.add(Calendar.DAY_OF_MONTH,7);
                endCalendar.add(Calendar.DAY_OF_MONTH,7);
                String start = sdf.format(startCalendar.getTime());
                String end = sdf.format(endCalendar.getTime());
                db = FirebaseFirestore.getInstance();
                userInfo.put("eventName", note);
                userInfo.put("startDate", start);
                userInfo.put("endDate", end);
                userInfo.put("frequency", repeat);
                userInfo.put("details", detail);
                userInfo.put("color", colorselect);
                db.collection("users").document(userUID).collection("calEvent").document().set(userInfo);
            }
            AlertDialog.Builder finishsignup = new AlertDialog.Builder(addNoteActivity.this);
            finishsignup.setMessage("新增成功");
            finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(addNoteActivity.this, calendarActivity.class);
                    startActivity(intent);
                }
            });
            finishsignup.setCancelable(false);
            finishsignup.show();
        }
        if (repeat.equals("每月")) {
            for (int i = 0; i < 4; i++) {
                startCalendar.add(Calendar.MONTH,1);
                endCalendar.add(Calendar.MONTH,1);
                String start = sdf.format(startCalendar.getTime());
                String end = sdf.format(endCalendar.getTime());
                db = FirebaseFirestore.getInstance();
                userInfo.put("eventName", note);
                userInfo.put("startDate", start);
                userInfo.put("endDate", end);
                userInfo.put("frequency", repeat);
                userInfo.put("details", detail);
                userInfo.put("color", colorselect);
                db.collection("users").document(userUID).collection("calEvent").document().set(userInfo);
            }
            AlertDialog.Builder finishsignup = new AlertDialog.Builder(addNoteActivity.this);
            finishsignup.setMessage("新增成功");
            finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(addNoteActivity.this, calendarActivity.class);
                    startActivity(intent);
                }
            });
            finishsignup.setCancelable(false);
            finishsignup.show();
        }
        if (repeat.equals("每年")) {
            for (int i = 0; i < 4; i++) {
                startCalendar.add(Calendar.YEAR,1);
                endCalendar.add(Calendar.YEAR,1);
                String start = sdf.format(startCalendar.getTime());
                String end = sdf.format(endCalendar.getTime());
                db = FirebaseFirestore.getInstance();
                userInfo.put("eventName", note);
                userInfo.put("startDate", start);
                userInfo.put("endDate", end);
                userInfo.put("frequency", repeat);
                userInfo.put("details", detail);
                userInfo.put("color", colorselect);
                db.collection("users").document(userUID).collection("calEvent").document().set(userInfo);
            }
            AlertDialog.Builder finishsignup = new AlertDialog.Builder(addNoteActivity.this);
            finishsignup.setMessage("新增成功");
            finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(addNoteActivity.this, calendarActivity.class);
                    startActivity(intent);
                }
            });
            finishsignup.setCancelable(false);
            finishsignup.show();
        }
    }
}
