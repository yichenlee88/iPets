package com.example.ipets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class photoGalleryActivity extends AppCompatActivity {


    FloatingActionButton btnChoose; //上傳按鈕
    private GridView mGridView; //顯示手機里的所有圖片的列表控件
    private ImageView imageView;
    private Uri ImageUri;
    Button btnUpload;
    TextView alert;

    private int upload_count = 0;
    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    String userUID = currentUser.getUid();

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    ArrayList<Uri> ImageList = new ArrayList<Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        Toolbar toolbar9 = findViewById(R.id.toolbar9);
        setSupportActionBar(toolbar9);
        getSupportActionBar().setTitle("我的寵物");
        toolbar9.setTitleTextColor(Color.WHITE);

        storage = FirebaseStorage.getInstance("gs://ipets-app.appspot.com");
        storageReference = storage.getReference();

        btnChoose = findViewById(R.id.addPhoto);
        btnUpload = findViewById(R.id.btnupload);
        imageView = findViewById(R.id.photoGalleryImage1);
        alert = findViewById(R.id.alert);


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                addPhotoIntent.setType("image/*");
                addPhotoIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(addPhotoIntent,PICK_IMAGE_REQUEST);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

    }

    private void uploadImage() {
        StorageReference ImageFolder = storageReference.child(userUID + '/' + "Gallery/");

        for (upload_count = 0; upload_count < ImageList.size(); upload_count++) {
            Uri IndividualImage = ImageList.get(upload_count);

            StorageReference ImageName = ImageFolder.child(IndividualImage.getLastPathSegment());

            ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = String.valueOf(uri);
                            StoreLink(url);
                            Toast.makeText(photoGalleryActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(photoGalleryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void StoreLink (String url){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("UserOne");
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("Imglink", url);
            databaseReference.push().setValue(hashMap);
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST) {
                if (resultCode == RESULT_OK) {
                    if (data.getClipData() != null) {
                        int countClipData = data.getClipData().getItemCount();
                        int currentImageSelect = 0;
                        while (currentImageSelect < countClipData) {
                            ImageUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                            ImageList.add(ImageUri);
                            currentImageSelect = currentImageSelect + 1;
                        }
                        alert.setText("You have Selected" + ImageList.size() + "Images");

                    } else {
                        Toast.makeText(this, "Please Select Mutiple Image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }




