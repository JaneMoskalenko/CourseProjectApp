package com.example.admin.courseproject.Model.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper mInstance;

    private static final String DATABASE_NAME = "project.db";
    private static final int DATABASE_VERSION = 1;

   private final Context mContext;

    private DBHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static void init(Context mContext){
        mInstance = new DBHelper(mContext);
    }

    public static DBHelper getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("DataBase is not instantiated yet. " +
                    "Did you call instantiate() before getInstance()?");
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ImageDescription " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "description TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
