package com.krishigadmin.android.Basics.Database;

import android.database.sqlite.SQLiteDatabase;


public class UserData {
    public static final String TABLE_NAME = "examoneOone";
    //    All Key
    public static final String KEY_ID = "_id";
    public static final String  KEY_StudentId = "studentId";
    public static final String Key_StudentName = "studentName";
    public static final String Key_StudentEmail = "studentEmail";
    public static final String Key_StudentMobileNo = "studentMobileNo";
    public static final String Key_CountryCode = "countryCode";
    public static final String Key_CountryName = "countryName";
    public static final String Key_StateName = "stateName";
    public static final String Key_FcmId = "fcmId";
    public static final String Key_MobileVerified= "mobileVerified";
    public static final String Key_EmailVerified = "emailVerified";
    public static final String Key_RoleType = "roleType";
    public static final String KEY_userPin = "userPin";
    public static final String KEY_profilePic = "profilePic";
    public static final String KEY_Token = "token";



    /**
     * Create Table Query
     *
     * @param db SQLiteDatabase
     */
    public static void CreateTable(SQLiteDatabase db) {
        String CreateTableQuery = "create table " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_StudentId + " text," +
                Key_StudentName + " text," +
                Key_StudentEmail + " text," +
                Key_StudentMobileNo + " text," +
                Key_CountryCode + " text," +
                Key_CountryName + " text," +
                Key_StateName + " text," +
                Key_FcmId + " text," +
                Key_MobileVerified + " text," +
                Key_EmailVerified + " text," +
                Key_RoleType + " text," +
                KEY_profilePic + " text," +
                KEY_Token + " text"+
                " )" ;
       // Utils.E("CreateTableQuery::" + CreateTableQuery);
        db.execSQL(CreateTableQuery);
    }

    /**
     * @param db SQLiteDatabase
     */
    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}