package com.example.ipets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class photoGalleryActivity extends AppCompatActivity {

    private Uri filepath,ImagesUri;
    private ImageView imageView,imageView2;
    private final int PICK_IMAGE_REQUEST = 71;
    private static final String TAG = "photoGalleryActivity";

    Button btnUpload; //上傳至Storage按鈕
    FloatingActionButton btnChoose; //打開相簿選取並顯示按鈕
    ArrayList<Uri> ImageList = new ArrayList<Uri>();

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    String userUID = currentUser.getUid();
    FirebaseStorage storage; // instance for firebase storage
    StorageReference storageReference; // instance for firebase StorageReference

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

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                addPhotoIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                addPhotoIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(addPhotoIntent,"Select Picture"),PICK_IMAGE_REQUEST);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

    }

    //上傳照片至Storage
    private void uploadImage() {

        //上傳多張照片至Gallery
        StorageReference ImageFolder = storageReference.child(userUID + '/' + "Gallery/");
        int upload_count;
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
                            Glide.with(photoGalleryActivity.this).load(uri).into(imageView); //選取照片後所呈現的資料庫裡的照片
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

        //上傳一張照片至gallery
        if (filepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child(userUID +'/' +"gallery/" + UUID.randomUUID().toString());
            ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Glide.with(photoGalleryActivity.this).load(filepath).into(imageView2); //選取照片後所呈現的資料庫裡的照片
                    Toast.makeText(photoGalleryActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(photoGalleryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
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

    //接收啟動相簿結果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data.getData() != null) {   //getData()處理選取1個檔案
                filepath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                    imageView2.setImageBitmap(bitmap); //選取照片後所呈現的本端照片
                    Log.i(TAG, "Uri: " + filepath.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (data.getClipData() != null) {  //getClipData()處理選取多個檔案
                int countClipData = data.getClipData().getItemCount();
                int currentImageSelect = 0;

                while (currentImageSelect < countClipData) {
                    ImagesUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                    ImageList.add(ImagesUri);
                    Log.d("ImageName", "onSuccess: url " + ImagesUri);
                    imageView.setImageURI(ImagesUri); //選取照片後所呈現的本端照片
                    currentImageSelect = currentImageSelect + 1;
                    Log.i(TAG, "Uri: " + ImagesUri.toString());
                }
            }
        }
    }

}





