package com.example.ipets;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class userinfoFragment extends Fragment {
    public userinfoFragment() {
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
                userInfo(v);
            }

            private void userInfo(View v) {
                EditText edmyname = getView().findViewById(R.id.myname);
                final EditText edmybirth = getView().findViewById(R.id.mybirth);
                final EditText edmyaddress = getView().findViewById(R.id.myaddress);
                String myname = edmyname.getText().toString();
                Integer mybirth = Integer.parseInt(edmybirth.getText().toString());
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
                userInfo.put("Myname", myname);
                userInfo.put("Mybirth", mybirth);
                userInfo.put("Mygender", mygender);
                userInfo.put("Myaddress", myaddress);
                db.collection("userInformation").document(userUID).update(userInfo);
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_userinfoFragment_to_petsinfoFragment);
            }
        });
    }


}
