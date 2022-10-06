package com.example.noman.myvoicerecorder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.noman.myvoicerecorder.Utils.Helper;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by noman on 1/2/2018.
 */

public class DBHealper extends SQLiteAssetHelper {


    // All Static variables
    // Database Version
    // If you change the database file, you have to increase the version by one
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "applicationdb.db";
    public static final String TABLE_EMAILADDRESS = "emailAddress";
    public static final String TABLE_SUBJECT = "emailSubject";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_EMAILADDRESS = "email_address";
    public static final String COL_SUBJECT = "email_subject";
    public DBHealper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();

    }

    public boolean addemailAddressData(String name, String emailAddress){

        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_EMAILADDRESS,emailAddress);


        long result = db.insert("emailAddress", null, contentValues);
        db.close();

        return result > 0;
    }

    public boolean addemailSubjectData(String subject){

        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SUBJECT, subject);


        long result = db.insert("emailSubject", null, contentValues);
        db.close();
        if(result>0)
            return true;
        else
            return false;
    }

    public boolean updatePersonInfo(String name, String emailAddress, int id)
    {
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_EMAILADDRESS,emailAddress);
        int i =  db.update(TABLE_EMAILADDRESS, contentValues, COL_ID + "=" + id, null);
        return i > 0;
    }



    public boolean updateSubjectInfo(String subject, int id)
    {
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SUBJECT,subject);

        int i =  db.update(TABLE_SUBJECT, contentValues, COL_ID + "=" + id, null);
        return i > 0;
    }

    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE MMM dd yyyy hh:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String viewData(){

        SQLiteDatabase db =  getWritableDatabase();
        String selectquery = "SELECT * FROM "+TABLE_EMAILADDRESS+" ORDER BY " +COL_ID+ " ASC";
        Cursor cursor = db.rawQuery(selectquery,null);

        StringBuffer stringBuffer = new StringBuffer();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String emailAddress = cursor.getString(2);


            stringBuffer.append("Last Count:\n"+name+"\n"+emailAddress+"\n");
        }
        return stringBuffer.toString();
    }

    public void clearData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EMAILADDRESS + " WHERE " + COL_NAME + "='" + name + "'");
        db.close();
    }

    public ArrayList<emailData> getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<emailData> emailData= new ArrayList<emailData>();
        String selectquery = "SELECT * FROM "+TABLE_EMAILADDRESS+" ORDER BY " +COL_ID+ " ASC";
        Cursor result = db.rawQuery(selectquery , null);
        while(result.moveToNext()){
            emailData.add( new emailData(result.getString(result.getColumnIndex(COL_NAME)),
                    (result.getString(result.getColumnIndex(COL_EMAILADDRESS))),
                    result.getString(result.getColumnIndex(COL_ID))));

        }
        return emailData;
    }


    public ArrayList<emailSubjectData> getDataSubject() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<emailSubjectData> emailSubjectData= new ArrayList<emailSubjectData>();
        String selectquery = "SELECT * FROM "+TABLE_SUBJECT+" ORDER BY " +COL_ID+ " ASC";
        Cursor result = db.rawQuery(selectquery , null);
        while(result.moveToNext()){
            if(Helper.CURRENT_LANGUAGE.equalsIgnoreCase("English"))
                emailSubjectData.add( new emailSubjectData(result.getString(result.getColumnIndex(COL_ID)),result.getString(result.getColumnIndex(COL_SUBJECT))));
            else
                emailSubjectData.add( new emailSubjectData(result.getString(result.getColumnIndex(COL_ID)),result.getString(result.getColumnIndex("email_subject_lang"))));

        }
        return emailSubjectData;
    }


    public List<emailData> getAllContacts() {
        List<emailData> returnFilepath = new ArrayList<emailData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMAILADDRESS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        {
            if (cursor.moveToFirst()) {
                do {
                    emailData counter = new emailData();

                    counter.setName(cursor.getString(1));
                    returnFilepath.add(counter);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return returnFilepath;
    }
    public boolean CheckIsDataAlreadyInDBorNot(String fileName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_EMAILADDRESS + " where " + COL_NAME + " = " + fileName;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }




}
