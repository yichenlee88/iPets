package com.example.ipets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editPwdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pwd);

        Toolbar toolbar5 = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar5);
        getSupportActionBar().setTitle("變更密碼");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar5.setTitleTextColor(Color.WHITE);
        toolbar5.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.check:
                        setPwd();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }
    public void setPwd(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userUID = currentUser.getUid();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        EditText edoldPWD = findViewById(R.id.oldPWD);
        EditText ednewPWD = findViewById(R.id.newPWD);
        EditText edcheckPWD = findViewById(R.id.checkPWD);
        String oldPWD = edoldPWD.getText().toString();
        String newPWD = ednewPWD.getText().toString();
        String checkPWD = edcheckPWD.getText().toString();
        String email =  currentUser.getEmail();
        if(newPWD.equals(checkPWD)) {
            AuthCredential credential = EmailAuthProvider
                    .getCredential( email, oldPWD);
// Prompt the user to re-provide their sign-in credentials
            currentUser.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            currentUser.updatePassword(checkPWD);
                        }
                    });
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("password", checkPWD);
            db.collection("users").document(userUID).update(userInfo);
            AlertDialog.Builder finishsignup = new AlertDialog.Builder(editPwdActivity.this);
            finishsignup.setMessage("修改成功");
            finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(editPwdActivity.this, homeActivity.class);
                    startActivity(intent);
                }
            });
            finishsignup.setCancelable(false);
            finishsignup.show();
        }else{
            AlertDialog.Builder finishsignup = new AlertDialog.Builder(editPwdActivity.this);
            finishsignup.setMessage("修改失敗，點擊確認重新修改");
            finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent intent = new Intent();
                    intent.setClass(editPwdActivity.this, editPwdActivity.class);
                    startActivity(intent);
                }
            });
            finishsignup.setCancelable(false);
            finishsignup.show();
        }
    }
}
