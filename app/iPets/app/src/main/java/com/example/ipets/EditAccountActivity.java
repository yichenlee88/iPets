package com.example.ipets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Toolbar toolbar4 = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar4);
        getSupportActionBar().setTitle("變更帳號 (Email)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar4.setTitleTextColor(Color.WHITE);
        toolbar4.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.check:
                        setaccount();
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
    public void setaccount(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userUID = currentUser.getUid();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        TextInputEditText editAcct = findViewById(R.id.editAcct);
        String email = editAcct.getText().toString();
        currentUser.updateEmail(email);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("Email",email);
        db.collection("userInformation").document(userUID).update(userInfo);
        AlertDialog.Builder finishsignup = new AlertDialog.Builder(EditAccountActivity.this);
        finishsignup.setMessage("修改成功");
        finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(EditAccountActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        finishsignup.setCancelable(false);
        finishsignup.show();
    }
}
