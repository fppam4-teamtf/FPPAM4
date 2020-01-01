package com.teamtf.portalamikom.handler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.teamtf.portalamikom.model.UserModel;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "amikom_portal_event.db";
    private static final int DB_Version = 1;

    private static final String TABLE_USER = "user";

    private static final String USER_ID = "id";
    private static final String USER_PASS = "password";
    private static final String USER_PRIVILAGES = "privilages";
    private static final String USER_NAME = "name";
    private static final String USER_GENDER = "gender";
    private static final String USER_ADDRESS = "address";

    private static final String TABLE_LOG_USER = "log_user";
    private static final String LOG_USER_ID = "id";
    private static final String LOG_USER_DATE = "date";
    private static final String LOG_USER_ACTIVITY = "activity";
    private static final String LOG_USER_USER = "user";

    private static final String TABLE_NEWS = "news";
    private static final String NEWS_ID = "id";
    private static final String NEWS_TITLE = "title";
    private static final String NEWS_DATE = "date";
    private static final String NEWS_CONTENT = "content";
    private static final String NEWS_IMAGE = "image";
    private static final String NEWS_PUBLISHER = "publisher";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER = "CREATE TABLE "+TABLE_USER+"("+USER_ID +" TEXT PRIMARY KEY NOT NULL, "+USER_PASS+" TEXT NOT NULL, "+USER_PRIVILAGES+" TEXT NOT NULL, "+USER_NAME+" TEXT NOT NULL, "+USER_GENDER+" TEXT NULL, "+USER_ADDRESS+" TEXT NULL)";
        Log.d("DB_Helper", "onCrreate: " + CREATE_USER);
        db.execSQL(CREATE_USER);

        String CREATE_LOG_USER = "CREATE TABLE "+TABLE_LOG_USER+"("+LOG_USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+LOG_USER_DATE+" DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, "+LOG_USER_ACTIVITY+" TEXT NOT NULL, "+LOG_USER_USER+" TEXT NOT NULL, FOREIGN KEY("+LOG_USER_USER+") REFERENCES "+TABLE_USER+"("+USER_ID+"))";
        Log.d("DB_Helper", "onCrreate: " + CREATE_LOG_USER);
        db.execSQL(CREATE_LOG_USER);

        String CREATE_NEWS = "CREATE TABLE "+TABLE_NEWS+"("+NEWS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+NEWS_TITLE+" TEXT NOT NULL, "+NEWS_DATE+" DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, "+NEWS_CONTENT+" TEXT NOT NULL, "+NEWS_IMAGE+" TEXT NULL, "+NEWS_PUBLISHER+" TEXT NOT NULL, FOREIGN KEY("+NEWS_PUBLISHER+") REFERENCES "+TABLE_USER+"("+USER_ID+"))";
        Log.d("DB_Helper", "onCrreate: " + CREATE_NEWS);
        db.execSQL(CREATE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LOG_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NEWS);
        onCreate(db);
    }

    public boolean addUser(String userid, String password, String privilages, String name, String gender, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_ID, userid);
        cv.put(USER_PASS, password);
        cv.put(USER_PRIVILAGES, privilages);
        cv.put(USER_NAME, name);
        cv.put(USER_GENDER, gender);
        cv.put(USER_ADDRESS, address);

        long ins = db.insert(TABLE_USER, null, cv);
        return ins != -1;
    }

    public boolean cekId(String userid){
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_USER+" WHERE "+USER_ID+"=?",new String[]{userid});
        return cursor.getCount() <= 0;
    }

    public boolean authUser(String userid, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_USER+" WHERE "+USER_ID+"=? AND "+USER_PASS+"=?", new String[]{userid,password});
        return cursor.getCount() > 0;
    }

    public UserModel getUserData(String userid, String password){

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_USER+" WHERE "+USER_ID+"=? AND "+USER_PASS+"=?", new String[]{userid,password});

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;

        return new UserModel(cursor.getString(0),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
    }
}
