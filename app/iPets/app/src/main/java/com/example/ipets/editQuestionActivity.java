package com.example.ipets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class editQuestionActivity extends AppCompatActivity {

    Button btn_q1;
    Button btn_q2;
    Button btn_q3;
    Button btn_q4;
    Button btn_q5;
    Button btn_q6;
    Button btn_q7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        Toolbar toolbar7 = findViewById(R.id.toolbar7);
        setSupportActionBar(toolbar7);
        getSupportActionBar().setTitle("常見問題");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar7.setTitleTextColor(Color.WHITE);

        btn_q1 = findViewById(R.id.button_q1);
        btn_q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(editQuestionActivity.this)
                        .setTitle("BUG通知")
                        .setMessage("非常抱歉造成各位使用上的不便，iPets對於已確認狀況的問題和對策進行了更新，在APP版本無法讀取的狀態下，請試著使用網頁版本。")
                        .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                }).show();
            }
        });

        btn_q2 = findViewById(R.id.button_q2);
        btn_q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(editQuestionActivity.this)
                        .setTitle("錯誤403")
                        .setMessage("錯誤403表示未能連接至iPets伺服器。要解決這個問題，您可以重新登入或者重新安裝iPets應用程式。")
                        .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                }).show();
            }
        });

        btn_q3 = findViewById(R.id.button_q3);
        btn_q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(editQuestionActivity.this)
                        .setTitle("註冊帳號")
                        .setMessage("以電子郵件信箱註冊")
                        .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                }).show();
            }
        });

        btn_q4 = findViewById(R.id.button_q4);
        btn_q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(editQuestionActivity.this)
                        .setTitle("如何重新登入?")
                        .setMessage("請謹記登出前沒有建立一個iPets帳號，所有資料將會移師且不能復原。" +
                                "請按照以下步驟重新登入:" +
                                "1.選擇主目錄" +
                                "2.點按您的頭像" +
                                "3.點按登出" +
                                "4.輸入您的登入資料和密碼，然後登入")
                        .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                }).show();
            }
        });

        btn_q5 = findViewById(R.id.button_q5);
        btn_q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(editQuestionActivity.this)
                        .setTitle("定位無法啟用?")
                        .setMessage("請跳出定位畫面，再點按一次定位鈕即可使用。")
                        .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                }).show();
            }
        });

        btn_q6 = findViewById(R.id.button_q6);
        btn_q6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(editQuestionActivity.this)
                        .setTitle("能在無網路環境使用嗎?")
                        .setMessage("無網路環境下可以做的事" +
                                "1.在行事曆裡新增/編輯行程" +
                                "2.使用相機拍照" +
                                "3.瀏覽相簿照片" )
                        .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                }).show();
            }
        });

        btn_q7 = findViewById(R.id.button_q7);
        btn_q7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(editQuestionActivity.this)
                        .setTitle("iPets行事曆能和其他行事曆同步嗎?")
                        .setMessage("iPets行事曆無法跟Google/Apple/Outlook行事曆共用。")
                        .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                }).show();
            }
        });

    }



}
