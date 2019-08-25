package com.example.cherry.attendencemgmt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.preference.PreferenceManager;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    private WebView myWebView;
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
//        Cursor c = db.rawQuery("Select * from ")

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
//            cst = db.compileStatement("update Attendence set '" + rollNo + "' = 1 where date = ?");
//            cst.bindString(1, date);
//                cst.executeUpdateDelete();
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
            if(cst.executeInsert() == -1 )
                Log.d("Error", "not Inserted");
            dates.add(String.valueOf(i) + "-02-2019");
        }

        // Creating a array list for testing purpose;
        h= new ArrayList<>();

        for(int i=0; i<30; i++)
        {
            if((i&1) == 1)
            {
                String d = String.valueOf(i);
                if(d.length() < 2)
                {
                    d = "0"+d;
                }
                h.add("18025A05"+d);
            }
        }
        for(String d : dates) {
            for (String rollNo : h) {
                cst = db.compileStatement("update Attendence set '" + rollNo + "' = 1 where date = ?");
                cst.bindString(1, d);
                cst.executeUpdateDelete();
            }
        }
    }

    private void createDataBase()
    {
        DbHelp d = new DbHelp(this);
        db = d.getWritableDatabase();
    }

    public void printData(View view) {

        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view,
                                                    WebResourceRequest request)
            {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageFinished(WebView view, String url)
            {
                createWebPrintJob(view);
                myWebView = null;
            }
        });

        Cursor dbCursor = db.query("Attendence", null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        for(String i : columnNames)
        {
            Log.d("Columns", i);
        }
        dbCursor.close();
        Cursor c = db.rawQuery("Select * from Attendence", null);
        Log.d("Total Count:", String.valueOf(c.getCount()));


//        ArrayList<String> dates = new ArrayList<>();

        //TreeMap to preserve ordering
        TreeMap<String, ArrayList<String>> list = new TreeMap<>();
        //Store the keys first
        for (String x : columnNames)
        {
            list.put(x, new ArrayList<String>());
        }

        // for each row store it as a coulumn in list.
        while(c.moveToNext()) {
//            for (int i = 0; i < c.getColumnCount(); i++) {
////                Log.d("col", String.valueOf(c.getString(i)));
////            }
////
////            Log.d("Row switch", "Next row");
////            dates.add(c.getString(0));
            for(String x : columnNames) {
                list.get(x).add(c.getString(c.getColumnIndex(x)));
            }
        }
        String htmlCode = "<body>" +
                "<table>";
        htmlCode += "<tr> <td>" + "date"+"</td><td>";
        htmlCode += TextUtils.join("</td><td>",list.get("date").toString().replace("[", "").replace("]", "").split(","));
        htmlCode += "</td></tr>";
        list.remove("date");
        for(Map.Entry<String, ArrayList<String >> e: list.entrySet())
        {
            htmlCode += "<tr> <td>" + e.getKey()+"</td><td>";
            htmlCode += TextUtils.join("</td><td>",e.getValue().toString().replace("[", "").replace("]", "").split(","));
            htmlCode += "</td></tr>";
            Log.d("dummy",String.valueOf(e.getKey())+e.getValue().toString());
        }
        htmlCode += "</table></body>";
        TextView  v =(TextView) findViewById(R.id.textView);
        v.setText(htmlCode);
        Log.d("html", htmlCode);
        c.close();


        webView.loadDataWithBaseURL(null, htmlCode,
                "text/HTML", "UTF-8", null);

        myWebView = webView;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter("MyDocument");

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }
}
