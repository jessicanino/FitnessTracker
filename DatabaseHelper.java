package com.example.team22_fitnesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME="user_database";

    public static final String TABLE_NAME="user_table";
    public static final String Table_Column_ID="id";
    public static final String Table_Column_1_FName="fname";
    public static final String Table_Column_2_LName="lname";
    public static final String Table_Column_3_Age="age";
    public static final String Table_Column_4_Height="height";
    public static final String Table_Column_5_Weight="weight";
    public static final String Table_Column_6_Gender="gender";
    public static final String Table_Column_7_Ename="ename";
    public static final String Table_Column_8_Ephone="ephone";
    public static final String Table_Column_9_Email="email";
    public static final String Table_Column_10_Password="password";
    public static final String Table_Column_11_CPassword="cpassword";



    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase database){
        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+Table_Column_ID+" INTEGER PRIMARY KEY, "+Table_Column_1_FName+" VARCHAR, "+Table_Column_2_LName+" VARCHAR, "+Table_Column_3_Age+" VARCHAR, "+Table_Column_4_Height+" VARCHAR, "+Table_Column_5_Weight+" VARCHAR,  "+Table_Column_6_Gender+" VARCHAR, "+Table_Column_7_Ename+" VARCHAR, "+Table_Column_8_Ephone+" VARCHAR,"+Table_Column_9_Email+" VARCHAR,"+Table_Column_10_Password+" VARCHAR,"+Table_Column_11_CPassword+" VARCHAR )";
        database.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }


    public Boolean updatePassword(String email, String password) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("password",password);

        long result = sqLiteDatabase.update("user_table",contentValues, "email = ?", new String[] {email});
        if(result==-1)return false;
        else{
            return true;
        }

    }

    public Boolean updateProfile(String fname, String lname,String age, String height,String weight, String gender,String ename, String ephone, String email) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("fname",fname);
        contentValues.put("lname",lname);
        contentValues.put("age",age);
        contentValues.put("height",height);
        contentValues.put("weight",weight);
        contentValues.put("gender",gender);
        contentValues.put("ename",ename);
        contentValues.put("ephone",ephone);
        contentValues.put("email",email);


        long result = sqLiteDatabase.update("user_table",contentValues, "email = ?", new String[] {email});
        if(result==-1)return false;
        else{
            return true;
        }

    }
}
