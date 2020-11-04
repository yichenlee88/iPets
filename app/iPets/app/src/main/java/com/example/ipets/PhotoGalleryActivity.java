package com.example.ipets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryActivity extends AppCompatActivity {

    private GridView gridView;
    private ImageView imageView;
    FloatingActionButton bUploadImage; //上傳按鈕
    private List<String> thumbs;  //存放縮圖的id
    private List<String> imagePaths;  //存放圖片的路徑
    private ImageAdapter imageAdapter;  //用來顯示縮圖

    private static final int PICK_IMAGE = 100;


    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    String userUID = currentUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        Toolbar toolbar9 = findViewById(R.id.toolbar9);
        setSupportActionBar(toolbar9);
        getSupportActionBar().setTitle("我的寵物");
        toolbar9.setTitleTextColor(Color.WHITE);

        bUploadImage = findViewById(R.id.addPhoto);
        bUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(PhotoGalleryActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(PhotoGalleryActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            100);

                    return;
                }
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                gallery.setType("image/*");
                gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        gridView = findViewById(R.id.photoView);
        imageView = findViewById(R.id.imageView1);

        ContentResolver cr = getContentResolver();
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};

        //查詢SD卡的圖片
        Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);

        thumbs = new ArrayList<String>();
        imagePaths = new ArrayList<String>();

        for (int i = 0; i < cursor.getCount(); i++) {

            cursor.moveToPosition(i);
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));// ID
            thumbs.add(id + "");

            String filepath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));//抓路徑

            imagePaths.add(filepath);
        }

        cursor.close();

        imageAdapter = new ImageAdapter(PhotoGalleryActivity.this, thumbs);
        gridView.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                imageView.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);
            }

        });
        imageView.setVisibility(View.GONE);

    }

    public void setImageView(int position) {
        Bitmap bm = BitmapFactory.decodeFile(imagePaths.get(position));
        imageView.setImageBitmap(bm);
        imageView.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
    }



}






