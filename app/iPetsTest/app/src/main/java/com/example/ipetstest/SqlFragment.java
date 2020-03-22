package com.example.ipetstest;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class SqlFragment extends Fragment {


    public SqlFragment() {
        // Required empty public constructor
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        openDatabase();   //開啟資料庫
        btnAdd = getView().findViewById(R.id.adddata);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sql, container, false);
    }
    private SqlData sqlData;
    private EditText editName;
    private EditText editTel;
    private EditText editEmail;
    private Button btnAdd;
    public static final String TABLE_NAME="user";
    public static final String NAME="name";
    public static final String TEL="tel";
    public static final String EMAIL="email";
    private void openDatabase(){
        sqlData=new SqlData(getActivity());   //取得DBHelper物件

    }
    private void closeDatabase(){
        sqlData.close();
    }
    public void onDestroy(){
        super.onDestroy();
        closeDatabase();     //關閉資料庫
    }
    private void add(){
        SQLiteDatabase db=sqlData.getWritableDatabase();  //透過dbHelper取得讀取資料庫的SQLiteDatabase物件，可用在新增、修改與刪除
        ContentValues values=new ContentValues();  //建立 ContentValues 物件並呼叫 put(key,value) 儲存欲新增的資料，key 為欄位名稱  value 為對應值。
        editName=getView().findViewById(R.id.edittext);
        editTel=getView().findViewById(R.id.edittext2);
        editEmail=getView().findViewById(R.id.edittext3);
        String tel =  editTel.getText().toString();
        Integer changeTel = Integer.parseInt(tel) ;
        values.put(NAME,editName.getText().toString());
        values.put(TEL,changeTel);
        values.put(EMAIL,editEmail.getText().toString());
        db.insert(TABLE_NAME,null,values);
        //cleanEditText();
        Toast.makeText(getActivity(),"資料已送出", LENGTH_SHORT).show();
    }
    private void cleanEditText(){
        editName.setText("");
        editTel.setText("");
        editEmail.setText("");
    }
}
