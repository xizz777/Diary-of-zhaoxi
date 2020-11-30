package com.example.diaryofzhaoxi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    private  static DatabaseHelper mInstance =null;
    private static final String DATABASE_NAME = "DiaryManagement.db";
    private static final int DATABASE_VERSION = 1;
    public static final String CREATE_DIARY =  "create table Diary1 ("
            + "id integer primary key autoincrement ,"
            + "title text ,"
            + "content text,"
            + "picture text,"
            + "Date date,"
            + "author text)";

    private Context mContext;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_DIARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
    }
    static synchronized DatabaseHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new DatabaseHelper(context);
        }
        return mInstance;
    }


}
