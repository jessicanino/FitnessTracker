package com.example.team22_fitnesstracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, "Database1", null, 1);
    }

    // public DatabaseHelper(@androidx.annotation.Nullable Context context, @androidx.annotation.Nullable String name, @androidx.annotation.Nullable SQLiteDatabase)

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateTable = "create table Table1(xValue INTEGER, yValue INTEGER)";
        db.execSQL(CreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertToData(long ValX, int ValY){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("xValue",ValX);
        contentValues.put("yValue",ValY);

        database.insert("Table1", null, contentValues);
    }


}