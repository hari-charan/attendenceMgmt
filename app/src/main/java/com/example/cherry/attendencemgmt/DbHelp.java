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
            String d = String.valueOf(i);
            if(d.length() < 2)
            {
                d = "0"+d;
            }
            db.execSQL("Alter table Attendence add '18025A05" + d + "' INTEGER DEFAULT 0");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE tbl_teacher");
    }


}
