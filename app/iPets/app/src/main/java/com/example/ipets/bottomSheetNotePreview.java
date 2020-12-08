package com.example.ipets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class bottomSheetNotePreview extends BottomSheetDialogFragment {
    String colorselect = null;
    private String title;
    private String content;
    private String id;
    public bottomSheetNotePreview(String title, String content,String id){
        this.title = title;
        this.content = content;
        this.id = id;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_notepreview, container, false);
        TextView txtTitle = v.findViewById(R.id.editNoteText);
        txtTitle.setText(this.title);
        TextView txtContent = v.findViewById(R.id.noteContentText);
        txtContent.setText(this.content);
        return v;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RadioGroup eventColor2 = getView().findViewById(R.id.eventColor3);
        eventColor2.check(R.id.redCircle2);
        Button editButton = getView().findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEvent();
            }
        });
        Button deleteButton = getView().findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent();
            }
        });

    }

    private void deleteEvent() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userUID = currentUser.getUid();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(userUID).collection("calEvent").document(id)
                .delete();

        AlertDialog.Builder finishdelete = new AlertDialog.Builder(getActivity());
        finishdelete.setMessage("刪除成功");
        finishdelete.setNegativeButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(getActivity(),calendarActivity.class);
                startActivity(intent);
            }
        });
                        finishdelete.setCancelable(false);
                        finishdelete.show();


    }

    private void setEvent() {
        EditText editNoteText = getView().findViewById(R.id.editNoteText);
        EditText noteContentText = getView().findViewById(R.id.noteContentText);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userUID = currentUser.getUid();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        String Note = editNoteText.getText().toString();
        String Content = noteContentText.getText().toString();
        RadioGroup eventColor3 = getView().findViewById(R.id.eventColor3);
        switch (eventColor3.getCheckedRadioButtonId()) {
            case R.id.redCircle2:
                colorselect = "red";
                break;
            case R.id.orangeCircle2:
                colorselect = "orange";
                break;
            case R.id.yellowCircle2:
                colorselect = "yellow";
                break;
            case R.id.greenCircle2:
                colorselect = "green";
                break;
        }
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name",Note);
        userInfo.put("details",Content);
        userInfo.put("color", colorselect);
        db.collection("users").document(userUID).collection("calEvent").document(id).update(userInfo);
        AlertDialog.Builder finishsignup = new AlertDialog.Builder(getActivity());
        finishsignup.setMessage("修改成功");
        finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(getActivity(),calendarActivity.class);
                startActivity(intent);
            }
        });
        finishsignup.setCancelable(false);
        finishsignup.show();
    }
}
