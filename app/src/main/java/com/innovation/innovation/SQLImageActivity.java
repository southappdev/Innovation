package com.innovation.innovation;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLClientInfoException;
import java.util.ArrayList;

/**
 * Created by KallenTu on 8/26/2016.
 */
public class SQLImageActivity extends ListActivity {
    private Button populate;
    private ArrayList id = new ArrayList();
    private ArrayList image = new ArrayList();
    private ArrayList caption = new ArrayList();
    private ArrayList description = new ArrayList();
    private static DatabaseHandler.DatabaseHelper dataHelp;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //Called when activity is created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populate = (Button) findViewById(R.id.populate);
        dataHelp = new DatabaseHandler.DatabaseHelper(this);

        SQLiteDatabase db = dataHelp.getWritableDatabase();


        Cursor cursors = getRawEvents("select * from gallery");

        //If something is returned
        if (cursors.moveToNext()) {
            populate.setVisibility(View.GONE);
            getDataAndPopulate();
        } else {
            populate.setVisibility(View.VISIBLE);
        }

        //What is this - if fucked up, uncomment cause dunno what this does
        //db.delete("gallery", "id=?", new String[]{"12"});

        populate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callInsertion("1", "http://2.bp.blogspot.com/-bkmnlZUKXPs/TjZeTVCgp9I/AAAAAAAAAHI/SPnWJYqq4uQ/s1600/twitter_follow.gif", "First", "This is the first item");
                callInsertion("2", "http://1.bp.blogspot.com/-HDNFnyRU2Cw/TcuMbBaL70I/AAAAAAAAAGc/7eWN1qnZbAw/s320/seek.JPG", "Second", "This is the second item");

                getDataAndPopulate();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void callInsertion(String id, String url, String caption, String description) {

        //Calls insertData and changes url to bytearray to use for that method

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        URL link = null;
        try {
            link = new URL(url);
            is = link.openStream ();
            byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
            int n;

            while ( (n = is.read(byteChunk)) > 0 ) {
                baos.write(byteChunk, 0, n);
            }

            insertData(id, baos.toByteArray(), caption, description);
        }
        catch (IOException e) {
            e.printStackTrace ();
            // Perform any other exception handling that's appropriate.
        }


    }


    private void insertData(String id, byte[] image, String caption, String description) {
        //Insert data into database
        SQLiteDatabase db = dataHelp.getWritableDatabase();
        ContentValues values;
        values = new ContentValues();
        values.put("id", id);
        values.put("image", image);
        values.put("caption", caption);
        values.put("description", description);

        db.insert("gallery", null, values);
    }

    private void getDataAndPopulate() {
        //Receiving all the input data in order to put in ArrayLists
        id = new ArrayList();
        image = new ArrayList();
        caption = new ArrayList();
        description = new ArrayList();
        Cursor cursor = getEvents("gallery");

        while (cursor.moveToNext()) {
            String temp_id = cursor.getString(0);
            byte[] temp_image = cursor.getBlob(1);
            String temp_caption = cursor.getString(2);
            String temp_description = cursor.getString(3);

            //Adding all data to ArrayLists
            id.add(temp_id);
            image.add(temp_image);
            caption.add(temp_caption);
            description.add(temp_description);
        }

        //Making array of characters of the caption
        String[] captionArray = (String[]) caption.toArray(new String[caption.size()]);

        //Creates new adapter
        ItemsAdapter itemsAdapter = new ItemsAdapter(
                SQLImageActivity.this, R.layout.activity_main, captionArray); //Was R.layout.item

        //ListAdapter specifies a layout resource for each row
        setListAdapter(itemsAdapter);
        populate.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SQLImage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.innovation.innovation/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SQLImage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.innovation.innovation/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class ItemsAdapter extends BaseAdapter {
        String[] items;

        //Constructor for ItemsAdapter that gets items from textview
        public ItemsAdapter(Context context, int textViewResourceId, String[] items) {
            this.items = items;
        }

        //This method will be called for every item in the ListView to create views with their properties set as we want.
        @Override
        public View getView(final int POSITION, View convertView, ViewGroup parent) {
            TextView desc;
            TextView cap;
            View view = convertView;
            ImageView img;

            if (view == null) {
                //LayoutInflater instantiates a layout XML file into its corresponding View objects.
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R.layout.activity_main, null); //Was R.layout.item
            }

            //Assigns views from the LayoutInflater to their proper view objects
            img = (ImageView) view.findViewById(R.id.image);
            cap = (TextView) view.findViewById(R.id.caption);
            desc = (TextView) view.findViewById(R.id.description);

            //cap.setText(caption.get(POSITION));
            //desc.setText(description.get(POSITION));

            int capPos = (int) caption.get(POSITION);
            int descPos = (int) description.get(POSITION);
            byte[] imagePos = (byte[]) image.get(POSITION);
            //Image must be bytearray to have length

            cap.setText(capPos);
            desc.setText(descPos);
            img.setImageBitmap(BitmapFactory.decodeByteArray(imagePos, 0, imagePos.length));

            return view;
        }

        public int getCount() {
            return items.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }
    }

    private Cursor getRawEvents(String sql) {
        SQLiteDatabase db = (dataHelp).getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        //startManagingCursor(cursor); //Find alternative
        return cursor;
    }

    private Cursor getEvents(String table) {
        SQLiteDatabase db = (dataHelp).getReadableDatabase();

        //Cursor box -> query = info that is retrieved from the database
        Cursor cursor = db.query(table, null, null, null, null, null, null);

        //startManagingCursor(cursor); //Find alternative

        return cursor;
    }


}
