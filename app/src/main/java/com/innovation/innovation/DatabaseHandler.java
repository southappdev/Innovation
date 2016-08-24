package com.innovation.innovation;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by KallenTu on 8/24/2016.
 */

public class DatabaseHandler {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    // Empty constructor in case anyone instantiates
    public DatabaseHandler() {}

    // Defines table contents
    public static abstract class DataEntry implements BaseColumns {
        public static final String TABLE_NAME = "Jath sucks a little";
        public static final String COLUMN_NAME_ENTRY_ID = "Jath's ID";
        public static final String COLUMN_NAME_TITLE = "Jathavan";
        public static final String COLUMN_NAME_IMAGE = "Jath's Image";
    }

    //Creates database entry with table columns and rows
    private static final String CREATE_ENTRY =
            "CREATE TABLE " + DataEntry.TABLE_NAME + " (" +
                    DataEntry._ID + " INTEGER PRIMARY KEY," +
                    DataEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    DataEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    DataEntry.COLUMN_NAME_IMAGE + TEXT_TYPE + COMMA_SEP +
            " )";

    //Used to delete the database entry
    private static final String DELETE_ENTRY =
            "DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME;

    //Helper with methods for database creating and discarding
    public static class DatabaseHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Database.db";

        //Grabs context and information to make database object
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //Creates database
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_ENTRY);
        }

        //Discards database and create a new
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DELETE_ENTRY);
            onCreate(db);
        }

        //If version of database is older, replaces
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }


        //Adding information into database
        public void WriteDatabase (String id, String title, String image) {

            SQLiteDatabase db = this.getWritableDatabase();

            //Assigning values, column names are the keys
            ContentValues values = new ContentValues();
            values.put(DataEntry.COLUMN_NAME_ENTRY_ID, id);
            values.put(DataEntry.COLUMN_NAME_TITLE, title);
            values.put(DataEntry.COLUMN_NAME_IMAGE, image);
        }
    }
}
