package com.example.ipets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class photoGalleryActivity extends AppCompatActivity {


    private static final String TAG = "photoGalleryActivity";
    FloatingActionButton btnChoose; //上傳按鈕
    private GridView mGridView; //顯示手機里的所有圖片的列表控件
    private ImageView imageView,imageView2;
    private Uri ImageUri,ImagesUri;
    Button btnUpload;
    TextView alert;

    private List<String> thumbs;  //存放縮圖的id
    private List<String> imagePaths;  //存放圖片的路徑
    private com.example.ipets.imageAdapter imageAdapter;  //用來顯示縮圖

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
        imageView2 = findViewById(R.id.photoGalleryImage2);
        alert = findViewById(R.id.alert);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(photoGalleryActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(photoGalleryActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            100);

                    return;
                }
                //使用Intent啟動文件供應程式
                Intent addPhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                addPhotoIntent.setType("image/*");
                addPhotoIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(addPhotoIntent, PICK_IMAGE_REQUEST);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


        mGridView = findViewById(R.id.photoView);
        thumbs = new ArrayList<String>(); //存放縮圖的id
        imagePaths = new ArrayList<String>();//存放圖片的路徑

        imageAdapter = new imageAdapter(photoGalleryActivity.this, thumbs);
        mGridView.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                imageView.setVisibility(View.GONE);
                mGridView.setVisibility(View.VISIBLE);
            }
        });
        imageView.setVisibility(View.GONE);

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
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
    //onActivityResult()處理回傳結果
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {

/*
                //選一個得先不理它，上船不了也顯示不了
                if (data.getData()!=null) {      // select one image //getData()處理選取1個檔案
                    ImageUri  = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri);
                        imageView.setImageBitmap(bitmap);
                        Log.i(TAG, "Uri: " + ImageUri.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

 */

                if (data.getClipData() != null) { // select 多個 image //getClipData()處理選取多個檔案
                    int countClipData = data.getClipData().getItemCount();
                    int currentImageSelect = 0;

                    while (currentImageSelect < countClipData) {
                        ImagesUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                        ImageList.add(ImagesUri);
                        imageView2.setImageURI(ImagesUri);

                        currentImageSelect = currentImageSelect + 1;
                        Log.i(TAG, "Uri: " + ImagesUri .toString());
                    }
                    alert.setText("You have Selected" + ImageList.size() + "Images");
                }
                else {
                    Toast.makeText(this, "Please Select Mutiple Image", Toast.LENGTH_SHORT).show();
                }
            }

    }

    public void setImageView(int position) {
        Bitmap bm = BitmapFactory.decodeFile(imagePaths.get(position));
        imageView.setImageBitmap(bm);
        imageView.setVisibility(View.VISIBLE);
        mGridView.setVisibility(View.GONE);
    }

}




