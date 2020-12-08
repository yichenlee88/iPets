package com.example.ipets;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class petInfoActivity extends AppCompatActivity {
    String petid;
    private ImageView img;
    private DisplayMetrics mPhone;
    private final static int CAMERA = 1;
    private final static int PHOTO = 2;
    private static final String FILE_PATH = "/sdcard/ipets/jpeg";
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    String userUID = currentUser.getUid();
    int petBirth_year;
    int petBirth_month;
    int petBirth_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_info);
        getPermission();

        Spinner petVarietySpinner = findViewById(R.id.variety);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(petInfoActivity.this, R.array.Spinner_variety, R.layout.myspinner_layout);
        adapter.setDropDownViewResource(R.layout.myspinner_dropdown_layout);
        petVarietySpinner.setAdapter(adapter);

        ImageButton camera = findViewById(R.id.camera1);
        ImageButton photo = findViewById(R.id.album);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.media.action.IMAGE_CAPTURE");
                File file = new File(FILE_PATH);
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri uri = FileProvider.getUriForFile(
                        v.getContext(),
                        petInfoActivity.this.getPackageName() + ".provider",
                        file);
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, CAMERA);
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //開啟相簿，須由startActivityForResult且帶入requestCode進行呼叫，原因為點選相片後返回程式呼叫onActivityResult
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PHOTO);
            }
        });
        Button finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImage();
            }
        });
        final EditText edpetsbirth = findViewById(R.id.petsbirth);
        edpetsbirth.setInputType(InputType.TYPE_NULL); //不顯示系統輸入鍵盤
        edpetsbirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            edpetsbirth.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
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

    private void addImage() {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://ipets-5fd4f.appspot.com");
        StorageReference mStorageRef = storage.getReference();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        petid = db.collection("users").document(userUID).collection("pets").document().getId();
        StorageReference mountainsRef = mStorageRef.child(userUID +'/'+ petid);
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        //取得照片網址
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
                                addPetInfo(imageUrl);
                            }
                        });
                    }
                }
            }});


    }

    private void addPetInfo(String uri) {
        EditText edpetsname = findViewById(R.id.petsname);
        final EditText edpetsbirth = findViewById(R.id.petsbirth);
        Spinner variety = findViewById(R.id.variety);
        int idsex = variety.getSelectedItemPosition();
        String[] Spinner_repeat = getResources().getStringArray(R.array.Spinner_variety);
        String breed = Spinner_repeat[idsex];
        final EditText edlikes = findViewById(R.id.likes);
        final EditText ednotes = findViewById(R.id.notes);
        String petsbirth = edpetsbirth.getText().toString();
        final String petsname = edpetsname.getText().toString();
        String petslikes = edlikes.getText().toString();
        String petsnotes = ednotes.getText().toString();
        String petsgender = null;

        RadioGroup sexselect = findViewById(R.id.sexselect);
        switch(sexselect.getCheckedRadioButtonId()){
            case R.id.malepet: //case mRadioButton0.getId():
                petsgender = "男孩";
                break;
            case R.id.femalepet: //case mRadioButton1.getId():
                petsgender = "女孩";
                break;
        }
        Calendar now = Calendar.getInstance();
        Date nowdate =  now.getTime();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("petName", petsname);
        userInfo.put("petGender", petsgender);
        userInfo.put("petBirth", petsbirth);
        userInfo.put("breed", breed);
        userInfo.put("petHobby", petslikes);
        userInfo.put("petNote", petsnotes);
        userInfo.put("petImage", uri);
        userInfo.put("uid", userUID);
        userInfo.put("timestamp", nowdate);
        userInfo.put("petBirth_year", petBirth_year);
        userInfo.put("petBirth_month", petBirth_month);
        userInfo.put("petBirth_date",petBirth_date);
        db.collection("users").document(userUID).collection("pets").document(petid).set(userInfo);

        Map<String, Object> countdowndate = new HashMap<>();
        countdowndate.put("startDay", "");
        countdowndate.put("endDay", "");
        countdowndate.put("countdownEvent",petsname+"洗澡");
        String countdownid = db.collection("users").document(userUID).collection("pets").document(petid).collection("countdown").document().getId();
        db.collection("users").document(userUID).collection("pets").document(petid).collection("countdown").document(countdownid).set(countdowndate);
        AlertDialog.Builder finishsignup = new AlertDialog.Builder(petInfoActivity.this);
        finishsignup.setMessage("新增成功");
        finishsignup.setNegativeButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(petInfoActivity.this, homeActivity.class);
                startActivity(intent);
            }
        });
        finishsignup.setCancelable(false);
        finishsignup.show();
    }

    private void getPermission() {
        List<String> permissionList = new ArrayList<>();

        // 判断有無權限,如果沒有就加入列表
        if (ContextCompat.checkSelfPermission(petInfoActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(petInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(petInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        // 列表為空及權限都有了
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(petInfoActivity.this,
                    permissionList.toArray(new String[permissionList.size()]), 1002);
        }
    }

    //拍照完畢或選取圖片後呼叫此函式
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //讀取手機解析度
        mPhone = new DisplayMetrics();
        petInfoActivity.this.getWindowManager().getDefaultDisplay().getMetrics(mPhone);
        if (requestCode == PHOTO && data != null) {
            //取得照片路徑uri
            Uri uri = data.getData();
            ContentResolver cr = petInfoActivity.this.getContentResolver();
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
        img = findViewById(R.id.img);
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
}
