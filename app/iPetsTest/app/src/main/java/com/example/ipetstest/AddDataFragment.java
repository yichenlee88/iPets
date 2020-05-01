package com.example.ipetstest;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddDataFragment extends Fragment {


    public AddDataFragment() {
        // Required empty public constructor
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button sent = getView().findViewById(R.id.button4);
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewContact();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_data, container, false);
    }
    private void addNewContact() {
        final String PASSWORD_KEY = "password";
        final String GENDER_KEY = "gender";
        EditText editaccount = getView().findViewById(R.id.editText);
        EditText editpassword = getView().findViewById(R.id.editText2);
        EditText editgender = getView().findViewById(R.id.editText3);
        final String account = editaccount.getText().toString();
        final String password = editpassword.getText().toString();
        final String gender = editgender.getText().toString();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        Map<String, Object> newContact = new HashMap<>();
        newContact.put(PASSWORD_KEY, password);
        newContact.put(GENDER_KEY, gender);
        db.collection("userInformation").document(account).set(newContact)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "User Registered",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
