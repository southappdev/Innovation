package com.innovation.innovation;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by KallenTu on 8/26/2016.
 */
public class SQLImageActivity extends ListActivity {
    private Button populate;
    private static DatabaseHandler.DatabaseHelper dataHelp;

    //Called when activity is created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populate = (Button) findViewById(R.id.populate); //Insert button that has id populate
        dataHelp = new DatabaseHandler.DatabaseHelper(this);

        SQLiteDatabase db = dataHelp.getWritableDatabase();


        Cursor cursors = getRawEvents("select * from gallery"); //Make function later in program

    }

}
