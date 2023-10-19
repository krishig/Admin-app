package com.krishigadmin.android.Basics.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anil on 9/4/2021.
 */

public class DataManager extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "examoneOone";

    /**
     *
     * @param context //
     * @param name //
     * @param factory //
     * @param version //
     */
    public DataManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    /**
     *
     * @param db //
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        UserData.CreateTable(db);
    }

    /**
     *
     * @param db //
     * @param paramInt1 //
     * @param paramInt2 //
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int paramInt1, int paramInt2) {
        UserData.dropTable(db);
        onCreate(db);
    }
}