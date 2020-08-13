package com.example.ipets;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
        db.collection("userInformation").document(userUID).collection("pets").document(query)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    StringBuilder fields = new StringBuilder("");
                    StringBuilder fields2 = new StringBuilder("");
                    fields.append(doc.get("Petsgender")).toString();
                    fields2.append(doc.get("Petsbirth")).toString();
                        text_petsSex.setText(fields);
                        text_petsBirth.setText(fields2);
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
                    StringBuilder fields = new StringBuilder("");
                    StringBuilder fields2 = new StringBuilder("");
                    //int countdownday = Integer.valueOf(fields.append(doc.get("Showercountdownday")).toString());
                    String showerday = fields2.append(doc.get("Showerday")).toString();
                    if(showerday !=""){
                         /*try {
                            showerBar.setMax(countdownday);
                            showerBar.setProgress(countdowndate(showerday));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } */
                    }
                }
            }
        });
    }
}
