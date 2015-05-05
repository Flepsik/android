package com.example.fleps.logicprog;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by Fleps_000 on 03.05.2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    private final Context context;
    final String LOG_TAG = "myLogs";
    final String CREATE_TABLE = "create table levels ("
            + "id integer primary key autoincrement,"
            + "type text,"
            + "name text,"
            + "tip text,"
            + "description text,"
            + "rightway text,"
            + "status boolean)";
    final String DROP_TABLE = "DROP TABLE IF EXISTS levels";

    public DBHelper(Context context) {
        super(context, "lvlDB", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d(LOG_TAG, "В креате");
        fillData(db);
    }

    private void fillData(SQLiteDatabase db) {
        if (db != null) {
            ContentValues values = new ContentValues();
            try {
                Resources resources = context.getResources();
                XmlPullParser parser = resources.getXml(R.xml.levels);
                while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() == XmlPullParser.START_TAG
                            && parser.getName().equals("level")) {
                        values.put("type", parser.getAttributeValue(0));
                        values.put("name", parser.getAttributeValue(1));
                        values.put("tip", parser.getAttributeValue(2));
                        values.put("description", parser.getAttributeValue(3));
                        values.put("rightway", parser.getAttributeValue(4));
                        if (parser.getAttributeValue(5).equals("true")) values.put("status", true);
                        else values.put("status", false);
                        Log.d(LOG_TAG, "--- Insert in mytable: ---" +
                                 values.get("type") + " " + values.get("name") + " " + values.get("tip") +
                                 values.get("description") + " " + values.get("rightway") + " " +
                                 values.get("status"));
                        long id = db.insert("levels", null, values);
                        Log.d(LOG_TAG, " id = " + id);
                    }
                    Log.d(LOG_TAG, "Вышли из итерации");
                    parser.next();
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
            Log.d(LOG_TAG, "Вышли из цикла");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
