package com.example.ipetstest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import static android.provider.BaseColumns._ID;

public class SqlData  extends SQLiteOpenHelper {
    private final static String DATABASE_NAME="mytest.db";  //資料庫檔案名稱
    private final static int DATABASE_VERSION=1;   //資料庫版本
    public static final String TABLE_NAME="user";
    public static final String NAME="name";
    public static final String TEL="tel";
    public static final String EMAIL="email";
    public SqlData(FragmentActivity activity) {
        super(activity,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String INIT_TABLE="create table if not exists "+TABLE_NAME+"("+_ID+" integer primary key autoincrement,"+NAME+" string,"+TEL+" integer,"+EMAIL+" string)";
        sqLiteDatabase.execSQL(INIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String DROP_TABLE="drop table if exists "+TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE);
    }
}
