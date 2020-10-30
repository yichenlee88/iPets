package com.example.ipets;


import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
        db.collection("pets").whereEqualTo("uid", userUID).get()
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
                            ArrayAdapter adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item , petname);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            petnamespinner.setAdapter(adapter);
                            petnamespinner.setSelection(0, true);
                            String query = petname.get(0);
                            pet_query = query;
                            querypet(query);
                            getDocumentName();
                            downloadimage(query);
                            petnamespinner.setPrompt("請選擇");
                            petnamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    Log.d("homeFramgment 135", "onItemSelected: position: " + position);
                                    String query = petname.get(position);
                                    pet_query = query;
                                    if ( position == petname.size()-1){
                                        Intent intent=new Intent();
                                        intent.setClass(getActivity(),petsInfoActivity.class);
                                        startActivity(intent);
                                    }else{
                                        querypet(query);
                                        downloadimage(query);
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
                setcountdowndate(Shower);

            }
        });
        hairCutBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Haircut = pet_query + "修剪毛髮";
                setcountdowndate(Haircut);

            }
        });
        fleaInBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Fleain = pet_query + "體內除蟲";
                setcountdowndate(Fleain);

            }
        });
        fleaOutBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Fleaout = pet_query + "體外除蟲";
                setcountdowndate(Fleaout);

            }
        });
        injectionBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Injection = pet_query + "注射";
                setcountdowndate(Injection);

            }
        });
        teethBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Teeth = pet_query + "看牙醫";
                setcountdowndate(Teeth);

            }
        });
        bloodBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Blood = pet_query + "經期";
                setcountdowndate(Blood);

            }
        });
        Button btn_editPets =getView().findViewById(R.id.petInfoEdit);
        btn_editPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPets = new Intent(getActivity(),EditPetsinfoActivity.class);
                intentPets.putExtra("date",pet_query);
                startActivity(intentPets);
            }
        });
    }
    public void getDocumentName(){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("pets").whereEqualTo("petName", pet_query)
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
                        countdown();
                    }
                });
    }
    public void notification(){
        String id ="channel_1";//channel的id
        int importance = NotificationManager.IMPORTANCE_LOW;//channel的重要性
        NotificationChannel channel = new NotificationChannel(id, "123", importance);//生成channel
        //为channel添加属性
        channel.enableVibration(true);
        channel.enableLights(true);
        NotificationManager manager = (NotificationManager)getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);//添加channel
        Intent it = new Intent(getActivity(), HomeActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pit = PendingIntent.getActivity(getActivity(), 0, it,PendingIntent.FLAG_ONE_SHOT);
        Notification notification = new Notification.Builder(getActivity(),id)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setSmallIcon(R.drawable.app_logo1)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_logo1))
                .setContentTitle("倒數計時器")
                .setContentText("某項行程該做囉!點擊確認")
                .setContentIntent(pit)
                .setAutoCancel(true)
                .build();
        manager.notify(1,notification);

    }

    private int countdowndate(String count) throws ParseException {
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

    public void querypet(String query){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final TextView text_petsSex = getView().findViewById(R.id.text_petsSex);
        final TextView text_petsBirth = getView().findViewById(R.id.text_petsBirth);
        final TextView text_petsAge = getView().findViewById(R.id.text_petsAge);
        final TextView text_route = getView().findViewById(R.id.text_route);
        db.collection("pets").whereEqualTo("petName", query)
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
                                    int day = ca2.get(Calendar.DATE) - ca1.get(Calendar.DATE);
                                    if (month <= 0) {
                                        year--;
                                        month = month + 12;
                                    }
                                    if (day <= 0) {
                                        month--;
                                    }
                                    text_petsAge.setText(year + "歲" + month + "月");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {

                        }
                    }
                });    }
    public void downloadimage(String query){
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

    public void setcountdowndate(String countdownEvent){
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
                db.collection("pets").document(documentname).collection("countdown").document(countdownEvent).set(countdowndate);
                countdown();
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
    public void countdown(){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        ProgressBar showerBar = getView().findViewById(R.id.showerBar);
        ProgressBar hairCutBar = getView().findViewById(R.id.hairCutBar);
        ProgressBar fleaInBar = getView().findViewById(R.id.fleaInBar);
        ProgressBar fleaOutBar = getView().findViewById(R.id.fleaOutBar);
        ProgressBar injectionBar = getView().findViewById(R.id.injectionBar);
        ProgressBar teethBar = getView().findViewById(R.id.teethBar);
        ProgressBar bloodBar = getView().findViewById(R.id.bloodBar);
        db.collection("pets").document(documentname).collection("countdown")
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
                                String startDay =field.append(doc.get("startDay")).toString();
                                String endDay = field1.append(doc.get("endDay")).toString();
                                String countdownEvent = field2.append(doc.get("countdownEvent")).toString();

                                if(countdownEvent.equals(pet_query+"洗澡")){
                                    try {
                                        int countdownday = dueDay(startDay,endDay);
                                        showerBar.setMax(countdownday);
                                        showerBar.setProgress(countdownday-countdowndate(endDay));
                                        Drawable progressDrawable1 = showerBar.getProgressDrawable().mutate();
                                        progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                        showerBar.setProgressDrawable(progressDrawable1);
                                        if (countdowndate(endDay) <= 0){
                                            notification();
                                            Drawable progressDrawable = showerBar.getProgressDrawable().mutate();
                                            progressDrawable.setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.SRC_IN);
                                            showerBar.setProgressDrawable(progressDrawable);
                                        }
                                        if(countdowndate(endDay)<=countdownday*0.5 && countdowndate(endDay)>0){
                                            Drawable progressDrawable = showerBar.getProgressDrawable().mutate();
                                            progressDrawable.setColorFilter(0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                            showerBar.setProgressDrawable(progressDrawable);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if(countdownEvent.equals(pet_query+"修剪毛髮")){
                                    try {
                                        int haircutcountdownday = dueDay(startDay,endDay);
                                        hairCutBar.setMax(haircutcountdownday);
                                        hairCutBar.setProgress(haircutcountdownday-countdowndate(endDay));
                                        Drawable progressDrawable1 = hairCutBar.getProgressDrawable().mutate();
                                        progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                        hairCutBar.setProgressDrawable(progressDrawable1);
                                        if (countdowndate(endDay) <= 0){
                                            notification();
                                            Drawable progressDrawable = hairCutBar.getProgressDrawable().mutate();
                                            progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                            hairCutBar.setProgressDrawable(progressDrawable);
                                        }
                                        if(countdowndate(endDay)<=haircutcountdownday*0.5 && countdowndate(endDay)> 0){
                                            Drawable progressDrawable = hairCutBar.getProgressDrawable().mutate();
                                            progressDrawable.setColorFilter( 0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                            hairCutBar.setProgressDrawable(progressDrawable);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if(countdownEvent.equals(pet_query+"體內除蟲")){
                                    try {
                                        int fleaincountdownday = dueDay(startDay,endDay);
                                        fleaInBar.setMax(fleaincountdownday);
                                        fleaInBar.setProgress(fleaincountdownday-countdowndate(endDay));
                                        Drawable progressDrawable1 = fleaInBar.getProgressDrawable().mutate();
                                        progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                        fleaInBar.setProgressDrawable(progressDrawable1);
                                        if (countdowndate(endDay) <= 0){
                                            notification();
                                            Drawable progressDrawable = fleaInBar.getProgressDrawable().mutate();
                                            progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                            fleaInBar.setProgressDrawable(progressDrawable);
                                        }
                                        if(countdowndate(endDay)<=fleaincountdownday*0.5 && countdowndate(endDay)>0){
                                            Drawable progressDrawable = fleaInBar.getProgressDrawable().mutate();
                                            progressDrawable.setColorFilter(0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                            fleaInBar.setProgressDrawable(progressDrawable);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if(countdownEvent.equals(pet_query+"體外除蟲")){
                                    try {
                                        int fleaoutcountdownday = dueDay(startDay,endDay);
                                        fleaOutBar.setMax(fleaoutcountdownday);
                                        fleaOutBar.setProgress(fleaoutcountdownday-countdowndate(endDay));
                                        Drawable progressDrawable1 = fleaOutBar.getProgressDrawable().mutate();
                                        progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                        fleaOutBar.setProgressDrawable(progressDrawable1);
                                        if (countdowndate(endDay) <= 0){
                                            notification();
                                            Drawable progressDrawable =  fleaOutBar.getProgressDrawable().mutate();
                                            progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                            fleaOutBar.setProgressDrawable(progressDrawable);
                                        }
                                        if(countdowndate(endDay)<=fleaoutcountdownday*0.5 && countdowndate(endDay)>0){
                                            Drawable progressDrawable =  fleaOutBar.getProgressDrawable().mutate();
                                            progressDrawable.setColorFilter(0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                            fleaOutBar.setProgressDrawable(progressDrawable);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if(countdownEvent.equals(pet_query+"注射")){
                                    try {
                                        int injectioncountdownday = dueDay(startDay,endDay);
                                        injectionBar.setMax(injectioncountdownday);
                                        injectionBar.setProgress(injectioncountdownday-countdowndate(endDay));
                                        Drawable progressDrawable1 = injectionBar.getProgressDrawable().mutate();
                                        progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                        injectionBar.setProgressDrawable(progressDrawable1);
                                        if (countdowndate(endDay) <= 0){
                                            notification();
                                            Drawable progressDrawable =  injectionBar.getProgressDrawable().mutate();
                                            progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                            injectionBar.setProgressDrawable(progressDrawable);
                                        }
                                        if(countdowndate(endDay)<=injectioncountdownday*0.5 && countdowndate(endDay)> 0){
                                            Drawable progressDrawable =  injectionBar.getProgressDrawable().mutate();
                                            progressDrawable.setColorFilter(0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                            injectionBar.setProgressDrawable(progressDrawable);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if(countdownEvent.equals(pet_query+"看牙醫")){
                                    try {
                                        int teethcountdownday = dueDay(startDay,endDay);
                                        teethBar.setMax(teethcountdownday);
                                        teethBar.setProgress(teethcountdownday-countdowndate(endDay));
                                        Drawable progressDrawable1 = teethBar.getProgressDrawable().mutate();
                                        progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                        teethBar.setProgressDrawable(progressDrawable1);
                                        if (countdowndate(endDay) <= 0){
                                            notification();
                                            Drawable progressDrawable = teethBar.getProgressDrawable().mutate();
                                            progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                            teethBar.setProgressDrawable(progressDrawable);
                                        }
                                        if(countdowndate(endDay)<=teethcountdownday*0.5 && countdowndate(endDay)> 0){
                                            Drawable progressDrawable =  teethBar.getProgressDrawable().mutate();
                                            progressDrawable.setColorFilter(0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                            teethBar.setProgressDrawable(progressDrawable);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if(countdownEvent.equals(pet_query+"經期")){
                                    try {
                                        int bloodcountdownday = dueDay(startDay,endDay);
                                        bloodBar.setMax(bloodcountdownday);
                                        bloodBar.setProgress(bloodcountdownday-countdowndate(endDay));
                                        Drawable progressDrawable1 = bloodBar.getProgressDrawable().mutate();
                                        progressDrawable1.setColorFilter(0xFF0071BC, android.graphics.PorterDuff.Mode.SRC_IN);
                                        bloodBar.setProgressDrawable(progressDrawable1);
                                        if (countdowndate(endDay) <= 0){
                                            notification();
                                            Drawable progressDrawable = bloodBar.getProgressDrawable().mutate();
                                            progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                            bloodBar.setProgressDrawable(progressDrawable);
                                        }
                                        if(countdowndate(endDay)<=bloodcountdownday*0.5 && countdowndate(endDay)> 0){
                                            Drawable progressDrawable =  bloodBar.getProgressDrawable().mutate();
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
