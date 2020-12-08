package com.example.ipets;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class editPetInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    String userUID = currentUser.getUid();
    private final static int CAMERA = 1;
    private final static int PHOTO = 2;
    private DisplayMetrics mPhone;
    private static final String FILE_PATH = "/sdcard/ipets/jpeg";
    private ImageView img;
    int petBirth_year;
    int petBirth_month;
    int petBirth_date;
    String oldpet;
    String documentname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_petsinfo);

        Toolbar toolbar6 = findViewById(R.id.toolbar6);
        setSupportActionBar(toolbar6);
        getSupportActionBar().setTitle("編輯寵物資訊");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar6.setTitleTextColor(Color.WHITE);

        Spinner petSexSpinner = findViewById(R.id.petSexSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Spinner_petSex, R.layout.myspinner_layout);
        adapter.setDropDownViewResource(R.layout.myspinner_dropdown_layout);
        petSexSpinner.setAdapter(adapter);
        petSexSpinner.setOnItemSelectedListener(this);
        setEditText();
        toolbar6.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.check:
                        addImage();
                        break;
                }
                return false;
            }
        });
        ImageButton album = findViewById(R.id.album);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PHOTO);
            }
        });
        ImageButton camera1 = findViewById(R.id.camera1);
        camera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, CAMERA);
            }
        });
        getPetInfo();
        getPetImage();
    }
    private void getPetInfo() {
        EditText edmypetName = findViewById(R.id.mypetName);
        final EditText edpetsbirth = findViewById(R.id.mypetBirth);
        Spinner petSexSpinner = findViewById(R.id.petSexSpinner);
        EditText edhobbyText = findViewById(R.id.hobbyText);
        final EditText edremarkText = findViewById(R.id.remarkText);
        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        String pet = intent.getStringExtra("date");
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(userUID).collection("pets").whereEqualTo("petName", pet)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documentname = document.getId();
                                DocumentSnapshot doc = document;
                                StringBuilder fields = new StringBuilder("");
                                StringBuilder fields2 = new StringBuilder("");
                                StringBuilder fields3 = new StringBuilder("");
                                StringBuilder fields4 = new StringBuilder("");
                                StringBuilder fields5 = new StringBuilder("");
                                oldpet = fields.append(doc.get("petName")).toString();
                                String Petsgender = fields2.append(doc.get("petGender")).toString();
                                fields3.append(doc.get("petBirth")).toString();
                                fields4.append(doc.get("petHobby")).toString();
                                fields5.append(doc.get("petNote")).toString();
                                edmypetName.setText(fields);
                                edpetsbirth.setText(fields3);
                                edhobbyText.setText(fields4);
                                edremarkText.setText(fields5);
                                if(Petsgender.equals("女孩")) {
                                    petSexSpinner.setSelection(1);
                                }
                            }
                        } else {

                        }
                    }
                });
    }
    public void getPetImage(){
        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        String petid = intent.getStringExtra("petid");
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://ipets-5fd4f.appspot.com");
        StorageReference mStorageRef = storage.getReference();
        StorageReference islandRef = mStorageRef.child(userUID +'/'+ petid);
        img = findViewById(R.id.petsHead);
        final long ONE_MEGABYTE = 3000 * 3000;

        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Glide.with(getApplicationContext())
                        .load(bytes)
                        .into(img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors'
            }
        });
    }



    private void addImage() {
        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        String petid = intent.getStringExtra("petid");
        EditText edmypetName = findViewById(R.id.mypetName);
        final String mypetName = edmypetName.getText().toString();
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://ipets-5fd4f.appspot.com");
        StorageReference mStorageRef = storage.getReference();
        StorageReference mountainsRef = mStorageRef.child(userUID +'/'+ petid);

        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getMetadata() != null) {
                    if (taskSnapshot.getMetadata().getReference() != null) {
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                setPetInfo(imageUrl,mypetName);
                            }
                        });
                    }
                }
            }});

    }

    private void setPetInfo(String petsimage, String mypetName) {
        final EditText edpetsbirth = findViewById(R.id.mypetBirth);
        String petsbirth = edpetsbirth.getText().toString();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(petsbirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(petBirth_year == 0){
        petBirth_year = calendar.get(Calendar.YEAR);
        petBirth_month = calendar.get(Calendar.MONTH)+1;
        petBirth_date = calendar.get(Calendar.DAY_OF_MONTH);
        }
        EditText edhobbyText = findViewById(R.id.hobbyText);
        String hobbyText = edhobbyText.getText().toString();
        final EditText edremarkText = findViewById(R.id.remarkText);
        String remarkText = edremarkText.getText().toString();
        String[] petSex =getResources().getStringArray(R.array.Spinner_petSex);
        Spinner petSexSpinner = findViewById(R.id.petSexSpinner);
        int idsex=petSexSpinner.getSelectedItemPosition();
        String sex = petSex[idsex];
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("petImage", petsimage);
        userInfo.put("petName", mypetName);
        userInfo.put("petBirth", petsbirth);
        userInfo.put("petGender", sex);
        userInfo.put("petHobby",  hobbyText);
        userInfo.put("petNote", remarkText);
        userInfo.put("petBirth_year", petBirth_year);
        userInfo.put("petBirth_month", petBirth_month);
        userInfo.put("petBirth_date",petBirth_date);
        db.collection("users").document(userUID).collection("pets").document(documentname).update(userInfo);
        db.collection("users").document(userUID).collection("pets").document(documentname).collection("countdown")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String countdownid = document.getId();
                                db.collection("users").document(userUID).collection("pets").document(documentname).collection("countdown").document(countdownid)
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot doc = document;
                                            StringBuilder fields = new StringBuilder("");
                                            String countdownEvent = fields.append(doc.get("countdownEvent")).toString();
                                            if (countdownEvent.equals(oldpet+"洗澡")){
                                                countdownEvent = mypetName+"洗澡";
                                            }
                                            if (countdownEvent.equals(oldpet+"修剪毛髮")){
                                                countdownEvent = mypetName+"修剪毛髮";
                                            }
                                            if (countdownEvent.equals(oldpet+"體內除蟲")){
                                                countdownEvent = mypetName+"體內除蟲";
                                            }
                                            if (countdownEvent.equals(oldpet+"體外除蟲")){
                                                countdownEvent = mypetName+"體外除蟲";
                                            }
                                            if (countdownEvent.equals(oldpet+"注射")){
                                                countdownEvent = mypetName+"注射";
                                            }
                                            if (countdownEvent.equals(oldpet+"看牙醫")){
                                                countdownEvent = mypetName+"看牙醫";
                                            }
                                            if (countdownEvent.equals(oldpet+"經期")){
                                                countdownEvent = mypetName+"經期";
                                            }
                                            Map<String, Object> countdownInfo = new HashMap<>();
                                            countdownInfo.put("countdownEvent", countdownEvent);
                                            db.collection("users").document(userUID).collection("pets").document(documentname).collection("countdown").document(countdownid).update(countdownInfo);
                                        }
                                    }
                                });
                            }
                        }

                    }
                });
        AlertDialog.Builder finishsignup = new AlertDialog.Builder(editPetInfoActivity.this);
        finishsignup.setMessage("修改成功");
        finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(editPetInfoActivity.this, homeActivity.class);
                startActivity(intent);
            }
        });
        finishsignup.setCancelable(false);
        finishsignup.show();
    }
    public void setEditText(){
        EditText edmypetBirth = findViewById(R.id.mypetBirth);
        edmypetBirth.setInputType(InputType.TYPE_NULL); //不顯示系統輸入鍵盤
        edmypetBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            edmypetBirth.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            petBirth_year = year;
                            petBirth_month = monthOfYear+1;
                            petBirth_date = dayOfMonth;
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    DatePicker datePicker = datePickerDialog.getDatePicker();
                    datePicker.setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();

                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //讀取手機解析度
        mPhone = new DisplayMetrics();
        editPetInfoActivity.this.getWindowManager().getDefaultDisplay().getMetrics(mPhone);
        if (requestCode == PHOTO && data != null) {
            //取得照片路徑uri
            Uri uri = data.getData();
            ContentResolver cr = editPetInfoActivity.this.getContentResolver();
            try {
                //讀取照片，型態為Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
                if (bitmap.getWidth() > bitmap.getHeight()) scaleImage(bitmap,
                        mPhone.heightPixels);
                else scaleImage(bitmap, mPhone.widthPixels);
            } catch (FileNotFoundException e) {
            }
        }
        if (requestCode == CAMERA && data != null) {
            Bitmap photo = null;
            if (data.getData() != null || data.getExtras() != null) { //防止沒有返回結果
                Uri camerauri = data.getData();
                if (camerauri != null) {
                    photo = BitmapFactory.decodeFile(camerauri.getPath()); //拿到圖片
                }
                if (photo == null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        photo = (Bitmap) bundle.get("data");
                    }
                }
                //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
                if (photo.getWidth() > photo.getHeight()) scaleImage(photo,
                        mPhone.heightPixels);
                else scaleImage(photo, mPhone.widthPixels);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void scaleImage(Bitmap bitmap, int phone) {
        //縮放比例預設為1
        float mScale = 1;
        img = findViewById(R.id.petsHead);
        //如果圖片寬度大於手機寬度則進行縮放，否則直接將圖片放入ImageView內
        if (bitmap.getWidth() > phone) {
            //判斷縮放比例
            mScale = (float) phone / (float) bitmap.getWidth();

            Matrix mMat = new Matrix();
            mMat.setScale(mScale, mScale);

            Bitmap mScaleBitmap = Bitmap.createBitmap(bitmap,
                    0,
                    0,
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    mMat,
                    false);
            img.setImageBitmap(mScaleBitmap);
        } else img.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
