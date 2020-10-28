package com.example.ipets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PhotoGalleryActivity extends AppCompatActivity {
    private GridView photoView;
    private final static int ADDPHOTO = 1;
    private static final String FILE_PATH = "/sdcard/ipets/jpeg";
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    String userUID = currentUser.getUid();
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        mGridView = findViewById(R.id.photoView);
        FloatingActionButton addPhoto = (FloatingActionButton) findViewById(R.id.addPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPhotoIntent = new Intent();
                addPhotoIntent.setType("image/*");
                addPhotoIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(addPhotoIntent,ADDPHOTO);
            }
        });
        /*mGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimage();
            }
        });*/



        Toolbar toolbar9 = findViewById(R.id.toolbar9);
        setSupportActionBar(toolbar9);
        getSupportActionBar().setTitle("我的寵物");
        toolbar9.setTitleTextColor(Color.WHITE);
    }

    /*private void uploadimage() {


    }*/
}
