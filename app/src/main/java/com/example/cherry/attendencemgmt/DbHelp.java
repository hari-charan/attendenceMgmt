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
        db.execSQL("CREATE TABLE Attendence (date varchar(20))");
        for(int i=0; i<40; ++i)
        {
            db.execSQL("Alter table Attendence add column 18025A05" + String.valueOf(i) + "int(1)");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE tbl_teacher");
    }


}
