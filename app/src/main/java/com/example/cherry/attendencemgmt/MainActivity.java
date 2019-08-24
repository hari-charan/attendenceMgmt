package com.example.cherry.attendencemgmt;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*This piece will be executed only once, when user open app for first time*/
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("FIRSTRUN", true)){
            createDataBase();
            //This method is for testing purpose
            insertData(null, null);
        }
    }

    /**
     * This function takes parameter @param h which contains the roll no of present students
     **/
    private void insertData(ArrayList<String> h, String date) {

        /**Actual code**/
//        SQLiteStatement cst = db.compileStatement("insert into Attendence(Date) values(?)");
//        cst.bindString(1,date);
//        cst.execute();
//        for (String rollNo : h) {
//            cst = db.compileStatement("update Attendence set" + rollNo + "= 1 where date = ?");
//            cst.bindString(1, date);
//        }
        /**Testing code
         * */
        /*creates a compiled statement to guard form SQL injection*/
        SQLiteStatement cst = db.compileStatement("insert into Attendence(Date) values(?)");
        //Inserts some dates for testing purpose
        ArrayList<String> dates = new ArrayList<>();
        for(int i=0; i<15; ++i)
        {
            cst.bindString(1, String.valueOf(i) + "-02-2019");
            cst.execute();
            dates.add(String.valueOf(i) + "-02-2019");
        }

        // Creating a array list for testing purpose;
        h= new ArrayList<>();

        for(int i=0; i<30; i++)
        {
            if((i&1) == 1)
            {
                h.add("18025A05"+String.valueOf(i));
            }
        }
        for(String d : dates) {
            for (String rollNo : h) {
                cst = db.compileStatement("update Attendence set" + rollNo + " = 1 where date = ?");
                cst.bindString(1, d);
            }
        }
    }

    private void createDataBase()
    {
        DbHelp d = new DbHelp(this);
        db = d.getWritableDatabase();
    }

    public void printData(View view) {
        Cursor c = db.rawQuery("Select * from Attendence", null);
    }
}
