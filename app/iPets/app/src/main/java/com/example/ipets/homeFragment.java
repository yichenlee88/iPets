package com.example.ipets;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    String userUID = currentUser.getUid();
    String countdownEvent;
    String pet_query = null;
    String documentname;

    // Spinner nameSpinner = getView().findViewById(R.id.nameSpinner);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Spinner petnamespinner = getView().findViewById(R.id.nameSpinner);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final List<String> petname = new ArrayList<>();
        db.collection("users").document(userUID).collection("pets").whereEqualTo("uid", userUID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentSnapshot doc = document;
                                StringBuilder fields = new StringBuilder("");
                                petname.add(fields.append(doc.get("petName")).toString());
                            }
                            if(petname.size()==0){
                                petname.add("尚未擁有寵物");
                            }
                            petname.add("新增寵物");
                            ArrayAdapter adapter = new ArrayAdapter<>(getContext(),R.layout.myspinner_layout , petname);
                            adapter.setDropDownViewResource(R.layout.myspinner_dropdown_layout);
                            petnamespinner.setAdapter(adapter);
                            petnamespinner.setSelection(0, true);
                            String query = petname.get(0);
                            pet_query = query;
                            getPetInfo(query);
                            getDocumentName();
                            getPetImage(query);
                            petnamespinner.setPrompt("請選擇");
                            petnamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    Log.d("homeFramgment 135", "onItemSelected: position: " + position);
                                    String query = petname.get(position);
                                    pet_query = query;
                                    if ( position == petname.size()-1){
                                        Intent intent=new Intent();
                                        intent.setClass(getActivity(), petInfoActivity.class);
                                        startActivity(intent);
                                    }else{
                                        getPetInfo(query);
                                        getPetImage(query);
                                        getDocumentName();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {

                        }
                    }
                });
        ProgressBar showerBar = getView().findViewById(R.id.showerBar);
        ProgressBar hairCutBar = getView().findViewById(R.id.hairCutBar);
        ProgressBar fleaInBar = getView().findViewById(R.id.fleaInBar);
        ProgressBar fleaOutBar = getView().findViewById(R.id.fleaOutBar);
        ProgressBar injectionBar = getView().findViewById(R.id.injectionBar);
        ProgressBar teethBar = getView().findViewById(R.id.teethBar);
        ProgressBar bloodBar = getView().findViewById(R.id.bloodBar);
        showerBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Shower =pet_query + "洗澡";
                setCountdownDate(Shower);

            }
        });
        hairCutBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Haircut = pet_query + "修剪毛髮";
                setCountdownDate(Haircut);

            }
        });
        fleaInBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Fleain = pet_query + "體內除蟲";
                setCountdownDate(Fleain);

            }
        });
        fleaOutBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Fleaout = pet_query + "體外除蟲";
                setCountdownDate(Fleaout);

            }
        });
        injectionBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Injection = pet_query + "注射";
                setCountdownDate(Injection);

            }
        });
        teethBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Teeth = pet_query + "看牙醫";
                setCountdownDate(Teeth);

            }
        });
        bloodBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Blood = pet_query + "經期";
                setCountdownDate(Blood);

            }
        });
        Button btn_editPets =getView().findViewById(R.id.petInfoEdit);
        btn_editPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPets = new Intent(getActivity(), editPetInfoActivity.class);
                intentPets.putExtra("date",pet_query);
                startActivity(intentPets);
            }
        });
    }
    public void getDocumentName(){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userUID).collection("pets").whereEqualTo("petName", pet_query)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documentname = document.getId();
                            }
                        } else {

                        }
                        setCountdownColor();
                        setNotification();
                    }
                });
    }

    private void setNotification() {
        if(!pet_query.equals("尚未擁有寵物")) {
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userUID).collection("pets").document(documentname).collection("countdown")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    DocumentSnapshot doc = document;
                                    StringBuilder field1 = new StringBuilder("");
                                    String endDay = field1.append(doc.get("endDay")).toString();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    if(!endDay.equals("")) {
                                        Date date = null;
                                        try {
                                            date = sdf.parse(endDay);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(date);
                                        calendar.set(Calendar.HOUR_OF_DAY, 00);
                                        calendar.set(Calendar.MINUTE, 00);
                                        calendar.set(Calendar.SECOND, 00);
                                        calendar.set(Calendar.MILLISECOND, 00);
                                        long settime = calendar.getTimeInMillis();
                                        int id = calendar.get(Calendar.MONTH)+calendar.get(Calendar.DAY_OF_MONTH);
                                        Log.i("LOVE", String.valueOf(settime));
                                        Intent intent = new Intent(getActivity(), alarmReceiver.class);
                                        //        PendingIntent.getBroadcast調用廣播
                                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, 0);
                                        //        獲得AlarmManager物件
                                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getContext().ALARM_SERVICE);
                                        //        設定單次提醒
                                        alarmManager.set(AlarmManager.RTC_WAKEUP, settime, pendingIntent);
                                    }
                                }
                            } else {

                            }

                        }
                    });
        }

    }

    private int calculationCountdownDate(String count) throws ParseException {
        Calendar optiondate = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date specialdate = df.parse(count);
        optiondate.setTime(specialdate); //真實的日期為+1個月
        int showercountdownday = (int) Math.ceil(((optiondate.getTimeInMillis() - now.getTimeInMillis()) / (24 * 60 * 60 * 1000.0)));
        return showercountdownday;
    }
    private int dueDay(String start,String end) throws ParseException {
        String startday=start;
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date =sdf.parse(startday);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String endday = end;
        Date date1 =sdf.parse(endday);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        int dueday = (int) Math.ceil(((calendar1.getTimeInMillis() - calendar.getTimeInMillis()) / (24 * 60 * 60 * 1000.0)));
        return dueday;
    }

    public void getPetInfo(String query){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final TextView text_petsSex = getView().findViewById(R.id.text_petsSex);
        final TextView text_petsBirth = getView().findViewById(R.id.text_petsBirth);
        final TextView text_petsAge = getView().findViewById(R.id.text_petsAge);
        final TextView text_route = getView().findViewById(R.id.text_route);
        db.collection("users").document(userUID).collection("pets").whereEqualTo("petName", query)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentSnapshot doc = document;
                                StringBuilder fields = new StringBuilder("");
                                StringBuilder fields2 = new StringBuilder("");
                                StringBuilder fields3 = new StringBuilder("");
                                fields.append(doc.get("petGender")).toString();
                                fields2.append(doc.get("petBirth")).toString();
                                fields3.append(doc.get("breed")).toString();
                                text_petsSex.setText(fields);
                                text_petsBirth.setText(fields2);
                                text_route.setText(fields3);
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String petsBirth = fields2.toString();
                                try {
                                    Date Birth = df.parse(petsBirth);
                                    Date nowdate = new Date();
                                    Calendar ca1 = Calendar.getInstance();
                                    Calendar ca2 = Calendar.getInstance();
                                    ca1.setTime(Birth);
                                    ca2.setTime(nowdate);
                                    int year = ca2.get(Calendar.YEAR) - ca1.get(Calendar.YEAR);
                                    int month = ca2.get(Calendar.MONTH) - ca1.get(Calendar.MONTH);

                                    text_petsAge.setText(year + "歲" + month + "月");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {

                        }
                    }
                });    }
    public void getPetImage(String query){
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://ipets-app.appspot.com");
        StorageReference mStorageRef = storage.getReference();
        StorageReference islandRef = mStorageRef.child(userUID +'/'+ query+".jpg");
        final ImageView head = getView().findViewById(R.id.head);
        final long ONE_MEGABYTE = 3000 * 3000;

        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Glide.with(getContext())
                        .load(bytes)
                        .into(head);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors'
            }
        });
    }

    public void setCountdownDate(String countdownEvent){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                Calendar optiondate = Calendar.getInstance();
                Calendar now = Calendar.getInstance();
                optiondate.set(year, monthOfYear, dayOfMonth); //真實的日期為+1個月
                monthOfYear = monthOfYear+1;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
                String showercountdownday = formatter.format(curDate);
                String showerday = year+"-"+monthOfYear+"-"+dayOfMonth;
                Map<String, Object> countdowndate = new HashMap<>();
                countdowndate.put("startDay", showercountdownday);
                countdowndate.put("endDay", showerday);
                countdowndate.put("countdownEvent",countdownEvent);
                db.collection("users").document(userUID).collection("pets").document(documentname).collection("countdown").document(countdownEvent).set(countdowndate);
                setCountdownColor();
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void setCountdownColor(){
        if(!pet_query.equals("尚未擁有寵物")) {
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            ProgressBar showerBar = getView().findViewById(R.id.showerBar);
            ProgressBar hairCutBar = getView().findViewById(R.id.hairCutBar);
            ProgressBar fleaInBar = getView().findViewById(R.id.fleaInBar);
            ProgressBar fleaOutBar = getView().findViewById(R.id.fleaOutBar);
            ProgressBar injectionBar = getView().findViewById(R.id.injectionBar);
            ProgressBar teethBar = getView().findViewById(R.id.teethBar);
            ProgressBar bloodBar = getView().findViewById(R.id.bloodBar);
            db.collection("users").document(userUID).collection("pets").document(documentname).collection("countdown")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    DocumentSnapshot doc = document;
                                    StringBuilder field = new StringBuilder("");
                                    StringBuilder field1 = new StringBuilder("");
                                    StringBuilder field2 = new StringBuilder("");
                                    String startDay = field.append(doc.get("startDay")).toString();
                                    String endDay = field1.append(doc.get("endDay")).toString();
                                    String countdownEvent = field2.append(doc.get("countdownEvent")).toString();

                                    if (countdownEvent.equals(pet_query + "洗澡")) {
                                        try {
                                            int countdownday = dueDay(startDay, endDay);
                                            showerBar.setMax(countdownday);
                                            showerBar.setProgress(countdownday - calculationCountdownDate(endDay));
                                            Drawable progressDrawable1 = showerBar.getProgressDrawable().mutate();
                                            progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                            showerBar.setProgressDrawable(progressDrawable1);
                                            if (calculationCountdownDate(endDay) <= 0) {
                                                Drawable progressDrawable = showerBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.SRC_IN);
                                                showerBar.setProgressDrawable(progressDrawable);
                                            }
                                            if (calculationCountdownDate(endDay) <= countdownday * 0.5 && calculationCountdownDate(endDay) > 0) {
                                                Drawable progressDrawable = showerBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                                showerBar.setProgressDrawable(progressDrawable);
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (countdownEvent.equals(pet_query + "修剪毛髮")) {
                                        try {
                                            int haircutcountdownday = dueDay(startDay, endDay);
                                            hairCutBar.setMax(haircutcountdownday);
                                            hairCutBar.setProgress(haircutcountdownday - calculationCountdownDate(endDay));
                                            Drawable progressDrawable1 = hairCutBar.getProgressDrawable().mutate();
                                            progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                            hairCutBar.setProgressDrawable(progressDrawable1);
                                            if (calculationCountdownDate(endDay) <= 0) {
                                                Drawable progressDrawable = hairCutBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                                hairCutBar.setProgressDrawable(progressDrawable);
                                            }
                                            if (calculationCountdownDate(endDay) <= haircutcountdownday * 0.5 && calculationCountdownDate(endDay) > 0) {
                                                Drawable progressDrawable = hairCutBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                                hairCutBar.setProgressDrawable(progressDrawable);
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (countdownEvent.equals(pet_query + "體內除蟲")) {
                                        try {
                                            int fleaincountdownday = dueDay(startDay, endDay);
                                            fleaInBar.setMax(fleaincountdownday);
                                            fleaInBar.setProgress(fleaincountdownday - calculationCountdownDate(endDay));
                                            Drawable progressDrawable1 = fleaInBar.getProgressDrawable().mutate();
                                            progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                            fleaInBar.setProgressDrawable(progressDrawable1);
                                            if (calculationCountdownDate(endDay) <= 0) {
                                                Drawable progressDrawable = fleaInBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                                fleaInBar.setProgressDrawable(progressDrawable);
                                            }
                                            if (calculationCountdownDate(endDay) <= fleaincountdownday * 0.5 && calculationCountdownDate(endDay) > 0) {
                                                Drawable progressDrawable = fleaInBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                                fleaInBar.setProgressDrawable(progressDrawable);
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (countdownEvent.equals(pet_query + "體外除蟲")) {
                                        try {
                                            int fleaoutcountdownday = dueDay(startDay, endDay);
                                            fleaOutBar.setMax(fleaoutcountdownday);
                                            fleaOutBar.setProgress(fleaoutcountdownday - calculationCountdownDate(endDay));
                                            Drawable progressDrawable1 = fleaOutBar.getProgressDrawable().mutate();
                                            progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                            fleaOutBar.setProgressDrawable(progressDrawable1);
                                            if (calculationCountdownDate(endDay) <= 0) {
                                                Drawable progressDrawable = fleaOutBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                                fleaOutBar.setProgressDrawable(progressDrawable);
                                            }
                                            if (calculationCountdownDate(endDay) <= fleaoutcountdownday * 0.5 && calculationCountdownDate(endDay) > 0) {
                                                Drawable progressDrawable = fleaOutBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                                fleaOutBar.setProgressDrawable(progressDrawable);
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (countdownEvent.equals(pet_query + "注射")) {
                                        try {
                                            int injectioncountdownday = dueDay(startDay, endDay);
                                            injectionBar.setMax(injectioncountdownday);
                                            injectionBar.setProgress(injectioncountdownday - calculationCountdownDate(endDay));
                                            Drawable progressDrawable1 = injectionBar.getProgressDrawable().mutate();
                                            progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                            injectionBar.setProgressDrawable(progressDrawable1);
                                            if (calculationCountdownDate(endDay) <= 0) {
                                                Drawable progressDrawable = injectionBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                                injectionBar.setProgressDrawable(progressDrawable);
                                            }
                                            if (calculationCountdownDate(endDay) <= injectioncountdownday * 0.5 && calculationCountdownDate(endDay) > 0) {
                                                Drawable progressDrawable = injectionBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                                injectionBar.setProgressDrawable(progressDrawable);
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (countdownEvent.equals(pet_query + "看牙醫")) {
                                        try {
                                            int teethcountdownday = dueDay(startDay, endDay);
                                            teethBar.setMax(teethcountdownday);
                                            teethBar.setProgress(teethcountdownday - calculationCountdownDate(endDay));
                                            Drawable progressDrawable1 = teethBar.getProgressDrawable().mutate();
                                            progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                            teethBar.setProgressDrawable(progressDrawable1);
                                            if (calculationCountdownDate(endDay) <= 0) {
                                                Drawable progressDrawable = teethBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                                teethBar.setProgressDrawable(progressDrawable);
                                            }
                                            if (calculationCountdownDate(endDay) <= teethcountdownday * 0.5 && calculationCountdownDate(endDay) > 0) {
                                                Drawable progressDrawable = teethBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                                teethBar.setProgressDrawable(progressDrawable);
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (countdownEvent.equals(pet_query + "經期")) {
                                        try {
                                            int bloodcountdownday = dueDay(startDay, endDay);
                                            bloodBar.setMax(bloodcountdownday);
                                            bloodBar.setProgress(bloodcountdownday - calculationCountdownDate(endDay));
                                            Drawable progressDrawable1 = bloodBar.getProgressDrawable().mutate();
                                            progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                            bloodBar.setProgressDrawable(progressDrawable1);
                                            if (calculationCountdownDate(endDay) <= 0) {
                                                Drawable progressDrawable = bloodBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                                bloodBar.setProgressDrawable(progressDrawable);
                                            }
                                            if (calculationCountdownDate(endDay) <= bloodcountdownday * 0.5 && calculationCountdownDate(endDay) > 0) {
                                                Drawable progressDrawable = bloodBar.getProgressDrawable().mutate();
                                                progressDrawable.setColorFilter(0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                                bloodBar.setProgressDrawable(progressDrawable);
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            } else {

                            }

                        }
                    });
        }
    }
}
