package com.example.cherry.attendencemgmt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import android.database.sqlite.Co;

public class DbHelp extends SQLiteOpenHelper {
    DbHelp(Context c)
    {
        super(c, "classDB", null, 1);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbl_teacher (name VARCHAR(20), password VARCHAR(20));");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE tbl_teacher;");
    }

    public void clearDataBase()
    {
//        this.
    }

}
