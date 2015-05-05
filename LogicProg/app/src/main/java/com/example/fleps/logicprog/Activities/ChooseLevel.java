package com.example.fleps.logicprog.Activities;

import android.app.Activity;
import android.app.Application;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.fleps.logicprog.DBHelper;
import com.example.fleps.logicprog.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by Fleps_000 on 03.05.2015.
 */
public class ChooseLevel extends Activity {

    DBHelper dbHelper;
    private ArrayAdapter<String> mAdapter=null;
    final String LOG_TAG = "myLogs";
    String selection = null;
    String[] selectionArgs = null;
    String[] names = null;
    ListView lv;
    String[] items = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_level);
        lv = (ListView) findViewById(R.id.listViewChooseLevel);
        getData();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lv.setAdapter(mAdapter);
        final Intent intent = new Intent(this, PlayGame.class);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                intent.putExtra("type", getIntent().getStringExtra("type"));
                intent.putExtra("lvl", names[position]);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //dbHelper.onUpgrade(db,1,1);
        selection = "type = ?";
        selectionArgs = new String[] { getIntent().getStringExtra("type")};
        Cursor c = db.query("levels", new String[] {"type","name","status"}, selection , selectionArgs, null, null, null);
        items = new String[c.getCount()];
        names = new String[c.getCount()];
        if (c.moveToFirst()) {
            int typeColIndex = c.getColumnIndex("type");
            int nameColIndex = c.getColumnIndex("name");
            int statusColIndex = c.getColumnIndex("status");
            int i = 0;
            do {
                names[i] = c.getString(nameColIndex);
                items[i] = c.getString(nameColIndex) + " " + c.getString(typeColIndex) + " " + c.getString(statusColIndex);
                i++;
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        dbHelper.close();
    }
}
