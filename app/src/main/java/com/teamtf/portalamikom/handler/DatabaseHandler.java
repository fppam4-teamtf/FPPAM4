package com.teamtf.portalamikom.handler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.teamtf.portalamikom.model.NewsList;
import com.teamtf.portalamikom.model.News;
import com.teamtf.portalamikom.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    private static final String NEWS_CATEGORY = "category";
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

        String CREATE_NEWS = "CREATE TABLE "+TABLE_NEWS+"("+NEWS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+NEWS_CATEGORY+" TEXT NOT NULL, "+NEWS_TITLE+" TEXT NOT NULL, "+NEWS_DATE+" DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, "+NEWS_CONTENT+" TEXT NOT NULL, "+NEWS_IMAGE+" TEXT NULL, "+NEWS_PUBLISHER+" TEXT NOT NULL, FOREIGN KEY("+NEWS_PUBLISHER+") REFERENCES "+TABLE_USER+"("+USER_ID+"))";
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

    public boolean cekId(String userid){
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_USER+" WHERE "+USER_ID+"=?",new String[]{userid});
        return cursor.getCount() <= 0;
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

    public boolean authUser(String userid, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_USER+" WHERE "+USER_ID+"=? AND "+USER_PASS+"=?", new String[]{userid,password});
        return cursor.getCount() > 0;
    }

    public User getUserData(String userid, String password){

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_USER+" WHERE "+USER_ID+"=? AND "+USER_PASS+"=?", new String[]{userid,password});

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;

        return new User(cursor.getString(0),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
    }

    public boolean addNews(String category, String title, String content, String image, String publisher){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NEWS_CATEGORY, category);
        cv.put(NEWS_TITLE, title);
        cv.put(NEWS_CONTENT, content);
        cv.put(NEWS_IMAGE, image);
        cv.put(NEWS_PUBLISHER, publisher);

        long ins = db.insert(TABLE_NEWS, null, cv);
        return ins != -1;
    }

    public ArrayList<NewsList> getNewsListData(String category){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+NEWS_ID+", "+NEWS_TITLE+", "+NEWS_DATE+" FROM "+TABLE_NEWS+" WHERE "+NEWS_CATEGORY+"=?",new String[]{category});
        ArrayList<NewsList> data = new ArrayList<NewsList>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
        if (cursor != null){
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(NEWS_ID));
                String title = cursor.getString(cursor.getColumnIndex(NEWS_TITLE));
                String dateDb = cursor.getString(cursor.getColumnIndex(NEWS_DATE));
                Date date = new Date();
                try {
                    date = format.parse(dateDb);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat getformat = new SimpleDateFormat("dd MMM yyyy, HH.mm",Locale.getDefault());
                String dateString = getformat.format(date);
                data.add(new NewsList(id,title,dateString));
            }
        }
        return data;
    }

    public News getNewsData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NEWS+" WHERE "+NEWS_ID+"=?",new String[]{String.valueOf(id)});
        if (cursor != null)
            cursor.moveToFirst();

        return new News(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
    }
}
