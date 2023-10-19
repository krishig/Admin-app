package com.krishigadmin.android.Basics.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Build;

import java.util.HashMap;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }

    // defining authority so that other application can access it
    static final String PROVIDER_NAME = "com.examoneOone.Credential";

    // defining content URI
    static final String URL = "content://" + PROVIDER_NAME + "/users";

    // parsing the content URI
   public static final Uri CONTENT_URI = Uri.parse(URL);

    static final String id = "id";
    public  static final String token = "token";
    static final int uriCode = 1;
    static final UriMatcher uriMatcher;
    private static HashMap<String, String> values;

    private static Context instance;
    static {

        // to match the content URI
        // every time user access table under content provider
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // to access whole table
        uriMatcher.addURI(PROVIDER_NAME, "users", uriCode);

        // to access a particular row
        // of the table
        uriMatcher.addURI(PROVIDER_NAME, "users/*", uriCode);
    }
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "vnd.android.cursor.dir/users";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
    // creating the database
    @Override
    public boolean onCreate() {
        instance = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(instance);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);
        qb.setStrict(true);
        switch (uriMatcher.match(uri)) {
            case uriCode:
                qb.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = id;
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs, null,
                null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    // adding data to the database
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(TABLE_NAME, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLiteException("Failed to add a record into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                    builder.setTables(TABLE_NAME);
                    builder.setProjectionMap(MyContentProvider.values);
                    builder.setStrict(true);
                    count = builder.update(db, values,selection, selectionArgs);
                }else {
                    count = db.update(TABLE_NAME, values, selection, selectionArgs);
                }
               // count = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case uriCode:
               // count = db.delete(TABLE_NAME, selection, selectionArgs);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                    builder.setTables(TABLE_NAME);
                    builder.setProjectionMap(values);
                    builder.setStrict(true);
                    count = builder.delete(db, selection, selectionArgs);
                }else {
                    count = db.delete(TABLE_NAME, selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);


        return count;
    }
    public static void deleteAll() {
        DatabaseHelper dbHelper = new DatabaseHelper(instance);
        dbHelper.getWritableDatabase().delete(TABLE_NAME, null, null);
        dbHelper.close();
    }
    // creating object of database
    // to perform query
    private SQLiteDatabase db;

    // declaring name of the database
    static final String DATABASE_NAME = "Credential";

    // declaring table name of the database
    static final String TABLE_NAME = "Users";

    // declaring version of the database
    static final int DATABASE_VERSION = 1;

    // sql query to create the table
    static final String CREATE_DB_TABLE = " CREATE TABLE " + TABLE_NAME
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " token TEXT NOT NULL);";

    // creating a database
    private static class DatabaseHelper extends SQLiteOpenHelper {

        // defining a constructor
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // creating a table in the database
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            // sql query to drop a table
            // having similar name
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}