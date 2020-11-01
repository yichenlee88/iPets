package com.example.ipets;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class userInfoFragment extends Fragment {
    int userBirth_year;
    int userBirth_month;
    int userBirth_date;
    public userInfoFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_userinfo, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Button next2 = getView().findViewById(R.id.next2);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserInfo(v);
            }

        });
        final EditText mybirth = getView().findViewById(R.id.mybirth);
        mybirth.setInputType(InputType.TYPE_NULL); //不顯示系統輸入鍵盤
        mybirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            mybirth.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
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
    private void addUserInfo(View v) {
        EditText edmyname = getView().findViewById(R.id.myname);
        final EditText edmybirth = getView().findViewById(R.id.mybirth);
        final EditText edmyaddress = getView().findViewById(R.id.myaddress);
        String myname = edmyname.getText().toString();
        String mybirth = edmybirth.getText().toString();
        String myaddress = edmyaddress.getText().toString();
        String mygender = null;
        RadioGroup genderselect = getView().findViewById(R.id.genderselect);
        switch(genderselect.getCheckedRadioButtonId()){
            case R.id.male: //case mRadioButton0.getId():
                mygender = "男";
                break;
            case R.id.female: //case mRadioButton1.getId():
                mygender = "女";
                break;
            case R.id.unwilling: //case mRadioButton2.getId():
                mygender = "不願透漏";
                break;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userUID = currentUser.getUid();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", myname);
        userInfo.put("userBirth", mybirth);
        userInfo.put("userGender", mygender);
        //phone 電話
        userInfo.put("address", myaddress);
        userInfo.put("userBirth_year", userBirth_year);
        userInfo.put("userBirth_month", userBirth_month);
        userInfo.put("userBirth_date",userBirth_date);
        db.collection("users").document(userUID).update(userInfo);
        AlertDialog.Builder finishsignup = new AlertDialog.Builder(getActivity());
        finishsignup.setMessage("歡迎加入iPets的大家庭~!");
        finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(getActivity(),HomeActivity.class);
                startActivity(intent);
            }
        });
        finishsignup.setCancelable(false);
        finishsignup.show();

    }


}
