package com.innovation.innovation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        public static final String COLUMN_NAME_CAPTION = "Hi I am Jath";
    }

    //Creates database entry with table columns and rows
    private static final String CREATE_ENTRY =
            "CREATE TABLE " + DataEntry.TABLE_NAME + " (" +
                    DataEntry._ID + " INTEGER PRIMARY KEY," +
                    DataEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    DataEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    DataEntry.COLUMN_NAME_IMAGE + TEXT_TYPE + COMMA_SEP +
                    DataEntry.COLUMN_NAME_CAPTION + TEXT_TYPE + COMMA_SEP +
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
        @Override
        public void onCreate(SQLiteDatabase db) {
            //db.execSQL(CREATE_ENTRY);
            //Makes gallery of image below
            db.execSQL("CREATE TABLE gallery (id varchar(20), image BLOB,caption varchar(160),description varchar(200))");
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

        /*
        //Adding information into database -> image is byte
        public void WriteInfoDatabase (String id, String title, String image, String caption) {

            SQLiteDatabase db = this.getWritableDatabase();

            //Assigning values, column names are the keys
            ContentValues values = new ContentValues();
            values.put(DataEntry.COLUMN_NAME_ENTRY_ID, id); //Columns ""
            values.put(DataEntry.COLUMN_NAME_TITLE, title);
            values.put(DataEntry.COLUMN_NAME_IMAGE, image);
            values.put(DataEntry.COLUMN_NAME_CAPTION, caption); //Fix

            long newRowId = db.insert(DataEntry.TABLE_NAME, null, values); //Insert row into database, returns primary key value
        }
         */

        public void ReadInfoDatabase () {

            SQLiteDatabase db = this.getReadableDatabase();

            /**
             * All variables used in query
             */

            //What to get
            //Defines a projection that specifies which columns are needed and will be used
            String[] projection = {
                    DataEntry._ID,
                    DataEntry.COLUMN_NAME_TITLE,
                    DataEntry.COLUMN_NAME_IMAGE,
                    DataEntry.COLUMN_NAME_CAPTION
            };

            // Filter results WHERE "title" = 'My Title' -> under what to get
            String selection = DataEntry.COLUMN_NAME_TITLE + " = ?";
            String[] selectionArgs = { "Jathavan" };
            // ^What the fuck does this do + if things are fucked up by Jathavan, add "My Title"

            //Order by descending order
            String sortOrder =
                    DataEntry.COLUMN_NAME_IMAGE + " DESC";

            //Cursor is a control structure that enables traversal over data records
            //DeForrest: It is a box that allows access shiz
            //Query is a set of instructions to describe what data to retrieve and the shape and organization of data
            //DeForrest: Like a manager, like get dis shit and put on desk
            Cursor c = db.query(
                    DataEntry.TABLE_NAME,                     // The table to query
                    projection,                               // The columns to return
                    selection,                                // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder                                 // The sort order
            );

            /* Use this code in order to move the Cursor, MUST call before reading values
            cursor.moveToFirst();
            long itemId = cursor.getLong(
                cursor.getColumnIndexOrThrow(FeedEntry._ID)
            );
             */

        }

        public void DeleteInfoDatabase () {

            SQLiteDatabase db = this.getWritableDatabase();

            //Under what to get -> LIKE is a pattern
            String selection = DataEntry.COLUMN_NAME_TITLE + " LIKE ?";

            // Specify arguments in placeholder order. -> where you delete from
            String[] selectionArgs = { "Jathavan" };

            // Issue SQL statement.
            db.delete(DataEntry.TABLE_NAME, selection, selectionArgs);
        }

        public void UpdateInfoDatabase(String title) {

            SQLiteDatabase db = this.getReadableDatabase();

            //Insert new values for one column
            ContentValues values = new ContentValues();
            values.put(DataEntry.COLUMN_NAME_TITLE, title);

            //Which row to update, based on title
            String selection = DataEntry.COLUMN_NAME_TITLE + " LIKE ?";
            String[] selectionArgs = { "Jathavan" };

            //Returns number of rows affected
            int count = db.update(
                    DataEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
            );
        }
    }
}