package com.example.ipets;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {
    Button btn_info;
    Button btn_editPets;
    Button btn_question;
    Button btn_communicate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setTitle("設定");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar2.setTitleTextColor(Color.WHITE);

        btn_info = (Button) findViewById(R.id.masterInfo);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInfo = new Intent(SettingActivity.this,EditMasterinfoActivity.class);
                startActivity(intentInfo);
            }
        });


        btn_question = (Button) findViewById(R.id.question);
        btn_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentQuestion = new Intent(SettingActivity.this,EditQuestionActivity.class);
                startActivity(intentQuestion);
            }
        });

        btn_communicate = (Button) findViewById(R.id.communicate);
        btn_communicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCommunicate = new Intent(SettingActivity.this,EditContactusActivity.class);
                startActivity(intentCommunicate);
            }
        });

        Button signout = findViewById(R.id.signout);
        signout.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(SettingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
