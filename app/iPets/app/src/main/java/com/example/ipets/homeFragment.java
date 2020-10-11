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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
    String pet_query = null;

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
        db.collection("userInformation").document(userUID).collection("pets").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                petname.add(document.getId());
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
                            downloadimage(query);
                            countdown(query);
                            petnamespinner.setPrompt("請選擇");
                            petnamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    Log.d("homeFramgment 135", "onItemSelected: position: " + position);
                                    String query = petname.get(position);
                                    pet_query = query;
                                    if ( position == petname.size()-1){
                                        NavController controller = Navigation.findNavController(getView());
                                        controller.navigate(R.id.action_navigation_home_to_petsinfoFragment2);
                                    }else{
                                        querypet(query);
                                        downloadimage(query);
                                        countdown(query);
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
                String Showercountdownday = "Showercountdownday";
                String Showerday=  "Showerday";
                setcountdowndate(Showercountdownday,Showerday);

            }
        });
        hairCutBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Haircutcountdownday = "Haircutcountdownday";
                String Haircutday=  "Haircutday";
                setcountdowndate(Haircutcountdownday,Haircutday);

            }
        });
        fleaInBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Fleaincountdownday = "Fleaincountdownday";
                String Fleainday=  "Fleainday";
                setcountdowndate(Fleaincountdownday,Fleainday);

            }
        });
        fleaOutBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Fleaoutcountdownday = "Fleaoutcountdownday";
                String Fleaoutday=  "Fleaoutday";
                setcountdowndate(Fleaoutcountdownday,Fleaoutday);

            }
        });
        injectionBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Injectioncountdownday = "Injectioncountdownday";
                String Injectionday=  "Injectionday";
                setcountdowndate(Injectioncountdownday,Injectionday);

            }
        });
        teethBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Teethcountdownday = "Teethcountdownday";
                String Teethday=  "Teethday";
                setcountdowndate(Teethcountdownday,Teethday);

            }
        });
        bloodBar.setOnClickListener(new ProgressBar.OnClickListener() {
            public void onClick(View v) {
                String Bloodcountdownday = "Bloodcountdownday";
                String Bloodday=  "Bloodday";
                setcountdowndate(Bloodcountdownday,Bloodday);

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
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date specialdate = df.parse(count);
        optiondate.setTime(specialdate); //真實的日期為+1個月
        int showercountdownday = (int) Math.ceil(((optiondate.getTimeInMillis() - now.getTimeInMillis()) / (24 * 60 * 60 * 1000.0)));
        return showercountdownday;
    }

    public void querypet(String query){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final TextView text_petsSex = getView().findViewById(R.id.text_petsSex);
        final TextView text_petsBirth = getView().findViewById(R.id.text_petsBirth);
        final TextView text_petsAge = getView().findViewById(R.id.text_petsAge);
        final TextView text_route = getView().findViewById(R.id.text_route);
        db.collection("userInformation").document(userUID).collection("pets").document(query)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    StringBuilder fields = new StringBuilder("");
                    StringBuilder fields2 = new StringBuilder("");
                    StringBuilder fields3 = new StringBuilder("");
                    fields.append(doc.get("Petsgender")).toString();
                    fields2.append(doc.get("Petsbirth")).toString();
                    fields3.append(doc.get("Petsvariety")).toString();
                        text_petsSex.setText(fields);
                        text_petsBirth.setText(fields2);
                        text_route.setText(fields3);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
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
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
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

    public void setcountdowndate(String countdownday,String countdown){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                Calendar optiondate = Calendar.getInstance();
                Calendar now = Calendar.getInstance();
                optiondate.set(year, monthOfYear, dayOfMonth); //真實的日期為+1個月
                int showercountdownday = (int) Math.ceil(((optiondate.getTimeInMillis() - now.getTimeInMillis()) / (24 * 60 * 60 * 1000.0)));
                monthOfYear = monthOfYear+1;
                String showerday = year+"/"+monthOfYear+"/"+dayOfMonth;
                Map<String, Object> countdowndate = new HashMap<>();
                countdowndate.put(countdownday, showercountdownday);
                countdowndate.put(countdown, showerday);
                db.collection("userInformation").document(userUID).collection("pets").document(pet_query).update(countdowndate);
                countdown(pet_query);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
    public void countdown(String query){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        ProgressBar showerBar = getView().findViewById(R.id.showerBar);
        ProgressBar hairCutBar = getView().findViewById(R.id.hairCutBar);
        ProgressBar fleaInBar = getView().findViewById(R.id.fleaInBar);
        ProgressBar fleaOutBar = getView().findViewById(R.id.fleaOutBar);
        ProgressBar injectionBar = getView().findViewById(R.id.injectionBar);
        ProgressBar teethBar = getView().findViewById(R.id.teethBar);
        ProgressBar bloodBar = getView().findViewById(R.id.bloodBar);
        db.collection("userInformation").document(userUID).collection("pets").document(pet_query)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    StringBuilder Showercountdownday = new StringBuilder("");StringBuilder Showerday = new StringBuilder("");
                    StringBuilder Haircutcountdownday = new StringBuilder("");StringBuilder Haircutday = new StringBuilder("");
                    StringBuilder Fleaincountdownday = new StringBuilder("");StringBuilder Fleainday = new StringBuilder("");
                    StringBuilder Fleaoutcountdownday = new StringBuilder("");StringBuilder Fleaoutday = new StringBuilder("");
                    StringBuilder Injectioncountdownday = new StringBuilder("");StringBuilder Injectionday = new StringBuilder("");
                    StringBuilder Teethcountdownday = new StringBuilder("");StringBuilder Teethday = new StringBuilder("");
                    StringBuilder Bloodcountdownday = new StringBuilder("");StringBuilder Bloodday = new StringBuilder("");
                    String showerday = Showerday.append(doc.get("Showerday")).toString();
                    String haircutday = Haircutday.append(doc.get("Haircutday")).toString();
                    String fleainday = Fleainday.append(doc.get("Fleainday")).toString();
                    String fleaoutday = Fleaoutday.append(doc.get("Fleaoutday")).toString();
                    String injectionday = Injectionday.append(doc.get("Injectionday")).toString();
                    String teethday = Teethday.append(doc.get("Teethday")).toString();
                    String bloodday = Bloodday.append(doc.get("Bloodday")).toString();
                    if(!showerday.equals("null")){
                        try {
                            int countdownday = Integer.valueOf(Showercountdownday.append(doc.get("Showercountdownday")).toString());
                            showerBar.setMax(countdownday);
                            showerBar.setProgress(countdownday-countdowndate(showerday));
                            if (countdowndate(showerday) == 0){
                                notification();
                                Drawable progressDrawable = showerBar.getProgressDrawable().mutate();
                                progressDrawable.setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.SRC_IN);
                                showerBar.setProgressDrawable(progressDrawable);
                            }
                            if(countdowndate(showerday)<=countdownday*0.6 && countdowndate(showerday)!=0){
                                Drawable progressDrawable = showerBar.getProgressDrawable().mutate();
                                progressDrawable.setColorFilter(0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                showerBar.setProgressDrawable(progressDrawable);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(!haircutday.equals("null")){
                        try {
                            int haircutcountdownday = Integer.valueOf(Haircutcountdownday.append(doc.get("Haircutcountdownday")).toString());
                            hairCutBar.setMax(haircutcountdownday);
                            hairCutBar.setProgress(haircutcountdownday-countdowndate(haircutday));
                            if (countdowndate(haircutday) == 0){
                                notification();
                                Drawable progressDrawable = hairCutBar.getProgressDrawable().mutate();
                                progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                hairCutBar.setProgressDrawable(progressDrawable);
                            }
                            if(countdowndate(haircutday)<=haircutcountdownday*0.6 && countdowndate(haircutday)!=0){
                                Drawable progressDrawable = hairCutBar.getProgressDrawable().mutate();
                                progressDrawable.setColorFilter( 0xFFFF6600, android.graphics.PorterDuff.Mode.SRC_IN);
                                hairCutBar.setProgressDrawable(progressDrawable);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(!fleainday.equals("null")){
                        try {
                            int fleaincountdownday = Integer.valueOf(Fleaincountdownday.append(doc.get("Fleaincountdownday")).toString());
                            fleaInBar.setMax(fleaincountdownday);
                            fleaInBar.setProgress(fleaincountdownday-countdowndate(fleainday));
                            if (countdowndate(fleainday) == 0){
                                notification();
                                Drawable progressDrawable = fleaInBar.getProgressDrawable().mutate();
                                progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                fleaInBar.setProgressDrawable(progressDrawable);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(!fleaoutday.equals("null")){
                        try {
                            int fleaoutcountdownday = Integer.valueOf(Fleaoutcountdownday.append(doc.get("Fleaoutcountdownday")).toString());
                            fleaOutBar.setMax(fleaoutcountdownday);
                            fleaOutBar.setProgress(fleaoutcountdownday-countdowndate(fleaoutday));
                            if (countdowndate(fleaoutday) == 0){
                                notification();
                                Drawable progressDrawable =  fleaOutBar.getProgressDrawable().mutate();
                                progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                fleaOutBar.setProgressDrawable(progressDrawable);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(!injectionday.equals("null")){
                        try {
                            int injectioncountdownday = Integer.valueOf(Injectioncountdownday.append(doc.get("Injectioncountdownday")).toString());
                            injectionBar.setMax(injectioncountdownday);
                            injectionBar.setProgress(injectioncountdownday-countdowndate(injectionday));
                            if (countdowndate(injectionday) == 0){
                                notification();
                                Drawable progressDrawable =  injectionBar.getProgressDrawable().mutate();
                                progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                injectionBar.setProgressDrawable(progressDrawable);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(!teethday.equals("null")){
                        try {
                            int teethcountdownday = Integer.valueOf(Teethcountdownday.append(doc.get("Teethcountdownday")).toString());
                            teethBar.setMax(teethcountdownday);
                            teethBar.setProgress(teethcountdownday-countdowndate(teethday));
                            if (countdowndate(teethday) == 0){
                                notification();
                                Drawable progressDrawable = teethBar.getProgressDrawable().mutate();
                                progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                teethBar.setProgressDrawable(progressDrawable);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(!bloodday.equals("null")){
                        try {
                            int bloodcountdownday = Integer.valueOf(Bloodcountdownday.append(doc.get("Bloodcountdownday")).toString());
                            bloodBar.setMax(bloodcountdownday);
                            bloodBar.setProgress(bloodcountdownday-countdowndate(bloodday));
                            if (countdowndate(bloodday) == 0){
                                notification();
                                Drawable progressDrawable = bloodBar.getProgressDrawable().mutate();
                                progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                bloodBar.setProgressDrawable(progressDrawable);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
