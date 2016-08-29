package com.innovation.innovation;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.io.IOException;
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

    //Called when activity is created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populate = (Button) findViewById(R.id.populate); //Insert button that has id populate
        dataHelp = new DatabaseHandler.DatabaseHelper(this);

        SQLiteDatabase db = dataHelp.getWritableDatabase();


        Cursor cursors = getRawEvents("select * from gallery"); //Make function later in program

        //If something is returned
        if (cursors.moveToNext()) {
            populate.setVisibility(View.GONE);
            getDataAndPopulate();
        }
        else {
            populate.setVisibility(View.VISIBLE);
        }

        //What is this - if fucked up, uncomment cause dunno what this does
        //db.delete("gallery", "id=?", new String[]{"12"});

        populate.setOnClickListener(new View.onClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    callInsertion("1", "http://2.bp.blogspot.com/-bkmnlZUKXPs/TjZeTVCgp9I/AAAAAAAAAHI/SPnWJYqq4uQ/s1600/twitter_follow.gif", "First", "This is the first item");
                    callInsertion("2","http://1.bp.blogspot.com/-HDNFnyRU2Cw/TcuMbBaL70I/AAAAAAAAAGc/7eWN1qnZbAw/s320/seek.JPG","Second","This is the second item");
                }
                catch (Exception e){ //No ClientProtocalException
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                getDataAndPopulate();
            }
        });
    }

    private void callInsertion (String id, String url, String caption, String description) throws Exception, IOException{
        //Come back to this function
    }


    private void insertData (String id, byte[] image, String caption, String description) {
        //need to update db with new values
    }

    private void getDataAndPopulate () {
        //Receiving all the inputed data in order to put in ArrayLists
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
                SQLImageActivity.this, R.layout.item, captionArray); //Need to create id

        //ListAdapter specifies a layout resource for each row
        setListAdapter(itemsAdapter);
        populate.setVisibility(View.GONE);
    }

    private class ItemsAdapter extends BaseAdapter {
        String[] items;


    }

}
