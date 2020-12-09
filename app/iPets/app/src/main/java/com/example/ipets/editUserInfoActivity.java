package com.example.ipets;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class editUserInfoActivity extends AppCompatActivity{
    LinearLayout btn_editAcct;
    Button btn_editPWD;
    String email;
    int userBirth_year;
    int userBirth_month;
    int userBirth_date;
    String password;
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

        btn_editAcct = (LinearLayout) findViewById(R.id.btnAccount);
        btn_editAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editUserInfoActivity.this, editAccountActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });
        btn_editPWD = (Button) findViewById(R.id.btnPWD);
        btn_editPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPWD = new Intent(editUserInfoActivity.this, editPwdActivity.class);
                startActivity(intentPWD);
            }
        });
        toolbar3.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.check:
                        setUserInfo();
                        break;
                }
                return false;
            }
        });
        setEditText();
        getUserInfo();
    }
    public void setUserInfo(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userUID = currentUser.getUid();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        String[] Spinner_sex =getResources().getStringArray(R.array.Spinner_sex);
        TextInputEditText edmasterName = findViewById(R.id.masterName);
        TextInputEditText eduserName = findViewById(R.id.userName);
        EditText edmasterBirth = findViewById(R.id.masterBirth);
        TextInputEditText edphoneNum = findViewById(R.id.phoneNum);
        TextInputEditText edmasterAddress = findViewById(R.id.masterAddress);
        String masterName = edmasterName.getText().toString();
        String masterBirth = edmasterBirth.getText().toString();
        String userName = eduserName.getText().toString();
        String phoneNum  = edphoneNum .getText().toString();
        String masterAddress = edmasterAddress.getText().toString();
        Spinner sexSpinner = findViewById(R.id.sexSpinner);
        int idsex=sexSpinner.getSelectedItemPosition();
        String sex = Spinner_sex[idsex];
        String edBirth = edmasterBirth.getText().toString();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(edBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(userBirth_year == 0){
            userBirth_year = calendar.get(Calendar.YEAR);
            userBirth_month = calendar.get(Calendar.MONTH)+1;
            userBirth_date = calendar.get(Calendar.DAY_OF_MONTH);
        }
        if(sex.equals("女")){
            sex = "female";
        }
        if(sex.equals("男")){
            sex = "male";
        }
        if(sex.equals("不願透漏")){
            sex = "I prefer not to say";
        }
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name",masterName);
        userInfo.put("userName",userName);
        userInfo.put("userBirth", masterBirth);
        userInfo.put("phone", phoneNum);
        userInfo.put("address", masterAddress);
        userInfo.put("userGender", sex);
        userInfo.put("userBirth_year", userBirth_year);
        userInfo.put("userBirth_month", userBirth_month);
        userInfo.put("userBirth_date",userBirth_date);
        db.collection("users").document(userUID).update(userInfo);
        AlertDialog.Builder finishsignup = new AlertDialog.Builder(editUserInfoActivity.this);
        finishsignup.setMessage("修改成功");
        finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(editUserInfoActivity.this, homeActivity.class);
                startActivity(intent);
            }
        });
        finishsignup.setCancelable(false);
        finishsignup.show();
    }
    public void setEditText(){
        EditText edmasterBirth = findViewById(R.id.masterBirth);
        edmasterBirth.setInputType(InputType.TYPE_NULL); //不顯示系統輸入鍵盤
        edmasterBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            edmasterBirth.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            userBirth_year = year;
                            userBirth_month = monthOfYear+1;
                            userBirth_date = dayOfMonth;
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    DatePicker datePicker = datePickerDialog.getDatePicker();
                    datePicker.setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();

                }
            }
        });
    }
    public void getUserInfo(){
        EditText edmasterName = findViewById(R.id.masterName);
        final EditText eduserName = findViewById(R.id.userName);
        final EditText edmasterBirth = findViewById(R.id.masterBirth);
        final EditText edphoneNum = findViewById(R.id.phoneNum);
        final EditText edmasterAddress = findViewById(R.id.masterAddress);
        Spinner sexSpinner = findViewById(R.id.sexSpinner);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userUID = currentUser.getUid();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(userUID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    StringBuilder fields = new StringBuilder("");
                    StringBuilder fields2 = new StringBuilder("");
                    StringBuilder fields3 = new StringBuilder("");
                    StringBuilder fields4 = new StringBuilder("");
                    StringBuilder fields5 = new StringBuilder("");
                    StringBuilder fields6 = new StringBuilder("");
                    StringBuilder fields7 = new StringBuilder("");
                    StringBuilder fields8= new StringBuilder("");
                    fields.append(doc.get("name")).toString();
                    fields2.append(doc.get("userName")).toString();
                    fields3.append(doc.get("userBirth")).toString();
                    fields4.append(doc.get("phone")).toString();
                    fields5.append(doc.get("address")).toString();
                    String gender = fields6.append(doc.get("userGender")).toString();
                    email = fields7.append(doc.get("email")).toString();
                    password = fields8.append(doc.get("password")).toString();
                    edmasterName.setText(fields);
                    eduserName.setText(fields2);
                    edmasterBirth.setText(fields3);
                    edphoneNum.setText(fields4);
                    edmasterAddress.setText(fields5);
                    if(gender.equals("female")) {
                        sexSpinner.setSelection(1);
                    }
                    if(gender.equals("I prefer not to say")) {
                        sexSpinner.setSelection(2);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }
}
