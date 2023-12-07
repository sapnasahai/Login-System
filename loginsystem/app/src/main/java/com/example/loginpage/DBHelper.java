package com.example.loginpage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";
    public DBHelper( Context context ) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key,password TEXT)");


    }

    // int i = oldVersion
    // inti1 = newVersion
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
       // MyDB.execSQL("drop Table if exists users");

        MyDB.execSQL("CREATE TABLE IF NOT EXISTS temp_users (username TEXT PRIMARY KEY, password TEXT)");

        // Copy data from the old table to the temporary table
        MyDB.execSQL("INSERT INTO temp_users SELECT * FROM users");

        // Drop the old table
        MyDB.execSQL("DROP TABLE IF EXISTS users");

        // Rename the temporary table to the original table name
        MyDB.execSQL("ALTER TABLE temp_users RENAME TO users");



        onCreate(MyDB);

    }

    public Boolean insertData(String username,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password",password);
       // contentValues.put("student", userType);

        long result = MyDB.insert( "users",  null, contentValues);
        if (result==-1)
            return false;
        else
           return true;
    }

    public  Boolean checkUsername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery( "Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkUsernamePassword(String username,String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery( "Select * from users where username = ? and password = ?", new String[]{username,password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }


    /*
    public String getUserType(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery(
                "SELECT user_type FROM users WHERE username = ? AND password = ?",
                new String[]{username, password}
        );

        if (cursor.moveToFirst()) {
            int userTypeColumnIndex = cursor.getColumnIndex("user_type");

            // Check if the column exists in the result set
            if (userTypeColumnIndex != -1) {
                return cursor.getString(userTypeColumnIndex);
            } else {
                // Handle the case where the column is not found
                return null;
            }
        } else {
            // User not found
            return null;
        }
    }*/







}
