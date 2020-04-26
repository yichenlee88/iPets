package com.example.ipetstest;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {
    private static final int CHOOSE_PHOTO =2 ;
    private static final int TAKE_PHOTO =1 ;
    TextView show_result;
    Button analysis, album;

    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show_result = getView().findViewById(R.id.textView);
        analysis = getView().findViewById(R.id.analysis);
        album = getView().findViewById(R.id.getphoto);

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });

        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 定義呼叫相機並取回圖片的Intent意圖
                startActivityForResult(intent, TAKE_PHOTO);
                //show_result.setText("這是哈士奇的機率有90%");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                ImageView ic = getView().findViewById(R.id.imageView);
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                File file = new File("/sdcard/pic/");
                file.mkdirs();// 建立資料夾
                String fileName = "/sdcard/pic/img.jpg";

                try {
                    FileOutputStream b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把資料寫入檔案,其中第一個引數表示圖片格式,
                    //第二個引數指壓縮率。100表示不壓縮
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        FileOutputStream b = new FileOutputStream(fileName);
                        b.flush();
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ic.setImageBitmap(bitmap);

            } else {
                finish(0);
            }

            if (requestCode == CHOOSE_PHOTO) {
                if (resultCode == Activity.RESULT_OK) {
                    ImageView ic = getView().findViewById(R.id.imageView);
                    Uri uri = data.getData();
                    Log.e("uri", uri.toString());
                    ContentResolver cr = getActivity().getContentResolver();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        /* 將Bitmap設定到ImageView */
                        ic.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.e("Exception", e.getMessage(), e);
                    }
                }
            }
        }
    }
        public void finish(int i) {
            finish(0);
        }
        private void openAlbum(){
            Intent intent=new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(intent,CHOOSE_PHOTO);  //開啟相簿
        }



}
