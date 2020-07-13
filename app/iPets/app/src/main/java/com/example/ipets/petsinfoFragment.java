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
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class petsinfoFragment extends Fragment {
    private ImageView img;
    private DisplayMetrics mPhone;
    private final static int CAMERA = 1;
    private final static int PHOTO = 2;
    private static final String FILE_PATH = "/sdcard/ipets/jpeg";
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    String userUID = currentUser.getUid();
    public petsinfoFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_petsinfo, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getrequest_permissions();
        ImageButton camera = getView().findViewById(R.id.camera1);
        ImageButton photo = getView().findViewById(R.id.album);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.media.action.IMAGE_CAPTURE");
                File file = new File(FILE_PATH);
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri uri = FileProvider.getUriForFile(
                        getContext(),
                        getActivity().getPackageName() + ".provider",
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
        Button finish = getView().findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimage();
            }
        });
        final EditText edpetsbirth = getView().findViewById(R.id.petsbirth);
        edpetsbirth.setInputType(InputType.TYPE_NULL); //不顯示系統輸入鍵盤
        edpetsbirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            edpetsbirth.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                }
            }
        });

        edpetsbirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        edpetsbirth.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }

    private void uploadimage() {
        EditText edpetsname = getView().findViewById(R.id.petsname);
        final String petsname = edpetsname.getText().toString();
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://ipets-app.appspot.com");
        StorageReference mStorageRef = storage.getReference();
        StorageReference mountainsRef = mStorageRef.child(userUID +'/'+ petsname+".jpg");

        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        //取得照片網址
        String petsimage = String.valueOf(mountainsRef.getDownloadUrl());
        petsinfo(petsimage);

    }

    private void petsinfo(String petsimage) {
        EditText edpetsname = getView().findViewById(R.id.petsname);
        final EditText edpetsbirth = getView().findViewById(R.id.petsbirth);
        final EditText edvariety = getView().findViewById(R.id.variety);
        final EditText edlikes = getView().findViewById(R.id.likes);
        final EditText ednotes = getView().findViewById(R.id.notes);
        String petsbirth = edpetsbirth.getText().toString();
        final String petsname = edpetsname.getText().toString();
        String petsvariety = edvariety.getText().toString();
        String petslikes = edlikes.getText().toString();
        String petsnotes = ednotes.getText().toString();
        String petsgender = null;

        RadioGroup sexselect = getView().findViewById(R.id.sexselect);
        switch(sexselect.getCheckedRadioButtonId()){
            case R.id.malepet: //case mRadioButton0.getId():
                petsgender = "公的";
                break;
            case R.id.femalepet: //case mRadioButton1.getId():
                petsgender = "母的";
                break;
        }
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("Petsimage", petsimage);
        userInfo.put("Petsname", petsname);
        userInfo.put("Petsbirth", petsbirth);
        userInfo.put("Petsgender", petsgender);
        userInfo.put("Petsvariety", petsvariety);
        userInfo.put("Petslikes", petslikes);
        userInfo.put("Petsnotes", petsnotes);
        db.collection("userInformation").document(userUID).collection("pets").document(petsname).set(userInfo);
        AlertDialog.Builder finishsignup = new AlertDialog.Builder(getActivity());
        finishsignup.setMessage("註冊成功");
        finishsignup.setNegativeButton("前往登入", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                FirebaseAuth.getInstance().signOut();
                NavController controller = Navigation.findNavController(getView());
                controller.navigate(R.id.action_petsinfoFragment_to_loginFragment);
            }
        });
        finishsignup.show();
    }

    private void getrequest_permissions() {
        List<String> permissionList = new ArrayList<>();

        // 判断有無權限,如果沒有就加入列表
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        // 列表為空及權限都有了
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(),
                    permissionList.toArray(new String[permissionList.size()]), 1002);
        }
    }

    //拍照完畢或選取圖片後呼叫此函式
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //讀取手機解析度
        mPhone = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mPhone);
        if (requestCode == PHOTO && data != null) {
            //取得照片路徑uri
            Uri uri = data.getData();
            ContentResolver cr = this.getActivity().getContentResolver();
            try {
                //讀取照片，型態為Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
                if (bitmap.getWidth() > bitmap.getHeight()) ScalePic(bitmap,
                        mPhone.heightPixels);
                else ScalePic(bitmap, mPhone.widthPixels);
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
                if (photo.getWidth() > photo.getHeight()) ScalePic(photo,
                        mPhone.heightPixels);
                else ScalePic(photo, mPhone.widthPixels);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ScalePic(Bitmap bitmap, int phone) {
        //縮放比例預設為1
        float mScale = 1;
        img = getView().findViewById(R.id.img);
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
